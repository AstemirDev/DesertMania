package org.astemir.desertmania.client.render.block.basket;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.io.ResourceUtils;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.block.BlockBasket;
import org.astemir.desertmania.common.block.DMBlocks;
import org.astemir.desertmania.common.blockentity.BlockEntityBasket;

public class ModelBasket extends SkillsModel<BlockEntityBasket, IDisplayArgument> {

    public static final ResourceLocation MODEL = ResourceUtils.loadModel(DesertMania.MOD_ID,"block/basket.geo.json");
    public static final ResourceLocation TEXTURE = ResourceUtils.loadTexture(DesertMania.MOD_ID,"block/basket.png");
    public static final ResourceLocation TEXTURE_CLOSED = ResourceUtils.loadTexture(DesertMania.MOD_ID,"block/basket_closed.png");

    public ModelBasket() {
        super(MODEL);
    }

    @Override
    public ResourceLocation getTexture(BlockEntityBasket target) {
        BlockState state = target.getLevel().getBlockState(target.getBlockPos());
        if (state != null && state.is(DMBlocks.BASKET.get())){
            if (state.getValue(BlockBasket.CLOSED) || target.isFull()){
                return TEXTURE_CLOSED;
            }
        }
        return TEXTURE;
    }
}
