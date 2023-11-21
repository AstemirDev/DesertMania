package org.astemir.desertmania.common.entity.camel;

import net.minecraft.world.item.DyeColor;

public enum CamelDecoration{
    NO_DECORATION(-1),BLACK(0),BLUE(1),BROWN(2),CYAN(3),GRAY(4),GREEN(5),LIGHT_BLUE(6),LIGHT_GRAY(7),LIME(8),MAGENTA(9),ORANGE(10),PINK(11),PURPLE(12),RED(13),WHITE(14),YELLOW(15);

    private int id = -1;

    CamelDecoration(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public static CamelDecoration getFromDye(DyeColor color){
        if (color == null){
            return NO_DECORATION;
        }
        switch (color){
            case BLACK->{
                return BLACK;
            }
            case BLUE ->{
                return BLUE;
            }
            case BROWN ->{
                return BROWN;
            }
            case CYAN ->{
                return CYAN;
            }
            case GRAY ->{
                return GRAY;
            }
            case LIME ->{
                return LIME;
            }
            case PINK ->{
                return PINK;
            }
            case GREEN ->{
                return GREEN;
            }
            case WHITE ->{
                return WHITE;
            }
            case ORANGE ->{
                return ORANGE;
            }
            case PURPLE ->{
                return PURPLE;
            }
            case YELLOW ->{
                return YELLOW;
            }
            case MAGENTA ->{
                return MAGENTA;
            }
            case LIGHT_BLUE ->{
                return LIGHT_BLUE;
            }
            case LIGHT_GRAY ->{
                return LIGHT_GRAY;
            }
            case RED -> {
                return RED;
            }

        }
        return NO_DECORATION;
    }

    public static CamelDecoration getFromId(int id){
        for (CamelDecoration value : values()) {
            if (value.getId() == id){
                return value;
            }
        }
        return NO_DECORATION;
    }
}
