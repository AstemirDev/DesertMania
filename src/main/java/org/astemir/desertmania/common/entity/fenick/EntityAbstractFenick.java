package org.astemir.desertmania.common.entity.fenick;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import org.astemir.api.common.entity.ISkillsMob;
import org.astemir.api.common.world.WorldUtils;
import org.astemir.desertmania.common.sound.DMSounds;
import org.jetbrains.annotations.Nullable;

public abstract class EntityAbstractFenick extends PathfinderMob implements ISkillsMob {

    protected EntityAbstractFenick(EntityType<? extends PathfinderMob> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        setPersistenceRequired();
    }

    public boolean canBeAggressive(){
        return WorldUtils.isNight(level);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return DMSounds.FENICK_LAUGH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return DMSounds.FENICK_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return DMSounds.FENICK_DEATH.get();
    }
}
