package org.astemir.desertmania.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.astemir.api.common.handler.WorldEventHandler;
import org.astemir.api.common.misc.ParticleEmitter;
import org.astemir.api.math.components.Vector3;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.api.network.PacketArgument;
import org.astemir.desertmania.client.sound.SoundSandstormLoop;
import org.astemir.desertmania.common.item.DMItems;
import org.astemir.desertmania.common.world.SandstormHandler;
import org.astemir.desertmania.common.world.WorldEvents;

public class DMLevelClientHandler implements WorldEventHandler.IClientWorldEventListener {


    public static SoundSandstormLoop SANDSTORM_LOOP = new SoundSandstormLoop();


    public static boolean SANDSTORM_ENABLED = false;
    public static boolean TIME_STOPPED = false;


    @Override
    public void onHandleEvent(ClientLevel level, BlockPos pos, int event, PacketArgument[] arguments) {
        if (event == WorldEvents.EVENT_TIMESTOP){
            TIME_STOPPED = arguments[0].asBoolean();
        }
        if (event == WorldEvents.EVENT_SANDSTORM_START){
            onSandstormBegin();
        }
        if (event == WorldEvents.EVENT_SANDSTORM_END){
            SANDSTORM_ENABLED = false;
        }
        if (event == WorldEvents.EVENT_SANDSTORM_DISPLAY_EVENT){
            if (!SANDSTORM_ENABLED){
                onSandstormBegin();
            }
            int count = RandomUtils.randomInt(40,80);
            if (RandomUtils.doWithChance(1)){
                count = 100;
            }
            Vector3 dir = Vector3.from(arguments[0].asVec3());
            LocalPlayer player = Minecraft.getInstance().player;
            boolean playerHasProtection = player.getItemBySlot(EquipmentSlot.HEAD).is(DMItems.SHEMAGH.get());
            if (playerHasProtection){
                count = 10;
            }
            if (SandstormHandler.canAffectEntity(player,dir,pos,level)) {
                player.push(dir.x * 0.05f, dir.y * 0.05f, dir.z * 0.05f);
            }
            ParticleEmitter emitter = new ParticleEmitter(ParticleEmitter.item(Blocks.SAND.asItem().getDefaultInstance())){
                @Override
                public void emit(Level level, Vector3 position, Vector3 speed) {
                    BlockPos pos = new BlockPos(position.toVec3());
                    if (!level.getBlockState(pos).isSolidRender(level,pos) && level.canSeeSky(pos)) {
                        super.emit(level, position, speed);
                    }
                }
            }.size(new Vector3(30, 10, 30)).count(count);
            for (int i = -10;i<0;i++) {
                emitter.emit(level, Vector3.from(pos).add(i).add(0, 10, 0), dir);
            }
        }
    }


    public void onSandstormBegin(){
        SANDSTORM_ENABLED = true;
        SoundManager soundManager = Minecraft.getInstance().getSoundManager();
        soundManager.stop(SANDSTORM_LOOP);
        SANDSTORM_LOOP = new SoundSandstormLoop();
        soundManager.play(SANDSTORM_LOOP);
    }

}
