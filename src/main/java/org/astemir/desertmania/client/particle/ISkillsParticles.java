package org.astemir.desertmania.client.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.math.collection.Couple;
import org.astemir.api.math.components.Color;
import org.astemir.api.math.components.Vector2;
import org.astemir.api.math.components.Vector3;


public interface ISkillsParticles {

    default void postRender(Camera camera, float partialTicks){};

    default void render(ParticleRenderType type, Camera camera, float partialTicks, Vector3 scale, Vector3 offset, Vector3 rotation, Color color){
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        type.begin(bufferbuilder, Minecraft.getInstance().getTextureManager());
        render(bufferbuilder,camera,partialTicks,scale,offset,rotation,color);
        type.end(tesselator);
    }

    default void render(VertexConsumer consumer,Camera camera, float partialTicks,Vector3 scale,Vector3 offset,Vector3 rotation,Color color){
        Vec3 vec3 = camera.getPosition();
        Vector3 posOld = getPosition().getKey();
        Vector3 pos = getPosition().getValue();
        float x1 = (float)(Mth.lerp(partialTicks, posOld.x, pos.x) - vec3.x());
        float y1 = (float)(Mth.lerp(partialTicks, posOld.y, pos.y) - vec3.y());
        float z1 = (float)(Mth.lerp(partialTicks, posOld.z, pos.z) - vec3.z());
        float oRoll = getRoll().getKey();
        float roll = getRoll().getValue();
        Quaternion quaternion;
        if (roll == 0.0F) {
            quaternion = camera.rotation();
        } else {
            quaternion = new Quaternion(camera.rotation());
            float f3 = Mth.lerp(partialTicks, oRoll, roll);
            quaternion.mul(Vector3f.ZP.rotation(f3));
        }
        Vector3f vector3f1 = new Vector3f(-1.0F, -1.0F, 0.0F);
        vector3f1.transform(quaternion);
        Vector3[] avector3f = new Vector3[]{new Vector3(-1.0F, -1.0F, 0.0F), new Vector3(-1.0F, 1.0F, 0.0F), new Vector3(1.0F, 1.0F, 0.0F), new Vector3(1.0F, -1.0F, 0.0F)};
        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i].toVector3f();
            if (rotation != null) {
                vector3f = Vector3.from(vector3f).rotateAroundZ(rotation.z).rotateAroundY(rotation.y).rotateAroundX(rotation.x).toVector3f();
            }
            vector3f.transform(quaternion);
            vector3f.mul(getSize(partialTicks)*scale.x,getSize(partialTicks)*scale.y,getSize(partialTicks)*scale.z);
            vector3f.add(x1+offset.x, y1+offset.y, z1+offset.z);
            avector3f[i] = Vector3.from(vector3f);
        }
        float f7 = getUV().getKey().x;
        float f8 = getUV().getKey().y;
        float f5 = getUV().getValue().x;
        float f6 = getUV().getValue().y;
        int lightColor = getLight(partialTicks);
        if (rotation != null) {
            RenderSystem.enableCull();
        }
        consumer.vertex(avector3f[0].x, avector3f[0].y, avector3f[0].z).uv(f8, f6).color(color.r, color.g,color. b, color.a).uv2(lightColor).endVertex();
        consumer.vertex(avector3f[1].x, avector3f[1].y, avector3f[1].z).uv(f8, f5).color(color.r, color.g,color. b, color.a).uv2(lightColor).endVertex();
        consumer.vertex(avector3f[2].x, avector3f[2].y, avector3f[2].z).uv(f7, f5).color(color.r, color.g,color. b, color.a).uv2(lightColor).endVertex();
        consumer.vertex(avector3f[3].x, avector3f[3].y, avector3f[3].z).uv(f7, f6).color(color.r, color.g, color.b, color.a).uv2(lightColor).endVertex();
        if (rotation != null) {
            RenderSystem.disableCull();
        }
    }

    float getSize(float partialTick);

    int getLight(float partialTick);

    Couple<Float,Float> getRoll();

    Couple<Vector3,Vector3> getPosition();

    Couple<Vector2,Vector2> getUV();
}
