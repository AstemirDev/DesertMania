package org.astemir.desertmania.client.render.entity.carpet;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import org.astemir.api.client.render.SkillsRendererEntity;
import org.astemir.desertmania.common.entity.EntityFlyingCarpet;

public class RendererFlyingCarpet extends SkillsRendererEntity<EntityFlyingCarpet, ModelWrapperFlyingCarpet> {

    public RendererFlyingCarpet(EntityRendererProvider.Context context) {
        super(context,new ModelWrapperFlyingCarpet());
        shadowRadius = 0.5f;
    }

    @Override
    protected void setupRotations(EntityFlyingCarpet entity, PoseStack stack, float yaw, float partialTicks) {
        super.setupRotations(entity, stack, yaw, partialTicks);
        float f2 = Mth.rotLerp(partialTicks, entity.xRotO, entity.getXRot());
        stack.mulPose(Vector3f.XP.rotationDegrees(f2));
    }
}
