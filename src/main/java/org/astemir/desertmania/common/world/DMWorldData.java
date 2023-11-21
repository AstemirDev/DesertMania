package org.astemir.desertmania.common.world;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.astemir.api.common.handler.WorldEventHandler;
import org.astemir.api.network.PacketArgument;

public class DMWorldData extends SavedData {

    private final SandstormHandler sandstorm = new SandstormHandler();

    private boolean timeStop = false;

    public static DMWorldData getInstance(Level level){
        if (level.isClientSide){
            return null;
        }
        DimensionDataStorage storage = ((ServerLevel)level).getDataStorage();
        return storage.computeIfAbsent(DMWorldData::new,DMWorldData::new,"desertmania_world_data");
    }



    public DMWorldData(CompoundTag tag) {
        sandstorm.load(tag.getCompound("Sandstorm"));
    }

    public DMWorldData() {
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.put("Sandstorm",sandstorm.save());
        return tag;
    }

    public boolean isTimeStop() {
        return timeStop;
    }

    public void setTimeStop(Level level,boolean timeStop) {
        this.timeStop = timeStop;
        if (!level.isClientSide){
            for (ServerPlayer player : ((ServerLevel) level).players()) {
                WorldEventHandler.playClientEvent(player,level,player.blockPosition(), WorldEvents.EVENT_TIMESTOP, PacketArgument.bool(timeStop));
            }
        }
    }

    public SandstormHandler getSandstorm() {
        return sandstorm;
    }
}
