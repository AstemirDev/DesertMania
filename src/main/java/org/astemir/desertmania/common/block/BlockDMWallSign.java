package org.astemir.desertmania.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.astemir.desertmania.common.blockentity.BlockEntityDMSign;

public class BlockDMWallSign extends WallSignBlock {

    public BlockDMWallSign(Properties p_58068_, WoodType p_58069_) {
        super(p_58068_, p_58069_);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos p_154556_, BlockState p_154557_) {
        return new BlockEntityDMSign(p_154556_,p_154557_);
    }
}
