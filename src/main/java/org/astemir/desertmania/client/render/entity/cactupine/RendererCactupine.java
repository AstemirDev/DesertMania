package org.astemir.desertmania.client.render.entity.cactupine;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.astemir.api.client.render.SkillsRendererLivingEntity;
import org.astemir.desertmania.common.entity.EntityCactupine;

public class RendererCactupine extends SkillsRendererLivingEntity<EntityCactupine, ModelWrapperCactupine> {

    public RendererCactupine(EntityRendererProvider.Context context) {
        super(context,new ModelWrapperCactupine());
        shadowRadius = 0.5f;
    }

    @Override
    public void render(EntityCactupine p_115308_, float p_115309_, float p_115310_, PoseStack p_115311_, MultiBufferSource p_115312_, int p_115313_) {
        super.render(p_115308_, p_115309_, p_115310_, p_115311_, p_115312_, p_115313_);
    }

    @Override
    protected void setupRotations(EntityCactupine p_115317_, PoseStack p_115318_, float p_115319_, float p_115320_, float p_115321_) {
        if (p_115317_.animationFactory.isPlaying(EntityCactupine.ANIMATION_ROLL)){
            float rot = (180.0F - p_115317_.yHeadRot)+Minecraft.getInstance().getPartialTick();
            p_115318_.mulPose(Vector3f.YP.rotationDegrees(rot));
        }else {
            super.setupRotations(p_115317_, p_115318_, p_115319_, p_115320_, p_115321_);
        }
    }
}
