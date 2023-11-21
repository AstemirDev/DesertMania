package org.astemir.desertmania.client.render.entity.mage;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.astemir.api.client.animation.SmoothnessType;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.client.render.cube.ModelElement;
import org.astemir.api.common.action.ActionController;
import org.astemir.api.io.ResourceUtils;
import org.astemir.api.math.MathUtils;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.entity.fenick.EntityFenickMage;

public class ModelFenickMage extends SkillsAnimatedModel<EntityFenickMage, IDisplayArgument> {

    public static ResourceLocation MODEL = ResourceUtils.loadModel(DesertMania.MOD_ID,"entity/fenick/fenick_mage.geo.json");
    public static ResourceLocation ANIMATIONS = ResourceUtils.loadAnimation(DesertMania.MOD_ID,"entity/fenick/fenick_mage.animation.json");
    public static ResourceLocation TEXTURE = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/fenick/fenick_mage.png");


    public ModelFenickMage() {
        super(MODEL,ANIMATIONS);
        smoothnessType(SmoothnessType.EXPONENTIAL);
    }

    @Override
    public void renderModel(PoseStack stack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha, RenderCall renderCall, boolean resetBuffer) {
        super.renderModel(stack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha, renderCall, resetBuffer);
    }

    @Override
    public void customAnimate(EntityFenickMage animated, IDisplayArgument argument,float limbSwing, float limbSwingAmount, float ticks, float delta, float headYaw, float headPitch) {
        ModelElement head = getModelElement("head");
        if (head != null) {
            lookAt(head, headPitch, headYaw);
        }
    }

    @Override
    public void onRenderModelCube(ModelElement cube, PoseStack matrixStackIn, VertexConsumer bufferIn, RenderCall renderCall, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        EntityFenickMage fenick = getRenderTarget();
        if (fenick != null){
            ActionController controller = fenick.actionController;
            float ticks = (float)controller.getTicks();
            if (controller.is(EntityFenickMage.ACTION_PUT_ITEMS)) {
                float partial = Minecraft.getInstance().getPartialTick();
                float p = MathUtils.progressOfTime(ticks+partial, ((float) EntityFenickMage.ACTION_PUT_ITEMS.getLength()));
                ItemStack[] stacks = fenick.getSlots();
                if (stacks.length > 0 && p > 0) {
                    matrixStackIn.pushPose();
                    matrixStackIn.translate(0, -1.75f * p, 0);
                    matrixStackIn.scale(0.6f, 0.6f, 0.6f);
                    matrixStackIn.mulPose(Vector3f.XN.rotationDegrees(180));
                    if (p < 0.85f) {
                        matrixStackIn.mulPose(Vector3f.YN.rotationDegrees(ticks * ticks));
                    }
                    if (cube.getName().equals("basket1")) {
                        ItemStack a = stacks[0];
                        renderItem(a, ItemTransforms.TransformType.FIXED, matrixStackIn, packedLightIn);
                    }
                    if (cube.getName().equals("basket2")) {
                        ItemStack b = stacks[1];
                        renderItem(b, ItemTransforms.TransformType.FIXED, matrixStackIn, packedLightIn);
                    }
                    if (cube.getName().equals("basket3")) {
                        ItemStack c = stacks[2];
                        renderItem(c, ItemTransforms.TransformType.FIXED, matrixStackIn, packedLightIn);
                    }
                    matrixStackIn.popPose();
                }
            }
        }
    }

    @Override
    public ResourceLocation getTexture(EntityFenickMage target) {
        return TEXTURE;
    }
}
