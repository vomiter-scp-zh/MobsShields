package com.vomiter.mobsshields;

import com.vomiter.mobsshields.common.CounterattackHandler;
import com.vomiter.mobsshields.common.ShieldConfig;
import com.vomiter.mobsshields.common.ShieldItems;
import com.vomiter.mobsshields.data.ModDataGenerators;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
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
        bus.addListener(ModDataGenerators::gather);
        bus.addListener(this::creativeTab);
        context.registerConfig(ModConfig.Type.COMMON, ShieldConfig.SPEC);
        MinecraftForge.EVENT_BUS.register(CounterattackHandler.class);
    }

    private void creativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ShieldItems.HOGLIN_SHIELD);
            event.accept(ShieldItems.GOLDEN_HOGLIN_SHIELD);
            event.accept(ShieldItems.SQUID_SHIELD);
        }
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
