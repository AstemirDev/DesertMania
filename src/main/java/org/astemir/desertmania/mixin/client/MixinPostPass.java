package org.astemir.desertmania.mixin.client;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.Tags;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.IntSupplier;

@Mixin(value = {PostPass.class},priority = 500)
public class MixinPostPass {


    @Shadow @Final public RenderTarget inTarget;
    @Shadow @Final public RenderTarget outTarget;
    @Shadow @Final private EffectInstance effect;
    @Shadow @Final private List<IntSupplier> auxAssets;
    @Shadow @Final private List<String> auxNames;
    @Shadow @Final private List<Integer> auxWidths;
    @Shadow @Final private List<Integer> auxHeights;
    @Shadow private Matrix4f shaderOrthoMatrix;


    /**
     * @author Astemir
     * @reason Unusable Mojang bullshit uniforms
     */
    @Overwrite
    public void process(float pPartialTicks) {
        this.inTarget.unbindWrite();
        float f = (float) this.outTarget.width;
        float f1 = (float) this.outTarget.height;
        RenderSystem.viewport(0, 0, (int) f, (int) f1);
        this.effect.setSampler("DiffuseSampler", this.inTarget::getColorTextureId);

        for (int i = 0; i < this.auxAssets.size(); ++i) {
            this.effect.setSampler(this.auxNames.get(i), this.auxAssets.get(i));
            this.effect.safeGetUniform("AuxSize" + i).set((float) this.auxWidths.get(i).intValue(), (float) this.auxHeights.get(i).intValue());
        }

        this.effect.safeGetUniform("ProjMat").set(this.shaderOrthoMatrix);
        this.effect.safeGetUniform("InSize").set((float) this.inTarget.width, (float) this.inTarget.height);
        this.effect.safeGetUniform("OutSize").set(f, f1);
        this.effect.safeGetUniform("Time").set(pPartialTicks);
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        Level level = minecraft.level;
        if (player != null){
            this.effect.safeGetUniform("GlobalTime").set(((float)player.tickCount)+pPartialTicks);
            this.effect.safeGetUniform("DayTime").set(((int)level.getDayTime()));
            this.effect.safeGetUniform("HotArea").set(getPlayerHotBiomeValue(level,player));
        }
        this.effect.safeGetUniform("ScreenSize").set((float) minecraft.getWindow().getWidth(), (float) minecraft.getWindow().getHeight());
        this.effect.apply();
        this.outTarget.clear(Minecraft.ON_OSX);
        this.outTarget.bindWrite(false);
        RenderSystem.depthFunc(519);
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
        bufferbuilder.vertex(0.0D, 0.0D, 500.0D).endVertex();
        bufferbuilder.vertex(f, 0.0D, 500.0D).endVertex();
        bufferbuilder.vertex(f, f1, 500.0D).endVertex();
        bufferbuilder.vertex(0.0D, f1, 500.0D).endVertex();
        BufferUploader.draw(bufferbuilder.end());
        RenderSystem.depthFunc(515);
        this.effect.clear();
        this.outTarget.unbindWrite();
        this.inTarget.unbindRead();

        for (Object object : this.auxAssets) {
            if (object instanceof RenderTarget) {
             ((RenderTarget) object).unbindRead();
            }
        }
    }


    public int getPlayerHotBiomeValue(Level level,Player player){
        Holder<Biome> biome = level.getBiome(player.blockPosition());
        if (biome.is(Biomes.DESERT)){
            return 1;
        }
        return 0;
    }
}