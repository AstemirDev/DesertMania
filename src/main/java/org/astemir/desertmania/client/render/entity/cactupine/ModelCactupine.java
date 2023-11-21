package org.astemir.desertmania.client.render.entity.cactupine;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import org.astemir.api.client.animation.SmoothnessType;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.client.render.cube.ModelElement;
import org.astemir.api.io.ResourceUtils;
import org.astemir.api.math.components.Vector3;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.entity.EntityCactupine;

public class ModelCactupine extends SkillsAnimatedModel<EntityCactupine, IDisplayArgument> {

    public static ResourceLocation TEXTURE = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/cactupine/cactupine.png");
    public static ResourceLocation MODEL = ResourceUtils.loadModel(DesertMania.MOD_ID,"entity/cactupine/cactupine.geo.json");
    public static ResourceLocation ANIMATIONS = ResourceUtils.loadAnimation(DesertMania.MOD_ID,"entity/cactupine.animation.json");

    public ModelCactupine() {
        super(MODEL, ANIMATIONS);
        smoothnessType(SmoothnessType.EXPONENTIAL);
    }

    @Override
    public ResourceLocation getTexture(EntityCactupine target) {
        return TEXTURE;
    }

    @Override
    public void customAnimate(EntityCactupine animated, IDisplayArgument argument,float limbSwing, float limbSwingAmount, float ticks, float delta, float headYaw, float headPitch) {
        super.customAnimate(animated, argument,limbSwing, limbSwingAmount, ticks, delta, headYaw, headPitch);
        if (animated.animationFactory.isPlaying(EntityCactupine.ANIMATION_ROLL)) {
            ModelElement body = getModelElement("body");
            if (body != null) {
                float tick = ((float) animated.tickCount) + Minecraft.getInstance().getPartialTick();
                body.setRotation(new Vector3(tick, 0, 0));
            }
        }
    }

    @Override
    public void onRenderModelCube(ModelElement cube, PoseStack matrixStackIn, VertexConsumer bufferIn, RenderCall renderCall, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (cube.getName().equals("body")){

            matrixStackIn.pushPose();
            matrixStackIn.scale(0.7f,0.7f,0.7f);
            matrixStackIn.translate(0,-0.1,0.75f);
            matrixStackIn.mulPose(Vector3f.XN.rotationDegrees(180));
            matrixStackIn.mulPose(Vector3f.ZN.rotationDegrees(-25));
            renderItem(Items.OAK_SAPLING.getDefaultInstance(), ItemTransforms.TransformType.FIXED,matrixStackIn,packedLightIn);
            matrixStackIn.popPose();

            matrixStackIn.pushPose();
            matrixStackIn.scale(0.7f,0.7f,0.7f);
            matrixStackIn.translate(0.75f,-0.1,0f);
            matrixStackIn.mulPose(Vector3f.YN.rotationDegrees(90));
            matrixStackIn.mulPose(Vector3f.XN.rotationDegrees(180));
            matrixStackIn.mulPose(Vector3f.ZN.rotationDegrees(60));

            renderItem(Items.DARK_OAK_SAPLING.getDefaultInstance(), ItemTransforms.TransformType.FIXED,matrixStackIn,packedLightIn);
            matrixStackIn.popPose();


            matrixStackIn.pushPose();
            matrixStackIn.scale(0.8f,0.8f,0.7f);
            matrixStackIn.translate(-0.67f,-0.33,0f);
            matrixStackIn.mulPose(Vector3f.YN.rotationDegrees(90));
            matrixStackIn.mulPose(Vector3f.XN.rotationDegrees(180));
            matrixStackIn.mulPose(Vector3f.ZN.rotationDegrees(10));
            renderItem(Items.BROWN_MUSHROOM.getDefaultInstance(), ItemTransforms.TransformType.FIXED,matrixStackIn,packedLightIn);
            matrixStackIn.popPose();

            matrixStackIn.pushPose();
            matrixStackIn.scale(0.7f,0.7f,0.7f);
            matrixStackIn.translate(-0.1,-0.75,0f);
            matrixStackIn.mulPose(Vector3f.YN.rotationDegrees(90));
            matrixStackIn.mulPose(Vector3f.XN.rotationDegrees(100));
            matrixStackIn.mulPose(Vector3f.ZN.rotationDegrees(10));
            renderItem(Items.APPLE.getDefaultInstance(), ItemTransforms.TransformType.FIXED,matrixStackIn,packedLightIn);
            matrixStackIn.popPose();
            bufferIn = returnDefaultBuffer();
        }
    }

    @Override
    public void renderModel(PoseStack stack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha, RenderCall renderCall, boolean resetBuffer) {
        super.renderModel(stack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha, renderCall, resetBuffer);
    }

    @Override
    public void renderToBuffer(PoseStack p_103111_, VertexConsumer p_103112_, int p_103113_, int p_103114_, float p_103115_, float p_103116_, float p_103117_, float p_103118_) {
        super.renderToBuffer(p_103111_, p_103112_, p_103113_, p_103114_, p_103115_, p_103116_, p_103117_, p_103118_);
    }
}
