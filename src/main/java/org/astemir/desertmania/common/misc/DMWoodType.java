package org.astemir.desertmania.common.misc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.astemir.desertmania.DesertMania;

public class DMWoodType extends WoodType {

    public static final WoodType PALM = create(new ResourceLocation(DesertMania.MOD_ID,"palm").toString());

    protected DMWoodType(String p_61842_) {
        super(p_61842_);
    }
}
