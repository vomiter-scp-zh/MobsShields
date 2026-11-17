package com.vomiter.mobsshields.client;

import com.vomiter.mobsshields.MobsShields;
import com.vomiter.mobsshields.client.model.HoglinShieldModel;
import com.vomiter.mobsshields.client.model.WitherRibShieldModel;
import com.vomiter.mobsshields.client.renderer.WitherRibShieldRenderer;
import com.vomiter.mobsshields.client.model.SquidShieldModel;
import com.vomiter.mobsshields.client.renderer.HoglinShieldRenderer;
import com.vomiter.mobsshields.client.renderer.SquidShieldRenderer;
import com.vomiter.mobsshields.client.renderer.RottenFleshShieldRenderer;
import com.vomiter.mobsshields.common.ShieldItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MobsShields.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientEvents {
    private ClientEvents() {}

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HoglinShieldModel.LAYER, HoglinShieldModel::createBodyLayer);
        event.registerLayerDefinition(SquidShieldModel.LAYER, SquidShieldModel::createBodyLayer);
        event.registerLayerDefinition(WitherRibShieldModel.LAYER, WitherRibShieldModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(HoglinShieldRenderer.getInstance());
        event.registerReloadListener(SquidShieldRenderer.getInstance());
        event.registerReloadListener(RottenFleshShieldRenderer.getInstance());
        event.registerReloadListener(WitherRibShieldRenderer.getInstance());
    }

    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            blockingProperty(ShieldItems.HOGLIN_SHIELD.get());
            blockingProperty(ShieldItems.GOLDEN_HOGLIN_SHIELD.get());
            blockingProperty(ShieldItems.SQUID_SHIELD.get());
            blockingProperty(ShieldItems.ROTTEN_FLESH_SHIELD.get());
            blockingProperty(ShieldItems.WITHER_RIB_SHIELD.get());
        });
    }

    private static void blockingProperty(Item item) {
        ItemProperties.register(item, MobsShields.id("blocking"), (stack, level, entity, seed) ->
                entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
    }
}
