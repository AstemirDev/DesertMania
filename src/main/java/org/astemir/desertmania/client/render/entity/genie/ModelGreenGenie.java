package org.astemir.desertmania.client.render.entity.genie;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.ResourceArray;
import org.astemir.api.client.animation.SmoothnessType;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.client.render.cube.ModelElement;
import org.astemir.api.io.ResourceUtils;
import org.astemir.api.math.components.Vector3;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.entity.genie.EntityAbstractGenie;
import org.astemir.desertmania.common.entity.genie.EntityGreenGenie;
import org.astemir.desertmania.common.entity.genie.misc.GenieAnimations;

public class ModelGreenGenie extends SkillsAnimatedModel<EntityAbstractGenie, IDisplayArgument> {


    public static ResourceArray TEXTURE = new ResourceArray(DesertMania.MOD_ID,"entity/genie/green/genie_%s.png",4,1);
    public static ResourceArray TEXTURE_JACKPOT = new ResourceArray(DesertMania.MOD_ID,"entity/genie/green/genie_treasure_%s.png",4,1);

    public static ResourceLocation MODEL = ResourceUtils.loadModel(DesertMania.MOD_ID,"entity/genie/genie_green.geo.json");
    public static ResourceLocation ANIMATIONS = ResourceUtils.loadAnimation(DesertMania.MOD_ID,"entity/genie/genie_green.animation.json");

    public ModelGreenGenie() {
        super(MODEL, ANIMATIONS);
        smoothnessType(SmoothnessType.EXPONENTIAL);
    }

    @Override
    public void customAnimate(EntityAbstractGenie animated, IDisplayArgument argument,float limbSwing, float limbSwingAmount, float ticks, float delta, float headYaw, float headPitch) {
        ModelElement head = getModelElement("head");
        if (animated instanceof EntityGreenGenie genie) {
            if (!genie.clientSideKushSkin) {
                if (head != null) {
                    lookAt(head, headPitch, headYaw / 2f);
                }
            }else{
                head.setRotation(Vector3.zero());
            }
        }
    }

    @Override
    public void renderModel(PoseStack stack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha, RenderCall renderCall, boolean resetBuffer) {
        boolean canRender = !getRenderTarget().getAnimationFactory().isPlaying(GenieAnimations.ANIMATION_SPAWN) || getRenderTarget().tickCount >= 5;
        if (canRender) {
            super.renderModel(stack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha, renderCall, resetBuffer);
        }
    }

    @Override
    public ResourceLocation getTexture(EntityAbstractGenie target) {
        if (((EntityGreenGenie)target).clientSideKushSkin || !((EntityGreenGenie)target).jackpotController.isNoAction()){
            return TEXTURE_JACKPOT.getResourceLocation(target.tickCount/2);
        }
        return TEXTURE.getResourceLocation(target.tickCount/2);
    }
}
