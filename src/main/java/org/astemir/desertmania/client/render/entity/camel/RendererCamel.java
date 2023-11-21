package org.astemir.desertmania.client.render.entity.camel;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.astemir.api.client.render.SkillsRendererLivingEntity;
import org.astemir.desertmania.client.render.entity.camel.layer.LayerCamelDecoration;
import org.astemir.desertmania.client.render.entity.camel.layer.LayerPotion;
import org.astemir.desertmania.common.entity.camel.EntityCamel;

public class RendererCamel extends SkillsRendererLivingEntity<EntityCamel, ModelWrapperCamel> {

    public RendererCamel(EntityRendererProvider.Context context) {
        super(context,new ModelWrapperCamel());
        shadowRadius = 0.5f;
        addLayer(new LayerCamelDecoration(this));
        addLayer(new LayerPotion(this));
    }

    @Override
    public void render(EntityCamel p_115308_, float p_115309_, float p_115310_, PoseStack p_115311_, MultiBufferSource p_115312_, int p_115313_) {
        if (p_115308_.isBaby()){
            p_115311_.scale(0.5f,0.5f,0.5f);
        }
        super.render(p_115308_, p_115309_, p_115310_, p_115311_, p_115312_, p_115313_);
    }
}
