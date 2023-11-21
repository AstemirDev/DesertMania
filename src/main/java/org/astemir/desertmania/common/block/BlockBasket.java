package org.astemir.desertmania.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.astemir.desertmania.common.blockentity.BlockEntityBasket;
import org.jetbrains.annotations.Nullable;

public class BlockBasket extends BaseEntityBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public static final BooleanProperty CLOSED = DMBlockProperties.CLOSED;

    protected BlockBasket() {
        super(Properties.of(Material.METAL, MaterialColor.COLOR_YELLOW).noOcclusion().sound(SoundType.WOOL));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(CLOSED,false));
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_49573_) {
        return this.defaultBlockState().setValue(FACING,p_49573_.getHorizontalDirection().getOpposite()).setValue(CLOSED,false);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49653_) {
        return RenderShape.MODEL;
    }


    public BlockState rotate(BlockState p_53157_, Rotation p_53158_) {
        return p_53157_.setValue(FACING, p_53158_.rotate(p_53157_.getValue(FACING)));
    }

    public BlockState mirror(BlockState p_53154_, Mirror p_53155_) {
        return p_53154_.rotate(p_53155_.getRotation(p_53154_.getValue(FACING)));
    }

    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof BlockEntityBasket) {
                Containers.dropContents(pLevel, pPos, (BlockEntityBasket)blockentity);
                pLevel.updateNeighbourForOutputSignal(pPos, this);
            }
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if (pEntity instanceof ItemEntity itemEntity){
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof BlockEntityBasket blockEntityBasket) {
                boolean closed = pLevel.getBlockState(pPos).getValue(CLOSED);
                if (!closed) {
                    if (!HopperBlockEntity.addItem(blockEntityBasket, itemEntity)) {
                        pLevel.setBlock(pPos, pState.setValue(CLOSED, true), 19);
                    }
                }
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49646_) {
        p_49646_.add(FACING,CLOSED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new BlockEntityBasket(p_153215_,p_153216_);
    }
}
