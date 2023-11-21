package org.astemir.desertmania.client.render.entity.scarablord.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.SkillsRenderTypes;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.io.ResourceUtils;
import org.astemir.api.math.MathUtils;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.client.render.entity.scarablord.ModelWrapperScarabLord;
import org.astemir.desertmania.common.entity.scarablord.EntityScarabLord;

public class LayerEmissive extends RenderLayer<EntityScarabLord, ModelWrapperScarabLord> {

    public final ResourceLocation TEXTURE = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/scarab_lord/scarab_lord_emissive.png");

    public LayerEmissive(RenderLayerParent<EntityScarabLord, ModelWrapperScarabLord> p_117346_) {
        super(p_117346_);
    }



    @Override
    protected ResourceLocation getTextureLocation(EntityScarabLord p_117348_) {
        return TEXTURE;
    }

    @Override
    public void render(PoseStack p_117349_, MultiBufferSource p_117350_, int p_117351_, EntityScarabLord p_117352_, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        float ticks = ((float)p_117352_.tickCount)+ Minecraft.getInstance().getPartialTick();
        float alpha = Math.max(Math.abs(MathUtils.sin(ticks/20f)),0.25f);
        getParentModel().renderWrapper(p_117349_, p_117350_.getBuffer(SkillsRenderTypes.eyesTransparent(getTextureLocation(p_117352_))), p_117351_, OverlayTexture.NO_OVERLAY, 1,1,1, alpha, RenderCall.LAYER, false);
    }
}
