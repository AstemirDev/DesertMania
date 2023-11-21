package org.astemir.desertmania.client.render.entity.cactupine;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.ResourceArray;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.client.render.cube.ModelElement;
import org.astemir.api.io.ResourceUtils;
import org.astemir.api.math.components.Vector3;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.entity.EntityCactupine;

public class ModelCactupineXmas extends SkillsAnimatedModel<EntityCactupine, IDisplayArgument> {

    public static ResourceArray TEXTURE = new ResourceArray(DesertMania.MOD_ID,"entity/cactupine/cactupine_xmas_%s.png",2,0.5f);
    public static ResourceLocation MODEL = ResourceUtils.loadModel(DesertMania.MOD_ID,"entity/cactupine/cactupine_xmas.geo.json");
    public static ResourceLocation ANIMATIONS = ResourceUtils.loadAnimation(DesertMania.MOD_ID,"entity/cactupine.animation.json");

    public ModelCactupineXmas() {
        super(MODEL, ANIMATIONS);
    }

    @Override
    public void customAnimate(EntityCactupine animated, IDisplayArgument argument,float limbSwing, float limbSwingAmount, float ticks, float delta, float headYaw, float headPitch) {
        super.customAnimate(animated,argument,limbSwing, limbSwingAmount, ticks, delta, headYaw, headPitch);
        if (animated.animationFactory.isPlaying(EntityCactupine.ANIMATION_ROLL)) {
            ModelElement body = getModelElement("body");
            if (body != null) {
                float tick = ((float) animated.tickCount) + Minecraft.getInstance().getPartialTick();
                body.setRotation(new Vector3(tick, 0, 0));
            }
        }
    }

    @Override
    public ResourceLocation getTexture(EntityCactupine target) {
        return TEXTURE.getResourceLocation(target.tickCount/4);
    }
}
