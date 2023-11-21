package org.astemir.desertmania.common.block;


import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class BlockFireTrap extends Block {


    public BlockFireTrap() {
        super(Properties.of(Material.STONE).noOcclusion().strength(3,3).randomTicks());
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        super.randomTick(pState, pLevel, pPos, pRandom);
        for (int i = 0;i<10;i++) {
            pLevel.addParticle(ParticleTypes.FLAME, pPos.getX(), pPos.getY() + 1, pPos.getZ(), 0.1f, 0.1f, 0.1f);
        }
        pLevel.setBlock(pPos.above(), Blocks.FIRE.defaultBlockState(),3);
    }
}
