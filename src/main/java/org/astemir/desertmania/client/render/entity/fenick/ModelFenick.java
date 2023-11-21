package org.astemir.desertmania.client.render.entity.fenick;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.astemir.api.client.animation.SmoothnessType;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.client.render.cube.ModelElement;
import org.astemir.api.io.ResourceUtils;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.entity.fenick.EntityFenick;


public class ModelFenick extends SkillsAnimatedModel<EntityFenick, IDisplayArgument> {

	public static ResourceLocation MODEL = ResourceUtils.loadModel(DesertMania.MOD_ID,"entity/fenick/fenick.geo.json");
	public static ResourceLocation ANIMATIONS = ResourceUtils.loadAnimation(DesertMania.MOD_ID,"entity/fenick/fenick.animation.json");
	public static ResourceLocation TEXTURE_1 = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/fenick/fenick1.png");
	public static ResourceLocation TEXTURE_2 = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/fenick/fenick2.png");
	public static ResourceLocation TEXTURE_3 = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/fenick/fenick3.png");

	public static ResourceLocation TEXTURE_ANGRY_1 = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/fenick/fenick1_angry.png");
	public static ResourceLocation TEXTURE_ANGRY_2 = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/fenick/fenick2_angry.png");
	public static ResourceLocation TEXTURE_ANGRY_3 = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/fenick/fenick3_angry.png");



	public ModelFenick() {
		super(MODEL,ANIMATIONS);
		smoothnessType(SmoothnessType.EXPONENTIAL);
	}

	@Override
	public void renderModel(PoseStack stack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha, RenderCall renderCall, boolean resetBuffer) {
		super.renderModel(stack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha, renderCall, resetBuffer);
	}

	@Override
	public void customAnimate(EntityFenick animated, IDisplayArgument argument,float limbSwing, float limbSwingAmount, float ticks, float delta, float headYaw, float headPitch) {
		ModelElement head = getModelElement("head");
		if (head != null) {
			lookAt(head, headPitch, headYaw);
		}
	}

	@Override
	public void onRenderModelCube(ModelElement cube, PoseStack matrixStackIn, VertexConsumer bufferIn, RenderCall renderCall, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		if (renderCall == RenderCall.MODEL) {
			if (cube.getName().equals("arm1")) {
				matrixStackIn.pushPose();
				matrixStackIn.translate(-0.05f, 0, -0.5f);
				EntityFenick entity = getRenderTarget();
				ItemStack held = entity.getItemInHand(InteractionHand.MAIN_HAND);
				if (!held.isEmpty() && EntityFenick.MASKED.get(entity)) {
					renderItem(held, ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND, matrixStackIn, packedLightIn);
				}
				matrixStackIn.popPose();
				bufferIn = returnDefaultBuffer();
			}
		}
	}

	@Override
	public ResourceLocation getTexture(EntityFenick target) {
		boolean masked = EntityFenick.MASKED.get(target);
		switch (EntityFenick.SKIN.get(target)){
			case 0:{
				if (masked){
					return TEXTURE_ANGRY_1;
				}
				return TEXTURE_1;
			}
			case 1:{
				if (masked){
					return TEXTURE_ANGRY_2;
				}
				return TEXTURE_2;
			}
			case 2:{
				if (masked) {
					return TEXTURE_ANGRY_3;
				}
				return TEXTURE_3;
			}
		}
		return TEXTURE_1;
	}
}