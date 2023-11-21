package org.astemir.desertmania.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.astemir.desertmania.common.blockentity.BlockEntityMagicBasket;
import org.jetbrains.annotations.Nullable;

public class BlockMagicBasket extends BlockBasket {


    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof BlockEntityMagicBasket) {
                Containers.dropContents(pLevel, pPos, (BlockEntityMagicBasket)blockentity);
                pLevel.updateNeighbourForOutputSignal(pPos, this);
            }

            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if (pEntity instanceof ItemEntity itemEntity){
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof BlockEntityMagicBasket blockEntityBasket) {
                boolean closed = pLevel.getBlockState(pPos).getValue(CLOSED);
                if (!closed) {
                    if (!HopperBlockEntity.addItem(blockEntityBasket, itemEntity)) {
                        pLevel.setBlock(pPos, pState.setValue(CLOSED, true), 19);
                    }
                }
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new BlockEntityMagicBasket(p_153215_,p_153216_);
    }
}
