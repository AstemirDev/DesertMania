package org.astemir.desertmania.common.entity.genie;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.astemir.api.common.action.ActionController;
import org.astemir.api.common.action.ActionStateMachine;
import org.astemir.api.common.animation.Animation;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.desertmania.common.entity.DMEntities;
import org.astemir.desertmania.common.entity.genie.misc.EntityPolymorph;
import org.astemir.desertmania.common.entity.scarablord.EntityScarabLord;

public class EntityPurpleGenie extends EntityAbstractGenie{

    public ActionStateMachine stateMachine = new ActionStateMachine(spawnController);

    public EntityPurpleGenie(EntityType<? extends EntityAbstractGenie> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        animation();
    }

    @Override
    public boolean onAttack(LivingEntity target) {
        if (target.getType() == DMEntities.POLYMORPH.get()){
            return true;
        }else{
            if (!(target.isMultipartEntity()) && !(target instanceof Player) && !(target instanceof WitherBoss) && (!(target instanceof EntityScarabLord))) {
                if (RandomUtils.doWithChance(25)) {
                    CompoundTag tag = new CompoundTag();
                    target.save(tag);
                    EntityPolymorph polymorph = DMEntities.POLYMORPH.get().create(level);
                    polymorph.setEntityData(tag);
                    polymorph.moveTo(target.getX(), target.getY(), target.getZ(), target.getYRot(), target.getXRot());
                    level.addFreshEntity(polymorph);
                    target.remove(RemovalReason.DISCARDED);
                    playSound(SoundEvents.EVOKER_PREPARE_SUMMON);
                    playClientEvent(0);
                    return false;
                }
            }
        }
        return super.onAttack(target);
    }


    @Override
    public ActionStateMachine getActionStateMachine() {
        return stateMachine;
    }
}
