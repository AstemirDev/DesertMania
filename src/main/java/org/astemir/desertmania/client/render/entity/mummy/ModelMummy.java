package org.astemir.desertmania.client.render.entity.mummy;

import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.animation.SmoothnessType;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.client.render.cube.ModelElement;
import org.astemir.api.io.ResourceUtils;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.entity.EntityMummy;

public class ModelMummy extends SkillsAnimatedModel<EntityMummy, IDisplayArgument> {

    public static ResourceLocation TEXTURE = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/mummy.png");
    public static ResourceLocation MODEL = ResourceUtils.loadModel(DesertMania.MOD_ID,"entity/mummy.geo.json");
    public static ResourceLocation ANIMATIONS = ResourceUtils.loadAnimation(DesertMania.MOD_ID,"entity/mummy.animation.json");

    public ModelMummy() {
        super(MODEL, ANIMATIONS);
        smoothnessType(SmoothnessType.EXPONENTIAL);
    }

    @Override
    public void customAnimate(EntityMummy animated, IDisplayArgument argument,float limbSwing, float limbSwingAmount, float ticks, float delta, float headYaw, float headPitch) {
        ModelElement arms1 = getModelElement("arms1");
        ModelElement arms2 = getModelElement("arms2");
        if (animated.animationFactory.isPlaying(EntityMummy.ANIMATION_ATTACK)){
            arms1.showModel = false;
            arms2.showModel = true;
        }else{
            arms1.showModel = true;
            arms2.showModel = false;
        }
    }

    @Override
    public ResourceLocation getTexture(EntityMummy target) {
        return TEXTURE;
    }
}
