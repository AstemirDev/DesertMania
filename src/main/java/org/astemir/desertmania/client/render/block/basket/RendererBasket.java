package org.astemir.desertmania.client.render.block.basket;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.astemir.api.client.render.SkillsRendererBlockEntity;
import org.astemir.desertmania.common.blockentity.BlockEntityBasket;

public class RendererBasket extends SkillsRendererBlockEntity<BlockEntityBasket> {

    public RendererBasket(BlockEntityRendererProvider.Context p_173636_) {
        super(p_173636_, new ModelWrapperBasket());
    }
}
