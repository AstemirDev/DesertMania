package org.astemir.desertmania.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.SkillsRenderTypes;

import java.util.function.Function;

public class DMRenderTypes extends SkillsRenderTypes {

    public DMRenderTypes(String pName, VertexFormat pFormat, VertexFormat.Mode pMode, int pBufferSize, boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) {
        super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
    }

    public static final Function<ResourceLocation, RenderType> ULTRA_GLOW = Util.memoize((p_173255_) -> {
        RenderStateShard.TextureStateShard textureStateShard = new RenderStateShard.TextureStateShard(p_173255_, false, false);
        return RenderType.create("ultra_glow", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 256, false, false, RenderType.CompositeState.builder().setShaderState(RenderStateShard.RENDERTYPE_ENERGY_SWIRL_SHADER).setCullState(RenderStateShard.NO_CULL).setTextureState(textureStateShard).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setLayeringState(LayeringStateShard.VIEW_OFFSET_Z_LAYERING).setLightmapState(RenderStateShard.LIGHTMAP).createCompositeState(true));
    });



}
