package org.astemir.desertmania.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.astemir.api.common.animation.AnimatedBlockEntity;
import org.astemir.api.common.animation.objects.IAnimatedBlock;
import org.astemir.api.common.block.BlocksUtils;
import org.astemir.desertmania.common.blockentity.BlockEntitySunAltar;
import org.astemir.desertmania.common.item.DMItems;
import org.astemir.desertmania.common.world.DMWorldData;
import org.astemir.example.common.block.BlockExampleCosmicBeacon;
import org.jetbrains.annotations.Nullable;

public class BlockSunAltar extends BaseEntityBlock {

    private static final VoxelShape SHAPE_A = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D);
    private static final VoxelShape SHAPE_B = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D);
    private static final VoxelShape SHAPE_C = Block.box(2.0D, 10.0D, 2.0D, 14.0D, 12.0D, 14.0D);
    private static final VoxelShape SHAPE = Shapes.or(SHAPE_A,SHAPE_B,SHAPE_C);

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    protected BlockSunAltar() {
        super(Properties.of(Material.METAL, MaterialColor.COLOR_YELLOW).strength(BlocksUtils.STRENGTH_OBSIDIAN,40).noOcclusion().sound(SoundType.STONE));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }



    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer.isCrouching()){
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity != null) {
                if (blockEntity instanceof BlockEntitySunAltar altar) {
                    ItemEntity itementity = new ItemEntity(pLevel, pPos.getX(), pPos.getY() + (double) 1, pPos.getZ(), altar.getAmulet());
                    itementity.setDefaultPickUpDelay();
                    pLevel.addFreshEntity(itementity);
                    pLevel.setBlock(pPos, DMBlocks.BRAZIER.get().defaultBlockState(), 4);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        if (!pLevel.isClientSide) {
            DMWorldData.getInstance(pLevel).setTimeStop(pLevel, false);
        }
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if (blockentity instanceof BlockEntitySunAltar) {
            if (!pLevel.isClientSide) {
                ItemStack itemstack = DMItems.SUN_ALTAR.get().getDefaultInstance();
                blockentity.saveToItem(itemstack);
                ItemEntity itementity = new ItemEntity(pLevel, (double)pPos.getX() + 0.5D, (double)pPos.getY() + 0.5D, (double)pPos.getZ() + 0.5D, itemstack);
                itementity.setDefaultPickUpDelay();
                pLevel.addFreshEntity(itementity);
            }
        }

        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_49573_) {
        return this.defaultBlockState().setValue(FACING,p_49573_.getHorizontalDirection().getOpposite());
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

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49646_) {
        p_49646_.add(FACING);
    }

    private VoxelShape getVoxelShape(BlockState p_49767_) {
        return SHAPE;
    }

    public VoxelShape getCollisionShape(BlockState p_49760_, BlockGetter p_49761_, BlockPos p_49762_, CollisionContext p_49763_) {
        return this.getVoxelShape(p_49760_);
    }

    public VoxelShape getShape(BlockState p_49755_, BlockGetter p_49756_, BlockPos p_49757_, CollisionContext p_49758_) {
        return this.getVoxelShape(p_49755_);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return createTickerHelper(p_153214_,p_153214_, IAnimatedBlock::onTick);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new BlockEntitySunAltar(p_153215_,p_153216_);
    }
}
