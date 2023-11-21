package org.astemir.desertmania.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.RandomSource;
import org.astemir.api.math.components.Color;

public class RendererDragonRays {

    private static final float HALF_SQRT_3 = (float)(Math.sqrt(3.0D) / 2.0D);


    public static void render(Color color, float ticks, float lifeTime, float degree, PoseStack poseStack, MultiBufferSource bufferSource) {
        float f5 = ticks%lifeTime/lifeTime;
        float f7 = Math.min(f5 > 0.8F ? (f5 - 0.8F) / 0.2F : 0.0F, 1.0F);
        RandomSource randomsource = RandomSource.create(232);
        VertexConsumer vertexconsumer2 = bufferSource.getBuffer(RenderType.lightning());
        for(int i = 0; (float)i < (f5 + f5) / 2.0F * degree; ++i) {

            poseStack.mulPose(Vector3f.XP.rotationDegrees(randomsource.nextFloat() * 360.0F));
            poseStack.mulPose(Vector3f.YP.rotationDegrees(randomsource.nextFloat() * 360.0F));
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(randomsource.nextFloat() * 360.0F));
            poseStack.mulPose(Vector3f.XP.rotationDegrees(randomsource.nextFloat() * 360.0F));
            poseStack.mulPose(Vector3f.YP.rotationDegrees(randomsource.nextFloat() * 360.0F));
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(randomsource.nextFloat() * 360.0F + f5 * 90.0F));
            float f3 = randomsource.nextFloat() * 10.0F + 5.0F + f7 * 10.0F;
            float f4 = randomsource.nextFloat() * 1.0F + 1.0F + f7 * 1.0F;
            Matrix4f matrix4f = poseStack.last().pose();
            int j = (int)(100f * (1.0F - f7));
            vertex01(vertexconsumer2, matrix4f, j);
            vertex2(vertexconsumer2, matrix4f, f3, f4,color);
            vertex3(vertexconsumer2, matrix4f, f3, f4,color);
            vertex01(vertexconsumer2, matrix4f, j);
            vertex3(vertexconsumer2, matrix4f, f3, f4,color);
            vertex4(vertexconsumer2, matrix4f, f3, f4,color);
            vertex01(vertexconsumer2, matrix4f, j);
            vertex4(vertexconsumer2, matrix4f, f3, f4,color);
            vertex2(vertexconsumer2, matrix4f, f3, f4,color);
        }
    }

    private static void vertex01(VertexConsumer p_114220_, Matrix4f p_114221_, int p_114222_) {
        p_114220_.vertex(p_114221_, 0.0F, 0.0F, 0.0F).color(255, 255, 255, p_114222_).endVertex();
    }

    private static void vertex2(VertexConsumer p_114215_, Matrix4f p_114216_, float p_114217_, float p_114218_,Color color) {
        int r = (int) (color.r*255);
        int g = (int) (color.g*255);
        int b = (int) (color.b*255);
        p_114215_.vertex(p_114216_, -HALF_SQRT_3 * p_114218_, p_114217_, -0.5F * p_114218_).color(r,g,b, 0).endVertex();
    }

    private static void vertex3(VertexConsumer p_114224_, Matrix4f p_114225_, float p_114226_, float p_114227_,Color color) {
        int r = (int) (color.r*255);
        int g = (int) (color.g*255);
        int b = (int) (color.b*255);
        p_114224_.vertex(p_114225_, HALF_SQRT_3 * p_114227_, p_114226_, -0.5F * p_114227_).color(r,g,b, 0).endVertex();
    }

    private static void vertex4(VertexConsumer p_114229_, Matrix4f p_114230_, float p_114231_, float p_114232_,Color color) {
        int r = (int) (color.r*255);
        int g = (int) (color.g*255);
        int b = (int) (color.b*255);
        p_114229_.vertex(p_114230_, 0.0F, p_114231_, 1.0F * p_114232_).color(r,g,b, 0).endVertex();
    }
}
