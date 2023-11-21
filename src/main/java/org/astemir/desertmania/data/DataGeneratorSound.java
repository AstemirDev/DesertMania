package org.astemir.desertmania.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.astemir.api.data.sound.DataSound;
import org.astemir.api.data.sound.SkillsSoundProvider;
import org.astemir.desertmania.common.sound.DMSounds;

public class DataGeneratorSound extends SkillsSoundProvider {
    
    public DataGeneratorSound(DataGenerator generator, String modId, ExistingFileHelper helper) {
        super(generator, modId, helper);
        loadSounds();
        register(generator);
    }
    
    private void loadSounds(){
        addSound(DMSounds.SCORPION_AMBIENT,createDefinition(sounds("entity/scorpion/ambient_0","entity/scorpion/ambient_1","entity/scorpion/ambient_2")));
        addSound(DMSounds.SCORPION_HURT,createDefinition(sounds("entity/scorpion/ambient_0","entity/scorpion/ambient_1","entity/scorpion/ambient_2")));
        addSound(DMSounds.SCORPION_DEATH,createDefinition(sounds("entity/scorpion/death")));
        addSound(DMSounds.FENICK_DEATH,createDefinition(sounds("entity/fenick/death1","entity/fenick/death2")));
        addSound(DMSounds.FENICK_HURT,createDefinition(sounds("entity/fenick/hurt1","entity/fenick/hurt2","entity/fenick/hurt3")));
        addSound(DMSounds.FENICK_LAUGH,createDefinition(sounds("entity/fenick/laugh1","entity/fenick/laugh2","entity/fenick/laugh3","entity/fenick/laugh4")));
        addSound(DMSounds.FENICK_TRADE,createDefinition(sounds("entity/fenick/trade")));
        addSound(DMSounds.SANDSTORM,createDefinition(sounds("generic/sandstorm_loop")));
        addSound(DMSounds.MEERKAT_IDLE,createDefinition(sounds("entity/meerkat/idle0","entity/meerkat/idle1","entity/meerkat/idle2","entity/meerkat/idle3","entity/meerkat/idle4","entity/meerkat/idle5","entity/meerkat/idle6")));
        addSound(DMSounds.MEERKAT_HURT,createDefinition(sounds("entity/meerkat/hurt0","entity/meerkat/hurt1","entity/meerkat/hurt2","entity/meerkat/hurt3")));
        addSound(DMSounds.MEERKAT_DEATH,createDefinition(sounds("entity/meerkat/death0","entity/meerkat/death1")));
        addSound(DMSounds.CAMEL_IDLE,createDefinition(sounds("entity/camel/ambient1","entity/camel/ambient2","entity/camel/ambient3","entity/camel/ambient4","entity/camel/ambient5","entity/camel/ambient6","entity/camel/ambient7","entity/camel/ambient8")));
        addSound(DMSounds.CAMEL_STEP,createDefinition(sounds("entity/camel/step1","entity/camel/step2","entity/camel/step3","entity/camel/step4","entity/camel/step5","entity/camel/step6")));
        addSound(DMSounds.CAMEL_STEP_SAND,createDefinition(sounds("entity/camel/step_sand1","entity/camel/step_sand2","entity/camel/step_sand3","entity/camel/step_sand4","entity/camel/step_sand5","entity/camel/step_sand6")));
        addSound(DMSounds.CAMEL_HURT,createDefinition(sounds("entity/camel/hurt1","entity/camel/hurt2","entity/camel/hurt3","entity/camel/hurt4")));
        addSound(DMSounds.CAMEL_DEATH,createDefinition(sounds("entity/camel/death1","entity/camel/death2")));
        addSound(DMSounds.SCARAB_LORD_IDLE,createDefinition(sounds("entity/scarab_lord/idle1","entity/scarab_lord/idle2","entity/scarab_lord/idle3","entity/scarab_lord/idle4")));
        addSound(DMSounds.SCARAB_LORD_HURT,createDefinition(sounds("entity/scarab_lord/hurt")));
        addSound(DMSounds.SCARAB_LORD_DEATH,createDefinition(sounds("entity/scarab_lord/death")));
        addSound(DMSounds.SCARAB_LORD_SCREAM,createDefinition(sounds("entity/scarab_lord/scream")));
        addSound(DMSounds.SCARAB_LORD_LIFE_STEAL,createDefinition(sounds("entity/scarab_lord/life_steal")));
        addSound(DMSounds.SCARAB_LORD_STEP,createDefinition(sounds("entity/scarab_lord/step1","entity/scarab_lord/step2","entity/scarab_lord/step3")));
        addSound(DMSounds.DESERT_WORM_DEATH,createDefinition(sounds("entity/desert_worm/death")));
        addSound(DMSounds.DESERT_WORM_STUN,createDefinition(sounds("entity/desert_worm/stun")));
        addSound(DMSounds.DESERT_WORM_GROWL,createDefinition(sounds("entity/desert_worm/growl0","entity/desert_worm/growl1")));
        addSound(DMSounds.DESERT_WORM_HURT,createDefinition(sounds("entity/desert_worm/hurt0","entity/desert_worm/hurt1","entity/desert_worm/hurt2")));
        addSound(DMSounds.DESERT_WORM_DIG,createDefinition(sounds("entity/desert_worm/dig")));
        addSound(DMSounds.SCARAB_FLY,createDefinition(sounds("entity/scarab/fly0","entity/scarab/fly1","entity/scarab/fly2","entity/scarab/fly3","entity/scarab/fly4","entity/scarab/fly5","entity/scarab/fly6")));
        addSound(DMSounds.SCARAB_STEP,createDefinition(sounds("entity/scarab/step1","entity/scarab/step2","entity/scarab/step3","entity/scarab/step4","entity/scarab/step5","entity/scarab/step6")));
        addSound(DMSounds.LOESS_BREAK,createDefinition(sounds("block/loess/break1","block/loess/break2","block/loess/break3","block/loess/break4")));
        addSound(DMSounds.LOESS_PLACE,createDefinition(sounds("block/loess/place1","block/loess/place2","block/loess/place3","block/loess/place4","block/loess/place5")));
        addSound(DMSounds.LOESS_STEP,createDefinition(sounds("block/loess/step1","block/loess/step2","block/loess/step3","block/loess/step4")));
        addSound(DMSounds.SCIMITAR_BLOCK,createDefinition(sounds("item/block")));
        addSound(DMSounds.SCIMITAR_WHIRL,createDefinition(sounds("item/whirl0","item/whirl1","item/whirl2")));

        addSound(DMSounds.MUSIC_DESERTMANIA,createDefinition(new DataSound("music/desertmania").preload()));
    }
}
