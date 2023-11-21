package org.astemir.desertmania.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.astemir.api.data.model.ItemModel;
import org.astemir.api.data.model.SkillsItemModelProvider;
import org.astemir.desertmania.common.item.DMItems;

public class DataGeneratorItemModel extends SkillsItemModelProvider {
    
    public DataGeneratorItemModel(DataGenerator generator, String modId, ExistingFileHelper existingFileHelper) {
        super(generator, modId, existingFileHelper);
        models();
        register(generator);
    }


    private void models(){
        handheld();
        spawnEggs();
        blocks();
        other();
    }


    private void handheld(){
        addModel(DMItems.SCIMITAR, ItemModel.HANDHELD);
    }

    private void spawnEggs(){
        addModel(DMItems.MEERKAT_SPAWN_EGG, ItemModel.SPAWN_EGG);
        addModel(DMItems.CAMEL_SPAWN_EGG, ItemModel.SPAWN_EGG);
        addModel(DMItems.DESERT_WORM_SPAWN_EGG, ItemModel.SPAWN_EGG);
        addModel(DMItems.MUMMY_SPAWN_EGG, ItemModel.SPAWN_EGG);
        addModel(DMItems.SCARAB_SPAWN_EGG, ItemModel.SPAWN_EGG);
        addModel(DMItems.SCARAB_LORD_SPAWN_EGG, ItemModel.SPAWN_EGG);
        addModel(DMItems.FENICK_SPAWN_EGG, ItemModel.SPAWN_EGG);
        addModel(DMItems.BLUE_GENIE_SPAWN_EGG, ItemModel.SPAWN_EGG);
        addModel(DMItems.GREEN_GENIE_SPAWN_EGG, ItemModel.SPAWN_EGG);
        addModel(DMItems.RED_GENIE_SPAWN_EGG, ItemModel.SPAWN_EGG);
        addModel(DMItems.PURPLE_GENIE_SPAWN_EGG, ItemModel.SPAWN_EGG);
        addModel(DMItems.FENICK_MAGE_SPAWN_EGG, ItemModel.SPAWN_EGG);
    }

    private void blocks(){
        addModel(DMItems.DUNE_SAND,ItemModel.BLOCK_ITEM);
        addModel(DMItems.LOESS,ItemModel.BLOCK_ITEM);
        addModel(DMItems.CHISELED_LOESS,ItemModel.BLOCK_ITEM);
        addModel(DMItems.LOESS_BRICKS,ItemModel.BLOCK_ITEM);
        addModel(DMItems.LOESS_BRICKS_SYMBOLS,ItemModel.BLOCK_ITEM);
        addModel(DMItems.LOESS_BRICKS_STAIRS,ItemModel.BLOCK_ITEM);
        addModel(DMItems.LOESS_BRICKS_SLAB,ItemModel.BLOCK_ITEM);
        addModel(DMItems.CUT_LOESS,ItemModel.BLOCK_ITEM);
        addModel(DMItems.CUT_LOESS_STAIRS,ItemModel.BLOCK_ITEM);
        addModel(DMItems.CUT_LOESS_WALL,ItemModel.WALL_ITEM);
        addModel(DMItems.CUT_LOESS_SLAB,ItemModel.BLOCK_ITEM);
        addModel(DMItems.SMOOTH_LOESS,ItemModel.BLOCK_ITEM);
        addModel(DMItems.SMOOTH_LOESS_STAIRS,ItemModel.BLOCK_ITEM);
        addModel(DMItems.SMOOTH_LOESS_SLAB,ItemModel.BLOCK_ITEM);
        addModel(DMItems.LOESS_BRICKS_WALL,ItemModel.WALL_ITEM);
        addModel(DMItems.SMOOTH_LOESS_WALL,ItemModel.WALL_ITEM);
        addModel(DMItems.PALM_LOG,ItemModel.BLOCK_ITEM);
        addModel(DMItems.PALM_LOG_STRIPPED,ItemModel.BLOCK_ITEM);
        addModel(DMItems.PALM_WOOD,ItemModel.BLOCK_ITEM);
        addModel(DMItems.PALM_WOOD_STRIPPED,ItemModel.BLOCK_ITEM);
        addModel(DMItems.PALM_PLANKS,ItemModel.BLOCK_ITEM);
        addModel(DMItems.PALM_PLANKS_STAIRS,ItemModel.BLOCK_ITEM);
        addModel(DMItems.PALM_PLANKS_PRESSURE_PLATE,ItemModel.BLOCK_ITEM);
        addModel(DMItems.PALM_PLANKS_BUTTON,ItemModel.BUTTON_ITEM);
        addModel(DMItems.PALM_PLANKS_GATE,ItemModel.FENCE_GATE_ITEM);
        addModel(DMItems.PALM_PLANKS_FENCE,ItemModel.FENCE_ITEM);
        addModel(DMItems.PALM_PLANKS_SLAB,ItemModel.BLOCK_ITEM);
        addModel(DMItems.PALM_PLANKS_TRAPDOOR,ItemModel.TRAPDOOR_ITEM);
        addModel(DMItems.COCONUT_BLOCK,ItemModel.BLOCK_ITEM);
        addModel(DMItems.OASIS_GRASS_BLOCK,ItemModel.BLOCK_ITEM);
        addModel(DMItems.PALM_LEAVES,ItemModel.BLOCK_ITEM);
        addModel(DMItems.BRAZIER,ItemModel.BLOCK_ITEM);
        addModel(DMItems.OASIS_DIRT,ItemModel.BLOCK_ITEM);
        addModel(DMItems.FADED_DESERT_GRASS,ItemModel.TALL_GRASS);
        addModel(DMItems.DESERT_GRASS,ItemModel.TALL_GRASS);
        addModel(DMItems.OASIS_TALL_GRASS,ItemModel.TALL_GRASS);
        addModel(DMItems.OASIS_GRASS,ItemModel.GRASS);
        addModel(DMItems.SAND_GRASS,ItemModel.GRASS);
        addModel(DMItems.SHORT_DESERT_GRASS,ItemModel.GRASS);
        addModel(DMItems.CAMEL_THORN,ItemModel.GRASS);
    }

    private void other(){
        addModel(DMItems.MUSIC_DISC_DESERTMANIA,ItemModel.GENERATED);
        addModel(DMItems.STING,ItemModel.GENERATED);
        addModel(DMItems.ANCIENT_AMULET,ItemModel.GENERATED);
        addModel(DMItems.STING_ARROW,ItemModel.GENERATED);
        addModel(DMItems.GENIE_LAMP, ItemModel.GENERATED);
        addModel(DMItems.GOLDEN_SCARAB, ItemModel.GENERATED);
        addModel(DMItems.GOLDEN_SCARAB_LEFT_PART, ItemModel.GENERATED);
        addModel(DMItems.GOLDEN_SCARAB_RIGHT_PART, ItemModel.GENERATED);
        addModel(DMItems.CLOTH, ItemModel.GENERATED);
        addModel(DMItems.SHEMAGH, ItemModel.GENERATED);
        addModel(DMItems.ENCHANTED_SCROLL, ItemModel.GENERATED);
        addModel(DMItems.BLUE_FLYING_CARPET, ItemModel.GENERATED);
        addModel(DMItems.RED_FLYING_CARPET, ItemModel.GENERATED);
        addModel(DMItems.GREEN_FLYING_CARPET, ItemModel.GENERATED);
        addModel(DMItems.PALM_PLANKS_DOOR,ItemModel.GENERATED);
        addModel(DMItems.PALM_SAPLING,ItemModel.GENERATED);
        addModel(DMItems.COCONUT_SLICE,ItemModel.GENERATED);
        addModel(DMItems.PALM_PLANKS_BOAT,ItemModel.GENERATED);
        addModel(DMItems.PALM_PLANKS_CHEST_BOAT,ItemModel.GENERATED);
        addModel(DMItems.PALM_PLANKS_SIGN,ItemModel.GENERATED);
    }
}
