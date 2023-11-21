package org.astemir.desertmania.data.lang;

import org.astemir.api.data.lang.LangDictionary;
import org.astemir.desertmania.common.block.DMBlocks;
import org.astemir.desertmania.common.effect.DMMobEffects;
import org.astemir.desertmania.common.enchantments.DMEnchantments;
import org.astemir.desertmania.common.entity.DMEntities;
import org.astemir.desertmania.common.item.DMItems;

public class LocalizationEnglish {

    private final LangDictionary lang;

    public LocalizationEnglish(LangDictionary dictionary) {
        this.lang = dictionary;
        items();
        blocks();
        entities();
        effects();
        lang.add("name.desertmania.empty","(Empty)");
        lang.add("container.desertmania.basket","Basket");
        lang.add("container.desertmania.magic_basket","Magic Basket");
        lang.add("item.desertmania.music_disc_desertmania.desc","Jonas Zaggo - Desert Mania");
        lang.addEnchantment(DMEnchantments.WHIRLING,"Whirling");
    }


    private void items(){
        lang.addItem(DMItems.MUSIC_DISC_DESERTMANIA,"Music Disc");
        lang.addItem(DMItems.GOLDEN_SCARAB, "Golden Scarab");
        lang.addItem(DMItems.GOLDEN_SCARAB_LEFT_PART, "Golden Scarab Left Part");
        lang.addItem(DMItems.GOLDEN_SCARAB_RIGHT_PART, "Golden Scarab Right Part");
        lang.addItem(DMItems.CLOTH, "Mummy's Cloth");
        lang.addItem(DMItems.SHEMAGH, "Shemagh");
        lang.addItem(DMItems.ENCHANTED_SCROLL, "Enchanted Scroll");
        lang.addItem(DMItems.SCIMITAR,"Scimitar");
        lang.addItem(DMItems.BLUE_GENIE_SPAWN_EGG,"Blue Genie Spawn Egg");
        lang.addItem(DMItems.GREEN_GENIE_SPAWN_EGG,"Green Genie Spawn Egg");
        lang.addItem(DMItems.RED_GENIE_SPAWN_EGG,"Red Genie Spawn Egg");
        lang.addItem(DMItems.PURPLE_GENIE_SPAWN_EGG,"Purple Genie Spawn Egg");
        lang.addItem(DMItems.MEERKAT_SPAWN_EGG,"Meerkat Spawn Egg");
        lang.addItem(DMItems.CAMEL_SPAWN_EGG,"Camel Spawn Egg");
        lang.addItem(DMItems.DESERT_WORM_SPAWN_EGG,"Desert Worm Spawn Egg");
        lang.addItem(DMItems.MUMMY_SPAWN_EGG,"Mummy Spawn Egg");
        lang.addItem(DMItems.SCARAB_SPAWN_EGG,"Scarab Spawn Egg");
        lang.addItem(DMItems.FENICK_SPAWN_EGG,"Fenick Spawn Egg");
        lang.addItem(DMItems.SCARAB_LORD_SPAWN_EGG,"Scarab-Lord Spawn Egg");
        lang.addItem(DMItems.FENICK_MAGE_SPAWN_EGG,"Fenick Mage Spawn Egg");
        lang.addItem(DMItems.BLUE_FLYING_CARPET,"Blue Flying Carpet");
        lang.addItem(DMItems.RED_FLYING_CARPET,"Red Flying Carpet");
        lang.addItem(DMItems.GREEN_FLYING_CARPET,"Green Flying Carpet");
        lang.addItem(DMItems.PALM_PLANKS_BOAT,"Palm Boat");
        lang.addItem(DMItems.PALM_PLANKS_CHEST_BOAT,"Palm Chest Boat");
        lang.addItem(DMItems.COCONUT_SLICE,"Coconut Slice");
        lang.addItem(DMItems.STING,"Sting");
        lang.addItem(DMItems.STING_ARROW,"Sting Arrow");
        lang.addItem(DMItems.ANCIENT_AMULET,"Ancient Amulet");
    }

    private void blocks(){
        lang.addBlock(DMBlocks.DUNE_SAND,"Dune Sand");
        lang.addBlock(DMBlocks.LOESS,"Loess");
        lang.addBlock(DMBlocks.CHISELED_LOESS,"Chiseled Loess");
        lang.addBlock(DMBlocks.GIANT_LOESS_BRICK,"Giant Cut Loess Brick");
        lang.addBlock(DMBlocks.CUT_LOESS,"Cut Loess");
        lang.addBlock(DMBlocks.CUT_LOESS_STAIRS,"Cut Loess Stairs");
        lang.addBlock(DMBlocks.CUT_LOESS_WALL,"Cut Loess Wall");
        lang.addBlock(DMBlocks.CUT_LOESS_SLAB,"Cut Loess Slab");
        lang.addBlock(DMBlocks.LOESS_BRICKS,"Loess Bricks");
        lang.addBlock(DMBlocks.LOESS_BRICKS_SYMBOLS,"Loess Bricks Symbols");
        lang.addBlock(DMBlocks.LOESS_BRICKS_STAIRS,"Loess Bricks Stairs");
        lang.addBlock(DMBlocks.LOESS_BRICKS_WALL,"Loess Bricks Wall");
        lang.addBlock(DMBlocks.LOESS_BRICKS_SLAB,"Loess Bricks Slab");
        lang.addBlock(DMBlocks.SMOOTH_LOESS,"Smooth Loess");
        lang.addBlock(DMBlocks.SMOOTH_LOESS_STAIRS,"Smooth Loess Stairs");
        lang.addBlock(DMBlocks.SMOOTH_LOESS_WALL,"Smooth Loess Wall");
        lang.addBlock(DMBlocks.SMOOTH_LOESS_SLAB,"Smooth Loess Slab");
        lang.addBlock(DMBlocks.GENIE_LAMP,"Genie Lamp");
        lang.addBlock(DMBlocks.PALM_LOG,"Palm Log");
        lang.addBlock(DMBlocks.PALM_WOOD,"Palm Wood");
        lang.addBlock(DMBlocks.PALM_LOG_STRIPPED,"Stripped Palm Log");
        lang.addBlock(DMBlocks.PALM_WOOD_STRIPPED,"Stripped Palm Wood");
        lang.addBlock(DMBlocks.PALM_PLANKS,"Palm Planks");
        lang.addBlock(DMBlocks.PALM_PLANKS_DOOR,"Palm Door");
        lang.addBlock(DMBlocks.PALM_PLANKS_BUTTON,"Palm Button");
        lang.addBlock(DMBlocks.PALM_PLANKS_GATE,"Palm Gate");
        lang.addBlock(DMBlocks.PALM_PLANKS_PRESSURE_PLATE,"Palm Pressure Plate");
        lang.addBlock(DMBlocks.PALM_PLANKS_STAIRS,"Palm Stairs");
        lang.addBlock(DMBlocks.PALM_PLANKS_FENCE,"Palm Fence");
        lang.addBlock(DMBlocks.PALM_PLANKS_TRAPDOOR,"Palm Trapdoor");
        lang.addBlock(DMBlocks.PALM_PLANKS_SLAB,"Palm Slab");
        lang.addBlock(DMBlocks.COCONUT_BLOCK,"Coconut");
        lang.addBlock(DMBlocks.PALM_LEAVES,"Palm Leaves");
        lang.addBlock(DMBlocks.PALM_SAPLING,"Palm Sapling");
        lang.addBlock(DMBlocks.PALM_PLANKS_SIGN,"Palm Sign");
        lang.addBlock(DMBlocks.BRAZIER,"Brazier");
        lang.addBlock(DMBlocks.OASIS_GRASS_BLOCK,"Fertile Grass");
        lang.addBlock(DMBlocks.OASIS_DIRT,"Fertile Dirt");
        lang.addBlock(DMBlocks.FIRE_TRAP,"Fire Trap");
        lang.addBlock(DMBlocks.SUN_ALTAR,"Sun Altar");
        lang.addBlock(DMBlocks.BASKET,"Basket");
        lang.addBlock(DMBlocks.MAGIC_BASKET,"Magic Basket");
        lang.addBlock(DMBlocks.OASIS_GRASS,"Oasis Grass");
        lang.addBlock(DMBlocks.SHORT_DESERT_GRASS,"Short Desert Grass");
        lang.addBlock(DMBlocks.OASIS_TALL_GRASS,"Oasis Tall Grass");
        lang.addBlock(DMBlocks.FADED_DESERT_GRASS,"Tall Faded Desert Grass");
        lang.addBlock(DMBlocks.DESERT_GRASS,"Tall Desert Grass");
        lang.addBlock(DMBlocks.SAND_GRASS,"Sand Grass");
        lang.addBlock(DMBlocks.CAMEL_THORN,"Camel Thorn");
        lang.addBlock(DMBlocks.OASIS_FARMLAND,"Oasis Farmland");
    }

    private void entities(){
        lang.addEntityType(DMEntities.MEERKAT,"Meerkat");
        lang.addEntityType(DMEntities.CAMEL,"Camel");
        lang.addEntityType(DMEntities.DESERT_WORM,"Desert Worm");
        lang.addEntityType(DMEntities.MUMMY,"Mummy");
        lang.addEntityType(DMEntities.SCARAB,"Scarab");
        lang.addEntityType(DMEntities.FLYING_CARPET,"Flying Carpet");
        lang.addEntityType(DMEntities.FENICK,"Fenick");
        lang.addEntityType(DMEntities.SCARAB_LORD,"Scarab-Lord");
        lang.addEntityType(DMEntities.BLUE_GENIE,"Blue Genie");
        lang.addEntityType(DMEntities.GREEN_GENIE,"Green Genie");
        lang.addEntityType(DMEntities.RED_GENIE,"Red Genie");
        lang.addEntityType(DMEntities.PURPLE_GENIE,"Purple Genie");
        lang.addEntityType(DMEntities.BOAT,"Boat");
        lang.addEntityType(DMEntities.CHEST_BOAT,"Boat With Chest");
        lang.addEntityType(DMEntities.GENIE_CHARGE,"Genie's Charge");
        lang.addEntityType(DMEntities.FENICK_MAGE,"Fenick Mage");
        lang.addEntityType(DMEntities.SCORPION,"Scorpion");
        lang.addEntityType(DMEntities.FAKIR,"Fenick Fakir");
        lang.addEntityType(DMEntities.CACTUPINE,"Cactupine");
        lang.addEntityType(DMEntities.STING_ARROW,"Sting Arrow");
        lang.addEntityType(DMEntities.POLYMORPH,"Polymorph");
        lang.addEntityType(DMEntities.CLOUD,"Magic Cloud");
        lang.addEntityType(DMEntities.BOX_GLOVE,"Boxing Glove");
        lang.addEntityType(DMEntities.GOLDEN_SCARAB,"Golden Scarab");
    }

    private void effects(){
        lang.addEffect(DMMobEffects.MUMMY_CURSE,"Mummy Curse");
    }
}
