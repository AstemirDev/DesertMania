package org.astemir.desertmania.client.render.block.sunaltar;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.SkillsRenderTypes;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModelLayer;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.io.ResourceUtils;
import org.astemir.api.lib.shimmer.ShimmerLib;
import org.astemir.api.math.MathUtils;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.blockentity.BlockEntitySunAltar;

public class LayerSunAltar extends SkillsModelLayer<BlockEntitySunAltar, IDisplayArgument,ModelSunAltar> {

    public final ResourceLocation TEXTURE = ResourceUtils.loadTexture(DesertMania.MOD_ID,"block/sun_altar_layer.png");

    public LayerSunAltar(ModelSunAltar model) {
        super(model);
    }


    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, BlockEntitySunAltar instance, int pPackedLight, float pPartialTick, float r, float g, float b, float a) {
        float ticks = ((float)instance.getTicks())+Minecraft.getInstance().getPartialTick();
        pPoseStack.pushPose();
        getModel().renderModel(pPoseStack, pBuffer.getBuffer(getRenderType(instance)), pPackedLight, OverlayTexture.NO_OVERLAY,1,1,1,Math.max(MathUtils.sin(ticks/5f),0.25f), RenderCall.LAYER, false);
        ShimmerLib.postModelForce(pPoseStack,getModel(), SkillsRenderTypes.eyes(TEXTURE),ShimmerLib.LIGHT_UNSHADED,OverlayTexture.NO_OVERLAY,1,1,1,1f);
        pPoseStack.popPose();
    }

    @Override
    public RenderType getRenderType(BlockEntitySunAltar instance) {
        return RenderType.entityTranslucentEmissive(getTexture(instance));
    }

    @Override
    public ResourceLocation getTexture(BlockEntitySunAltar instance) {
        return TEXTURE;
    }
}
