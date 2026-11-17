package com.vomiter.mobsshields.client.renderer;

import com.vomiter.mobsshields.MobsShields;
import com.vomiter.mobsshields.client.model.SquidShieldModel;
import com.vomiter.mobsshields.common.SquidShieldItem;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public final class SquidShieldRenderer extends AbstractShieldRenderer<SquidShieldModel> {
    private static final ResourceLocation TEXTURE = MobsShields.id("textures/entity/shield/squid_shield.png");
    private static SquidShieldRenderer instance;

    private SquidShieldRenderer() {}

    public static SquidShieldRenderer getInstance() {
        if (instance == null) instance = new SquidShieldRenderer();
        return instance;
    }

    @Override
    protected boolean supports(ItemStack stack) { return stack.getItem() instanceof SquidShieldItem; }

    @Override
    protected SquidShieldModel bakeModel(Minecraft minecraft) {
        return new SquidShieldModel(minecraft.getEntityModels().bakeLayer(SquidShieldModel.LAYER));
    }

    @Override
    protected ResourceLocation texture(ItemStack stack) { return TEXTURE; }
}
