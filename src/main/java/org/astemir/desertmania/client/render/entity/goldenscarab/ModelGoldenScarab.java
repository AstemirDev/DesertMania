package org.astemir.desertmania.client.render.entity.goldenscarab;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.SkillsRenderTypes;
import org.astemir.api.client.animation.SmoothnessType;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.io.ResourceUtils;
import org.astemir.api.lib.shimmer.ShimmerLib;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.entity.scarab.EntityGoldenScarab;

public class ModelGoldenScarab extends SkillsAnimatedModel<EntityGoldenScarab, IDisplayArgument> {

    public static ResourceLocation TEXTURE = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/golden_scarab.png");
    public static ResourceLocation MODEL = ResourceUtils.loadModel(DesertMania.MOD_ID,"entity/scarab.geo.json");
    public static ResourceLocation ANIMATIONS = ResourceUtils.loadAnimation(DesertMania.MOD_ID,"entity/scarab.animation.json");

    public ModelGoldenScarab() {
        super(MODEL, ANIMATIONS);
        smoothnessType(SmoothnessType.EXPONENTIAL);
    }

    @Override
    public void renderWithLayers(PoseStack stack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha, RenderCall renderCall, boolean resetBuffer) {
        super.renderWithLayers(stack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha, renderCall, resetBuffer);
        ShimmerLib.postModelForce(stack,this, SkillsRenderTypes.eyesTransparent(TEXTURE),ShimmerLib.LIGHT_UNSHADED, OverlayTexture.NO_OVERLAY,1,1,1,0.5f);
        if (getRenderTarget() != null){
            getRenderTarget().getOrCreateLight().tick(getRenderTarget());
        }
    }

    @Override
    public ResourceLocation getTexture(EntityGoldenScarab target) {
        return TEXTURE;
    }
}
