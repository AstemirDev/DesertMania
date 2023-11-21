package org.astemir.desertmania.client.render.entity.fenick.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.SkillsRenderTypes;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.common.world.WorldUtils;
import org.astemir.api.io.ResourceUtils;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.client.render.entity.fenick.ModelWrapperFenick;
import org.astemir.desertmania.common.entity.fenick.EntityFenick;

public class LayerFenickEyes extends RenderLayer<EntityFenick, ModelWrapperFenick> {

    public final ResourceLocation TEXTURE = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/fenick/fenick_eyes.png");

    public LayerFenickEyes(RenderLayerParent<EntityFenick, ModelWrapperFenick> p_117346_) {
        super(p_117346_);
    }

    @Override
    public void render(PoseStack p_117349_, MultiBufferSource p_117350_, int p_117351_, EntityFenick p_117352_, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        if (WorldUtils.isNight(p_117352_.level)) {
            getParentModel().renderWrapper(p_117349_, p_117350_.getBuffer(SkillsRenderTypes.eyesTransparent(getTextureLocation(p_117352_))), p_117351_, OverlayTexture.NO_OVERLAY, 1,1,1, 0.9f, RenderCall.LAYER, false);
        }
    }


    @Override
    protected ResourceLocation getTextureLocation(EntityFenick p_117348_) {
        return TEXTURE;
    }
}
