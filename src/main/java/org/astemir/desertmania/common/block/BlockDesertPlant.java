package org.astemir.desertmania.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BlockDesertPlant extends TallGrassBlock{

    public BlockDesertPlant(Properties p_57318_) {
        super(p_57318_);
    }


    @Override
    protected boolean mayPlaceOn(BlockState p_51042_, BlockGetter p_51043_, BlockPos p_51044_) {
        return super.mayPlaceOn(p_51042_, p_51043_, p_51044_) || p_51042_.is(Blocks.SAND)  || p_51042_.is(DMBlocks.DUNE_SAND.get());
    }
}
