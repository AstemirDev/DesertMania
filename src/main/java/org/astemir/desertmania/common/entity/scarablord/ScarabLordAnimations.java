package org.astemir.desertmania.common.entity.scarablord;

import org.astemir.api.common.action.ActionController;
import org.astemir.api.common.animation.Animation;
import org.astemir.api.common.animation.AnimationFactory;
import org.astemir.api.common.animation.objects.IAnimated;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.math.random.RandomUtils;


public class ScarabLordAnimations {


    public static final Animation ANIMATION_IDLE = new Animation("animation.model.idle",2.08f).layer(0).smoothness(2).loop();
    public static final Animation ANIMATION_WALK = new Animation("animation.model.walk",2.08f).layer(0).smoothness(2).loop();
    public static final Animation ANIMATION_FLY_IDLE = new Animation("animation.model.fly_idle",2.08f).layer(0).smoothness(2).loop();
    public static final Animation ANIMATION_FLY_WALK = new Animation("animation.model.fly",2.08f).layer(0).smoothness(2).loop();
    public static final Animation ANIMATION_RUN = new Animation("animation.model.run",0.48f).layer(0).smoothness(2).loop();
    public static final Animation ANIMATION_IDLE_DOWN = new Animation("animation.model.idle_down",2.08f).layer(0).loop();
    public static final Animation ANIMATION_RUN_DOWN = new Animation("animation.model.run_down",0.64f).speed(1.5f).layer(0).loop();
    public static final Animation ANIMATION_STAND_UP = new Animation("animation.model.stand_up",1.04f).layer(0).loop(Animation.Loop.HOLD_ON_LAST_FRAME);
    public static final Animation ANIMATION_SPAWN = new Animation("animation.model.spawn",2.04f).layer(0);
    public static final Animation ANIMATION_RAGE = new Animation("animation.model.rage",1.56f).layer(1).priority(1).loop(Animation.Loop.HOLD_ON_LAST_FRAME);
    public static final Animation ANIMATION_ATTACK_ONCE = new Animation("animation.model.attack1",1f).layer(1).priority(1);
    public static final Animation ANIMATION_ATTACK_DOUBLE = new Animation("animation.model.attack2",1.12f).layer(1).priority(1);
    public static final Animation ANIMATION_ATTACK_SPREAD = new Animation("animation.model.attack3",1.08f).layer(1).priority(1);
    public static final Animation ANIMATION_ATTACK_FLY = new Animation("animation.model.fly_attack",0.76f).layer(1).priority(1).smoothness(1);
    public static final Animation ANIMATION_ATTACK_WHIRLING = new Animation("animation.model.attack4",0.48f).speed(1.25f).layer(0).loop();
    public static final Animation ANIMATION_ATTACK_AMULET = new Animation("animation.model.amulet_attack",2.08f).smoothness(2).layer(0).loop();
    public static final Animation ANIMATION_ATTACK_LIFE_STEAL = new Animation("animation.model.steal_life",2.16f).layer(0).loop();

    public static <T extends EntityScarabLord & IAnimated> void idleAnimation(T entity){
        AnimationFactory animationFactory = entity.getAnimationFactory();
        ActionController movementController = entity.getMovementController();
        ActionController actionController = entity.getActionController();
        if (movementController.is(ScarabLordActions.ACTION_MOVEMENT_WHIRL)){
            animationFactory.play(ANIMATION_ATTACK_WHIRLING);
            return;
        }else
        if (actionController.is(ScarabLordActions.ACTION_AMULET)){
            animationFactory.play(ANIMATION_ATTACK_AMULET);
            return;
        }else
        if (actionController.is(ScarabLordActions.ACTION_LIFE_STEAL)) {
            animationFactory.play(ANIMATION_ATTACK_LIFE_STEAL);
            return;
        }else
        if (actionController.is(ScarabLordActions.ACTION_SPAWN)){
            animationFactory.play(ANIMATION_SPAWN);
            return;
        }
        if (actionController.is(ScarabLordActions.ACTION_STAND_UP)){
            animationFactory.play(ANIMATION_STAND_UP);
            return;
        }
        if (EntityUtils.isMoving(entity,-0.15f,0.15f)){
            if (movementController.is(ScarabLordActions.ACTION_MOVEMENT_DOWN)){
                animationFactory.play(ANIMATION_RUN_DOWN);
            }else
            if (movementController.is(ScarabLordActions.ACTION_MOVEMENT_FLY)){
                animationFactory.play(ANIMATION_FLY_WALK);
            }else
            if (movementController.is(ScarabLordActions.ACTION_MOVEMENT_RUN)){
                animationFactory.play(ANIMATION_RUN);
            }else
            if (movementController.isNoAction()){
                animationFactory.play(ANIMATION_WALK);
            }
        }else{
            if (movementController.is(ScarabLordActions.ACTION_MOVEMENT_DOWN)){
                animationFactory.play(ANIMATION_IDLE_DOWN);
            }else
            if (movementController.is(ScarabLordActions.ACTION_MOVEMENT_FLY)){
                animationFactory.play(ANIMATION_FLY_IDLE);
            }else
            if (movementController.is(ScarabLordActions.ACTION_MOVEMENT_RUN) || movementController.isNoAction()){
                animationFactory.play(ANIMATION_IDLE);
            }
        }
    }

    public static <T extends EntityScarabLord & IAnimated> void attackAnimation(T entity) {
        AnimationFactory animationFactory = entity.getAnimationFactory();
        ActionController movementController = entity.getMovementController();
        if (!isAttacking(entity)) {
            if (movementController.is(ScarabLordActions.ACTION_MOVEMENT_FLY)) {
                animationFactory.play(ScarabLordAnimations.ANIMATION_ATTACK_FLY);
            } else {
                if (RandomUtils.doWithChance(25)) {
                    animationFactory.play(ScarabLordAnimations.ANIMATION_ATTACK_SPREAD);
                } else if (RandomUtils.doWithChance(25)) {
                    animationFactory.play(ScarabLordAnimations.ANIMATION_ATTACK_DOUBLE);
                } else {
                    animationFactory.play(ScarabLordAnimations.ANIMATION_ATTACK_ONCE);
                }
            }
        }
    }

    public static <T extends EntityScarabLord & IAnimated> boolean isAttacking(T entity){
        AnimationFactory animationFactory = entity.getAnimationFactory();
        return animationFactory.isPlaying(ANIMATION_ATTACK_WHIRLING,ANIMATION_ATTACK_FLY,ANIMATION_ATTACK_SPREAD,ANIMATION_ATTACK_ONCE,ANIMATION_ATTACK_DOUBLE);
    }

    public static Animation[] getAnimations(){
        return new Animation[]{ANIMATION_IDLE,ANIMATION_WALK,ANIMATION_FLY_IDLE,ANIMATION_FLY_WALK,ANIMATION_RUN,ANIMATION_RUN_DOWN,ANIMATION_STAND_UP,ANIMATION_RAGE,ANIMATION_ATTACK_ONCE,ANIMATION_ATTACK_DOUBLE,ANIMATION_ATTACK_SPREAD,ANIMATION_ATTACK_FLY,ANIMATION_ATTACK_WHIRLING,ANIMATION_ATTACK_AMULET,ANIMATION_ATTACK_LIFE_STEAL,ANIMATION_SPAWN,ANIMATION_IDLE_DOWN};
    }
}
