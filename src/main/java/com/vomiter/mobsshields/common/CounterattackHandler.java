package com.vomiter.mobsshields.common;

import com.vomiter.mobsshields.MobsShields;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayDeque;
import java.util.Deque;

public final class CounterattackHandler {
    public static final String ANIMATION_TAG = "mobsshields_counterattack";
    private static final String COOLDOWN_TAG = "mobsshields_last_counterattack";
    public static final ResourceKey<DamageType> COUNTERATTACK = ResourceKey.create(
            Registries.DAMAGE_TYPE, MobsShields.id("hoglin_counterattack"));
    private static final Deque<PendingCounterattack> PENDING = new ArrayDeque<>();

    private CounterattackHandler() {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onShieldBlock(ShieldBlockEvent event) {
        LivingEntity holder = event.getEntity();
        if (holder.level().isClientSide() || event.getBlockedDamage() <= 0) return;
        DamageSource source = event.getDamageSource();
        // Counterattacks cannot start shield-to-shield recursion. Arrows/tridents do not count as melee.
        if (source.is(COUNTERATTACK) || !(source.getDirectEntity() instanceof LivingEntity attacker)
                || source.getEntity() != attacker || attacker == holder) return;
        ItemStack shield = holder.getUseItem();
        if (!(shield.getItem() instanceof AbstractMobShieldItem)) return;
        PENDING.addLast(new PendingCounterattack(event, holder, attacker, shield));
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        // The original hit and all other event listeners finish before we deal retaliation damage.
        while (!PENDING.isEmpty()) execute(PENDING.removeFirst());
    }

    private static void execute(PendingCounterattack pending) {
        ShieldBlockEvent event = pending.event();
        LivingEntity holder = pending.holder();
        LivingEntity attacker = pending.attacker();
        ItemStack shield = pending.shield();
        if (event.isCanceled() || event.getBlockedDamage() <= 0 || !holder.isAlive() || !attacker.isAlive()
                || holder.level() != attacker.level() || !(holder.level() instanceof ServerLevel level)
                || shield.isEmpty() || (holder.getMainHandItem() != shield && holder.getOffhandItem() != shield)) return;
        double reach = ShieldConfig.MAX_DISTANCE.get();
        if (holder.distanceToSqr(attacker) > reach * reach || !holder.hasLineOfSight(attacker)) return;
        long now = level.getGameTime();
        String dimension = level.dimension().location().toString();
        CompoundTag last = holder.getPersistentData().getCompound(COOLDOWN_TAG);
        if (dimension.equals(last.getString("dimension")) && last.contains("tick")
                && now - last.getLong("tick") >= 0 && now - last.getLong("tick") < ShieldConfig.COOLDOWN.get()) return;
        CompoundTag state = new CompoundTag();
        state.putLong("tick", now);
        state.putString("dimension", dimension);
        holder.getPersistentData().put(COOLDOWN_TAG, state.copy());
        // Standard equipment/container synchronization carries this to the holder and tracking players.
        shield.getOrCreateTag().put(ANIMATION_TAG, state);
        AbstractMobShieldItem item = (AbstractMobShieldItem) shield.getItem();
        item.counterattack(new CounterattackContext(level, holder, attacker, shield, event.getBlockedDamage()));
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) { PENDING.clear(); }

    private record PendingCounterattack(ShieldBlockEvent event, LivingEntity holder,
                                        LivingEntity attacker, ItemStack shield) {}
}
