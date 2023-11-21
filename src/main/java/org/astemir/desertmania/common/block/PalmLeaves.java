package org.astemir.desertmania.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import org.astemir.api.common.block.BlocksUtils;

import static org.astemir.desertmania.common.block.DMBlockProperties.PALM_LEAVES_DISTANCE;

public class PalmLeaves extends LeavesBlock {

    public PalmLeaves() {
        super(BlockBehaviour.Properties.of(Material.LEAVES).strength(BlocksUtils.STRENGTH_LEAVES).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(BlocksUtils::ocelotOrParrot).isSuffocating((a, b, c)->false).isViewBlocking((a, b, c)->false));
        this.registerDefaultState(this.stateDefinition.any().setValue(DISTANCE, Integer.valueOf(7)).setValue(PALM_LEAVES_DISTANCE,Integer.valueOf(14)).setValue(PERSISTENT, Boolean.valueOf(false)).setValue(WATERLOGGED, Boolean.valueOf(false)));
    }


    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return pState.getValue(PALM_LEAVES_DISTANCE) == 14 && !pState.getValue(PERSISTENT);
    }

    @Override
    protected boolean decaying(BlockState p_221386_) {
        return !p_221386_.getValue(PERSISTENT) && p_221386_.getValue(PALM_LEAVES_DISTANCE) == 14;
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        pLevel.setBlock(pPos, updateDistancePalm(pState, pLevel, pPos), 3);
    }

    private static BlockState updateDistancePalm(BlockState pState, LevelAccessor pLevel, BlockPos pPos) {
        int i = 14;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(Direction direction : Direction.values()) {
            blockpos$mutableblockpos.setWithOffset(pPos, direction);
            i = Math.min(i, getDistanceAt(pLevel.getBlockState(blockpos$mutableblockpos)) + 1);
            if (i == 1) {
                break;
            }
        }
        return pState.setValue(PALM_LEAVES_DISTANCE, Integer.valueOf(i));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(DISTANCE, PALM_LEAVES_DISTANCE,PERSISTENT, WATERLOGGED);
    }

    private static int getDistanceAt(BlockState pNeighbor) {
        if (pNeighbor.is(BlockTags.LOGS)) {
            return 0;
        } else {
            return pNeighbor.getBlock() instanceof PalmLeaves ? pNeighbor.getValue(PALM_LEAVES_DISTANCE) : 14;
        }
    }
}
