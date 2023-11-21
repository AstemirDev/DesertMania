package org.astemir.desertmania.client.render.block.sunaltar;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.render.SkillsRendererBlockEntity;
import org.astemir.api.client.render.cube.ModelElement;
import org.astemir.api.math.MathUtils;
import org.astemir.api.math.components.Color;
import org.astemir.desertmania.client.render.RendererDragonRays;
import org.astemir.desertmania.common.blockentity.BlockEntitySunAltar;

public class RendererSunAltar extends SkillsRendererBlockEntity<BlockEntitySunAltar> {

    public RendererSunAltar(BlockEntityRendererProvider.Context p_173636_) {
        super(p_173636_, new ModelWrapperSunAltar());
    }

    @Override
    public void render(BlockEntitySunAltar blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        super.render(blockEntity, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
        float ticks = (float) blockEntity.getTicks();
        float lerpTicks = ticks+ Minecraft.getInstance().getPartialTick();
        float scale = Math.min(0.04f+ MathUtils.sin(ticks/30f)/40f,0.04f);
        poseStack.pushPose();
        SkillsModel<BlockEntitySunAltar, IDisplayArgument> modelSunAltar = getModelWrapper().getModel(blockEntity);
        if (modelSunAltar != null) {
            Color color = Color.PURPLE.interpolate(Color.ORANGE,MathUtils.progressOfTime(lerpTicks,20));
            ModelElement element = modelSunAltar.getModelElement("bone2");
            poseStack.translate(0.5f,0.95f+(element.positionY/16f) , 0.5f);
            poseStack.mulPose(Vector3f.XN.rotation(MathUtils.sin(lerpTicks/40f)*10));
            poseStack.mulPose(Vector3f.YN.rotation(MathUtils.sin(lerpTicks/20f)*10));
            poseStack.scale(scale, scale, scale);
            RendererDragonRays.render(color, ticks, 100, 90, poseStack, bufferSource);
            poseStack.popPose();
        }
    }


}
