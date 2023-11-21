package org.astemir.desertmania.common.block;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class DMBlockProperties {

    public static BooleanProperty CLOSED = BooleanProperty.create("closed");
    public static IntegerProperty PALM_LEAVES_DISTANCE = IntegerProperty.create("palm_leaves_distance", 1, 14);

}
