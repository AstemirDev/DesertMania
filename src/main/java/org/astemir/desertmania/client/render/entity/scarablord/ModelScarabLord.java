package org.astemir.desertmania.client.render.entity.scarablord;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.animation.SmoothnessType;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.client.render.cube.ModelElement;
import org.astemir.api.io.ResourceUtils;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.entity.scarablord.EntityScarabLord;


public class ModelScarabLord extends SkillsAnimatedModel<EntityScarabLord, IDisplayArgument> {

	public static ResourceLocation MODEL = ResourceUtils.loadModel(DesertMania.MOD_ID,"entity/scarab_lord.geo.json");
	public static ResourceLocation ANIMATIONS = ResourceUtils.loadAnimation(DesertMania.MOD_ID,"entity/scarab_lord.animation.json");
	public static ResourceLocation TEXTURE = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/scarab_lord/scarab_lord.png");


	public ModelScarabLord() {
		super(MODEL,ANIMATIONS);
		smoothnessType(SmoothnessType.EXPONENTIAL);
		smoothness(1.5f);
	}

	@Override
	public void renderModel(PoseStack stack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha, RenderCall renderCall, boolean resetBuffer) {
		if (getRenderTarget().tickCount > 5){
			float a = alpha;
			if (renderCall == RenderCall.MODEL) {
				stack.translate(0, -getRenderTarget().flyingOffset.value(Minecraft.getInstance().getPartialTick()) / 2f, 0);
			}
			super.renderModel(stack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, a, renderCall, resetBuffer);
		}
	}

	@Override
	public void customAnimate(EntityScarabLord animated, IDisplayArgument argument,float limbSwing, float limbSwingAmount, float ticks, float delta, float headYaw, float headPitch) {
		ModelElement head = getModelElement("head");
		ModelElement amulet = getModelElement("amulet");
		ModelElement sword7 = getModelElement("sword7");
		ModelElement sword8 = getModelElement("sword8");
		if (amulet != null && sword7 != null && sword8 != null) {
			if (animated.clientSideGhosting) {
				amulet.showModel = false;
				sword7.showModel = false;
				sword8.showModel = false;
			} else {
				amulet.showModel = true;
				sword7.showModel = true;
				sword8.showModel = true;
			}
		}
		if (head != null) {
			lookAt(head, headPitch, headYaw);
		}
	}

	@Override
	public ResourceLocation getTexture(EntityScarabLord target) {
		return TEXTURE;
	}
}