package org.astemir.desertmania.client.render.entity.genie.layer;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.SkillsRenderTypes;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.client.render.SkillsRendererLayer;
import org.astemir.api.io.ResourceUtils;
import org.astemir.api.lib.shimmer.ShimmerLib;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.client.render.entity.genie.ModelWrapperGenie;
import org.astemir.desertmania.common.entity.DMEntities;
import org.astemir.desertmania.common.entity.genie.EntityAbstractGenie;

public class GenieEyesLayer extends SkillsRendererLayer<EntityAbstractGenie, ModelWrapperGenie> {

    public static ResourceLocation BLUE = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/genie/eyes/blue.png");
    public static ResourceLocation GREEN = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/genie/eyes/green.png");
    public static ResourceLocation PURPLE = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/genie/eyes/purple.png");
    public static ResourceLocation RED = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/genie/eyes/red.png");

    public GenieEyesLayer(RenderLayerParent<EntityAbstractGenie, ModelWrapperGenie> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, EntityAbstractGenie entity, float limbSwing, float swingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        super.render(poseStack, bufferSource, packedLight, entity, limbSwing, swingAmount, partialTick, ageInTicks, netHeadYaw, headPitch);
        float ticks = ((float)entity.tickCount)+ Minecraft.getInstance().getPartialTick();
        ShimmerLib.postModelForce(poseStack, getParentModel().getModel(entity),SkillsRenderTypes.eyesTransparentNoCull(getTextureLocation(entity)), getEyeLight(packedLight), OverlayTexture.NO_OVERLAY, 1, 1, 1, 1f);
        getParentModel().renderWrapper(poseStack, bufferSource.getBuffer(SkillsRenderTypes.eyesTransparentNoCull(getTextureLocation(entity))), getEyeLight(packedLight), OverlayTexture.NO_OVERLAY, 1, 1, 1, 1f, RenderCall.LAYER, false);
    }

    @Override
    public RenderType getBuffer(EntityAbstractGenie entity) {
        return SkillsRenderTypes.eyesTransparent(getTextureLocation(entity));
    }



    @Override
    protected ResourceLocation getTextureLocation(EntityAbstractGenie pEntity) {
        if (pEntity.getType() == DMEntities.BLUE_GENIE.get()){
            return BLUE;
        }
        if (pEntity.getType() == DMEntities.GREEN_GENIE.get()){
            return GREEN;
        }
        if (pEntity.getType() == DMEntities.RED_GENIE.get()){
            return RED;
        }
        if (pEntity.getType() == DMEntities.PURPLE_GENIE.get()){
            return PURPLE;
        }
        return BLUE;
    }
}
