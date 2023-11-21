package org.astemir.desertmania.client.render.block.magicbasket;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.astemir.api.client.render.SkillsRendererBlockEntity;
import org.astemir.desertmania.common.blockentity.BlockEntityMagicBasket;

public class RendererMagicBasket extends SkillsRendererBlockEntity<BlockEntityMagicBasket> {

    public RendererMagicBasket(BlockEntityRendererProvider.Context p_173636_) {
        super(p_173636_, new ModelWrapperMagicBasket());
    }
}
