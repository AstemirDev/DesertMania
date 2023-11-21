package org.astemir.desertmania.common.world.generation.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.astemir.api.common.world.IFeature;
import org.astemir.api.common.world.schematic.ISchematicBuilder;
import org.astemir.api.math.MathUtils;
import org.astemir.api.math.components.Vector3;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.desertmania.common.world.generation.schematic.SimplifiedSchematic;

import java.util.Arrays;


public class FeatureDesertRock extends Feature<NoneFeatureConfiguration> implements IFeature<NoneFeatureConfiguration>, ISchematicBuilder {


    public static final SimplifiedSchematic SCHEMATIC_0 = new SimplifiedSchematic("features/desert_rock_0.schem");
    public static final SimplifiedSchematic SCHEMATIC_1 = new SimplifiedSchematic("features/desert_rock_1.schem");
    public static final SimplifiedSchematic SCHEMATIC_2 = new SimplifiedSchematic("features/desert_rock_2.schem");
    public static final SimplifiedSchematic SCHEMATIC_3 = new SimplifiedSchematic("features/desert_rock_3.schem");

    public FeatureDesertRock(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        if (context.random().nextInt(15) != 0){
            return false;
        }
        BlockPos centerPos = surfacePosition(context,8,8);
        if (!context.level().getFluidState(centerPos.below()).isEmpty()){
            return false;
        }
        int rotation = RandomUtils.randomElement(Arrays.asList(0,90,180,270));
        buildSchematic(RandomUtils.randomElement(Arrays.asList(SCHEMATIC_0,SCHEMATIC_1,SCHEMATIC_2,SCHEMATIC_3)),context.level(),centerPos,new Vector3(0, MathUtils.rad(rotation),0),true,true);
        return true;
    }


}
