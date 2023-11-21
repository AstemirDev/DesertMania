package org.astemir.desertmania.common.entity.genie.misc;

import org.astemir.api.common.animation.Animation;

public class GenieAnimations {

    public static final Animation ANIMATION_IDLE = new Animation("animation.model.idle",2.08f).layer(0).loop();
    public static final Animation ANIMATION_WALK_FLY = new Animation("animation.model.fly",1.08f).layer(0).loop();
    public static final Animation ANIMATION_CAST = new Animation("animation.model.cast",0.32f).layer(0).loop();
    public static final Animation ANIMATION_SING = new Animation("animation.model.sing",2.08f).layer(0).loop();
    public static final Animation ANIMATION_SPAWN = new Animation("animation.model.spawn",2.08f).layer(0).loop(Animation.Loop.HOLD_ON_LAST_FRAME);
    public static final Animation ANIMATION_HIDE = new Animation("animation.model.hide",1.16f).layer(0).loop(Animation.Loop.HOLD_ON_LAST_FRAME);
    public static final Animation ANIMATION_ATTACK = new Animation("animation.model.attack",0.84f).layer(0);
    public static final Animation ANIMATION_PLAYFUL = new Animation("animation.model.playful",1.04f).layer(1).priority(1);
    public static final Animation ANIMATION_WINKS = new Animation("animation.model.winks",0.8f).layer(1).priority(1);
    public static final Animation ANIMATION_ANGRY = new Animation("animation.model.angry",0.8f).layer(1).priority(1);
    public static final Animation ANIMATION_SAD = new Animation("animation.model.sad",0.8f).layer(1).priority(1);
    public static final Animation ANIMATION_JACKPOT = new Animation("animation.model.kush",1.04f).layer(0).loop();

    public static Animation[] getAnimations(){
        return new Animation[]{ANIMATION_IDLE,ANIMATION_WALK_FLY,ANIMATION_CAST,ANIMATION_SING,ANIMATION_SPAWN,ANIMATION_HIDE,ANIMATION_ATTACK,ANIMATION_PLAYFUL,ANIMATION_WINKS,ANIMATION_ANGRY,ANIMATION_SAD,ANIMATION_JACKPOT};
    }
}
