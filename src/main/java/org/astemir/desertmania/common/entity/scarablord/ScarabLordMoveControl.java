package org.astemir.desertmania.common.entity.scarablord;

import net.minecraft.world.entity.ai.control.MoveControl;
import org.astemir.api.common.action.ActionController;

import static org.astemir.desertmania.common.entity.scarablord.ScarabLordAnimations.*;

public class ScarabLordMoveControl extends MoveControl {

    public ScarabLordMoveControl(EntityScarabLord mob) {
        super(mob);
    }

    public static boolean canMove(EntityScarabLord mob){
        ActionController<EntityScarabLord> actionController = mob.getActionController();
        return actionController.isNoAction() && !mob.getAnimationFactory().isPlaying(ANIMATION_ATTACK_FLY,ANIMATION_ATTACK_SPREAD,ANIMATION_ATTACK_ONCE,ANIMATION_ATTACK_DOUBLE);
    }


    public EntityScarabLord getMob(){
        return (EntityScarabLord)mob;
    }


    @Override
    public void setWantedPosition(double p_24984_, double p_24985_, double p_24986_, double p_24987_) {
        if (canMove(getMob())) {
            super.setWantedPosition(p_24984_, p_24985_, p_24986_, p_24987_);
        }
    }
}
