package com.vomiter.mobsshields.client.model;

import com.vomiter.mobsshields.MobsShields;
import com.vomiter.mobsshields.client.HoglinTuskShieldAnimation;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public final class HoglinShieldModel extends AbstractShieldModel {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(MobsShields.id("hoglin_shield"), "main");

    public HoglinShieldModel(ModelPart root) {
        super(root, HoglinTuskShieldAnimation.blocking);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();

        PartDefinition plate = root.addOrReplaceChild(
                "plate",
                CubeListBuilder.create()
                        .texOffs(0, 11)
                        .addBox(
                                -6.0F, -11.0F, -2.0F,
                                12.0F, 18.0F, 1.0F,
                                new CubeDeformation(0.0F)
                        ),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        plate.addOrReplaceChild(
                "appendix",
                CubeListBuilder.create()
                        .texOffs(0, 30)
                        .addBox(
                                -4.0F, -3.0F, 0.25F,
                                8.0F, 6.0F, 2.0F,
                                new CubeDeformation(0.0F)
                        ),
                PartPose.offset(0.0F, -2.0F, -4.0F)
        );

        plate.addOrReplaceChild(
                "tusk",
                CubeListBuilder.create()
                        .texOffs(26, 11)
                        .addBox(
                                -5.0F, 2.0F, -9.0F,
                                2.0F, 2.0F, 11.0F,
                                new CubeDeformation(0.0F)
                        )
                        .texOffs(26, 24)
                        .addBox(
                                3.0F, 2.0F, -9.0F,
                                2.0F, 2.0F, 11.0F,
                                new CubeDeformation(0.0F)
                        )
                        .texOffs(26, 11)
                        .addBox(
                                -5.5F, 1.5F, 1.5F,
                                3.0F, 3.0F, 2.0F,
                                new CubeDeformation(0.0F)
                        )
                        .texOffs(26, 11)
                        .addBox(
                                2.5F, 1.5F, 1.5F,
                                3.0F, 3.0F, 2.0F,
                                new CubeDeformation(0.0F)
                        ),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        plate.addOrReplaceChild(
                "mane",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(
                                -9.0F, -7.0F, -1.5F,
                                18.0F, 11.0F, 0.0F,
                                new CubeDeformation(0.0F)
                        ),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        root.addOrReplaceChild(
                "handle",
                CubeListBuilder.create()
                        .texOffs(20, 37)
                        .addBox(
                                -1.0F, -3.0F, -1.0F,
                                2.0F, 6.0F, 6.0F,
                                new CubeDeformation(0.0F)
                        ),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        return LayerDefinition.create(meshDefinition, 64, 64);
    }
}
