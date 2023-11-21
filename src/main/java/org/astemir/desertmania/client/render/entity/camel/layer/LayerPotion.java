package org.astemir.desertmania.client.render.entity.camel.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.io.ResourceUtils;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.client.render.entity.camel.ModelWrapperCamel;
import org.astemir.desertmania.common.entity.camel.CamelLiquid;
import org.astemir.desertmania.common.entity.camel.EntityCamel;

import java.awt.*;

public class LayerPotion extends RenderLayer<EntityCamel, ModelWrapperCamel> {

    public final ResourceLocation TEXTURE = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/camel/potion.png");

    public LayerPotion(RenderLayerParent<EntityCamel, ModelWrapperCamel> p_117346_) {
        super(p_117346_);
    }

    @Override
    public void render(PoseStack p_117349_, MultiBufferSource p_117350_, int p_117351_, EntityCamel p_117352_, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        Color color = getColor(p_117352_);
        if (color != null) {
            getParentModel().renderWrapper(p_117349_, p_117350_.getBuffer(RenderType.entityTranslucentCull(getTextureLocation(p_117352_))), p_117351_, OverlayTexture.NO_OVERLAY, color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, 0.5f, RenderCall.LAYER, false);
        }
    }

    private Color getColor(EntityCamel camel){
        CamelLiquid liquid = camel.getLiquid();
        if (liquid.hasOverlayOnFace()) {
            if (liquid.getAmount() > 0) {
                return new Color(liquid.getLiquidColor());
            }
        }
        return null;
    }


    @Override
    protected ResourceLocation getTextureLocation(EntityCamel p_117348_) {
        return TEXTURE;
    }
}
