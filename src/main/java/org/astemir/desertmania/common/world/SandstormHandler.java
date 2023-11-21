package org.astemir.desertmania.common.world;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.astemir.api.common.handler.WorldEventHandler;
import org.astemir.api.math.components.Vector3;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.api.network.PacketArgument;
import org.astemir.desertmania.common.item.DMItems;

public class SandstormHandler {

    private Vector3 windDirection = new Vector3(1,0,0);

    private long ticks = 0;

    private long lifeTicks = 0;

    private boolean enabled = false;

    public void update(Level level){
        if (isEnabled()) {
            if (ticks < lifeTicks) {
                ticks++;
            }else{
                end(level);
            }
        }
    }

    public void start(Level level,int time){
        lifeTicks = time;
        windDirection = new Vector3(RandomUtils.randomInt(-1,2),0,RandomUtils.randomInt(-1,2));
        if (windDirection.x == 0 && windDirection.z == 0){
            windDirection.x = 1;
        }
        enabled = true;
        ticks = 0;
        if (!level.isClientSide){
            for (ServerPlayer player : ((ServerLevel) level).players()) {
                WorldEventHandler.playClientEvent(player,level,player.blockPosition(), WorldEvents.EVENT_SANDSTORM_START);
            }
        }
    }

    public void end(Level level){
        lifeTicks = 0;
        enabled = false;
        ticks = 0;
        if (!level.isClientSide){
            for (ServerPlayer player : ((ServerLevel) level).players()) {
                WorldEventHandler.playClientEvent(player,level,player.blockPosition(), WorldEvents.EVENT_SANDSTORM_END);
            }
        }
    }

    public void load(CompoundTag tag){
        lifeTicks = tag.getInt("LifeTicks");
        ticks = tag.getInt("Ticks");
        enabled = tag.getBoolean("Enabled");
    }

    public CompoundTag save(){
        CompoundTag tag = new CompoundTag();
        tag.putInt("LifeTicks", (int) lifeTicks);
        tag.putInt("Ticks", (int) ticks);
        tag.putBoolean("Enabled",enabled);
        return tag;
    }

    public long getTicks() {
        return ticks;
    }

    public Vector3 getWindDirection() {
        return windDirection;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public static void sendUpdatePacket(Player player){
        DMWorldData worldData = DMWorldData.getInstance(player.level);
        if (worldData.getSandstorm().isEnabled()){
            if (player.level.isClientSide){
                return;
            }
            if (player instanceof ServerPlayer serverPlayer) {
                WorldEventHandler.playClientEvent(serverPlayer,player.level,player.blockPosition(),WorldEvents.EVENT_SANDSTORM_DISPLAY_EVENT, new PacketArgument(PacketArgument.ArgumentType.VEC3, worldData.getSandstorm().getWindDirection().toVec3()));
            }
        }
    }

    public static boolean canAffectEntity(Entity entity,Vector3 dir,BlockPos pos,Level level){
        boolean protection = false;
        if (entity instanceof Player player) {
            protection = player.isCreative() || player.isSpectator() || player.getItemBySlot(EquipmentSlot.HEAD).is(DMItems.SHEMAGH.get());
        }
        return (level.canSeeSky(pos) && canMoveEntity(entity,dir,level)) && !protection;
    }

    public static boolean canMoveEntity(Entity entity,Vector3 windDirection,Level level){
        boolean moveEntity = true;
        for (int i = 1; i < 20; i++) {
            if (moveEntity) {
                BlockPos upHitPos = new BlockPos(entity.getX() + windDirection.x * -i, entity.getY() + entity.getEyeHeight(), entity.getZ() + windDirection.z * -i);
                BlockPos downHitPos = new BlockPos(entity.getX() + windDirection.x * -i, entity.getY(), entity.getZ() + windDirection.z * -i);
                if (level.getBlockState(upHitPos).isSolidRender(level, upHitPos) && level.getBlockState(downHitPos).isSolidRender(level, downHitPos)) {
                    moveEntity = false;
                    break;
                }
            }
        }
        return moveEntity;
    }
}
