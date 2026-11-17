package com.vomiter.mobsshields.common;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public final class ShieldCounterattacks {
    private ShieldCounterattacks() {}

    public static ShieldCounterattack hoglin(boolean golden) {
        return context -> {
            LivingEntity holder = context.holder();
            LivingEntity attacker = context.attacker();
            if (golden && ShieldConfig.GOLDEN_DAMAGE.get() > 0) {
                DamageSource damage = new DamageSource(context.level().registryAccess()
                        .registryOrThrow(Registries.DAMAGE_TYPE)
                        .getHolderOrThrow(CounterattackHandler.COUNTERATTACK), holder);
                attacker.hurt(damage, ShieldConfig.GOLDEN_DAMAGE.get().floatValue());
            }
            Vec3 away = awayFrom(holder, attacker);
            // LivingEntity.knockback subtracts the supplied direction from its velocity.
            attacker.knockback(golden ? ShieldConfig.GOLDEN_KNOCKBACK.get() : ShieldConfig.HOGLIN_KNOCKBACK.get(),
                    -away.x, -away.z);
            attacker.hurtMarked = true;
            context.level().playSound(null, holder.blockPosition(), SoundEvents.HOGLIN_ATTACK,
                    holder.getSoundSource(), golden ? 0.75F : 0.5F, golden ? 0.8F : 1.1F);
        };
    }

    public static void squid(CounterattackContext context) {
        LivingEntity holder = context.holder();
        LivingEntity attacker = context.attacker();
        int duration = ShieldConfig.SQUID_DARKNESS_TICKS.get();
        if (duration > 0) {
            attacker.addEffect(new MobEffectInstance(MobEffects.DARKNESS, duration, 0), holder);
        }
        Vec3 direction = attacker.getEyePosition().subtract(holder.getEyePosition()).normalize();
        Vec3 ink = holder.getEyePosition().add(direction.scale(0.6));
        context.level().sendParticles(ParticleTypes.SQUID_INK, ink.x, ink.y, ink.z,
                24, 0.25, 0.25, 0.25, 0.08);
        context.level().playSound(null, holder.blockPosition(), SoundEvents.SQUID_SQUIRT,
                holder.getSoundSource(), 0.8F, 1.0F);
        if (holder.isInWater() && ShieldConfig.SQUID_RETREAT_SPEED.get() > 0) {
            Vec3 away = awayFrom(attacker, holder);
            // Set horizontal retreat speed rather than accumulating impulses every block.
            double speed = ShieldConfig.SQUID_RETREAT_SPEED.get();
            holder.setDeltaMovement(away.x * speed, holder.getDeltaMovement().y, away.z * speed);
            holder.hurtMarked = true;
            if (holder instanceof ServerPlayer player) {
                player.connection.send(new ClientboundSetEntityMotionPacket(holder));
            }
        }
    }

    /** Horizontal direction from origin to target; uses the holder's facing if coincident. */
    private static Vec3 awayFrom(LivingEntity origin, LivingEntity target) {
        Vec3 away = new Vec3(target.getX() - origin.getX(), 0, target.getZ() - origin.getZ());
        if (away.lengthSqr() < 1.0E-6) {
            Vec3 facing = origin.getLookAngle();
            away = new Vec3(facing.x, 0, facing.z);
            if (away.lengthSqr() < 1.0E-6) away = new Vec3(0, 0, 1);
        }
        return away.normalize();
    }
}
