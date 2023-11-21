package org.astemir.desertmania.client.render.entity.arrow;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.io.ResourceUtils;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.entity.EntityStingArrow;

public class RendererStingArrow extends ArrowRenderer<EntityStingArrow> {

    public static final ResourceLocation STING_ARROW_LOCATION = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/sting_arrow.png");

    public RendererStingArrow(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    public ResourceLocation getTextureLocation(EntityStingArrow pEntity) {
        return STING_ARROW_LOCATION;
    }
}