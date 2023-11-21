package org.astemir.desertmania.client.render.entity.genie.other.polymorph;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import org.astemir.api.client.render.SkillsRendererLivingEntity;
import org.astemir.desertmania.common.entity.genie.misc.EntityPolymorph;

public class RendererPolymorph extends SkillsRendererLivingEntity<EntityPolymorph, ModelWrapperPolymorph> {

    public RendererPolymorph(EntityRendererProvider.Context context) {
        super(context,new ModelWrapperPolymorph());
        shadowRadius = 0.4f;
    }

    @Override
    protected int getBlockLightLevel(EntityPolymorph pEntity, BlockPos pPos) {
        return 7;
    }

    @Override
    public void render(EntityPolymorph p_115308_, float p_115309_, float p_115310_, PoseStack p_115311_, MultiBufferSource p_115312_, int p_115313_) {
        super.render(p_115308_, p_115309_, p_115310_, p_115311_, p_115312_, p_115313_);
    }
}
