package org.astemir.desertmania.common.world.generation.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.astemir.api.common.world.IFeature;
import org.astemir.desertmania.common.block.DMBlocks;


public class FeatureDesertPalm extends Feature<NoneFeatureConfiguration> implements IFeature<NoneFeatureConfiguration> {


    public FeatureDesertPalm(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        if (context.random().nextInt(100) != 0) {
            return false;
        }
        BlockPos pos = surfacePosition(context, 8, 8);
        if (context.level().getBlockState(pos.below()).is(Blocks.SAND) || context.level().getBlockState(pos.below()).is(DMBlocks.DUNE_SAND.get())) {
            DMFeatures.Configured.PALM_TREE_BUILDER.get().place(context.level(),context.chunkGenerator(),context.random(),pos);
        }
        return true;
    }

}
