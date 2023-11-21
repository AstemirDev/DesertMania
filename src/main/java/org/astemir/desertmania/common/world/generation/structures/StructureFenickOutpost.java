package org.astemir.desertmania.common.world.generation.structures;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.ScatteredFeaturePiece;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import org.astemir.api.common.world.schematic.ISchematicBuilder;
import org.astemir.api.math.components.Vector3;
import org.astemir.api.math.random.WeightedRandom;
import org.astemir.desertmania.common.world.generation.schematic.SimplifiedSchematic;

import java.util.Optional;

public class StructureFenickOutpost extends Structure {

    public static final SimplifiedSchematic HOUSE_0 = new SimplifiedSchematic("fenick_village/fenick_house_0.schem").
            replacement((state) -> {
                if (state.is(Blocks.STRUCTURE_VOID)){
                    return Blocks.AIR.defaultBlockState();
                }
                if (state.is(Blocks.BARRIER)){
                    return Blocks.SAND.defaultBlockState();
                }
                return state;
            });
    public static final SimplifiedSchematic HOUSE_1 = new SimplifiedSchematic("fenick_village/fenick_house_1.schem").
            replacement((state) -> {
                if (state.is(Blocks.STRUCTURE_VOID)){
                    return Blocks.AIR.defaultBlockState();
                }
                if (state.is(Blocks.BARRIER)){
                    return Blocks.SAND.defaultBlockState();
                }
                return state;
            });
    public static final SimplifiedSchematic HOUSE_2 = new SimplifiedSchematic("fenick_village/fenick_house_2.schem").
            replacement((state) -> {
                if (state.is(Blocks.STRUCTURE_VOID)){
                    return Blocks.AIR.defaultBlockState();
                }
                if (state.is(Blocks.BARRIER)){
                    return Blocks.SAND.defaultBlockState();
                }
                return state;
            });
    public static final SimplifiedSchematic HOUSE_3 = new SimplifiedSchematic("fenick_village/fenick_house_3.schem").
            replacement((state) -> {
                if (state.is(Blocks.STRUCTURE_VOID)){
                    return Blocks.AIR.defaultBlockState();
                }
                if (state.is(Blocks.BARRIER)){
                    return Blocks.SAND.defaultBlockState();
                }
                return state;
            });
    public static final SimplifiedSchematic OUTPOST = new SimplifiedSchematic("fenick_village/fenick_outpost.schem").
            replacement((state) -> {
                if (state.is(Blocks.STRUCTURE_VOID)){
                    return Blocks.AIR.defaultBlockState();
                }
                if (state.is(Blocks.BARRIER)){
                    return Blocks.SAND.defaultBlockState();
                }
                return state;
            });
    public static final SimplifiedSchematic OUTPOST_MINI = new SimplifiedSchematic("fenick_village/fenick_outpost_mini.schem").
            replacement((state) -> {
                if (state.is(Blocks.STRUCTURE_VOID)){
                    return Blocks.AIR.defaultBlockState();
                }
                if (state.is(Blocks.BARRIER)){
                    return Blocks.SAND.defaultBlockState();
                }
                return state;
            });
    public static final SimplifiedSchematic STORAGE = new SimplifiedSchematic("fenick_village/fenick_storage.schem").
            replacement((state) -> {
                if (state.is(Blocks.STRUCTURE_VOID)){
                    return Blocks.AIR.defaultBlockState();
                }
                if (state.is(Blocks.BARRIER)){
                    return Blocks.SAND.defaultBlockState();
                }
                return state;
            });

    public static WeightedRandom<SimplifiedSchematic> SCHEMATICS = new WeightedRandom<>().
            add(30,HOUSE_0).
            add(30,HOUSE_1).
            add(30,HOUSE_2).
            add(30,HOUSE_3).
            add(10,OUTPOST).
            add(10,OUTPOST_MINI).
            add(10,STORAGE).build();

    public static final Codec<StructureFenickOutpost> CODEC = simpleCodec(StructureFenickOutpost::new);

    protected StructureFenickOutpost(StructureSettings settings) {
        super(settings);
    }


    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        int y = getLowestY(context, 100,100);
        return y < context.chunkGenerator().getSeaLevel() ? Optional.empty() : onTopOfChunkCenter(context, Heightmap.Types.WORLD_SURFACE_WG, (builder) -> {
            int minX = context.chunkPos().getMinBlockX()-100;
            int minZ = context.chunkPos().getMinBlockZ()+100;
            for (int i = 0;i<5;i++) {
                for (int j = 0;j<5;j++) {
                    builder.addPiece(new StructureFenickOutpost.Piece(minX+i*100, y, minZ+j*100));
                }
            }
        });
    }

    public static class Piece extends ScatteredFeaturePiece implements ISchematicBuilder {

        public Piece(int pX, int pY, int pZ) {
            super(DMStructures.PieceTypes.FENICK_OUTPOST_PIECE.get(), pX, pY, pZ, 30,30,30, Direction.NORTH);
        }

        public Piece(CompoundTag pTag) {
            super(DMStructures.PieceTypes.FENICK_OUTPOST_PIECE.get(), pTag);
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag pTag) {
            super.addAdditionalSaveData(pContext, pTag);
        }

        @Override
        public void postProcess(WorldGenLevel pLevel, StructureManager pStructureManager, ChunkGenerator pGenerator, RandomSource pRandom, BoundingBox pBox, ChunkPos pChunkPos, BlockPos pPos) {
            buildSchematic(SCHEMATICS.random(),pLevel,pPos,new Vector3(0,0,0),true,true);
        }
    }


    @Override
    public StructureType<?> type() {
        return DMStructures.Types.FENICK_OUTPOST.get();
    }
}
