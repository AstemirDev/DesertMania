package org.astemir.desertmania.client.render.entity.genie.other.charge;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.SkillsRenderTypes;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.client.render.cube.ModelElement;
import org.astemir.api.io.ResourceUtils;
import org.astemir.api.lib.shimmer.ShimmerLib;
import org.astemir.api.math.components.Color;
import org.astemir.api.math.components.Vector3;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.client.render.RendererDragonRays;
import org.astemir.desertmania.common.entity.genie.misc.EntityGenieCharge;

public class ModelCharge extends SkillsAnimatedModel<EntityGenieCharge, IDisplayArgument> {

    public static ResourceLocation TEXTURE = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/genie/other/genie_charge.png");
    public static ResourceLocation MODEL = ResourceUtils.loadModel(DesertMania.MOD_ID,"entity/genie/other/genie_charge.geo.json");
    public static ResourceLocation ANIMATIONS = ResourceUtils.loadAnimation(DesertMania.MOD_ID,"entity/genie/other/genie_charge.animation.json");

    public ModelCharge() {
        super(MODEL, ANIMATIONS);
        smoothness(1f);
    }

    @Override
    public void customAnimate(EntityGenieCharge animated, IDisplayArgument argument,float limbSwing, float limbSwingAmount, float ticks, float delta, float headYaw, float headPitch) {
        ModelElement element = getModelElement("attack");
        float lerpTicks = ((float)animated.tickCount)+Minecraft.getInstance().getPartialTick();
        if (lerpTicks < 5) {
            element.setScale(new Vector3(lerpTicks/5,lerpTicks/5,lerpTicks/5));
        }
    }

    @Override
    public void renderWithLayers(PoseStack stack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha, RenderCall renderCall, boolean resetBuffer) {
        super.renderWithLayers(stack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha, renderCall, resetBuffer);
        ShimmerLib.postModelForce(stack,this,SkillsRenderTypes.eyesTransparent(TEXTURE),ShimmerLib.LIGHT_UNSHADED, OverlayTexture.NO_OVERLAY,1,1,1,1);
    }

    @Override
    public void renderModel(PoseStack stack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha, RenderCall renderCall, boolean resetBuffer) {
        super.renderModel(stack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha, renderCall, resetBuffer);
        float partialTick = Minecraft.getInstance().getPartialTick();
        float lerpTicks = getRenderTarget().tickCount+partialTick;
        VertexConsumer consumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(SkillsRenderTypes.eyesTransparent(getTexture(getRenderTarget())));
        for (int i = 0;i<5;i++) {
            float scale = 1+i/10f;
            stack.pushPose();
            stack.scale(scale, scale, scale);
            stack.translate(0, -i/10f, 0);
            super.renderModel(stack, consumer, packedLightIn, packedOverlayIn, red, green, blue, Math.max(0.1f,0.5f-i/5f), RenderCall.LAYER, false);
            stack.popPose();
        }
        stack.pushPose();
        stack.translate(0,1.25f,0);
        stack.scale(0.025f,0.025f,0.025f);
        RendererDragonRays.render(Color.YELLOW,lerpTicks,200,360,stack,Minecraft.getInstance().renderBuffers().bufferSource());
        stack.popPose();
    }

    @Override
    public ResourceLocation getTexture(EntityGenieCharge target) {
        return TEXTURE;
    }
}
