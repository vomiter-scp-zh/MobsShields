package com.vomiter.mobsshields.registry;

import com.vomiter.mobsshields.MobsShields;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ShieldEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MobsShields.MOD_ID);
    public static final RegistryObject<MobEffect> ZOMBIE_TRUCE =
            EFFECTS.register("zombie_truce", ZombieTruceEffect::new);

    private ShieldEffects() {}

    private static final class ZombieTruceEffect extends MobEffect {
        private ZombieTruceEffect() { super(MobEffectCategory.BENEFICIAL, 0x7C8650); }
    }
}
