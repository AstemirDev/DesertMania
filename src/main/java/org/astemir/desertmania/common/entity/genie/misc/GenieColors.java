package org.astemir.desertmania.common.entity.genie.misc;

import net.minecraft.world.entity.EntityType;
import org.astemir.api.math.components.Color;
import org.astemir.desertmania.common.entity.DMEntities;

public class GenieColors {

    public static Color BLUE = new Color(49f / 255f, 160f / 255f, 202f / 255f, 1f);
    public static Color GREEN = new Color(103f / 255f, 185f / 255f, 64f / 255f, 1f);
    public static Color PURPLE = new Color(166f / 255f, 103f / 255f, 212f / 255f, 1f);
    public static Color RED = new Color(208f / 255f, 79f / 255f, 58f / 255f, 1f);



    public static Color fromType(EntityType type){
        if (type == DMEntities.BLUE_GENIE.get()){
            return GenieColors.BLUE;
        }else
        if (type == DMEntities.GREEN_GENIE.get()){
            return GenieColors.GREEN;
        }else
        if (type == DMEntities.RED_GENIE.get()){
            return GenieColors.RED;
        }else
        if (type == DMEntities.PURPLE_GENIE.get()){
            return GenieColors.PURPLE;
        }
        return GenieColors.BLUE;
    }
}
