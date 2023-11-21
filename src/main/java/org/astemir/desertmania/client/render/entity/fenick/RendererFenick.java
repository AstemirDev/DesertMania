package org.astemir.desertmania.client.render.entity.fenick;


import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.astemir.api.client.render.SkillsRendererLivingEntity;
import org.astemir.desertmania.client.render.entity.fenick.layer.LayerFenickEyes;
import org.astemir.desertmania.common.entity.fenick.EntityFenick;

public class RendererFenick extends SkillsRendererLivingEntity<EntityFenick, ModelWrapperFenick> {

    public RendererFenick(EntityRendererProvider.Context context) {
        super(context,new ModelWrapperFenick());
        shadowRadius = 0.4f;
        addLayer(new LayerFenickEyes(this));
    }
}
