package com.vomiter.mobsshields.client.renderer;

import com.vomiter.mobsshields.MobsShields;
import com.vomiter.mobsshields.client.model.HoglinShieldModel;
import com.vomiter.mobsshields.common.HoglinShieldItem;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public final class HoglinShieldRenderer extends AbstractShieldRenderer<HoglinShieldModel> {
    private static final ResourceLocation NORMAL = MobsShields.id("textures/entity/shield/hoglin_shield.png");
    private static final ResourceLocation GOLDEN = MobsShields.id("textures/entity/shield/golden_hoglin_shield.png");
    private static HoglinShieldRenderer instance;

    private HoglinShieldRenderer() {}

    public static HoglinShieldRenderer getInstance() {
        if (instance == null) instance = new HoglinShieldRenderer();
        return instance;
    }

    @Override
    protected boolean supports(ItemStack stack) { return stack.getItem() instanceof HoglinShieldItem; }

    @Override
    protected HoglinShieldModel bakeModel(Minecraft minecraft) {
        return new HoglinShieldModel(minecraft.getEntityModels().bakeLayer(HoglinShieldModel.LAYER));
    }

    @Override
    protected ResourceLocation texture(ItemStack stack) {
        return ((HoglinShieldItem) stack.getItem()).isGolden() ? GOLDEN : NORMAL;
    }
}
