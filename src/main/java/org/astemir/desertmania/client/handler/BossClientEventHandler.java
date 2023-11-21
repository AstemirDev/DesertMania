package org.astemir.desertmania.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import org.astemir.api.client.animation.AnimationUtils;
import org.astemir.api.client.animation.KeyFrame;
import org.astemir.api.common.handler.WorldEventHandler;
import org.astemir.api.math.components.Vector3;
import org.astemir.api.network.PacketArgument;
import org.astemir.desertmania.common.entity.scarablord.EntityScarabLord;
import org.astemir.desertmania.common.world.WorldEvents;

import java.util.function.Function;

public class BossClientEventHandler implements WorldEventHandler.IClientWorldEventListener {

    public static final KeyFrame[] BOSS_ROTATION_ANIMATION = new KeyFrame[]{
            new KeyFrame(0,new Vector3(0,100,0)),
            new KeyFrame(45,new Vector3(0,-90,0))
    };

    public static final KeyFrame[] BOSS_POSITION_ANIMATION = new KeyFrame[]{
            new KeyFrame(0,new Vector3(0,650,0)),
            new KeyFrame(40,new Vector3(0,1200,0))
    };

    private final FakeBoss fakeBoss = new FakeBoss();
    private int startTicks = 0;
    private boolean showBoss = false;
    private float position = 0;
    private float rotation = 0;

    @Override
    public void onHandleEvent(ClientLevel level, BlockPos pos, int event, PacketArgument[] arguments) {
        if (event == WorldEvents.EVENT_BOSS_TAUNT) {
            Player player = Minecraft.getInstance().player;
            this.showBoss = true;
            this.position = 0;
            this.rotation = 0;
            this.startTicks = player.tickCount;
            EntityScarabLord boss = fakeBoss.getMob();
            boss.getAnimationFactory().getAnimationTicks().clear();
        }
    }



    public Vector3 getRotation(int ticks){
        return AnimationUtils.interpolatePointsCatmullRom(BOSS_ROTATION_ANIMATION,ticks);
    }

    public int getPosition(int ticks){
        return (int) AnimationUtils.interpolatePoints(BOSS_POSITION_ANIMATION,ticks).y;
    }

    public int getStartTicks() {
        return startTicks;
    }

    public boolean isShowBoss() {
        return showBoss;
    }

    public float getPosition() {
        return position;
    }

    public float setPosition(float pos) {
        this.position = pos;
        return this.position;
    }

    public float getRotation() {
        return rotation;
    }

    public float setRotation(float rot) {
        this.rotation = rot;
        return this.rotation;
    }

    public void setShowBoss(boolean showBoss) {
        this.showBoss = showBoss;
    }

    public EntityScarabLord getBoss(){
        return fakeBoss.getMob();
    }

    private static class FakeBoss{

        private EntityScarabLord boss;

        public EntityScarabLord getMob() {
            if (boss == null){
                CompoundTag nbt = new CompoundTag();
                nbt.putString("id","desertmania:scarab_lord");
                boss = (EntityScarabLord) EntityType.loadEntityRecursive(nbt, Minecraft.getInstance().level, Function.identity());
            }
            return boss;
        }
    }
}
