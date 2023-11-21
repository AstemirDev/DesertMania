package org.astemir.desertmania.client.render.entity.desertworm;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.astemir.api.client.render.SkillsRendererLivingEntity;
import org.astemir.desertmania.common.entity.desertworm.EntityDesertWorm;

public class RendererDesertWorm extends SkillsRendererLivingEntity<EntityDesertWorm, ModelWrapperDesertWorm> {

    public RendererDesertWorm(EntityRendererProvider.Context context) {
        super(context,new ModelWrapperDesertWorm());
        shadowRadius = 0;
    }

    @Override
    public void render(EntityDesertWorm p_115308_, float p_115309_, float p_115310_, PoseStack p_115311_, MultiBufferSource p_115312_, int p_115313_) {
        super.render(p_115308_, p_115309_, p_115310_, p_115311_, p_115312_, p_115313_);
    }

    @Override
    protected void scale(EntityDesertWorm p_115314_, PoseStack p_115315_, float p_115316_) {
        float scale = EntityDesertWorm.SCALE.get(p_115314_);
        p_115315_.scale(scale,scale,scale);
    }
}
