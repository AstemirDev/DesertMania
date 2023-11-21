package org.astemir.desertmania.client.render.entity.genie.other.cloud;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import org.astemir.api.client.render.SkillsRendererEntity;
import org.astemir.desertmania.common.entity.genie.misc.EntityCloud;

public class RendererCloud extends SkillsRendererEntity<EntityCloud, ModelWrapperCloud> {

    public RendererCloud(EntityRendererProvider.Context context) {
        super(context, new ModelWrapperCloud());
    }

    @Override
    protected int getBlockLightLevel(EntityCloud pEntity, BlockPos pPos) {
        return 7;
    }


    @Override
    protected void setupRotations(EntityCloud entity, PoseStack stack, float yaw, float partialTicks) {
        super.setupRotations(entity, stack, yaw, partialTicks);
        float f2 = Mth.rotLerp(partialTicks, entity.xRotO, entity.getXRot());
        stack.mulPose(Vector3f.YP.rotationDegrees(-yaw));
        stack.mulPose(Vector3f.XP.rotationDegrees(-f2));
    }

    @Override
    protected void scale(EntityCloud entity, PoseStack stack, float partialTicks) {
        super.scale(entity, stack, partialTicks);
    }
}
