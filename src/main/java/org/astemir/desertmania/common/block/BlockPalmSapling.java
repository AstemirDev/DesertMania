package org.astemir.desertmania.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.astemir.desertmania.common.world.generation.features.PalmTreeGrower;

public class BlockPalmSapling extends SaplingBlock {

    public BlockPalmSapling() {
        super(new PalmTreeGrower(), BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS));
    }


    @Override
    protected boolean mayPlaceOn(BlockState p_51042_, BlockGetter p_51043_, BlockPos p_51044_) {
        return super.mayPlaceOn(p_51042_, p_51043_, p_51044_) || p_51042_.is(Blocks.SAND)  || p_51042_.is(DMBlocks.DUNE_SAND.get());
    }
}
