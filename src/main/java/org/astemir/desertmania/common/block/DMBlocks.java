package org.astemir.desertmania.common.block;

import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.astemir.api.common.block.BlocksUtils;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.misc.DMWoodType;
import org.astemir.desertmania.common.sound.DMSounds;

import java.awt.*;

public class DMBlocks {


    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DesertMania.MOD_ID);


    public static final RegistryObject<Block> PALM_PLANKS_SIGN = BLOCKS.register("palm_sign", ()->new BlockDMSign(BlockBehaviour.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), DMWoodType.PALM));
    public static final RegistryObject<Block> PALM_PLANKS_WALL_SIGN = BLOCKS.register("palm_wall_sign", ()->new BlockDMWallSign(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(PALM_PLANKS_SIGN), DMWoodType.PALM));

    public static RegistryObject<Block> DUNE_SAND = BLOCKS.register("dune_sand",()-> BlocksUtils.sand(MaterialColor.COLOR_YELLOW,new Color(255, 240, 159)));
    public static RegistryObject<Block> LOESS = BLOCKS.register("loess",()->BlocksUtils.stone(MaterialColor.COLOR_YELLOW, DMSounds.LOESS));
    public static RegistryObject<Block> LOESS_BRICKS = BLOCKS.register("loess_bricks",()->BlocksUtils.stone(MaterialColor.COLOR_YELLOW,DMSounds.LOESS));
    public static RegistryObject<Block> LOESS_BRICKS_SYMBOLS = BLOCKS.register("loess_bricks_symbols",()->BlocksUtils.stone(MaterialColor.COLOR_YELLOW,DMSounds.LOESS));
    public static RegistryObject<Block> LOESS_BRICKS_STAIRS = BLOCKS.register("loess_bricks_stairs",()->BlocksUtils.stairs(LOESS_BRICKS.get()));
    public static RegistryObject<Block> LOESS_BRICKS_SLAB = BLOCKS.register("loess_bricks_slab",()->BlocksUtils.slab(LOESS_BRICKS.get()));
    public static RegistryObject<Block> LOESS_BRICKS_WALL = BLOCKS.register("loess_bricks_wall",()->BlocksUtils.wall(LOESS_BRICKS.get()));
    public static RegistryObject<Block> CUT_LOESS = BLOCKS.register("cut_loess",()->BlocksUtils.stone(MaterialColor.COLOR_YELLOW,DMSounds.LOESS));
    public static RegistryObject<Block> CUT_LOESS_STAIRS = BLOCKS.register("cut_loess_stairs",()->BlocksUtils.stairs(CUT_LOESS.get()));
    public static RegistryObject<Block> CUT_LOESS_SLAB = BLOCKS.register("cut_loess_slab",()->BlocksUtils.slab(CUT_LOESS.get()));
    public static RegistryObject<Block> CUT_LOESS_WALL = BLOCKS.register("cut_loess_wall",()->BlocksUtils.wall(CUT_LOESS.get()));
    public static RegistryObject<Block> SMOOTH_LOESS = BLOCKS.register("smooth_loess",()-> BlocksUtils.stone(MaterialColor.COLOR_YELLOW,DMSounds.LOESS));
    public static RegistryObject<Block> SMOOTH_LOESS_STAIRS = BLOCKS.register("smooth_loess_stairs",()->BlocksUtils.stairs(SMOOTH_LOESS.get()));
    public static RegistryObject<Block> SMOOTH_LOESS_SLAB = BLOCKS.register("smooth_loess_slab",()->BlocksUtils.slab(SMOOTH_LOESS.get()));
    public static RegistryObject<Block> SMOOTH_LOESS_WALL = BLOCKS.register("smooth_loess_wall",()->BlocksUtils.wall(SMOOTH_LOESS.get()));
    public static RegistryObject<Block> CHISELED_LOESS = BLOCKS.register("chiseled_loess",()->BlocksUtils.stone(MaterialColor.COLOR_YELLOW,DMSounds.LOESS));
    public static RegistryObject<Block> GIANT_LOESS_BRICK = BLOCKS.register("giant_loess_brick",()->new BlockBigBrick(Block.Properties.of(Material.STONE,MaterialColor.COLOR_YELLOW).strength(BlocksUtils.STRENGTH_STONE*2,6).requiresCorrectToolForDrops().sound(DMSounds.LOESS)));
    public static RegistryObject<Block> PALM_LOG = BLOCKS.register("palm_log",()->BlocksUtils.log(MaterialColor.COLOR_BROWN,MaterialColor.COLOR_BROWN));
    public static RegistryObject<Block> PALM_WOOD = BLOCKS.register("palm_wood",()->BlocksUtils.log(MaterialColor.COLOR_BROWN,MaterialColor.COLOR_BROWN));
    public static RegistryObject<Block> PALM_LOG_STRIPPED = BLOCKS.register("stripped_palm_log",()->BlocksUtils.log(MaterialColor.COLOR_BROWN,MaterialColor.COLOR_BROWN));
    public static RegistryObject<Block> PALM_WOOD_STRIPPED = BLOCKS.register("stripped_palm_wood",()->BlocksUtils.log(MaterialColor.COLOR_BROWN,MaterialColor.COLOR_BROWN));
    public static RegistryObject<Block> PALM_PLANKS = BLOCKS.register("palm_planks",()->BlocksUtils.planks(MaterialColor.COLOR_YELLOW));
    public static RegistryObject<Block> PALM_PLANKS_STAIRS = BLOCKS.register("palm_planks_stairs",()->BlocksUtils.stairs(PALM_PLANKS.get()));
    public static RegistryObject<Block> PALM_PLANKS_SLAB = BLOCKS.register("palm_planks_slab",()->BlocksUtils.slab(PALM_PLANKS.get()));
    public static RegistryObject<Block> PALM_PLANKS_PRESSURE_PLATE = BLOCKS.register("palm_planks_pressure_plate",()->new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).noCollission().strength(0.5F).sound(SoundType.WOOD)));
    public static RegistryObject<Block> PALM_PLANKS_BUTTON = BLOCKS.register("palm_planks_button",()->new WoodButtonBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).noCollission().strength(0.5F).sound(SoundType.WOOD)));
    public static RegistryObject<Block> PALM_PLANKS_FENCE = BLOCKS.register("palm_planks_fence",()->new FenceBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static RegistryObject<Block> PALM_PLANKS_GATE = BLOCKS.register("palm_planks_gate",()->new FenceGateBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static RegistryObject<Block> PALM_PLANKS_DOOR = BLOCKS.register("palm_planks_door",()->new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD,MaterialColor.COLOR_YELLOW).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static RegistryObject<Block> PALM_PLANKS_TRAPDOOR = BLOCKS.register("palm_planks_trapdoor",()->new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD,MaterialColor.COLOR_YELLOW).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static RegistryObject<Block> COCONUT_BLOCK = BLOCKS.register("coconut_block",()->new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.VEGETABLE).strength(1.0F).sound(SoundType.WOOD)));
    public static RegistryObject<Block> PALM_LEAVES = BLOCKS.register("palm_leaves",PalmLeaves::new);
    public static RegistryObject<Block> GENIE_LAMP = BLOCKS.register("genie_lamp",BlockGenieLamp::new);
    public static RegistryObject<Block> PALM_SAPLING = BLOCKS.register("palm_sapling",BlockPalmSapling::new);
    public static RegistryObject<Block> BRAZIER = BLOCKS.register("brazier",BlockBrazier::new);
    public static RegistryObject<Block> SAND_GRASS = BLOCKS.register("sand_grass",()->new BlockDesertPlant(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)));
    public static RegistryObject<Block> DESERT_GRASS = BLOCKS.register("desert_grass",()->new BlockDesertDoublePlant(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static RegistryObject<Block> FADED_DESERT_GRASS = BLOCKS.register("faded_desert_grass",()->new BlockDesertDoublePlant(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static RegistryObject<Block> OASIS_GRASS = BLOCKS.register("oasis_grass",()->new TallGrassBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)));
    public static RegistryObject<Block> OASIS_TALL_GRASS = BLOCKS.register("oasis_tall_grass",()->new DoublePlantBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static RegistryObject<Block> SHORT_DESERT_GRASS = BLOCKS.register("short_desert_grass",()->new BlockDesertPlant(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)));
    public static RegistryObject<Block> CAMEL_THORN = BLOCKS.register("camelthorn",BlockCamelThorn::new);
    public static RegistryObject<Block> OASIS_GRASS_BLOCK = BLOCKS.register("oasis_grass_block",BlockOasisGrass::new);
    public static RegistryObject<Block> OASIS_DIRT = BLOCKS.register("oasis_dirt",BlockOasisDirt::new);
    public static RegistryObject<Block> OASIS_FARMLAND = BLOCKS.register("oasis_farmland",BlockOasisFarmland::new);
    public static RegistryObject<Block> BASKET = BLOCKS.register("basket",BlockBasket::new);
    public static RegistryObject<Block> MAGIC_BASKET = BLOCKS.register("magic_basket",BlockMagicBasket::new);
    public static RegistryObject<Block> SUN_ALTAR = BLOCKS.register("sun_altar",BlockSunAltar::new);
    public static RegistryObject<Block> FIRE_TRAP = BLOCKS.register("fire_trap",BlockFireTrap::new);
    public static RegistryObject<Block> BOSS_SPAWN = BLOCKS.register("boss_spawn",BlockBossSpawn::new);

}
