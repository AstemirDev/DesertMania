package org.astemir.desertmania.mixin.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.astemir.desertmania.client.handler.DMLevelClientHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Supplier;

@Mixin({ClientLevel.class})
public abstract class MixinClientLevel extends Level {

    @Shadow public abstract void setGameTime(long pTime);

    @Shadow public abstract void setDayTime(long pTime);

    protected MixinClientLevel(WritableLevelData pLevelData, ResourceKey<Level> pDimension, Holder<DimensionType> pDimensionTypeRegistration, Supplier<ProfilerFiller> pProfiler, boolean pIsClientSide, boolean pIsDebug, long pBiomeZoomSeed, int pMaxChainedNeighborUpdates) {
        super(pLevelData, pDimension, pDimensionTypeRegistration, pProfiler, pIsClientSide, pIsDebug, pBiomeZoomSeed, pMaxChainedNeighborUpdates);
    }

    /**
     * @author
     * @reason handling
     */
    @Overwrite
    private void tickTime() {
        this.setGameTime(this.levelData.getGameTime() + 1L);
        if (!DMLevelClientHandler.TIME_STOPPED) {
            if (this.levelData.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
                this.setDayTime(this.levelData.getDayTime() + 1L);
            }
        }
    }
}
