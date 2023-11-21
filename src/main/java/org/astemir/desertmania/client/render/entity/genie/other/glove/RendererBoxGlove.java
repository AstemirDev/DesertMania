package org.astemir.desertmania.client.render.entity.genie.other.glove;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import org.astemir.api.client.render.SkillsRendererEntity;
import org.astemir.desertmania.common.entity.genie.misc.EntityBoxGlove;

public class RendererBoxGlove extends SkillsRendererEntity<EntityBoxGlove, ModelWrapperBoxGlove> {

    public RendererBoxGlove(EntityRendererProvider.Context context) {
        super(context, new ModelWrapperBoxGlove());
    }

    @Override
    protected void setupRotations(EntityBoxGlove entity, PoseStack stack, float yaw, float partialTicks) {
        super.setupRotations(entity, stack, yaw, partialTicks);
        float f2 = Mth.rotLerp(partialTicks, entity.xRotO, entity.getXRot());
        stack.mulPose(Vector3f.YP.rotationDegrees(-yaw));
        stack.mulPose(Vector3f.XP.rotationDegrees(-f2));
    }

    @Override
    protected int getBlockLightLevel(EntityBoxGlove pEntity, BlockPos pPos) {
        return 7;
    }

}
