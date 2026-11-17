package com.vomiter.mobsshields.client.model;

import com.vomiter.mobsshields.MobsShields;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

/** Static C-variant: widened vanilla plate, flat skull and monochrome handle. */
public final class WitherRibShieldModel extends AbstractShieldModel {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(MobsShields.id("wither_rib_shield"), "main");

    public WitherRibShieldModel(ModelPart root) { super(root); }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition plate = root.addOrReplaceChild("plate", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-8, -11, -2, 16, 22, 1), PartPose.ZERO);
        plate.addOrReplaceChild("skull", CubeListBuilder.create()
                .texOffs(0, 24).addBox(-4, -4, -4, 8, 8, 4), PartPose.offset(0, 0, -2));
        root.addOrReplaceChild("handle", CubeListBuilder.create()
                .texOffs(34, 0).addBox(-1, -3, 0, 2, 6, 6), PartPose.ZERO);
        return LayerDefinition.create(mesh, 64, 64);
    }
}
