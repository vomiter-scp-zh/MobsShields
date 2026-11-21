package com.vomiter.mobsshields.client.model;

import com.vomiter.mobsshields.MobsShields;
import com.vomiter.mobsshields.client.MobShieldAnimation;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public final class SquidShieldModel extends AbstractShieldModel {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(MobsShields.id("squid_shield"), "main");

    public SquidShieldModel(ModelPart root) {
        super(root, MobShieldAnimation.squid);
    }

    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition plate = partdefinition.addOrReplaceChild("plate", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -11.0F, -2.0F, 12.0F, 18.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tentacles1 = plate.addOrReplaceChild("tentacles1", CubeListBuilder.create().texOffs(16, 19).addBox(-6.0F, -9.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(16, 23).addBox(-6.0F, -5.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(26, 0).addBox(-6.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(26, 4).addBox(-6.0F, 3.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 0.0F, -1.75F));

		PartDefinition tentacles2 = plate.addOrReplaceChild("tentacles2", CubeListBuilder.create().texOffs(26, 8).addBox(-2.0F, -9.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(26, 12).addBox(-2.0F, -5.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(16, 27).addBox(-2.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 31).addBox(-2.0F, 3.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 0.0F, -1.75F));

		PartDefinition mouth = plate.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(20, 31).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -2.0F));

		PartDefinition handle = partdefinition.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(0, 19).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
