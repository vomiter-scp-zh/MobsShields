package com.vomiter.mobsshields;

import com.vomiter.mobsshields.client.ClientEvents;
import com.vomiter.mobsshields.common.counterattack.CounterattackHandler;
import com.vomiter.mobsshields.registry.ShieldEffects;
import com.vomiter.mobsshields.common.counterattack.reinforcement.RottenFleshReinforcements;
import com.vomiter.mobsshields.common.ShieldConfig;
import com.vomiter.mobsshields.data.loot.AddShieldLootModifier;
import com.vomiter.mobsshields.registry.ShieldItems;
import com.vomiter.mobsshields.data.ModDataGenerators;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraft.world.item.CreativeModeTabs;

@Mod(MobsShields.MOD_ID)
public final class MobsShields {
    public static final String MOD_ID = "mobsshields";

    public MobsShields(FMLJavaModLoadingContext context) {
        var bus = context.getModEventBus();
        ShieldItems.ITEMS.register(bus);
        ShieldEffects.EFFECTS.register(bus);
        AddShieldLootModifier.SERIALIZERS.register(bus);
        bus.addListener(ModDataGenerators::gather);
        bus.addListener(this::creativeTab);
        context.registerConfig(ModConfig.Type.SERVER, ShieldConfig.SPEC);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ClientEvents.register(bus);
        }
        RottenFleshReinforcements.init();
        CounterattackHandler.init();
    }

    private void creativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ShieldItems.HOGLIN_SHIELD);
            event.accept(ShieldItems.GOLDEN_HOGLIN_SHIELD);
            event.accept(ShieldItems.SQUID_SHIELD);
            event.accept(ShieldItems.ROTTEN_FLESH_SHIELD);
            event.accept(ShieldItems.WITHER_RIB_SHIELD);
        }
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
