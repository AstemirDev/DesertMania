package org.astemir.desertmania.client.render.entity.fakir;


import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.astemir.api.client.render.SkillsRendererLivingEntity;
import org.astemir.desertmania.common.entity.fenick.EntityFenickFakir;

public class RendererFenickFakir extends SkillsRendererLivingEntity<EntityFenickFakir, ModelWrapperFenickFakir> {

    public RendererFenickFakir(EntityRendererProvider.Context context) {
        super(context,new ModelWrapperFenickFakir());
        shadowRadius = 0.4f;
    }

}
