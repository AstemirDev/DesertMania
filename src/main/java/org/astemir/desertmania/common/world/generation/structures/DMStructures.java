package org.astemir.desertmania.common.world.generation.structures;


import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.astemir.desertmania.DesertMania;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class DMStructures {

    public static final DeferredRegister<Structure> STRUCTURES = DeferredRegister.create(Registry.STRUCTURE_REGISTRY, DesertMania.MOD_ID);
    public static RegistryObject<Structure> SCARAB_TEMPLE = STRUCTURES.register("scarab_temple",()->new StructureScarabTemple(new Structure.StructureSettings(biomes(Biomes.DESERT), ImmutableMap.of(), GenerationStep.Decoration.SURFACE_STRUCTURES,TerrainAdjustment.BEARD_BOX)));
    public static RegistryObject<Structure> ANCIENT_RUINS = STRUCTURES.register("ancient_ruins",()->new StructureAncientRuins(new Structure.StructureSettings(biomes(Biomes.DESERT), ImmutableMap.of(), GenerationStep.Decoration.SURFACE_STRUCTURES,TerrainAdjustment.BEARD_BOX)));
    public static RegistryObject<Structure> FENICK_OUTPOST = STRUCTURES.register("fenick_outpost",()->new StructureFenickOutpost(new Structure.StructureSettings(biomes(Biomes.DESERT), ImmutableMap.of(), GenerationStep.Decoration.SURFACE_STRUCTURES,TerrainAdjustment.BEARD_BOX)));
    public static RegistryObject<Structure> MAGE_HUT = STRUCTURES.register("mage_hut",()->new StructureMageHut(new Structure.StructureSettings(biomes(Biomes.DESERT), ImmutableMap.of(), GenerationStep.Decoration.SURFACE_STRUCTURES,TerrainAdjustment.BEARD_BOX)));
    public static RegistryObject<Structure> OASIS = STRUCTURES.register("oasis",()->new StructureOasis(new Structure.StructureSettings(biomes(Biomes.DESERT), ImmutableMap.of(), GenerationStep.Decoration.SURFACE_STRUCTURES,TerrainAdjustment.BEARD_BOX)));


    public static HolderSet<Biome> biomes(ResourceKey<Biome>... biomes){
        List<Holder<Biome>> holders = new ArrayList<>();
        for (ResourceKey<Biome> key : biomes) {
            Optional<Holder<Biome>> optional = ForgeRegistries.BIOMES.getHolder(key);
            if (optional.isPresent()) {
                Holder<Biome> holder = optional.get();
                holders.add(holder);
            }
        }
        return HolderSet.direct(holders);
    }

    public class PieceTypes{
        public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(Registry.STRUCTURE_PIECE_REGISTRY, DesertMania.MOD_ID);
        public static RegistryObject<StructurePieceType> SCARAB_TEMPLE_PIECE  = STRUCTURE_PIECE_TYPES.register("scarab_temple_piece",()->(pContext,pTag)->new StructureScarabTemple.Piece(pTag));
        public static RegistryObject<StructurePieceType> ANCIENT_RUINS_PIECE  = STRUCTURE_PIECE_TYPES.register("ancient_ruins_piece",()-> (pContext, pTag) -> new StructureAncientRuins.Piece(pTag));
        public static RegistryObject<StructurePieceType> FENICK_OUTPOST_PIECE  = STRUCTURE_PIECE_TYPES.register("fenick_outpost_piece",()-> (pContext, pTag) -> new StructureFenickOutpost.Piece(pTag));
        public static RegistryObject<StructurePieceType> MAGE_HUT_PIECE  = STRUCTURE_PIECE_TYPES.register("mage_hue_piece",()-> (pContext, pTag) -> new StructureMageHut.Piece(pTag));
        public static RegistryObject<StructurePieceType> OASIS_PIECE  = STRUCTURE_PIECE_TYPES.register("oasis_piece",()-> (pContext, pTag) -> new StructureOasis.Piece(pTag));

    }

    public class Types{

        public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registry.STRUCTURE_TYPE_REGISTRY, DesertMania.MOD_ID);

        public static RegistryObject<StructureType<StructureScarabTemple>> SCARAB_TEMPLE = STRUCTURE_TYPES.register("scarab_temple",()-> (StructureType<StructureScarabTemple>) () -> StructureScarabTemple.CODEC);
        public static RegistryObject<StructureType<StructureAncientRuins>> ANCIENT_RUINS = STRUCTURE_TYPES.register("ancient_ruins",()-> (StructureType<StructureAncientRuins>) () -> StructureAncientRuins.CODEC);
        public static RegistryObject<StructureType<StructureFenickOutpost>> FENICK_OUTPOST = STRUCTURE_TYPES.register("fenick_outpost",()-> (StructureType<StructureFenickOutpost>) () -> StructureFenickOutpost.CODEC);
        public static RegistryObject<StructureType<StructureMageHut>> MAGE_HUT = STRUCTURE_TYPES.register("mage_hut",()-> (StructureType<StructureMageHut>) () -> StructureMageHut.CODEC);
        public static RegistryObject<StructureType<StructureOasis>> OASIS = STRUCTURE_TYPES.register("oasis",()-> (StructureType<StructureOasis>) () -> StructureOasis.CODEC);

    }

    public class Sets{
        public static final DeferredRegister<StructureSet> STRUCTURE_SETS = DeferredRegister.create(Registry.STRUCTURE_SET_REGISTRY, DesertMania.MOD_ID);
//        public static RegistryObject<StructureSet> SCARAB_TEMPLE = STRUCTURE_SETS.register("scarab_temple",()->new StructureSet(DMStructures.SCARAB_TEMPLE.getHolder().get(),new RandomSpreadStructurePlacement(80, 20, RandomSpreadType.LINEAR, 903142187)));
//        public static RegistryObject<StructureSet> ANCIENT_RUINS = STRUCTURE_SETS.register("ancient_ruins",()->new StructureSet(DMStructures.ANCIENT_RUINS.getHolder().get(),new RandomSpreadStructurePlacement(40, 10, RandomSpreadType.LINEAR, 102494289)));
//        public static RegistryObject<StructureSet> FENICK_OUTPOST = STRUCTURE_SETS.register("fenick_outpost",()->new StructureSet(DMStructures.FENICK_OUTPOST.getHolder().get(),new RandomSpreadStructurePlacement(35, 15, RandomSpreadType.LINEAR, 334892818)));
//        public static RegistryObject<StructureSet> MAGE_HUT = STRUCTURE_SETS.register("mage_hut",()->new StructureSet(DMStructures.MAGE_HUT.getHolder().get(),new RandomSpreadStructurePlacement(35, 15, RandomSpreadType.LINEAR, 1133489881)));
//        public static RegistryObject<StructureSet> OASIS = STRUCTURE_SETS.register("oasis",()->new StructureSet(DMStructures.OASIS.getHolder().get(),new RandomSpreadStructurePlacement(35, 15, RandomSpreadType.LINEAR, 113646981)));

    }
}
