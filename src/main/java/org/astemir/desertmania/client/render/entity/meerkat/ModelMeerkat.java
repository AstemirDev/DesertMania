package org.astemir.desertmania.client.render.entity.meerkat;

import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.animation.SmoothnessType;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.client.render.cube.ModelElement;
import org.astemir.api.io.ResourceUtils;
import org.astemir.api.math.components.Vector3;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.entity.EntityMeerkat;


public class ModelMeerkat extends SkillsAnimatedModel<EntityMeerkat, IDisplayArgument> {

	public final ResourceLocation TEXTURE = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/meerkat.png");
	public static ResourceLocation MODEL = ResourceUtils.loadModel(DesertMania.MOD_ID,"entity/meerkat.geo.json");
	public static ResourceLocation ANIMATIONS = ResourceUtils.loadAnimation(DesertMania.MOD_ID,"entity/meerkat.animation.json");

	public ModelMeerkat() {
		super(MODEL,ANIMATIONS);
		smoothnessType(SmoothnessType.EXPONENTIAL);
	}

	@Override
	public void customAnimate(EntityMeerkat animated, IDisplayArgument argument,float limbSwing, float limbSwingAmount, float ticks, float delta, float headYaw, float headPitch) {
		ModelElement head = getModelElement("head");
		if (head != null) {
			if (animated.isBaby()) {
				head.setScale(new Vector3(1.35f, 1.35f, 1.35f));
			}
			lookAt(head, headPitch, headYaw / 2f);
		}
	}


	@Override
	public ResourceLocation getTexture(EntityMeerkat target) {
		return TEXTURE;
	}
}