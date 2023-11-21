package org.astemir.desertmania.client.render.entity.genie.other.charge;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.BlazeRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.client.render.SkillsRendererEntity;
import org.astemir.desertmania.client.render.RendererDragonRays;
import org.astemir.desertmania.common.entity.genie.misc.EntityGenieCharge;

public class RendererCharge extends SkillsRendererEntity<EntityGenieCharge,ModelWrapperCharge> {

    public RendererCharge(EntityRendererProvider.Context context) {
        super(context, new ModelWrapperCharge());
    }

    @Override
    protected void setupRotations(EntityGenieCharge entity, PoseStack stack, float yaw, float partialTicks) {
        float pitch = Mth.rotLerp(partialTicks, entity.xRotO, entity.getXRot());
        stack.mulPose(Vector3f.YP.rotationDegrees(-yaw));
        stack.mulPose(Vector3f.XP.rotationDegrees(-pitch));
    }

    @Override
    public void render(EntityGenieCharge entity, float yaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, yaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    protected int getBlockLightLevel(EntityGenieCharge pEntity, BlockPos pPos) {
        return 15;
    }


    @Override
    protected void scale(EntityGenieCharge entity, PoseStack stack, float partialTicks) {
        super.scale(entity, stack, partialTicks);
    }
}
