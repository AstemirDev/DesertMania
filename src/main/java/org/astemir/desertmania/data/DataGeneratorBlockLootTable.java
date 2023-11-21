package org.astemir.desertmania.data;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import org.astemir.api.data.loot.block.BlockDrop;
import org.astemir.api.data.loot.block.BlockDropType;
import org.astemir.api.data.loot.block.LootProviderBlocks;
import org.astemir.desertmania.common.block.DMBlocks;
import org.astemir.desertmania.common.item.DMItems;

public class DataGeneratorBlockLootTable extends LootProviderBlocks {

    public DataGeneratorBlockLootTable() {
        super(DMBlocks.BLOCKS);
        blocks();
    }

    private BlockDrop createDrop(Block block,BlockDropType type){
        return new BlockDrop(block,type);
    }

    private void blocks(){
        addDrop(createDropSelf(DMBlocks.FIRE_TRAP.get()));
        addDrop(createDropOther(DMBlocks.OASIS_FARMLAND.get(),DMBlocks.OASIS_DIRT.get()));
        addDrop(createDrop(DMBlocks.GENIE_LAMP.get(), BlockDropType.EMPTY));
        addDrop(createDrop(DMBlocks.BASKET.get(), BlockDropType.SELF));
        addDrop(createDrop(DMBlocks.MAGIC_BASKET.get(), BlockDropType.SELF));
        addDrop(createDrop(DMBlocks.SUN_ALTAR.get(), BlockDropType.SELF));
        addDrop(createDrop(DMBlocks.OASIS_GRASS_BLOCK.get(),BlockDropType.GRASS_BLOCK).otherDrop(DMBlocks.OASIS_DIRT.get()));
        addDrop(createDropSelf(DMBlocks.DUNE_SAND.get()));
        addDrop(createDropSelf(DMBlocks.OASIS_DIRT.get()));
        addDrop(createDropSelf(DMBlocks.LOESS.get()));
        addDrop(createDropSelf(DMBlocks.CHISELED_LOESS.get()));
        addDrop(createDropSelf(DMBlocks.GIANT_LOESS_BRICK.get()));
        addDrop(createDropSelf(DMBlocks.CUT_LOESS.get()));
        addDrop(createDropSelf(DMBlocks.CUT_LOESS_STAIRS.get()));
        addDrop(createDropSelf(DMBlocks.CUT_LOESS_WALL.get()));
        addDrop(createDropSelf(DMBlocks.PALM_LOG.get()));
        addDrop(createDropSelf(DMBlocks.PALM_LOG_STRIPPED.get()));
        addDrop(createDropSelf(DMBlocks.PALM_WOOD.get()));
        addDrop(createDropSelf(DMBlocks.PALM_WOOD_STRIPPED.get()));
        addDrop(createDropSelf(DMBlocks.PALM_PLANKS.get()));
        addDrop(createDropSelf(DMBlocks.PALM_PLANKS_BUTTON.get()));
        addDrop(createDropSelf(DMBlocks.PALM_PLANKS_FENCE.get()));
        addDrop(createDropSelf(DMBlocks.PALM_PLANKS_GATE.get()));
        addDrop(createDropSlab(DMBlocks.PALM_PLANKS_SLAB.get()));
        addDrop(createDropSlab(DMBlocks.CUT_LOESS_SLAB.get()));
        addDrop(createDropDoor(DMBlocks.PALM_PLANKS_DOOR.get()));
        addDrop(createDropSelf(DMBlocks.PALM_PLANKS_PRESSURE_PLATE.get()));
        addDrop(createDropSelf(DMBlocks.PALM_PLANKS_STAIRS.get()));
        addDrop(createDropSelf(DMBlocks.PALM_PLANKS_TRAPDOOR.get()));
        addDrop(createDropOre(DMBlocks.COCONUT_BLOCK.get(),DMItems.COCONUT_SLICE.get()).count(2,2));
        addDrop(createDropSelf(DMBlocks.LOESS_BRICKS.get()));
        addDrop(createDropSelf(DMBlocks.LOESS_BRICKS_SYMBOLS.get()));
        addDrop(createDropSelf(DMBlocks.LOESS_BRICKS_STAIRS.get()));
        addDrop(createDropSelf(DMBlocks.LOESS_BRICKS_WALL.get()));
        addDrop(createDropSlab(DMBlocks.LOESS_BRICKS_SLAB.get()));
        addDrop(createDropSelf(DMBlocks.SMOOTH_LOESS.get()));
        addDrop(createDropSelf(DMBlocks.SMOOTH_LOESS_STAIRS.get()));
        addDrop(createDropSelf(DMBlocks.SMOOTH_LOESS_WALL.get()));
        addDrop(createDropSlab(DMBlocks.SMOOTH_LOESS_SLAB.get()));
        addDrop(createDropSelf(DMBlocks.PALM_SAPLING.get()));
        addDrop(createDropSelf(DMBlocks.BRAZIER.get()));
        addDrop(createDropSelf(DMBlocks.PALM_PLANKS_SIGN.get()));
        addDrop(createDropSelf(DMBlocks.PALM_PLANKS_WALL_SIGN.get()));
        addDrop(createDrop(DMBlocks.PALM_LEAVES.get(),BlockDropType.LEAVES).otherDrop(DMBlocks.PALM_SAPLING.get()));

        addDrop(new BlockDrop(DMBlocks.FADED_DESERT_GRASS.get(),BlockDropType.CUSTOM){
            @Override
            public LootTable.Builder customBuild(LootProviderBlocks providerBlocks) {
                return createDoublePlantWithSeedDrops(getBlock(),getBlock());
            }
        });
        addDrop(new BlockDrop(DMBlocks.DESERT_GRASS.get(),BlockDropType.CUSTOM){
            @Override
            public LootTable.Builder customBuild(LootProviderBlocks providerBlocks) {
                return createDoublePlantWithSeedDrops(getBlock(),getBlock());
            }
        });
        addDrop(new BlockDrop(DMBlocks.OASIS_TALL_GRASS.get(),BlockDropType.CUSTOM){
            @Override
            public LootTable.Builder customBuild(LootProviderBlocks providerBlocks) {
                return createDoublePlantWithSeedDrops(getBlock(),getBlock());
            }
        });
        addDrop(new BlockDrop(DMBlocks.OASIS_GRASS.get(),BlockDropType.CUSTOM){
            @Override
            public LootTable.Builder customBuild(LootProviderBlocks providerBlocks) {
                return createGrassDrops(getBlock());
            }
        });
        addDrop(new BlockDrop(DMBlocks.SAND_GRASS.get(),BlockDropType.CUSTOM){
            @Override
            public LootTable.Builder customBuild(LootProviderBlocks providerBlocks) {
                return createGrassDrops(getBlock());
            }
        });
        addDrop(new BlockDrop(DMBlocks.CAMEL_THORN.get(),BlockDropType.CUSTOM){
            @Override
            public LootTable.Builder customBuild(LootProviderBlocks providerBlocks) {
                return createGrassDrops(getBlock());
            }
        });
        addDrop(new BlockDrop(DMBlocks.SHORT_DESERT_GRASS.get(),BlockDropType.CUSTOM){
            @Override
            public LootTable.Builder customBuild(LootProviderBlocks providerBlocks) {
                return createGrassDrops(getBlock());
            }
        });
    }
}
