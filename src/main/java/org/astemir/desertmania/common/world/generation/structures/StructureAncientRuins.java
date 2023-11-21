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
import org.astemir.api.math.components.Rect2;
import org.astemir.api.math.components.Vector3;
import org.astemir.desertmania.common.world.generation.schematic.SimplifiedSchematic;

import java.util.Optional;
import java.util.Set;

public class StructureAncientRuins extends Structure{

    public static final SimplifiedSchematic SCHEMATIC = new SimplifiedSchematic("ancient_ruins.schem").
            replacement((state) -> {
                if (state.is(Blocks.STRUCTURE_VOID)){
                    return Blocks.AIR.defaultBlockState();
                }
                if (state.is(Blocks.BARRIER)){
                    return Blocks.SAND.defaultBlockState();
                }
                return state;
            });

    public static final ISchematicBuilder.SchematicSafePlacement[] PLACEMENTS = loadPlacements();

    public static final Codec<StructureAncientRuins> CODEC = simpleCodec(StructureAncientRuins::new);

    protected StructureAncientRuins(StructureSettings settings) {
        super(settings);
    }


    public static ISchematicBuilder.SchematicSafePlacement[] loadPlacements(){
        Set<ISchematicBuilder.SchematicSafePlacement> placements = ISchematicBuilder.schematicToPieces(SCHEMATIC,true);
        return placements.toArray(new ISchematicBuilder.SchematicSafePlacement[placements.size()]);
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        int y = getLowestY(context, SCHEMATIC.getWidth(), SCHEMATIC.getLength());
        if (y < context.chunkGenerator().getSeaLevel()-10){
            return onTopOfChunkCenter(context, Heightmap.Types.WORLD_SURFACE_WG, (builder) -> {
                int x = context.chunkPos().getMiddleBlockX();
                int z = context.chunkPos().getMiddleBlockZ();
                for (int i = 0; i < PLACEMENTS.length; i++) {
                    Rect2 rectangle = PLACEMENTS[i].getRectangle();
                    int finalPieceX = (int) (x+rectangle.getX());
                    int finalPieceZ = (int) (z+rectangle.getY());
                    builder.addPiece(new StructureAncientRuins.Piece(i,finalPieceX,y,finalPieceZ,(int)rectangle.getWidth(),(int)rectangle.getHeight()));
                }
            });
        }else{
            return Optional.empty();
        }
    }

    public static class Piece extends ScatteredFeaturePiece {

        private int index;

        public Piece(int index,int pX, int pY, int pZ,int width,int depth) {
            super(DMStructures.PieceTypes.ANCIENT_RUINS_PIECE.get(), pX, pY, pZ, width,SCHEMATIC.getHeight(),depth, Direction.NORTH);
            this.index = index;
        }

        public Piece(CompoundTag pTag) {
            super(DMStructures.PieceTypes.ANCIENT_RUINS_PIECE.get(), pTag);
            index = pTag.getInt("PlacementIndex");
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag pTag) {
            super.addAdditionalSaveData(pContext, pTag);
            pTag.putInt("PlacementIndex",index);
        }


        @Override
        public void postProcess(WorldGenLevel pLevel, StructureManager pStructureManager, ChunkGenerator pGenerator, RandomSource pRandom, BoundingBox pBox, ChunkPos pChunkPos, BlockPos pPos) {
            PLACEMENTS[index].place(pLevel, pPos, new Vector3(0, 0, 0), true);
        }
    }


    @Override
    public StructureType<?> type() {
        return DMStructures.Types.ANCIENT_RUINS.get();
    }
}
