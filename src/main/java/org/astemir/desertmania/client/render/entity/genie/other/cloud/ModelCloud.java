package org.astemir.desertmania.client.render.entity.genie.other.cloud;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.client.render.cube.ModelElement;
import org.astemir.api.io.ResourceUtils;
import org.astemir.api.math.components.Vector3;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.entity.genie.misc.EntityCloud;

public class ModelCloud extends SkillsAnimatedModel<EntityCloud, IDisplayArgument> {

    public static ResourceLocation TEXTURE = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/genie/other/cloud.png");
    public static ResourceLocation MODEL = ResourceUtils.loadModel(DesertMania.MOD_ID,"entity/genie/other/cloud.geo.json");
    public static ResourceLocation ANIMATIONS = ResourceUtils.loadAnimation(DesertMania.MOD_ID,"entity/genie/other/cloud.animation.json");

    public ModelCloud() {
        super(MODEL, ANIMATIONS);
    }

    @Override
    public void customAnimate(EntityCloud animated,IDisplayArgument argument, float limbSwing, float limbSwingAmount, float ticks, float delta, float headYaw, float headPitch) {
        ModelElement element = getModelElement("bone");
        float lerpTicks = (float)(animated.tickCount)+Minecraft.getInstance().getPartialTick();
        element.setPosition(new Vector3(0,8,0));
        if (lerpTicks < 5) {
            element.setScale(new Vector3(lerpTicks/5,lerpTicks/5,lerpTicks/5));
        }
    }

    @Override
    public ResourceLocation getTexture(EntityCloud target) {
        return TEXTURE;
    }
}
