package org.astemir.desertmania.common.misc;

import net.minecraft.world.level.block.state.BlockState;
import org.astemir.api.math.random.WeightedRandom;

import java.util.function.Supplier;

public class BlockStatesTable {

    private final WeightedRandom<RandomState> table = new WeightedRandom<>();

    public BlockStatesTable(RandomState... states) {
        for (RandomState state : states) {
            table.add(state.getChance(),state);
        }
        table.build();
    }

    public RandomState random(){
        return table.random();
    }

    public static class RandomState{

        private final Supplier<BlockState> stateSupplier;
        private final double chance;

        public RandomState(double chance,Supplier<BlockState> stateSupplier) {
            this.stateSupplier = stateSupplier;
            this.chance = chance;
        }

        public double getChance() {
            return chance;
        }

        public Supplier<BlockState> getStateSupplier() {
            return stateSupplier;
        }
    }
}
