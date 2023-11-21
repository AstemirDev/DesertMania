package org.astemir.desertmania.common.item;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.astemir.api.common.register.ItemRegistry;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.block.DMBlocks;
import org.astemir.desertmania.common.entity.DMEntities;
import org.astemir.desertmania.common.entity.boat.DMBoatType;
import org.astemir.desertmania.common.sound.DMSounds;

import java.awt.*;

public class DMItems extends ItemRegistry {

    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DesertMania.MOD_ID);
    public static RegistryObject<Item> STING_ARROW = ITEMS.register("sting_arrow",ItemStingArrow::new);
    public static RegistryObject<Item> GENIE_LAMP = ITEMS.register("genie_lamp",ItemGenieLamp::new);
    public static RegistryObject<Item> CLOTH = ITEMS.register("cloth",()->new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(64)));
    public static RegistryObject<Item> ANCIENT_AMULET = ITEMS.register("ancient_amulet",()->new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC).durability(1000).rarity(Rarity.RARE)));
    public static RegistryObject<Item> STING = ITEMS.register("sting",()->new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(64)));
    public static RegistryObject<Item> ENCHANTED_SCROLL = ITEMS.register("enchanted_scroll",ItemEnchantedScroll::new);
    public static RegistryObject<Item> GOLDEN_SCARAB_RIGHT_PART = ITEMS.register("golden_scarab_right_part",()->new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC).rarity(Rarity.RARE).stacksTo(64)));
    public static RegistryObject<Item> GOLDEN_SCARAB_LEFT_PART = ITEMS.register("golden_scarab_left_part",()->new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC).rarity(Rarity.RARE).stacksTo(64)));
    public static RegistryObject<Item> GOLDEN_SCARAB = ITEMS.register("golden_scarab",ItemGoldenScarab::new);
    public static RegistryObject<Item> SHEMAGH = ITEMS.register("shemagh",ItemShemagh::new);
    public static RegistryObject<Item> SCIMITAR = ITEMS.register("scimitar",ItemScimitar::new);
    public static RegistryObject<Item> DUNE_SAND = block(DMBlocks.DUNE_SAND,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static RegistryObject<Item> BRAZIER = block(DMBlocks.BRAZIER,CreativeModeTab.TAB_DECORATIONS);
    public static RegistryObject<Item> BASKET = ITEMS.register(DMBlocks.BASKET.getId().getPath(),ItemBasket::new);
    public static RegistryObject<Item> MAGIC_BASKET = ITEMS.register(DMBlocks.MAGIC_BASKET.getId().getPath(),ItemMagicBasket::new);
    public static RegistryObject<Item> SUN_ALTAR = ITEMS.register(DMBlocks.SUN_ALTAR.getId().getPath(),ItemSunAltar::new);
    public static RegistryObject<Item> LOESS = block(DMBlocks.LOESS,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static RegistryObject<Item> SMOOTH_LOESS = block(DMBlocks.SMOOTH_LOESS,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static RegistryObject<Item> SMOOTH_LOESS_STAIRS = block(DMBlocks.SMOOTH_LOESS_STAIRS,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static RegistryObject<Item> SMOOTH_LOESS_WALL = block(DMBlocks.SMOOTH_LOESS_WALL,CreativeModeTab.TAB_DECORATIONS);
    public static RegistryObject<Item> SMOOTH_LOESS_SLAB = block(DMBlocks.SMOOTH_LOESS_SLAB,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static RegistryObject<Item> CUT_LOESS = block(DMBlocks.CUT_LOESS,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static RegistryObject<Item> CUT_LOESS_STAIRS = block(DMBlocks.CUT_LOESS_STAIRS,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static RegistryObject<Item> CUT_LOESS_WALL = block(DMBlocks.CUT_LOESS_WALL,CreativeModeTab.TAB_DECORATIONS);
    public static RegistryObject<Item> CUT_LOESS_SLAB = block(DMBlocks.CUT_LOESS_SLAB,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static RegistryObject<Item> LOESS_BRICKS = block(DMBlocks.LOESS_BRICKS,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static RegistryObject<Item> LOESS_BRICKS_SYMBOLS = block(DMBlocks.LOESS_BRICKS_SYMBOLS,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static RegistryObject<Item> LOESS_BRICKS_STAIRS = block(DMBlocks.LOESS_BRICKS_STAIRS,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static RegistryObject<Item> LOESS_BRICKS_WALL = block(DMBlocks.LOESS_BRICKS_WALL,CreativeModeTab.TAB_DECORATIONS);
    public static RegistryObject<Item> LOESS_BRICKS_SLAB = block(DMBlocks.LOESS_BRICKS_SLAB,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static RegistryObject<Item> CHISELED_LOESS = block(DMBlocks.CHISELED_LOESS,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static RegistryObject<Item> GIANT_LOESS_BRICK = block(DMBlocks.GIANT_LOESS_BRICK,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static RegistryObject<Item> PALM_LOG = block(DMBlocks.PALM_LOG,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static RegistryObject<Item> PALM_LOG_STRIPPED = block(DMBlocks.PALM_LOG_STRIPPED,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static RegistryObject<Item> PALM_WOOD = block(DMBlocks.PALM_WOOD,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static RegistryObject<Item> PALM_WOOD_STRIPPED = block(DMBlocks.PALM_WOOD_STRIPPED,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static RegistryObject<Item> PALM_PLANKS = block(DMBlocks.PALM_PLANKS,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static RegistryObject<Item> PALM_PLANKS_SLAB = block(DMBlocks.PALM_PLANKS_SLAB,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static RegistryObject<Item> PALM_PLANKS_STAIRS = block(DMBlocks.PALM_PLANKS_STAIRS,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static RegistryObject<Item> PALM_PLANKS_DOOR = block(DMBlocks.PALM_PLANKS_DOOR,CreativeModeTab.TAB_REDSTONE);
    public static RegistryObject<Item> PALM_PLANKS_TRAPDOOR = block(DMBlocks.PALM_PLANKS_TRAPDOOR,CreativeModeTab.TAB_REDSTONE);
    public static RegistryObject<Item> PALM_PLANKS_BUTTON = block(DMBlocks.PALM_PLANKS_BUTTON,CreativeModeTab.TAB_REDSTONE);
    public static RegistryObject<Item> PALM_PLANKS_PRESSURE_PLATE = block(DMBlocks.PALM_PLANKS_PRESSURE_PLATE,CreativeModeTab.TAB_REDSTONE);
    public static RegistryObject<Item> PALM_PLANKS_FENCE = block(DMBlocks.PALM_PLANKS_FENCE,CreativeModeTab.TAB_DECORATIONS);
    public static RegistryObject<Item> PALM_PLANKS_GATE = block(DMBlocks.PALM_PLANKS_GATE,CreativeModeTab.TAB_REDSTONE);
    public static RegistryObject<Item> COCONUT_BLOCK = block(DMBlocks.COCONUT_BLOCK,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static RegistryObject<Item> OASIS_GRASS_BLOCK = block(DMBlocks.OASIS_GRASS_BLOCK,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static RegistryObject<Item> OASIS_DIRT = block(DMBlocks.OASIS_DIRT,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static RegistryObject<Item> PALM_LEAVES = block(DMBlocks.PALM_LEAVES,CreativeModeTab.TAB_DECORATIONS);
    public static RegistryObject<Item> SAND_GRASS = block(DMBlocks.SAND_GRASS,CreativeModeTab.TAB_DECORATIONS);
    public static RegistryObject<Item> SHORT_DESERT_GRASS = block(DMBlocks.SHORT_DESERT_GRASS,CreativeModeTab.TAB_DECORATIONS);
    public static RegistryObject<Item> DESERT_GRASS = block(DMBlocks.DESERT_GRASS,CreativeModeTab.TAB_DECORATIONS);
    public static RegistryObject<Item> FADED_DESERT_GRASS = block(DMBlocks.FADED_DESERT_GRASS,CreativeModeTab.TAB_DECORATIONS);
    public static RegistryObject<Item> OASIS_GRASS = block(DMBlocks.OASIS_GRASS,CreativeModeTab.TAB_DECORATIONS);
    public static RegistryObject<Item> OASIS_TALL_GRASS = block(DMBlocks.OASIS_TALL_GRASS,CreativeModeTab.TAB_DECORATIONS);
    public static RegistryObject<Item> CAMEL_THORN = block(DMBlocks.CAMEL_THORN,CreativeModeTab.TAB_DECORATIONS);
    public static RegistryObject<Item> FIRE_TRAP = block(DMBlocks.FIRE_TRAP,CreativeModeTab.TAB_DECORATIONS);

    public static RegistryObject<Item> PALM_SAPLING = block(DMBlocks.PALM_SAPLING,CreativeModeTab.TAB_DECORATIONS);
    public static RegistryObject<Item> PALM_PLANKS_BOAT = ITEMS.register("palm_planks_boat",()->new ItemDMBoat(false, DMBoatType.PALM,new Item.Properties().tab(CreativeModeTab.TAB_TRANSPORTATION).stacksTo(1)));
    public static RegistryObject<Item> PALM_PLANKS_CHEST_BOAT = ITEMS.register("palm_planks_chest_boat",()->new ItemDMBoat(true, DMBoatType.PALM,new Item.Properties().tab(CreativeModeTab.TAB_TRANSPORTATION).stacksTo(1)));
    public static RegistryObject<Item> PALM_PLANKS_SIGN = ITEMS.register("palm_planks_sign",()->new SignItem((new Item.Properties()).stacksTo(16).tab(CreativeModeTab.TAB_DECORATIONS), DMBlocks.PALM_PLANKS_SIGN.get(), DMBlocks.PALM_PLANKS_WALL_SIGN.get()));
    public static final RegistryObject<Item> BLUE_FLYING_CARPET = ITEMS.register("blue_flying_carpet",()->new ItemFlyingCarpet(ItemFlyingCarpet.CarpetColor.BLUE));
    public static final RegistryObject<Item> RED_FLYING_CARPET = ITEMS.register("red_flying_carpet",()->new ItemFlyingCarpet(ItemFlyingCarpet.CarpetColor.RED));
    public static final RegistryObject<Item> GREEN_FLYING_CARPET = ITEMS.register("green_flying_carpet",()->new ItemFlyingCarpet(ItemFlyingCarpet.CarpetColor.GREEN));
    public static final RegistryObject<Item> COCONUT_SLICE = ITEMS.register("coconut_slice",ItemCoconutSlice::new);
    public static final RegistryObject<Item> MUSIC_DISC_DESERTMANIA = ITEMS.register("music_disc_desertmania",()->new RecordItem(11, DMSounds.MUSIC_DESERTMANIA,new Item.Properties().tab(CreativeModeTab.TAB_MISC).rarity(Rarity.RARE).stacksTo(1),1600));

    public static final RegistryObject<Item> MEERKAT_SPAWN_EGG = ITEMS.register("meerkat_spawn_egg", ()->spawnEgg(DMEntities.MEERKAT, new Color(153, 91, 16),new Color(90, 51, 23),new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> CAMEL_SPAWN_EGG = ITEMS.register("camel_spawn_egg", ()->spawnEgg(DMEntities.CAMEL, new Color(245, 181, 128),new Color(189, 130, 86),new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> DESERT_WORM_SPAWN_EGG = ITEMS.register("desert_worm_spawn_egg", ()->spawnEgg(DMEntities.DESERT_WORM, new Color(83, 68, 50),new Color(170, 105, 48),new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> MUMMY_SPAWN_EGG = ITEMS.register("mummy_spawn_egg", ()->spawnEgg(DMEntities.MUMMY, new Color(255, 215, 174),new Color(146, 118, 96),new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> SCARAB_SPAWN_EGG = ITEMS.register("scarab_spawn_egg", ()->spawnEgg(DMEntities.SCARAB, new Color(38, 15, 78),new Color(69, 41, 120),new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> SCARAB_LORD_SPAWN_EGG = ITEMS.register("scarab_lord_spawn_egg", ()->spawnEgg(DMEntities.SCARAB_LORD, new Color(66, 10, 160),new Color(255, 192, 0),new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> FENICK_SPAWN_EGG = ITEMS.register("fenick_spawn_egg", ()->spawnEgg(DMEntities.FENICK, new Color(139, 98, 62),new Color(127, 71, 43),new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> BLUE_GENIE_SPAWN_EGG = ITEMS.register("blue_genie_spawn_egg", ()->spawnEgg(DMEntities.BLUE_GENIE, new Color(71, 174, 222),new Color(28, 85, 147),new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> GREEN_GENIE_SPAWN_EGG = ITEMS.register("green_genie_spawn_egg", ()->spawnEgg(DMEntities.GREEN_GENIE, new Color(73, 180, 53),new Color(28, 101, 20),new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> RED_GENIE_SPAWN_EGG = ITEMS.register("red_genie_spawn_egg", ()->spawnEgg(DMEntities.RED_GENIE, new Color(213, 24, 24),new Color(153, 2, 2),new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> PURPLE_GENIE_SPAWN_EGG = ITEMS.register("purple_genie_spawn_egg", ()->spawnEgg(DMEntities.PURPLE_GENIE, new Color(172, 24, 213),new Color(121, 18, 165),new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> FENICK_MAGE_SPAWN_EGG = ITEMS.register("fenick_mage_spawn_egg", ()->spawnEgg(DMEntities.FENICK_MAGE, new Color(35, 22, 94),new Color(80, 44, 26),new Item.Properties().tab(CreativeModeTab.TAB_MISC)));


    public static RegistryObject<Item> block(RegistryObject<Block> block,CreativeModeTab tab){
        return ITEMS.register(block.getId().getPath(),()->new BlockItem(block.get(),new Item.Properties().tab(tab)));
    }

    public static RegistryObject<Item> block(RegistryObject<Block> block,int maxStack){
        return ITEMS.register(block.getId().getPath(),()->new BlockItem(block.get(),new Item.Properties().stacksTo(maxStack)));
    }

    public static RegistryObject<Item> block(RegistryObject<Block> block,int maxStack,CreativeModeTab tab){
        return ITEMS.register(block.getId().getPath(),()->new BlockItem(block.get(),new Item.Properties().stacksTo(maxStack).tab(tab)));
    }

}
