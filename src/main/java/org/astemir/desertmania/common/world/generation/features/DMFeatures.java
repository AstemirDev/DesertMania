package org.astemir.desertmania.common.world.generation.features;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.astemir.desertmania.DesertMania;

public class DMFeatures {


    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, DesertMania.MOD_ID);

    public static final RegistryObject<Feature<?>> DESERT_ROCK = FEATURES.register("desert_rock", () -> new FeatureDesertRock(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>> DESERT_WEEDS = FEATURES.register("desert_weeds", () -> new FeatureDesertWeeds(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>> DESERT_DUNES = FEATURES.register("desert_dunes", () -> new FeatureDesertDunes(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>> DESERT_PALM = FEATURES.register("desert_palm", () -> new FeatureDesertPalm(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<?>> PALM_TREE_BUILDER = FEATURES.register("palm_tree_builder", () -> new FeaturePalmTreeBuilder(NoneFeatureConfiguration.CODEC));

    public static final class Configured {

        public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, DesertMania.MOD_ID);

        public static final RegistryObject<ConfiguredFeature<?, ?>> DESERT_ROCK = CONFIGURED_FEATURES.register("desert_rock", () -> new ConfiguredFeature(DMFeatures.DESERT_ROCK.get(), FeatureConfiguration.NONE));
        public static final RegistryObject<ConfiguredFeature<?, ?>> DESERT_WEEDS = CONFIGURED_FEATURES.register("desert_weeds", () -> new ConfiguredFeature(DMFeatures.DESERT_WEEDS.get(), FeatureConfiguration.NONE));
        public static final RegistryObject<ConfiguredFeature<?, ?>> DESERT_DUNES = CONFIGURED_FEATURES.register("desert_dunes", () -> new ConfiguredFeature(DMFeatures.DESERT_DUNES.get(), FeatureConfiguration.NONE));
        public static final RegistryObject<ConfiguredFeature<?, ?>> DESERT_PALM = CONFIGURED_FEATURES.register("desert_palm", () -> new ConfiguredFeature(DMFeatures.DESERT_PALM.get(), FeatureConfiguration.NONE));
        public static final RegistryObject<ConfiguredFeature<?, ?>> PALM_TREE_BUILDER = CONFIGURED_FEATURES.register("palm_tree_builder", ()->new ConfiguredFeature(DMFeatures.PALM_TREE_BUILDER.get(),FeatureConfiguration.NONE));

    }

    public static final class Placed{

        public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, DesertMania.MOD_ID);

        public static final RegistryObject<PlacedFeature> DESERT_ROCK = PLACED_FEATURES.register("desert_rock", () -> new PlacedFeature(Configured.DESERT_ROCK.getHolder().get(), ImmutableList.of()));
        public static final RegistryObject<PlacedFeature> DESERT_WEEDS = PLACED_FEATURES.register("desert_weeds", () -> new PlacedFeature(Configured.DESERT_WEEDS.getHolder().get(), ImmutableList.of()));
        public static final RegistryObject<PlacedFeature> DESERT_DUNES = PLACED_FEATURES.register("desert_dunes", () -> new PlacedFeature(Configured.DESERT_DUNES.getHolder().get(), ImmutableList.of()));
        public static final RegistryObject<PlacedFeature> DESERT_PALM = PLACED_FEATURES.register("desert_palm", () -> new PlacedFeature(Configured.DESERT_PALM.getHolder().get(), ImmutableList.of()));

    }
}
