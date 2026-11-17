package com.vomiter.mobsshields.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3f;

public abstract class AbstractShieldModel extends HierarchicalModel<LivingEntity> {
    private final ModelPart root;
    private final AnimationDefinition counterattackAnimation;
    private final Vector3f animationVector = new Vector3f();

    protected AbstractShieldModel(ModelPart root, AnimationDefinition counterattackAnimation) {
        super(RenderType::entityCutoutNoCull);
        this.root = root;
        this.counterattackAnimation = counterattackAnimation;
    }

    @Override
    public final ModelPart root() { return root; }

    public final void applyCounterattack(float elapsedTicks) {
        root.getAllParts().forEach(ModelPart::resetPose);
        if (!Float.isFinite(elapsedTicks) || elapsedTicks < 0
                || elapsedTicks >= counterattackAnimation.lengthInSeconds() * 20) return;
        KeyframeAnimations.animate(this, counterattackAnimation, (long) (elapsedTicks * 50), 1, animationVector);
    }

    @Override
    public final void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount,
                                float ageInTicks, float netHeadYaw, float headPitch) {
        // Item renderers supply elapsed counterattack time directly.
    }

    @Override
    public final void renderToBuffer(PoseStack pose, VertexConsumer vertices, int light, int overlay,
                                     float red, float green, float blue, float alpha) {
        root.render(pose, vertices, light, overlay, red, green, blue, alpha);
    }
}
