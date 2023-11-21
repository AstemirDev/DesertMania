package org.astemir.desertmania.common.misc;


import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.astemir.desertmania.common.item.DMItems;

import java.util.function.Supplier;

public class TabSorting {

    public static TabSorting SPAWN_EGGS = new TabSorting(()-> Items.ZOMBIFIED_PIGLIN_SPAWN_EGG,
            DMItems.MEERKAT_SPAWN_EGG,
            DMItems.CAMEL_SPAWN_EGG,
            DMItems.DESERT_WORM_SPAWN_EGG,
            DMItems.MUMMY_SPAWN_EGG,
            DMItems.SCARAB_SPAWN_EGG,
            DMItems.SCARAB_LORD_SPAWN_EGG,
            DMItems.FENICK_SPAWN_EGG,
            DMItems.BLUE_GENIE_SPAWN_EGG,
            DMItems.GREEN_GENIE_SPAWN_EGG,
            DMItems.RED_GENIE_SPAWN_EGG,
            DMItems.PURPLE_GENIE_SPAWN_EGG,
            DMItems.FENICK_MAGE_SPAWN_EGG
    );
    public static TabSorting COCONUT = new TabSorting(()-> Items.MELON, DMItems.COCONUT_BLOCK);
    public static TabSorting SCIMITAR = new TabSorting(()-> Items.TRIDENT, DMItems.SCIMITAR);
    public static TabSorting FENCES = new TabSorting(()-> Items.ACACIA_FENCE, DMItems.PALM_PLANKS_FENCE);
    public static TabSorting OTHER_DECORATIONS = new TabSorting(()-> Items.FLOWER_POT, DMItems.GENIE_LAMP);
    public static TabSorting LEAVES = new TabSorting(()-> Items.MANGROVE_LEAVES, DMItems.PALM_LEAVES);
    public static TabSorting SANDS = new TabSorting(()-> Items.SAND, DMItems.DUNE_SAND);
    public static TabSorting GRASS_BLOCKS = new TabSorting(()-> Items.GRASS_BLOCK, DMItems.OASIS_GRASS_BLOCK);
    public static TabSorting DIRT_BLOCKS = new TabSorting(()-> Items.DIRT, DMItems.OASIS_DIRT);
    public static TabSorting LOGS = new TabSorting(()-> Items.MANGROVE_LOG, DMItems.PALM_LOG);
    public static TabSorting WOODS = new TabSorting(()-> Items.MANGROVE_WOOD, DMItems.PALM_WOOD);
    public static TabSorting STRIPPED_LOGS = new TabSorting(()-> Items.STRIPPED_MANGROVE_LOG, DMItems.PALM_LOG_STRIPPED);
    public static TabSorting STRIPPED_WOODS = new TabSorting(()-> Items.STRIPPED_MANGROVE_WOOD, DMItems.PALM_WOOD_STRIPPED);
    public static TabSorting PLANKS = new TabSorting(()-> Items.MANGROVE_PLANKS, DMItems.PALM_PLANKS);
    public static TabSorting STAIRS = new TabSorting(()-> Items.MANGROVE_STAIRS, DMItems.PALM_PLANKS_STAIRS);
    public static TabSorting SAPLINGS = new TabSorting(()-> Items.MANGROVE_PROPAGULE, DMItems.PALM_SAPLING);
    public static TabSorting SLABS = new TabSorting(()-> Items.MANGROVE_SLAB, DMItems.PALM_PLANKS_SLAB);
    public static TabSorting TRAPDOORS = new TabSorting(()-> Items.MANGROVE_TRAPDOOR, DMItems.PALM_PLANKS_TRAPDOOR);
    public static TabSorting DOORS = new TabSorting(()-> Items.MANGROVE_DOOR, DMItems.PALM_PLANKS_DOOR);
    public static TabSorting BUTTONS = new TabSorting(()-> Items.MANGROVE_BUTTON, DMItems.PALM_PLANKS_BUTTON);
    public static TabSorting PRESSURE_PLATES = new TabSorting(()-> Items.MANGROVE_PRESSURE_PLATE, DMItems.PALM_PLANKS_PRESSURE_PLATE);
    public static TabSorting FENCE_GATES = new TabSorting(()-> Items.MANGROVE_FENCE_GATE, DMItems.PALM_PLANKS_GATE);
    public static TabSorting WALLS = new TabSorting(()-> Items.MUD_BRICK_WALL, DMItems.CUT_LOESS_WALL,DMItems.LOESS_BRICKS_WALL,DMItems.SMOOTH_LOESS_WALL);
    public static TabSorting FLYING_CARPETS = new TabSorting(()-> Items.ELYTRA, DMItems.BLUE_FLYING_CARPET,DMItems.RED_FLYING_CARPET,DMItems.GREEN_FLYING_CARPET);
    public static TabSorting FOOD = new TabSorting(()-> Items.MELON_SLICE, DMItems.COCONUT_SLICE);
    public static TabSorting SIGNS = new TabSorting(()-> Items.MANGROVE_SIGN, DMItems.PALM_PLANKS_SIGN);
    public static TabSorting FURNITURE = new TabSorting(()-> Items.SOUL_CAMPFIRE, DMItems.BRAZIER,DMItems.SUN_ALTAR,DMItems.MAGIC_BASKET,DMItems.BASKET);
    public static TabSorting PLANTS = new TabSorting(()-> Items.GRASS, DMItems.OASIS_GRASS,DMItems.OASIS_TALL_GRASS,DMItems.SAND_GRASS,DMItems.CAMEL_THORN,DMItems.DESERT_GRASS,DMItems.FADED_DESERT_GRASS,DMItems.SHORT_DESERT_GRASS);
    public static TabSorting MUSIC_DISCS = new TabSorting(()->Items.MUSIC_DISC_PIGSTEP,DMItems.MUSIC_DISC_DESERTMANIA);

    private final Supplier<Item>[] items;
    private final Supplier<Item> itemReplacement;

    public TabSorting(Supplier<Item> replacement, Supplier<Item>... items) {
        this.items = items;
        this.itemReplacement = replacement;
    }

    public Item getItemReplacement() {
        return itemReplacement.get();
    }

    public boolean isItemOfGroup(Item item){
        for (Supplier<Item> itemSupplier : items) {
            if (itemSupplier.get() == item){
                return true;
            }
        }
        return false;
    }

    public static TabSorting[] getGroups(){
        return new TabSorting[]{MUSIC_DISCS,PLANTS,DIRT_BLOCKS,GRASS_BLOCKS,FURNITURE,SIGNS,FOOD,SAPLINGS,WOODS,STRIPPED_WOODS,FLYING_CARPETS,WALLS,COCONUT,SCIMITAR,SPAWN_EGGS,FENCES,OTHER_DECORATIONS,LEAVES,SANDS,LOGS,STRIPPED_LOGS,PLANKS,STAIRS,SLABS,TRAPDOORS,DOORS,BUTTONS,PRESSURE_PLATES,FENCE_GATES};
    }
}
