package com.vomiter.mobsshields.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.BooleanSupplier;

/** Server-only reinforcement behavior and the lifetime of summoned zombies. */
public final class RottenFleshReinforcements {
    private static final String SUMMON_TAG = "mobsshields_rotten_reinforcement";
    private static final String TRUCE_EXPIRY_TAG = "mobsshields_zombie_truce_expires_at";
    private static final double OWNER_CHECK_RADIUS = 32;
    private static final Deque<PendingCommand> PENDING_COMMANDS = new ArrayDeque<>();

    private RottenFleshReinforcements() {}

    public static void summon(CounterattackContext context) {
        ServerLevel level = context.level();
        LivingEntity holder = context.holder();

        if (isSummoned(holder) || level.getDifficulty() == Difficulty.PEACEFUL
                || holder.getRandom().nextDouble() >= ShieldConfig.ROTTEN_SUMMON_CHANCE.get()) return;
        int nearby = level.getEntitiesOfClass(Zombie.class, holder.getBoundingBox().inflate(OWNER_CHECK_RADIUS),
                zombie -> zombie.isAlive() && isOwnedBy(zombie, holder)).size();
        if (nearby >= ShieldConfig.ROTTEN_MAX_NEARBY.get()) return;

        Zombie zombie = EntityType.ZOMBIE.create(level);
        if (zombie == null) return;
        if (!findSpawnPosition(level, holder, zombie)) {
            zombie.discard();
            return;
        }
        ForgeEventFactory.onFinalizeSpawn(zombie, level, level.getCurrentDifficultyAt(zombie.blockPosition()),
                MobSpawnType.MOB_SUMMONED, new Zombie.ZombieGroupData(false, false), null);
        zombie.setBaby(false);
        zombie.setCanPickUpLoot(false);
        zombie.setPersistenceRequired();
        var reinforcementChance = zombie.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
        if (reinforcementChance != null) reinforcementChance.setBaseValue(0);
        CompoundTag state = new CompoundTag();
        state.putUUID("owner", holder.getUUID());
        int lifetime = ShieldConfig.ROTTEN_LIFETIME_TICKS.get();
        long expiresAt = ReinforcementOrders.now(level.getServer()) + lifetime;
        state.putLong("expiresAt", expiresAt);
        rememberTarget(state, context.attacker());
        zombie.getPersistentData().put(SUMMON_TAG, state);
        clearTargetSelector(zombie);
        if (!level.addFreshEntity(zombie)) {
            zombie.discard();
            return;
        }

        if (holder instanceof Player) {
            holder.getPersistentData().putLong(TRUCE_EXPIRY_TAG,
                    Math.max(expiresAt, holder.getPersistentData().getLong(TRUCE_EXPIRY_TAG)));
            holder.addEffect(new MobEffectInstance(ShieldEffects.ZOMBIE_TRUCE.get(),
                    lifetime));
        }
        // Persist the latest owner order too, so that unloaded summons receive it when they return.
        ReinforcementOrders.get(level.getServer()).command(holder.getUUID(), context.attacker(), expiresAt);
        commandLoadedSummons(holder, context.attacker());
        level.sendParticles(ParticleTypes.POOF, zombie.getX(), zombie.getY() + 0.8, zombie.getZ(),
                12, 0.3, 0.5, 0.3, 0.02);
        level.playSound(null, zombie.blockPosition(), SoundEvents.ZOMBIE_AMBIENT,
                SoundSource.HOSTILE, 0.8F, 0.8F);
    }

    private static boolean findSpawnPosition(ServerLevel level, LivingEntity holder, Zombie zombie) {
        BlockPos center = holder.blockPosition();
        for (int attempt = 0; attempt < 16; attempt++) {
            double angle = holder.getRandom().nextDouble() * Math.PI * 2;
            double radius = 2 + holder.getRandom().nextDouble() * 2;
            int x = (int) Math.floor(holder.getX() + Math.cos(angle) * radius);
            int z = (int) Math.floor(holder.getZ() + Math.sin(angle) * radius);
            for (int dy = 2; dy >= -3; dy--) {
                BlockPos pos = new BlockPos(x, center.getY() + dy, z);
                if (!level.hasChunkAt(pos) || !level.getWorldBorder().isWithinBounds(pos)
                        || !level.getFluidState(pos).isEmpty() || !level.getFluidState(pos.above()).isEmpty()
                        || !level.getBlockState(pos.below()).isValidSpawn(level, pos.below(), EntityType.ZOMBIE)) continue;
                zombie.moveTo(x + 0.5, pos.getY(), z + 0.5, holder.getYRot(), 0);
                if (level.noCollision(zombie) && level.isUnobstructed(zombie)) return true;
            }
        }
        return false;
    }

    private static void clearTargetSelector(Zombie zombie) {
        zombie.targetSelector.getAvailableGoals().forEach(WrappedGoal::stop);
        zombie.targetSelector.removeAllGoals(goal -> true);
        zombie.setLastHurtByMob(null);
    }

    private static void rememberTarget(CompoundTag state, LivingEntity attacker) {
        state.putUUID("target", attacker.getUUID());
        state.putString("targetDimension", attacker.level().dimension().location().toString());
    }

    private static LivingEntity resolveTarget(ServerLevel level, CompoundTag state) {
        if (!state.hasUUID("target") || !level.dimension().location().toString()
                .equals(state.getString("targetDimension"))) return null;
        Entity target = level.getEntity(state.getUUID("target"));
        return target instanceof LivingEntity living && living.isAlive() ? living : null;
    }

    private static void commandLoadedSummons(LivingEntity holder, LivingEntity attacker) {
        // Command every loaded reinforcement, regardless of the nearby summon cap or dimension.
        for (ServerLevel level : holder.getServer().getAllLevels()) {
            for (Entity entity : level.getAllEntities()) {
                if (entity instanceof Zombie zombie && zombie.isAlive() && isOwnedBy(zombie, holder)) {
                    rememberTarget(zombie.getPersistentData().getCompound(SUMMON_TAG), attacker);
                    zombie.getNavigation().stop();
                    zombie.setTarget(level == attacker.level() ? attacker : null);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onDamage(LivingDamageEvent event) {
        queueCommand(event.getEntity(), event.getSource(),
                () -> !event.isCanceled() && event.getAmount() > 0);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onShieldBlock(ShieldBlockEvent event) {
        if (event.getEntity().getUseItem().getItem() instanceof RottenFleshShieldItem) {
            queueCommand(event.getEntity(), event.getDamageSource(),
                    () -> !event.isCanceled() && event.getBlockedDamage() > 0);
        }
    }

    private static void queueCommand(LivingEntity holder, DamageSource source, BooleanSupplier accepted) {
        if (!holder.level().isClientSide() && holder.hasEffect(ShieldEffects.ZOMBIE_TRUCE.get())
                && source.getEntity() instanceof LivingEntity attacker && attacker != holder) {
            PENDING_COMMANDS.addLast(new PendingCommand(holder, attacker, accepted));
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        // Wait until later listeners have finalized damage/block cancellation and amounts.
        while (!PENDING_COMMANDS.isEmpty()) {
            PendingCommand pending = PENDING_COMMANDS.removeFirst();
            LivingEntity holder = pending.holder();
            LivingEntity attacker = pending.attacker();
            if (!pending.accepted().getAsBoolean() || !attacker.isAlive()
                    || holder.level() != attacker.level()) continue;
            ReinforcementOrders orders = ReinforcementOrders.get(event.getServer());
            var truce = holder.getEffect(ShieldEffects.ZOMBIE_TRUCE.get());
            long validUntil = ReinforcementOrders.now(event.getServer()) + (truce == null ? 1 : truce.getDuration());
            orders.command(holder.getUUID(), attacker, validUntil);
            commandLoadedSummons(holder, attacker);
        }
        if (event.getServer().getTickCount() % 200 == 0) {
            ReinforcementOrders.get(event.getServer()).prune(ReinforcementOrders.now(event.getServer()));
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onJoinLevel(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide() && event.getEntity() instanceof Zombie zombie && isSummoned(zombie)) {
            // Only alter goals here; world/entity lookups wait until the first tick to avoid chunk-load deadlocks.
            clearTargetSelector(zombie);
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        var truce = player.getEffect(ShieldEffects.ZOMBIE_TRUCE.get());
        if (truce == null || !player.getPersistentData().contains(TRUCE_EXPIRY_TAG)) return;
        long remaining = player.getPersistentData().getLong(TRUCE_EXPIRY_TAG)
                - ReinforcementOrders.now(player.getServer());
        if (remaining < truce.getDuration()) {
            player.removeEffect(ShieldEffects.ZOMBIE_TRUCE.get());
            if (remaining > 0) player.addEffect(new MobEffectInstance(ShieldEffects.ZOMBIE_TRUCE.get(),
                    (int) remaining, truce.getAmplifier(), truce.isAmbient(), truce.isVisible(), truce.showIcon()));
        }
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) { PENDING_COMMANDS.clear(); }

    private record PendingCommand(LivingEntity holder, LivingEntity attacker, BooleanSupplier accepted) {}

    private static boolean isSummoned(LivingEntity entity) {
        return entity.getPersistentData().contains(SUMMON_TAG);
    }

    private static boolean isOwnedBy(Zombie zombie, LivingEntity holder) {
        CompoundTag state = zombie.getPersistentData().getCompound(SUMMON_TAG);
        return state.hasUUID("owner") && state.getUUID("owner").equals(holder.getUUID());
    }

    @SubscribeEvent
    public static void onZombieTick(LivingEvent.LivingTickEvent event) {
        if (event.getEntity().level().isClientSide() || !(event.getEntity() instanceof Zombie zombie)) return;
        if (!isSummoned(zombie)) return;
        CompoundTag state = zombie.getPersistentData().getCompound(SUMMON_TAG);
        ServerLevel level = (ServerLevel) zombie.level();
        long now = ReinforcementOrders.now(level.getServer());
        if (now >= state.getLong("expiresAt")) {
            zombie.discard();
            return;
        }
        if (state.hasUUID("owner")) {
            var order = ReinforcementOrders.get(level.getServer()).find(state.getUUID("owner"), now);
            if (order != null) {
                state.putUUID("target", order.target());
                state.putString("targetDimension", order.dimension());
            }
        }
        // Keep the assigned target even when unloaded temporarily; never pick an unrelated target.
        LivingEntity target = resolveTarget(level, state);
        if (zombie.getTarget() != target) {
            zombie.getNavigation().stop();
            zombie.setTarget(target);
        }
    }

    @SubscribeEvent
    public static void onSummonDrops(LivingDropsEvent event) {
        if (isSummoned(event.getEntity())) event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onSummonExperience(LivingExperienceDropEvent event) {
        if (isSummoned(event.getEntity())) event.setDroppedExperience(0);
    }
}
