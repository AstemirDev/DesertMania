package org.astemir.desertmania.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.TextureManager;
import org.astemir.desertmania.client.particle.ISkillsParticles;
import org.lwjgl.opengl.GL13;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Queue;

@Mixin(value = ParticleEngine.class,priority = 500)
public class MixinParticleEngine {


    @Shadow @Final private Map<ParticleRenderType, Queue<Particle>> particles;
    @Shadow @Final private TextureManager textureManager;

    /**
     * @author Astemir
     * @reason Flexibility
     */
    @Overwrite(remap = false)
    public void render(PoseStack pMatrixStack, MultiBufferSource.BufferSource pBuffer, LightTexture pLightTexture, Camera pActiveRenderInfo, float pPartialTicks, @Nullable Frustum clippingHelper) {
        pLightTexture.turnOnLightLayer();
        RenderSystem.enableDepthTest();
        RenderSystem.activeTexture(GL13.GL_TEXTURE2);
        RenderSystem.enableTexture();
        RenderSystem.activeTexture(GL13.GL_TEXTURE0);
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.mulPoseMatrix(pMatrixStack.last().pose());
        RenderSystem.applyModelViewMatrix();

        for (ParticleRenderType particlerendertype : this.particles.keySet()) { // Forge: allow custom IParticleRenderType's
            if (particlerendertype == ParticleRenderType.NO_RENDER) continue;
            Iterable<Particle> iterable = this.particles.get(particlerendertype);
            if (iterable != null) {
             RenderSystem.setShader(GameRenderer::getParticleShader);
             RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
             Tesselator tesselator = Tesselator.getInstance();
             BufferBuilder bufferbuilder = tesselator.getBuilder();
             particlerendertype.begin(bufferbuilder, this.textureManager);

             for (Particle particle : iterable) {
                 if (clippingHelper != null && particle.shouldCull() && !clippingHelper.isVisible(particle.getBoundingBox()))
                     continue;
                 try {
                     particle.render(bufferbuilder, pActiveRenderInfo, pPartialTicks);
                 } catch (Throwable throwable) {
                     CrashReport crashreport = CrashReport.forThrowable(throwable, "Rendering Particle");
                     CrashReportCategory crashreportcategory = crashreport.addCategory("Particle being rendered");
                     crashreportcategory.setDetail("Particle", particle::toString);
                     crashreportcategory.setDetail("Particle Type", particlerendertype::toString);
                     throw new ReportedException(crashreport);
                 }
             }
             particlerendertype.end(tesselator);
             for (Particle particle : iterable) {
                 if (particle instanceof ISkillsParticles skillsParticles) {
                     if (clippingHelper != null && particle.shouldCull() && !clippingHelper.isVisible(particle.getBoundingBox()))
                         continue;
                     try {
                         skillsParticles.postRender(pActiveRenderInfo, pPartialTicks);
                     } catch (Throwable throwable) {
                         CrashReport crashreport = CrashReport.forThrowable(throwable, "Rendering Particle");
                         CrashReportCategory crashreportcategory = crashreport.addCategory("Particle being rendered");
                         crashreportcategory.setDetail("Particle", particle::toString);
                         crashreportcategory.setDetail("Particle Type", particlerendertype::toString);
                         throw new ReportedException(crashreport);
                     }
                 }
              }
            }
        }
        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
        pLightTexture.turnOffLightLayer();
    }
}
