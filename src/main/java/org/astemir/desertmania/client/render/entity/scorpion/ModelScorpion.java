package org.astemir.desertmania.client.render.entity.scorpion;

import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.animation.SmoothnessType;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.client.render.cube.ModelElement;
import org.astemir.api.io.ResourceUtils;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.entity.EntityScorpion;

public class ModelScorpion extends SkillsAnimatedModel<EntityScorpion, IDisplayArgument> {

    public static ResourceLocation TEXTURE = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/scorpion.png");
    public static ResourceLocation MODEL = ResourceUtils.loadModel(DesertMania.MOD_ID,"entity/scorpion.geo.json");
    public static ResourceLocation ANIMATIONS = ResourceUtils.loadAnimation(DesertMania.MOD_ID,"entity/scorpion.animation.json");

    public ModelScorpion() {
        super(MODEL, ANIMATIONS);
        smoothnessType(SmoothnessType.EXPONENTIAL);
    }

    @Override
    public void customAnimate(EntityScorpion animated, IDisplayArgument argument,float limbSwing, float limbSwingAmount, float ticks, float delta, float headYaw, float headPitch) {
        ModelElement element = getModelElement("head");
        if (element != null){
            lookAt(element,headPitch,headYaw);
        }
    }

    @Override
    public ResourceLocation getTexture(EntityScorpion target) {
        return TEXTURE;
    }
}
