package org.astemir.desertmania.common.world.generation.features;

import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class PalmTreeGrower extends AbstractTreeGrower{

    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource p_222938_, boolean p_222939_) {
        return DMFeatures.Configured.PALM_TREE_BUILDER.getHolder().get();
    }

}
