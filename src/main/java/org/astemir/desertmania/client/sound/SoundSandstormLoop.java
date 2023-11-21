package org.astemir.desertmania.client.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.biome.Biomes;
import org.astemir.desertmania.client.handler.DMLevelClientHandler;
import org.astemir.desertmania.common.sound.DMSounds;

public class SoundSandstormLoop extends AbstractTickableSoundInstance {


    public SoundSandstormLoop() {
        super(DMSounds.SANDSTORM.get(), SoundSource.WEATHER, SoundInstance.createUnseededRandom());
        this.looping = true;
        this.delay = 0;
        this.volume = 0.1f;
    }

    private boolean canBeHeard() {
        return volume > 0f;
    }

    @Override
    public void tick() {
        boolean end = !DMLevelClientHandler.SANDSTORM_ENABLED;
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            this.x = (float)player.getX();
            this.y = (float)player.getY();
            this.z = (float)player.getZ();
            float speed = Minecraft.getInstance().getPartialTick()/20f;
            if (!end) {
                boolean inDesert = player.level.getBiome(player.blockPosition()).is(Biomes.DESERT);
                if (!inDesert) {
                    if (canBeHeard()) {
                        volume -= speed;
                    }
                }else{
                    if (volume < 1f) {
                        volume += speed;
                    }
                }
            }else {
                volume-= speed;
                if (!canBeHeard()) {
                    stop();
                }
            }
        }
    }
}