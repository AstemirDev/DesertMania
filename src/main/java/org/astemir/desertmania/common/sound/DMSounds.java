package org.astemir.desertmania.common.sound;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.astemir.api.common.register.SoundRegistry;
import org.astemir.desertmania.DesertMania;

public class DMSounds extends SoundRegistry {

    public static DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DesertMania.MOD_ID);
    public static RegistryObject<SoundEvent> LOESS_BREAK = registerSound("block.loess.break");
    public static RegistryObject<SoundEvent> LOESS_PLACE = registerSound("block.loess.place");
    public static RegistryObject<SoundEvent> LOESS_STEP = registerSound("block.loess.step");
    public static RegistryObject<SoundEvent> MEERKAT_IDLE = registerSound("entity.meerkat.idle");
    public static RegistryObject<SoundEvent> MEERKAT_HURT = registerSound("entity.meerkat.hurt");
    public static RegistryObject<SoundEvent> MEERKAT_DEATH = registerSound("entity.meerkat.death");
    public static RegistryObject<SoundEvent> SANDSTORM = registerSound("generic.sandstorm.loop");
    public static RegistryObject<SoundEvent> SCARAB_FLY = registerSound("entity.scarab.fly");
    public static RegistryObject<SoundEvent> SCARAB_STEP = registerSound("entity.scarab.step");
    public static RegistryObject<SoundEvent> CAMEL_IDLE = registerSound("entity.camel.idle");
    public static RegistryObject<SoundEvent> CAMEL_HURT = registerSound("entity.camel.hurt");
    public static RegistryObject<SoundEvent> CAMEL_DEATH = registerSound("entity.camel.death");
    public static RegistryObject<SoundEvent> CAMEL_STEP = registerSound("entity.camel.step");
    public static RegistryObject<SoundEvent> CAMEL_STEP_SAND = registerSound("entity.camel.step_sand");
    public static RegistryObject<SoundEvent> DESERT_WORM_DEATH = registerSound("entity.desert_worm.death");
    public static RegistryObject<SoundEvent> DESERT_WORM_DIG = registerSound("entity.desert_worm.dig");
    public static RegistryObject<SoundEvent> DESERT_WORM_GROWL = registerSound("entity.desert_worm.growl");
    public static RegistryObject<SoundEvent> DESERT_WORM_HURT = registerSound("entity.desert_worm.hurt");
    public static RegistryObject<SoundEvent> DESERT_WORM_STUN = registerSound("entity.desert_worm.stun");
    public static RegistryObject<SoundEvent> SCARAB_LORD_IDLE = registerSound("entity.scarab_lord.idle");
    public static RegistryObject<SoundEvent> SCARAB_LORD_HURT = registerSound("entity.scarab_lord.hurt");
    public static RegistryObject<SoundEvent> SCARAB_LORD_STEP = registerSound("entity.scarab_lord.step");
    public static RegistryObject<SoundEvent> SCARAB_LORD_SCREAM = registerSound("entity.scarab_lord.scream");
    public static RegistryObject<SoundEvent> SCARAB_LORD_LIFE_STEAL = registerSound("entity.scarab_lord.life_steal");
    public static RegistryObject<SoundEvent> SCARAB_LORD_DEATH = registerSound("entity.scarab_lord.death");
    public static RegistryObject<SoundEvent> FENICK_DEATH = registerSound("entity.fenick.death");
    public static RegistryObject<SoundEvent> FENICK_HURT = registerSound("entity.fenick.hurt");
    public static RegistryObject<SoundEvent> FENICK_LAUGH = registerSound("entity.fenick.laugh");
    public static RegistryObject<SoundEvent> FENICK_TRADE = registerSound("entity.fenick.trade");
    public static RegistryObject<SoundEvent> SCORPION_AMBIENT = registerSound("entity.scorpion.ambient");
    public static RegistryObject<SoundEvent> SCORPION_HURT = registerSound("entity.scorpion.hurt");
    public static RegistryObject<SoundEvent> SCORPION_DEATH = registerSound("entity.scorpion.death");
    public static RegistryObject<SoundEvent> SCIMITAR_BLOCK = registerSound("item.scimitar.block");
    public static RegistryObject<SoundEvent> SCIMITAR_WHIRL = registerSound("item.scimitar.whirl");

    public static RegistryObject<SoundEvent> MUSIC_DESERTMANIA = registerSound("music.records.desertmania");

    public static final ForgeSoundType LOESS = new ForgeSoundType(1.0F, 1.0F, LOESS_BREAK, LOESS_STEP, LOESS_PLACE, ()->SoundEvents.STONE_HIT,()->SoundEvents.STONE_FALL);

    public static RegistryObject<SoundEvent> registerSound(String name){
        return register(SOUNDS,DesertMania.MOD_ID,name);
    }
}
