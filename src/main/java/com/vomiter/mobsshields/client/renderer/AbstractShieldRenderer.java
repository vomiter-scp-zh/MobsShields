package com.vomiter.mobsshields.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.vomiter.mobsshields.client.model.AbstractShieldModel;
import com.vomiter.mobsshields.common.CounterattackHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractShieldRenderer<M extends AbstractShieldModel> extends BlockEntityWithoutLevelRenderer {
    private M model;

    protected AbstractShieldRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    protected abstract boolean supports(ItemStack stack);
    protected abstract M bakeModel(Minecraft minecraft);
    protected abstract ResourceLocation texture(ItemStack stack);

    @Override
    public final void onResourceManagerReload(ResourceManager manager) { model = null; }

    @Override
    public final void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack pose,
                                    MultiBufferSource buffers, int light, int overlay) {
        if (!supports(stack)) return;
        Minecraft minecraft = Minecraft.getInstance();
        if (model == null) model = bakeModel(minecraft);
        // Models are shared: reset even when this stack has no active animation.
        model.applyCounterattack(elapsedTicks(minecraft, stack));
        pose.pushPose();
        try {
            pose.scale(1, -1, -1);
            var vertices = ItemRenderer.getFoilBufferDirect(buffers, model.renderType(texture(stack)), false, stack.hasFoil());
            model.renderToBuffer(pose, vertices, light, overlay, 1, 1, 1, 1);
        } finally {
            pose.popPose();
        }
    }

    private static float elapsedTicks(Minecraft minecraft, ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (minecraft.level == null || tag == null) return -1;
        CompoundTag state = tag.getCompound(CounterattackHandler.ANIMATION_TAG);
        if (!state.contains("tick") || !minecraft.level.dimension().location().toString()
                .equals(state.getString("dimension"))) return -1;
        long elapsed = minecraft.level.getGameTime() - state.getLong("tick");
        return elapsed < 0 ? -1 : elapsed + minecraft.getFrameTime();
    }
}
