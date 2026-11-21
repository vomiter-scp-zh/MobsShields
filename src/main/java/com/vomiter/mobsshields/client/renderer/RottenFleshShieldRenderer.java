package com.vomiter.mobsshields.client.renderer;

import com.vomiter.mobsshields.MobsShields;
import com.vomiter.mobsshields.client.model.RottenFleshShieldModel;
import com.vomiter.mobsshields.common.item.RottenFleshShieldItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public final class RottenFleshShieldRenderer extends AbstractShieldRenderer<RottenFleshShieldModel> {
    private static final ResourceLocation TEXTURE = MobsShields.id("textures/entity/shield/rotten_flesh_shield.png");
    private static RottenFleshShieldRenderer instance;

    private RottenFleshShieldRenderer() {}

    public static RottenFleshShieldRenderer getInstance() {
        if (instance == null) instance = new RottenFleshShieldRenderer();
        return instance;
    }

    @Override
    protected boolean supports(ItemStack stack) { return stack.getItem() instanceof RottenFleshShieldItem; }

    @Override
    protected RottenFleshShieldModel bakeModel(Minecraft minecraft) {
        return new RottenFleshShieldModel(minecraft.getEntityModels().bakeLayer(ModelLayers.SHIELD));
    }

    @Override
    protected ResourceLocation texture(ItemStack stack) { return TEXTURE; }
}
