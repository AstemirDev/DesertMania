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
import java.util.Collections;
import java.util.List;

public class FeaturePalmTreeBuilder extends Feature<NoneFeatureConfiguration> implements IFeature<NoneFeatureConfiguration>, ISchematicBuilder {

    public static final SimplifiedSchematic SCHEMATIC_0 = new SimplifiedSchematic("palms/palm_tree_0.schem");
    public static final SimplifiedSchematic SCHEMATIC_1 = new SimplifiedSchematic("palms/palm_tree_1.schem");
    public static final SimplifiedSchematic SCHEMATIC_2 = new SimplifiedSchematic("palms/palm_tree_2.schem");
    public static final SimplifiedSchematic SCHEMATIC_3 = new SimplifiedSchematic("palms/palm_tree_3.schem");

    public FeaturePalmTreeBuilder(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
        BlockPos pos = pContext.origin();
        int rotation = RandomUtils.randomElement(Arrays.asList(0,90,180,270));
        List<SimplifiedSchematic> list = Arrays.asList(SCHEMATIC_0,SCHEMATIC_1,SCHEMATIC_2,SCHEMATIC_3);
        Collections.shuffle(list);
        for (SimplifiedSchematic schematic : list) {
            if (buildSchematicCheckFreeSpace(schematic,pContext.level(),pos,new Vector3(0, MathUtils.rad(rotation),0),true,true)){
                return true;
            }
        }
        return false;
    }
}
