package org.astemir.desertmania.common.particle;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.astemir.desertmania.DesertMania;

import java.util.function.Function;

public class DMParticles {

    public static DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, DesertMania.MOD_ID);
    public static final RegistryObject<ParticleType<GlowingDustParticleOptions>> GLOWING_DUST = PARTICLE_TYPES.register("glowing_dust",()->register(false, GlowingDustParticleOptions.DESERIALIZER, (p_123819_) -> GlowingDustParticleOptions.CODEC));

    private static <T extends ParticleOptions> ParticleType<T> register(boolean pOverrideLimiter, ParticleOptions.Deserializer<T> pDeserializer, final Function<ParticleType<T>, Codec<T>> pCodecFactory) {
        return new ParticleType<T>(pOverrideLimiter, pDeserializer) {
            public Codec<T> codec() {
                return pCodecFactory.apply(this);
            }
        };
    }
}
