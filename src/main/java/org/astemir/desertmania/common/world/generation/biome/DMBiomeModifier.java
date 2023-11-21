package org.astemir.desertmania.common.world.generation.biome;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.astemir.api.common.event.BiomeModifyEvent;
import org.astemir.desertmania.DesertMania;

public class DMBiomeModifier implements BiomeModifier {

    private static final RegistryObject<Codec<? extends BiomeModifier>> SERIALIZER = RegistryObject.create(new ResourceLocation(DesertMania.MOD_ID, "dm_biome_modifier"), ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, DesertMania.MOD_ID);

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.ADD) {
            BiomeModifyEvent event = new BiomeModifyEvent(biome,builder);
            MinecraftForge.EVENT_BUS.post(event);
        }
    }

    public static void register(IEventBus eventBus){
        DeferredRegister<Codec<? extends BiomeModifier>> biomeModifiers = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, DesertMania.MOD_ID);
        biomeModifiers.register(eventBus);
        biomeModifiers.register("dm_biome_modifier", DMBiomeModifier::makeCodec);
    }

    public Codec<? extends BiomeModifier> codec() {
        return SERIALIZER.get();
    }

    public static Codec<DMBiomeModifier> makeCodec() {
        return Codec.unit(DMBiomeModifier::new);
    }

}