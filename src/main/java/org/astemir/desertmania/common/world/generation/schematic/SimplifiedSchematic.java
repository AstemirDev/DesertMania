package org.astemir.desertmania.common.world.generation.schematic;

import org.astemir.api.common.world.schematic.WESchematic;
import org.astemir.api.io.FileUtils;
import org.astemir.desertmania.DesertMania;


public class SimplifiedSchematic extends WESchematic {

    public SimplifiedSchematic(String path) {
        super(FileUtils.getResource(DesertMania.MOD_ID+":"+"data/schematics/"+path));
    }


    @Override
    public SimplifiedSchematic replacement(StateReplacement replacement){
        return (SimplifiedSchematic) super.replacement(replacement);
    }

}
