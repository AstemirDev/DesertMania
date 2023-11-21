package org.astemir.desertmania.mixin.common;


import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WritableLevelData;
import org.astemir.desertmania.common.world.DMWorldData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class MixinServerLevel extends Level implements WorldGenLevel {

    @Shadow @Final private boolean tickTime;
    @Shadow @Final private ServerLevelData serverLevelData;
    @Shadow @Final private MinecraftServer server;

    protected MixinServerLevel(WritableLevelData pLevelData, ResourceKey<Level> pDimension, Holder<DimensionType> pDimensionTypeRegistration, Supplier<ProfilerFiller> pProfiler, boolean pIsClientSide, boolean pIsDebug, long pBiomeZoomSeed, int pMaxChainedNeighborUpdates) {
        super(pLevelData, pDimension, pDimensionTypeRegistration, pProfiler, pIsClientSide, pIsDebug, pBiomeZoomSeed, pMaxChainedNeighborUpdates);
    }


    /**
     * @author
     * @reason handling
     */
    @Overwrite
    protected void tickTime() {
        if (this.tickTime) {
            ServerLevel serverLevel = (ServerLevel) (Object)this;
            long i = serverLevel.getLevelData().getGameTime() + 1L;
            this.serverLevelData.setGameTime(i);
            this.serverLevelData.getScheduledEvents().tick(this.server, i);
            if (!DMWorldData.getInstance(serverLevel).isTimeStop()) {
                if (serverLevel.getLevelData().getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
                    serverLevel.setDayTime(serverLevel.getLevelData().getDayTime() + 1L);
                }
            }
        }
    }

    /**
     * @author
     * @reason handling
     */
    @Overwrite
    private void advanceWeatherCycle() {
        boolean flag = this.isRaining();
        if (this.dimensionType().hasSkyLight()) {
            ServerLevel serverLevel = (ServerLevel) (Object)this;
            if (!DMWorldData.getInstance(serverLevel).isTimeStop()) {

                if (this.getGameRules().getBoolean(GameRules.RULE_WEATHER_CYCLE)) {
                    int i = this.serverLevelData.getClearWeatherTime();
                    int j = this.serverLevelData.getThunderTime();
                    int k = this.serverLevelData.getRainTime();
                    boolean flag1 = this.levelData.isThundering();
                    boolean flag2 = this.levelData.isRaining();
                    if (i > 0) {
                        --i;
                        j = flag1 ? 0 : 1;
                        k = flag2 ? 0 : 1;
                        flag1 = false;
                        flag2 = false;
                    } else {
                        if (j > 0) {
                            --j;
                            if (j == 0) {
                                flag1 = !flag1;
                            }
                        } else if (flag1) {
                            j = Mth.randomBetweenInclusive(this.random, 3600, 15600);
                        } else {
                            j = Mth.randomBetweenInclusive(this.random, 12000, 180000);
                        }

                        if (k > 0) {
                            --k;
                            if (k == 0) {
                                flag2 = !flag2;
                            }
                        } else if (flag2) {
                            k = Mth.randomBetweenInclusive(this.random, 12000, 24000);
                        } else {
                            k = Mth.randomBetweenInclusive(this.random, 12000, 180000);
                        }
                    }

                    this.serverLevelData.setThunderTime(j);
                    this.serverLevelData.setRainTime(k);
                    this.serverLevelData.setClearWeatherTime(i);
                    this.serverLevelData.setThundering(flag1);
                    this.serverLevelData.setRaining(flag2);
                }

                this.oThunderLevel = this.thunderLevel;
                if (this.levelData.isThundering()) {
                    this.thunderLevel += 0.01F;
                } else {
                    this.thunderLevel -= 0.01F;
                }

                this.thunderLevel = Mth.clamp(this.thunderLevel, 0.0F, 1.0F);
                this.oRainLevel = this.rainLevel;
                if (this.levelData.isRaining()) {
                    this.rainLevel += 0.01F;
                } else {
                    this.rainLevel -= 0.01F;
                }

                this.rainLevel = Mth.clamp(this.rainLevel, 0.0F, 1.0F);
            }
        }
        if (this.oRainLevel != this.rainLevel) {
            this.server.getPlayerList().broadcastAll(new ClientboundGameEventPacket(ClientboundGameEventPacket.RAIN_LEVEL_CHANGE, this.rainLevel), this.dimension());
        }

        if (this.oThunderLevel != this.thunderLevel) {
            this.server.getPlayerList().broadcastAll(new ClientboundGameEventPacket(ClientboundGameEventPacket.THUNDER_LEVEL_CHANGE, this.thunderLevel), this.dimension());
        }

        if (flag != this.isRaining()) {
            if (flag) {
                this.server.getPlayerList().broadcastAll(new ClientboundGameEventPacket(ClientboundGameEventPacket.STOP_RAINING, 0.0F), this.dimension());
            } else {
                this.server.getPlayerList().broadcastAll(new ClientboundGameEventPacket(ClientboundGameEventPacket.START_RAINING, 0.0F), this.dimension());
            }

            this.server.getPlayerList().broadcastAll(new ClientboundGameEventPacket(ClientboundGameEventPacket.RAIN_LEVEL_CHANGE, this.rainLevel), this.dimension());
            this.server.getPlayerList().broadcastAll(new ClientboundGameEventPacket(ClientboundGameEventPacket.THUNDER_LEVEL_CHANGE, this.thunderLevel), this.dimension());
        }
    }
}
