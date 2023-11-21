package org.astemir.desertmania.common.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.DustParticleOptionsBase;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

public class GlowingDustParticleOptions extends DustParticleOptionsBase {

    public static final Codec<GlowingDustParticleOptions> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder.group(Vector3f.CODEC.fieldOf("color").forGetter((p_175797_) -> {
            return p_175797_.getColor();
        }), Codec.FLOAT.fieldOf("scale").forGetter((p_175795_) -> {
            return p_175795_.getScale();
        })).apply(builder, GlowingDustParticleOptions::new);
    });

    public static final ParticleOptions.Deserializer<GlowingDustParticleOptions> DESERIALIZER = new ParticleOptions.Deserializer<GlowingDustParticleOptions>() {
        public GlowingDustParticleOptions fromCommand(ParticleType<GlowingDustParticleOptions> type, StringReader reader) throws CommandSyntaxException {
            Vector3f vector3f = DustParticleOptionsBase.readVector3f(reader);
            reader.expect(' ');
            float f = reader.readFloat();
            return new GlowingDustParticleOptions(vector3f, f);
        }

        public GlowingDustParticleOptions fromNetwork(ParticleType<GlowingDustParticleOptions> type, FriendlyByteBuf buf) {
            return new GlowingDustParticleOptions(DustParticleOptionsBase.readVector3f(buf), buf.readFloat());
        }
    };

    public GlowingDustParticleOptions(Vector3f color, float size) {
        super(color, size);
    }

    public ParticleType<GlowingDustParticleOptions> getType() {
        return DMParticles.GLOWING_DUST.get();
    }
}
