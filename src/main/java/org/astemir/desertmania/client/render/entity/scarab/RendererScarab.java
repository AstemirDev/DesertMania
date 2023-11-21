package org.astemir.desertmania.client.render.entity.scarab;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.astemir.api.client.render.SkillsRendererLivingEntity;
import org.astemir.desertmania.common.entity.scarab.EntityScarab;

public class RendererScarab extends SkillsRendererLivingEntity<EntityScarab, ModelWrapperScarab> {

    public RendererScarab(EntityRendererProvider.Context context) {
        super(context,new ModelWrapperScarab());
        shadowRadius = 0.25f;
    }

    @Override
    public void render(EntityScarab p_115308_, float p_115309_, float p_115310_, PoseStack p_115311_, MultiBufferSource p_115312_, int p_115313_) {
        super.render(p_115308_, p_115309_, p_115310_, p_115311_, p_115312_, p_115313_);
    }
}
