package org.astemir.desertmania.client.render.entity.genie;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import org.astemir.api.client.render.SkillsRendererLivingEntity;
import org.astemir.desertmania.client.render.entity.genie.layer.GenieEyesLayer;
import org.astemir.desertmania.common.entity.genie.EntityAbstractGenie;

public class RendererGenie extends SkillsRendererLivingEntity<EntityAbstractGenie, ModelWrapperGenie> {

    public RendererGenie(EntityRendererProvider.Context context) {
        super(context,new ModelWrapperGenie());
        shadowRadius = 0.25f;
        addLayer(new GenieEyesLayer(this));
    }

    @Override
    public void render(EntityAbstractGenie p_115308_, float p_115309_, float p_115310_, PoseStack p_115311_, MultiBufferSource p_115312_, int p_115313_) {
        super.render(p_115308_, p_115309_, p_115310_, p_115311_, p_115312_, p_115313_);
    }

    @Override
    protected int getBlockLightLevel(EntityAbstractGenie pEntity, BlockPos pPos) {
        return 7;
    }
}
