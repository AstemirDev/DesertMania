package org.astemir.desertmania.client.render.block.genielamp;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.astemir.api.client.render.SkillsRendererBlockEntity;
import org.astemir.desertmania.common.blockentity.BlockEntityGenieLamp;

public class RendererGenieLamp extends SkillsRendererBlockEntity<BlockEntityGenieLamp> {

    public RendererGenieLamp(BlockEntityRendererProvider.Context p_173636_) {
        super(p_173636_, new ModelWrapperGenieLamp());
    }
}
