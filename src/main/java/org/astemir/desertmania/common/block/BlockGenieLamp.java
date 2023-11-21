package org.astemir.desertmania.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.astemir.api.common.animation.objects.IAnimatedBlock;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.common.handler.WorldEventHandler;
import org.astemir.api.network.PacketArgument;
import org.astemir.desertmania.client.handler.GenieClientEventHandler;
import org.astemir.desertmania.common.blockentity.BlockEntityGenieLamp;
import org.astemir.desertmania.common.entity.genie.EntityAbstractGenie;
import org.astemir.desertmania.common.entity.genie.misc.GenieColors;
import org.astemir.desertmania.common.item.DMItems;
import org.jetbrains.annotations.Nullable;

public class BlockGenieLamp extends BaseEntityBlock {

    private static final VoxelShape SHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D);

    public static final IntegerProperty ROTATION = IntegerProperty.create("rotation", 0, 15);

    protected BlockGenieLamp() {
        super(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_YELLOW).noOcclusion().sound(SoundType.CHAIN).instabreak());
        this.registerDefaultState(this.stateDefinition.any().setValue(ROTATION, Integer.valueOf(0)));
    }


    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            if (level.getBlockEntity(pos) instanceof BlockEntityGenieLamp lamp){
                if (!lamp.isEmpty()){
                    if (lamp.getGenieData().isEmpty()) {
                        lamp.getGenieData().randomize();
                    }
                    WorldEventHandler.playClientEvent(level,pos, GenieClientEventHandler.EVENT_GENIE_LAMP_SYNC.getValue(),lamp.getGenieData().entityIdPacket(), PacketArgument.create(PacketArgument.ArgumentType.COLOR,lamp.getGenieData().getColor()));
                    lamp.setClickedPlayer(player);
                    lamp.getAnimationFactory().play(BlockEntityGenieLamp.ANIMATION_SHAKE);
                }else{
                    if (lamp.isEmpty()) {
                        EntityAbstractGenie genie = EntityUtils.getEntity(EntityAbstractGenie.class, level, pos, 3);
                        if (genie != null) {
                            if (genie.getSpawnController().isNoAction()) {
                                genie.getSpawnController().playAction(EntityAbstractGenie.ACTION_HIDE);
                                lamp.getGenieData().setType(genie.getType());
                                lamp.getGenieData().setColor(GenieColors.fromType(genie.getType()));
                                lamp.setEmpty(false);
                                WorldEventHandler.playClientEvent(level,pos, GenieClientEventHandler.EVENT_GENIE_LAMP_SYNC.getValue(),lamp.getGenieData().entityIdPacket(), PacketArgument.create(PacketArgument.ArgumentType.COLOR,lamp.getGenieData().getColor()));
                            }
                        }
                    }
                }
            }
            return InteractionResult.CONSUME;
        }
    }

    public void playerWillDestroy(Level p_56212_, BlockPos p_56213_, BlockState p_56214_, Player p_56215_) {
        BlockEntity blockentity = p_56212_.getBlockEntity(p_56213_);
        if (blockentity instanceof BlockEntityGenieLamp blockEntityGenieLamp) {
            if (!p_56212_.isClientSide) {
                ItemStack itemstack = DMItems.GENIE_LAMP.get().getDefaultInstance();
                blockentity.saveToItem(itemstack);
                ItemEntity itementity = new ItemEntity(p_56212_, (double)p_56213_.getX() + 0.5D, (double)p_56213_.getY() + 0.5D, (double)p_56213_.getZ() + 0.5D, itemstack);
                itementity.setDefaultPickUpDelay();
                p_56212_.addFreshEntity(itementity);
            }
        }

        super.playerWillDestroy(p_56212_, p_56213_, p_56214_, p_56215_);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_49573_) {
        return this.defaultBlockState().setValue(ROTATION, Integer.valueOf(Mth.floor((double)(p_49573_.getRotation() * 16.0F / 360.0F) + 0.5D) & 15));
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49653_) {
        return RenderShape.MODEL;
    }


    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(ROTATION, Integer.valueOf(rotation.rotate(blockState.getValue(ROTATION), 16)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.setValue(ROTATION, Integer.valueOf(mirror.mirror(blockState.getValue(ROTATION), 16)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49646_) {
        p_49646_.add(ROTATION);
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

    public boolean canSurvive(BlockState p_153479_, LevelReader p_153480_, BlockPos p_153481_) {
        return Block.canSupportCenter(p_153480_, p_153481_.relative(Direction.DOWN), Direction.DOWN.getOpposite());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return createTickerHelper(p_153214_,p_153214_, IAnimatedBlock::onTick);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new BlockEntityGenieLamp(p_153215_,p_153216_);
    }
}
