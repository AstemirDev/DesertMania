package org.astemir.desertmania.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import org.astemir.api.client.animation.AnimationUtils;
import org.astemir.api.client.animation.KeyFrame;
import org.astemir.api.common.animation.Animation;
import org.astemir.api.common.handler.CustomEvent;
import org.astemir.api.common.handler.CustomEventMap;
import org.astemir.api.common.handler.WorldEventHandler;
import org.astemir.api.math.components.Vector3;
import org.astemir.api.network.PacketArgument;
import org.astemir.desertmania.common.blockentity.BlockEntityGenieLamp;
import org.astemir.desertmania.common.entity.genie.EntityGreenGenie;
import org.astemir.desertmania.common.entity.genie.misc.GenieAnimations;

import java.util.function.Function;

public class GenieClientEventHandler implements WorldEventHandler.IClientWorldEventListener {


    public GenieHandler handler = new GenieHandler();

    public static final KeyFrame[] GENIE_ANIMATION_FRAMES = new KeyFrame[]{
            new KeyFrame(0,new Vector3(0,0,0)),
            new KeyFrame(20,new Vector3(0,250,0)),
            new KeyFrame(60,new Vector3(0,250,0)),
            new KeyFrame(80,new Vector3(0,30,0))
    };


    public static final CustomEvent EVENT_GENIE_JACKPOT = CustomEventMap.createEvent();
    public static final CustomEvent EVENT_GENIE_LAMP_SYNC = CustomEventMap.createEvent();

    private CustomEventMap eventMap = CustomEventMap.initialize().
            registerEvent(EVENT_GENIE_JACKPOT,(pos,level,arguments)->{
                Player player = Minecraft.getInstance().player;
                handler.showGenie = true;
                handler.position = 0;
                handler.startTicks = player.tickCount;
                Animation animation = GenieAnimations.ANIMATION_JACKPOT;
                EntityGreenGenie genie = getGenie();
                genie.clientSideKushSkin = true;
                genie.getAnimationFactory().getAnimationTicks().clear();
                genie.getAnimationFactory().setAnimation(animation, 0);
            }).
            registerEvent(EVENT_GENIE_LAMP_SYNC,(pos,level,arguments)->{
                if (level.getBlockEntity(pos) instanceof BlockEntityGenieLamp lamp){
                    lamp.getGenieData().setType(EntityType.byString(arguments[0].asString()).get());
                    lamp.getGenieData().setColor(arguments[1].asColor());
                }
            });


    @Override
    public void onHandleEvent(ClientLevel level, BlockPos pos, int event, PacketArgument[] arguments) {
        eventMap.handleEvent(event,level,pos,arguments);
    }


    public int getPosition(int ticks){
        return (int) AnimationUtils.interpolatePointsCatmullRom(GENIE_ANIMATION_FRAMES,ticks).y;
    }

    public int getStartTicks() {
        return handler.startTicks;
    }

    public boolean isShowGenie() {
        return handler.showGenie;
    }

    public void setShowGenie(boolean showGenie) {
        handler.showGenie = showGenie;
    }

    public float getPosition() {
        return handler.position;
    }

    public float setPosition(float pos) {
        this.handler.position = pos;
        return handler.position;
    }

    public EntityGreenGenie getGenie(){
        return handler.fakeGenie.getMob();
    }

    private class GenieHandler{
        public final FakeGenie fakeGenie = new FakeGenie();
        public int startTicks = 0;
        public boolean showGenie = false;
        public float position = 0;
    }

    private class FakeGenie{

        private EntityGreenGenie genie;

        public EntityGreenGenie getMob() {
            if (genie == null){
                CompoundTag nbt = new CompoundTag();
                nbt.putString("id","desertmania:green_genie");
                genie = (EntityGreenGenie) EntityType.loadEntityRecursive(nbt, Minecraft.getInstance().level, Function.identity());
            }
            return genie;
        }
    }
}
