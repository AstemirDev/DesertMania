package org.astemir.desertmania.client.render.entity.carpet;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.animation.SmoothnessType;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.io.ResourceUtils;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.entity.EntityFlyingCarpet;

public class ModelFlyingCarpet extends SkillsAnimatedModel<EntityFlyingCarpet, IDisplayArgument> {

    public static ResourceLocation TEXTURE_1 = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/carpet/carpet1.png");
    public static ResourceLocation TEXTURE_2 = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/carpet/carpet2.png");
    public static ResourceLocation TEXTURE_3 = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/carpet/carpet3.png");


    public static ResourceLocation MODEL = ResourceUtils.loadModel(DesertMania.MOD_ID,"entity/carpet.geo.json");
    public static ResourceLocation ANIMATIONS = ResourceUtils.loadAnimation(DesertMania.MOD_ID,"entity/carpet.animation.json");

    public ModelFlyingCarpet() {
        super(MODEL, ANIMATIONS);
        smoothnessType(SmoothnessType.EXPONENTIAL);
    }

    @Override
    public void renderModel(PoseStack stack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha, RenderCall renderCall, boolean resetBuffer) {
        super.renderModel(stack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha, renderCall, resetBuffer);
    }

    @Override
    public ResourceLocation getTexture(EntityFlyingCarpet target) {
        switch (EntityFlyingCarpet.SKIN.get(target)){
            case 0:{
                return TEXTURE_1;
            }
            case 1:{
                return TEXTURE_2;
            }
            case 2:{
                return TEXTURE_3;
            }
        }
        return TEXTURE_1;
    }
}
