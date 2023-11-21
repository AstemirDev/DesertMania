package org.astemir.desertmania.common.entity.scarablord;


import org.astemir.api.common.action.Action;

public class ScarabLordActions {

    //Movement
    public static final Action ACTION_MOVEMENT_DOWN = new Action(0,"run_down",-1);
    public static final Action ACTION_MOVEMENT_RUN = new Action(1,"run",-1);
    public static final Action ACTION_MOVEMENT_FLY = new Action(2,"fly",-1);
    public static final Action ACTION_MOVEMENT_WHIRL = new Action(3,"whirl",20);

    //Actions
    public static final Action ACTION_LIFE_STEAL = new Action(0,"life_steal",20);
    public static final Action ACTION_AMULET = new Action(1,"amulet",20);
    public static final Action ACTION_RAGE = new Action(2,"rage",1.56f);
    public static final Action ACTION_SPAWN = new Action(3,"spawn",2.04f);
    public static final Action ACTION_STAND_UP = new Action(4,"stand_up",1.04f);

    public static boolean isFlying(EntityScarabLord lord){
        return lord.getMovementController().is(ACTION_MOVEMENT_FLY) || lord.getActionController().is(ACTION_LIFE_STEAL,ACTION_AMULET);
    }

    public static final Action[] getMovementActions(){
        return new Action[]{ACTION_MOVEMENT_RUN,ACTION_MOVEMENT_DOWN,ACTION_MOVEMENT_FLY,ACTION_MOVEMENT_WHIRL};
    }


    public static final Action[] getActions(){
        return new Action[]{ACTION_LIFE_STEAL,ACTION_AMULET,ACTION_RAGE,ACTION_SPAWN,ACTION_STAND_UP};
    }
}
