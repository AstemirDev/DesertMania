package org.astemir.desertmania.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.astemir.api.math.collection.Couple;
import org.astemir.api.math.components.Color;
import org.astemir.api.math.components.Vector2;
import org.astemir.api.math.components.Vector3;
import org.astemir.desertmania.common.particle.GlowingDustParticleOptions;

public class ParticleGlowingDust extends DustParticleBase<GlowingDustParticleOptions> implements ISkillsParticles{


    private long ticks = 0;

    protected ParticleGlowingDust(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, GlowingDustParticleOptions pOptions, SpriteSet pSprites) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, pOptions, pSprites);
    }

    @Override
    public ParticleRenderType getRenderType() {

        return DMParticleRenderTypes.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {
        ticks++;
    }

    @Override
    public void postRender(Camera camera, float partialTicks) {
        float t =((float)ticks)+partialTicks;
        float speed = 0.1f;
        float alpha = 0.35f;
        render(DMParticleRenderTypes.VERY_BRIGHT_FLAME,camera,partialTicks,new Vector3(1,1,1),new Vector3(0,0,0),null,new Color(rCol,gCol,bCol,0.1f));
        render(DMParticleRenderTypes.VERY_BRIGHT_FLAME,camera,partialTicks,new Vector3(1.5f,1.5f,1.5f),new Vector3(0,0,0),new Vector3((t%360)*speed,0,0),new Color(rCol,gCol,bCol,alpha));
        render(DMParticleRenderTypes.VERY_BRIGHT_FLAME,camera,partialTicks,new Vector3(1.5f,1.5f,1.5f),new Vector3(0,0,0),new Vector3(0,0,(t%360)*speed),new Color(rCol,gCol,bCol,alpha));
        render(DMParticleRenderTypes.VERY_BRIGHT_FLAME,camera,partialTicks,new Vector3(1.5f,1.5f,1.5f),new Vector3(0,0,0),new Vector3(0,(t%360)*speed,0),new Color(rCol,gCol,bCol,alpha));
        render(DMParticleRenderTypes.VERY_BRIGHT_FLAME,camera,partialTicks,new Vector3(1,1,1),new Vector3(0,0,0),null,new Color(rCol,gCol,bCol,0.1f));
        render(DMParticleRenderTypes.VERY_BRIGHT_FLAME,camera,partialTicks,new Vector3(1.5f,1.5f,1.5f),new Vector3(0,0,0),new Vector3(-(t%360)*speed,0,0),new Color(rCol,gCol,bCol,alpha));
        render(DMParticleRenderTypes.VERY_BRIGHT_FLAME,camera,partialTicks,new Vector3(1.5f,1.5f,1.5f),new Vector3(0,0,0),new Vector3(0,0,-(t%360)*speed),new Color(rCol,gCol,bCol,alpha));
        render(DMParticleRenderTypes.VERY_BRIGHT_FLAME,camera,partialTicks,new Vector3(1.5f,1.5f,1.5f),new Vector3(0,0,0),new Vector3(0,-(t%360)*speed,0),new Color(rCol,gCol,bCol,alpha));
    }

    @Override
    public Couple<Float, Float> getRoll() {
        return Couple.create(oRoll,roll);
    }

    @Override
    public Couple<Vector3, Vector3> getPosition() {
        return Couple.create(new Vector3(xo,yo,zo),new Vector3(x,y,z));
    }

    @Override
    public Couple<Vector2, Vector2> getUV() {
        return Couple.create(new Vector2(getU0(),getU1()),new Vector2(getV0(),getV1()));
    }

    @Override
    public float getSize(float partialTick) {
        return getQuadSize(partialTick);
    }

    @Override
    public int getLight(float partialTick) {
        return 255;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<GlowingDustParticleOptions> {
        private final SpriteSet sprites;

        public Provider(SpriteSet pSprites) {
            this.sprites = pSprites;
        }

        public Particle createParticle(GlowingDustParticleOptions pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new ParticleGlowingDust(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, pType, this.sprites);
        }
    }

}