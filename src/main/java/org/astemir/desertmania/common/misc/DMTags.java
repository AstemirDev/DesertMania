package org.astemir.desertmania.common.misc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.astemir.desertmania.DesertMania;

public class DMTags {

    public static TagKey<Block> SCARAB_SPAWN = BlockTags.create(new ResourceLocation(DesertMania.MOD_ID,"scarab_spawn"));

}
