package com.vomiter.mobsshields.client.renderer;

import com.vomiter.mobsshields.MobsShields;
import com.vomiter.mobsshields.client.model.WitherRibShieldModel;
import com.vomiter.mobsshields.common.WitherRibShieldItem;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public final class WitherRibShieldRenderer extends AbstractShieldRenderer<WitherRibShieldModel> {
    private static final ResourceLocation TEXTURE = MobsShields.id("textures/entity/shield/wither_rib_shield.png");
    private static WitherRibShieldRenderer instance;

    private WitherRibShieldRenderer() {}

    public static WitherRibShieldRenderer getInstance() {
        if (instance == null) instance = new WitherRibShieldRenderer();
        return instance;
    }

    @Override
    protected boolean supports(ItemStack stack) { return stack.getItem() instanceof WitherRibShieldItem; }

    @Override
    protected WitherRibShieldModel bakeModel(Minecraft minecraft) {
        return new WitherRibShieldModel(minecraft.getEntityModels().bakeLayer(WitherRibShieldModel.LAYER));
    }

    @Override
    protected ResourceLocation texture(ItemStack stack) { return TEXTURE; }
}
