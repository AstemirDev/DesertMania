package org.astemir.desertmania.common.entity.boat;

import net.minecraft.world.level.block.Block;
import org.astemir.desertmania.common.block.DMBlocks;

public enum DMBoatType {

    PALM(DMBlocks.PALM_PLANKS.get(), "palm");

    private final String name;
    private final Block planks;

    DMBoatType(Block p_38427_, String p_38428_) {
        this.name = p_38428_;
        this.planks = p_38427_;
    }

    public String getName() {
        return this.name;
    }

    public Block getPlanks() {
        return this.planks;
    }

    public String toString() {
        return this.name;
    }

    public static DMBoatType byId(int p_38431_) {
        DMBoatType[] aboat$type = values();
        if (p_38431_ < 0 || p_38431_ >= aboat$type.length) {
            p_38431_ = 0;
        }

        return aboat$type[p_38431_];
    }

    public static DMBoatType byName(String p_38433_) {
        DMBoatType[] aboat$type = values();

        for(int i = 0; i < aboat$type.length; ++i) {
            if (aboat$type[i].getName().equals(p_38433_)) {
                return aboat$type[i];
            }
        }

        return aboat$type[0];
    }
}
