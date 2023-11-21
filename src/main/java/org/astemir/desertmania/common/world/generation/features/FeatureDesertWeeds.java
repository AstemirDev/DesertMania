package org.astemir.desertmania.common.world.generation.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.astemir.api.common.world.IFeature;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.desertmania.common.block.DMBlocks;
import org.astemir.desertmania.common.misc.BlockStatesTable;


public class FeatureDesertWeeds extends Feature<NoneFeatureConfiguration> implements IFeature<NoneFeatureConfiguration> {

    public static BlockStatesTable PLANTS = new BlockStatesTable(
            new BlockStatesTable.RandomState(50,()->DMBlocks.SHORT_DESERT_GRASS.get().defaultBlockState()),
            new BlockStatesTable.RandomState(30,()->DMBlocks.SAND_GRASS.get().defaultBlockState()),
            new BlockStatesTable.RandomState(15,()->DMBlocks.CAMEL_THORN.get().defaultBlockState()),
            new BlockStatesTable.RandomState(20,()->DMBlocks.DESERT_GRASS.get().defaultBlockState()),
            new BlockStatesTable.RandomState(10,()->DMBlocks.FADED_DESERT_GRASS.get().defaultBlockState())
    );

    public FeatureDesertWeeds(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        if (context.random().nextInt(40) != 0) {
            return false;
        }
        BlockPos pos = surfacePosition(context, 8, 8);
        BlockState state = PLANTS.random().getStateSupplier().get();
        sphere(pos, RandomUtils.randomInt(4,10), 0.3f, 0.5f).forEach((blockPos) -> {
            if (context.level().getBlockState(blockPos).is(Blocks.SAND)) {
                if (context.level().isEmptyBlock(blockPos.above()) && context.random().nextInt(5) == 0) {
                    BlockState toPlace = state;
                    if (context.random().nextInt(20) == 0){
                        toPlace = PLANTS.random().getStateSupplier().get();
                    }
                    placePlant(context.level(),toPlace,blockPos.above());
                }
            }
        });
        return true;
    }

}
