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
import org.astemir.desertmania.common.entity.camel.CamelDecoration;
import org.astemir.desertmania.common.entity.camel.EntityCamel;

public class LayerCamelDecoration extends RenderLayer<EntityCamel, ModelWrapperCamel> {

    public final ResourceLocation[] TEXTURES = new ResourceLocation[]{
            ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/camel/decor/black.png"),
            ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/camel/decor/blue.png"),
            ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/camel/decor/brown.png"),
            ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/camel/decor/cyan.png"),
            ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/camel/decor/gray.png"),
            ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/camel/decor/green.png"),
            ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/camel/decor/light_blue.png"),
            ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/camel/decor/light_gray.png"),
            ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/camel/decor/lime.png"),
            ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/camel/decor/magenta.png"),
            ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/camel/decor/orange.png"),
            ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/camel/decor/pink.png"),
            ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/camel/decor/purple.png"),
            ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/camel/decor/red.png"),
            ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/camel/decor/white.png"),
            ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/camel/decor/yellow.png")
    };

    public LayerCamelDecoration(RenderLayerParent<EntityCamel, ModelWrapperCamel> p_117346_) {
        super(p_117346_);
    }

    @Override
    public void render(PoseStack p_117349_, MultiBufferSource p_117350_, int p_117351_, EntityCamel p_117352_, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        if (p_117352_.getDecoration() != CamelDecoration.NO_DECORATION) {
            getParentModel().renderWrapper(p_117349_, p_117350_.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(p_117352_))),p_117351_, OverlayTexture.NO_OVERLAY,1,1,1,1, RenderCall.LAYER,false);
        }
    }

    @Override
    protected ResourceLocation getTextureLocation(EntityCamel p_117348_) {
        return TEXTURES[p_117348_.getDecoration().getId()];
    }
}
