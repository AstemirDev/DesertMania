package org.astemir.desertmania.client.render.entity.mummy;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.astemir.api.client.render.SkillsRendererLivingEntity;
import org.astemir.desertmania.common.entity.EntityMummy;

public class RendererMummy extends SkillsRendererLivingEntity<EntityMummy, ModelWrapperMummy> {

    public RendererMummy(EntityRendererProvider.Context context) {
        super(context,new ModelWrapperMummy());
        shadowRadius = 0.5f;
    }

    @Override
    public void render(EntityMummy p_115308_, float p_115309_, float p_115310_, PoseStack p_115311_, MultiBufferSource p_115312_, int p_115313_) {
        super.render(p_115308_, p_115309_, p_115310_, p_115311_, p_115312_, p_115313_);
    }
}
