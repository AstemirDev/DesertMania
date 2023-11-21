package org.astemir.desertmania.client.render.entity.camel;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.animation.SmoothnessType;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.client.render.cube.ModelElement;
import org.astemir.api.io.ResourceUtils;
import org.astemir.api.math.components.Vector3;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.entity.camel.EntityCamel;


public class ModelCamel extends SkillsAnimatedModel<EntityCamel, IDisplayArgument> {

	public static ResourceLocation MODEL = ResourceUtils.loadModel(DesertMania.MOD_ID,"entity/camel.geo.json");
	public static ResourceLocation ANIMATIONS = ResourceUtils.loadAnimation(DesertMania.MOD_ID,"entity/camel.animation.json");
	public static ResourceLocation TEXTURE = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/camel/camel.png");
	public static ResourceLocation TEXTURE_ZOMBIE = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/camel/zombie.png");


	public ModelCamel() {
		super(MODEL,ANIMATIONS);
		smoothnessType(SmoothnessType.EXPONENTIAL);
	}

	@Override
	public void customAnimate(EntityCamel animated, IDisplayArgument argument,float limbSwing, float limbSwingAmount, float ticks, float delta, float headYaw, float headPitch) {
		ModelElement head = getModelElement("Head");
		if (head != null) {
			lookAt(head, headPitch, headYaw);
		}
	}

	@Override
	public void onRenderModelCube(ModelElement cube, PoseStack matrixStackIn, VertexConsumer bufferIn, RenderCall renderCall, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		if (cube.equals("Head")) {
			if (getRenderTarget().isBaby()) {
				cube.setScale(new Vector3(1.25f, 1.25f, 1.25f));
			}
		}
	}

	@Override
	public ResourceLocation getTexture(EntityCamel target) {
		if (EntityCamel.IS_ZOMBIE.get(target)) {
			return TEXTURE_ZOMBIE;
		}
		return TEXTURE;
	}
}