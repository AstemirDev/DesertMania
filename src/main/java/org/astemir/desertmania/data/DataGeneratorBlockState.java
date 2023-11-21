package org.astemir.desertmania.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.astemir.api.data.blockstate.BlockStateHolder;
import org.astemir.api.data.blockstate.BlockStateType;
import org.astemir.api.data.blockstate.SkillsBlockStateProvider;
import org.astemir.api.io.ResourceUtils;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.block.DMBlocks;

import static org.astemir.api.io.ResourceUtils.createModelFile;
import static org.astemir.api.io.ResourceUtils.getBlockLocation;

public class DataGeneratorBlockState extends SkillsBlockStateProvider {
    
    public DataGeneratorBlockState(DataGenerator gen, String modId, ExistingFileHelper exFileHelper) {
        super(gen, modId, exFileHelper);
        blockStates();
        register(gen);
    }


    private void blockStates(){
        addState(DMBlocks.GENIE_LAMP, BlockStateType.EMPTY);
        addState(DMBlocks.BASKET, BlockStateType.EMPTY);
        addState(DMBlocks.MAGIC_BASKET, BlockStateType.EMPTY);
        addState(DMBlocks.SUN_ALTAR, BlockStateType.EMPTY);
        addState(DMBlocks.DUNE_SAND, BlockStateType.DEFAULT);
        addState(DMBlocks.OASIS_DIRT, BlockStateType.DEFAULT);
        addState(DMBlocks.LOESS,BlockStateType.MIRRORED);
        addState(DMBlocks.CHISELED_LOESS,BlockStateType.CHISELED).material("end",DMBlocks.CUT_LOESS.get());
        addState(DMBlocks.LOESS_BRICKS,BlockStateType.DEFAULT);
        addState(DMBlocks.LOESS_BRICKS_SYMBOLS,BlockStateType.DEFAULT);
        addState(DMBlocks.LOESS_BRICKS_STAIRS,BlockStateType.STAIRS).material("texture",DMBlocks.LOESS_BRICKS.get());
        addState(DMBlocks.LOESS_BRICKS_SLAB,BlockStateType.SLAB).material("texture",DMBlocks.LOESS_BRICKS.get()).material("double",DMBlocks.LOESS_BRICKS.get());
        addState(DMBlocks.LOESS_BRICKS_WALL,BlockStateType.WALL).material("texture",DMBlocks.LOESS_BRICKS.get());
        addState(DMBlocks.CUT_LOESS,BlockStateType.DEFAULT);
        addState(DMBlocks.CUT_LOESS_STAIRS,BlockStateType.STAIRS).material("texture",DMBlocks.CUT_LOESS.get());
        addState(DMBlocks.CUT_LOESS_SLAB,BlockStateType.SLAB).material("texture",DMBlocks.CUT_LOESS.get()).material("double",DMBlocks.CUT_LOESS.get());
        addState(DMBlocks.CUT_LOESS_WALL,BlockStateType.WALL).material("texture",DMBlocks.CUT_LOESS.get());
        addState(DMBlocks.SMOOTH_LOESS,BlockStateType.DEFAULT);
        addState(DMBlocks.SMOOTH_LOESS_STAIRS,BlockStateType.STAIRS).material("texture",DMBlocks.SMOOTH_LOESS.get());
        addState(DMBlocks.SMOOTH_LOESS_SLAB,BlockStateType.SLAB).material("texture",DMBlocks.SMOOTH_LOESS.get()).material("double",DMBlocks.SMOOTH_LOESS.get());
        addState(DMBlocks.SMOOTH_LOESS_WALL,BlockStateType.WALL).material("texture",DMBlocks.SMOOTH_LOESS.get());
        addState(DMBlocks.PALM_LEAVES,BlockStateType.LEAVES);
        addState(DMBlocks.PALM_LOG,BlockStateType.LOG);
        addState(DMBlocks.PALM_WOOD,BlockStateType.DEFAULT);
        addState(DMBlocks.PALM_WOOD_STRIPPED,BlockStateType.DEFAULT);
        addState(DMBlocks.PALM_LOG_STRIPPED,BlockStateType.LOG);
        addState(DMBlocks.PALM_PLANKS,BlockStateType.DEFAULT);
        addState(DMBlocks.PALM_PLANKS_STAIRS,BlockStateType.STAIRS).material("texture",DMBlocks.PALM_PLANKS.get());
        addState(DMBlocks.PALM_PLANKS_PRESSURE_PLATE,BlockStateType.PRESSURE_PLATE).material("texture",DMBlocks.PALM_PLANKS.get());
        addState(DMBlocks.PALM_PLANKS_BUTTON,BlockStateType.BUTTON).material("texture",DMBlocks.PALM_PLANKS.get());
        addState(DMBlocks.PALM_PLANKS_GATE,BlockStateType.FENCE_GATE).material("texture",DMBlocks.PALM_PLANKS.get());
        addState(DMBlocks.PALM_PLANKS_FENCE,BlockStateType.FENCE).material("texture",DMBlocks.PALM_PLANKS.get());
        addState(DMBlocks.PALM_PLANKS_SLAB,BlockStateType.SLAB).material("texture",DMBlocks.PALM_PLANKS.get()).material("double",DMBlocks.PALM_PLANKS.get());
        addState(DMBlocks.PALM_PLANKS_DOOR,BlockStateType.DOOR).material("texture",DMBlocks.PALM_PLANKS.get());
        addState(DMBlocks.PALM_PLANKS_TRAPDOOR,BlockStateType.TRAPDOOR);
        addState(DMBlocks.COCONUT_BLOCK,BlockStateType.LOG);
        addState(DMBlocks.PALM_SAPLING,BlockStateType.CROSS);
        addState(DMBlocks.DESERT_GRASS,BlockStateType.TALL_GRASS);
        addState(DMBlocks.FADED_DESERT_GRASS,BlockStateType.TALL_GRASS);
        addState(DMBlocks.OASIS_TALL_GRASS,BlockStateType.TALL_GRASS);
        addState(DMBlocks.OASIS_GRASS,BlockStateType.GRASS);
        addState(DMBlocks.SAND_GRASS,BlockStateType.GRASS);
        addState(DMBlocks.SHORT_DESERT_GRASS,BlockStateType.GRASS);
        addState(DMBlocks.CAMEL_THORN,BlockStateType.GRASS);
        addState(new BlockStateHolder(DMBlocks.OASIS_FARMLAND,BlockStateType.CUSTOM){
            @Override
            public void customBuild() {
                getVariantBuilder(getBlock()).
                        partialState().with(FarmBlock.MOISTURE, 0).addModels(new ConfiguredModel(createFarmland(DataGeneratorBlockState.this,this))).
                        partialState().with(FarmBlock.MOISTURE, 1).addModels(new ConfiguredModel(createFarmland(DataGeneratorBlockState.this,this))).
                        partialState().with(FarmBlock.MOISTURE, 2).addModels(new ConfiguredModel(createFarmland(DataGeneratorBlockState.this,this))).
                        partialState().with(FarmBlock.MOISTURE, 3).addModels(new ConfiguredModel(createFarmland(DataGeneratorBlockState.this,this))).
                        partialState().with(FarmBlock.MOISTURE, 4).addModels(new ConfiguredModel(createFarmland(DataGeneratorBlockState.this,this))).
                        partialState().with(FarmBlock.MOISTURE, 5).addModels(new ConfiguredModel(createFarmland(DataGeneratorBlockState.this,this))).
                        partialState().with(FarmBlock.MOISTURE, 6).addModels(new ConfiguredModel(createFarmland(DataGeneratorBlockState.this,this))).
                        partialState().with(FarmBlock.MOISTURE, 7).addModels(new ConfiguredModel(createFarmlandMoisted(DataGeneratorBlockState.this,this)));
            }
        }.material("dirt",DMBlocks.OASIS_DIRT.get()).material("top", ResourceUtils.loadResource(DesertMania.MOD_ID,"block/oasis_farmland")).material("wet", ResourceUtils.loadResource(DesertMania.MOD_ID,"block/oasis_farmland_moisture")));
    }

    public static BlockModelBuilder createFarmland(BlockStateProvider provider, BlockStateHolder stateHolder) {
        ResourceLocation location = getBlockLocation(stateHolder.getBlock());
        return provider.models().getBuilder(location.toString()).parent(createModelFile("block/template_farmland"))
                .texture("dirt", stateHolder.getMaterial("dirt"))
                .texture("top", stateHolder.getMaterial("top"));
    }

    public static BlockModelBuilder createFarmlandMoisted(BlockStateProvider provider, BlockStateHolder stateHolder) {
        ResourceLocation location = getBlockLocation(stateHolder.getBlock());
        return provider.models().getBuilder(location.toString()+"_moist").parent(createModelFile("block/template_farmland"))
                .texture("dirt", stateHolder.getMaterial("dirt"))
                .texture("top", stateHolder.getMaterial("wet"));
    }




}
