package org.astemir.desertmania.data;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.astemir.api.data.tag.SkillsTagProvider;
import org.astemir.desertmania.common.block.DMBlocks;
import org.astemir.desertmania.common.misc.DMTags;
import org.jetbrains.annotations.Nullable;

public class DataGeneratorBlockTag extends SkillsTagProvider<Block> {
    
    public DataGeneratorBlockTag(DataGenerator p_126546_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_126546_, Registry.BLOCK, modId, existingFileHelper);
        tags();
        register(p_126546_);
    }

    private void tags(){
        addTag(DMBlocks.OASIS_DIRT.get(),BlockTags.DIRT);
        addTag(DMBlocks.OASIS_FARMLAND.get(),BlockTags.DIRT);
        addTag(DMBlocks.OASIS_GRASS_BLOCK.get(),BlockTags.DIRT);
        addTag(DMBlocks.LOESS, DMTags.SCARAB_SPAWN);
        addTag(DMBlocks.LOESS_BRICKS, DMTags.SCARAB_SPAWN);
        addTag(DMBlocks.LOESS_BRICKS_SYMBOLS, DMTags.SCARAB_SPAWN);
        addTag(DMBlocks.CHISELED_LOESS, DMTags.SCARAB_SPAWN);
        addTag(DMBlocks.CUT_LOESS, DMTags.SCARAB_SPAWN);
        addTag(DMBlocks.SMOOTH_LOESS, DMTags.SCARAB_SPAWN);
        addTag(DMBlocks.GIANT_LOESS_BRICK, DMTags.SCARAB_SPAWN);
        addTag(DMBlocks.SUN_ALTAR,BlockTags.MINEABLE_WITH_PICKAXE);
        addTag(DMBlocks.BRAZIER,BlockTags.MINEABLE_WITH_PICKAXE);
        addTag(DMBlocks.DUNE_SAND, BlockTags.MINEABLE_WITH_SHOVEL);
        addTag(DMBlocks.LOESS, BlockTags.MINEABLE_WITH_PICKAXE);
        addTag(DMBlocks.CHISELED_LOESS, BlockTags.MINEABLE_WITH_PICKAXE);
        addTag(DMBlocks.GIANT_LOESS_BRICK, BlockTags.MINEABLE_WITH_PICKAXE);
        addTag(DMBlocks.PALM_LEAVES, BlockTags.LEAVES);
        addTag(DMBlocks.PALM_LEAVES, BlockTags.MINEABLE_WITH_HOE);
        addTag(DMBlocks.PALM_PLANKS_SIGN, BlockTags.SIGNS,BlockTags.STANDING_SIGNS);
        addTag(DMBlocks.PALM_PLANKS_WALL_SIGN, BlockTags.SIGNS,BlockTags.WALL_SIGNS);
        addTag(DMBlocks.LOESS_BRICKS, BlockTags.MINEABLE_WITH_PICKAXE);
        addTag(DMBlocks.LOESS_BRICKS_SYMBOLS, BlockTags.MINEABLE_WITH_PICKAXE);
        addTag(DMBlocks.LOESS_BRICKS_STAIRS, BlockTags.MINEABLE_WITH_PICKAXE,BlockTags.STAIRS);
        addTag(DMBlocks.LOESS_BRICKS_SLAB, BlockTags.MINEABLE_WITH_PICKAXE,BlockTags.SLABS);
        addTag(DMBlocks.LOESS_BRICKS_WALL, BlockTags.MINEABLE_WITH_PICKAXE,BlockTags.WALLS);
        addTag(DMBlocks.OASIS_GRASS_BLOCK, BlockTags.MINEABLE_WITH_SHOVEL,BlockTags.DIRT);
        addTag(DMBlocks.OASIS_DIRT, BlockTags.MINEABLE_WITH_SHOVEL,BlockTags.DIRT);
        addTag(DMBlocks.CUT_LOESS, BlockTags.MINEABLE_WITH_PICKAXE);
        addTag(DMBlocks.CUT_LOESS_STAIRS, BlockTags.MINEABLE_WITH_PICKAXE,BlockTags.STAIRS);
        addTag(DMBlocks.CUT_LOESS_SLAB, BlockTags.MINEABLE_WITH_PICKAXE,BlockTags.SLABS);
        addTag(DMBlocks.CUT_LOESS_WALL, BlockTags.MINEABLE_WITH_PICKAXE,BlockTags.WALLS);
        addTag(DMBlocks.SMOOTH_LOESS, BlockTags.MINEABLE_WITH_PICKAXE);
        addTag(DMBlocks.SMOOTH_LOESS_STAIRS, BlockTags.MINEABLE_WITH_PICKAXE,BlockTags.STAIRS);
        addTag(DMBlocks.SMOOTH_LOESS_SLAB, BlockTags.MINEABLE_WITH_PICKAXE,BlockTags.SLABS);
        addTag(DMBlocks.SMOOTH_LOESS_WALL, BlockTags.MINEABLE_WITH_PICKAXE,BlockTags.WALLS);
        addTag(DMBlocks.PALM_LOG, BlockTags.MINEABLE_WITH_AXE,BlockTags.OVERWORLD_NATURAL_LOGS,BlockTags.LOGS,BlockTags.LOGS_THAT_BURN);
        addTag(DMBlocks.PALM_LOG_STRIPPED, BlockTags.MINEABLE_WITH_AXE,BlockTags.LOGS,BlockTags.LOGS_THAT_BURN);
        addTag(DMBlocks.PALM_WOOD, BlockTags.MINEABLE_WITH_AXE,BlockTags.OVERWORLD_NATURAL_LOGS,BlockTags.LOGS,BlockTags.LOGS_THAT_BURN);
        addTag(DMBlocks.PALM_WOOD_STRIPPED, BlockTags.MINEABLE_WITH_AXE,BlockTags.LOGS,BlockTags.LOGS_THAT_BURN);
        addTag(DMBlocks.PALM_SAPLING, BlockTags.SAPLINGS);
        addTag(DMBlocks.PALM_PLANKS, BlockTags.MINEABLE_WITH_AXE,BlockTags.PLANKS);
        addTag(DMBlocks.PALM_PLANKS_SLAB, BlockTags.MINEABLE_WITH_AXE,BlockTags.SLABS,BlockTags.WOODEN_SLABS);
        addTag(DMBlocks.PALM_PLANKS_BUTTON, BlockTags.MINEABLE_WITH_AXE,BlockTags.BUTTONS,BlockTags.WOODEN_BUTTONS);
        addTag(DMBlocks.PALM_PLANKS_DOOR, BlockTags.MINEABLE_WITH_AXE,BlockTags.DOORS, BlockTags.WOODEN_DOORS);
        addTag(DMBlocks.PALM_PLANKS_TRAPDOOR, BlockTags.MINEABLE_WITH_AXE,BlockTags.TRAPDOORS, BlockTags.WOODEN_TRAPDOORS);
        addTag(DMBlocks.PALM_PLANKS_GATE, BlockTags.MINEABLE_WITH_AXE,BlockTags.FENCE_GATES);
        addTag(DMBlocks.PALM_PLANKS_STAIRS, BlockTags.MINEABLE_WITH_AXE,BlockTags.STAIRS,BlockTags.WOODEN_STAIRS);
        addTag(DMBlocks.PALM_PLANKS_FENCE, BlockTags.MINEABLE_WITH_AXE,BlockTags.FENCES, BlockTags.WOODEN_FENCES);
        addTag(DMBlocks.PALM_PLANKS_PRESSURE_PLATE, BlockTags.MINEABLE_WITH_AXE,BlockTags.WOODEN_PRESSURE_PLATES,BlockTags.PRESSURE_PLATES);
        addTag(DMBlocks.COCONUT_BLOCK, BlockTags.MINEABLE_WITH_AXE);

    }

}
