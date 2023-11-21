package org.astemir.desertmania.common.world.generation.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.astemir.api.common.world.IFeature;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.desertmania.common.block.DMBlocks;

public class FeatureDesertDunes extends Feature<NoneFeatureConfiguration> implements IFeature<NoneFeatureConfiguration> {

    public FeatureDesertDunes(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        if (context.random().nextInt(20) != 0){
            return false;
        }
        BlockPos pos = surfacePosition(context,8,8);
        sphere(pos, RandomUtils.randomInt(10,32),0.3f,0.5f).forEach((blockPos)->{
            if (context.level().getBlockState(blockPos).is(Blocks.SAND)){
                if (context.random().nextInt(2) == 0){
                    context.level().setBlock(blockPos, DMBlocks.DUNE_SAND.get().defaultBlockState(),4);
                }
            }
        });
        return true;
    }
}
