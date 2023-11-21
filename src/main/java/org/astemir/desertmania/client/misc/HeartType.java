package org.astemir.desertmania.client.misc;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import org.astemir.desertmania.common.effect.DMMobEffects;


public class HeartType {

    public static final HeartType CONTAINER = new HeartType(0,false);
    public static final HeartType NORMAL = new HeartType(2,true);
    public static final HeartType POISIONED = new HeartType(4,true);
    public static final HeartType WITHERED = new HeartType(6,true);
    public static final HeartType ABSORBING = new HeartType(8,false);
    public static final HeartType FROZEN = new HeartType(9,false);
    public static final HeartType CURSED = new HeartType(10,false);

    private final int index;
    private final boolean canBlink;

    private HeartType(int p_168729_, boolean p_168730_) {
        this.index = p_168729_;
        this.canBlink = p_168730_;
    }

    public int getX(boolean p_168735_, boolean p_168736_) {
        int i;
        if (this == CONTAINER) {
            i = p_168736_ ? 1 : 0;
        } else {
            int j = p_168735_ ? 1 : 0;
            int k = this.canBlink && p_168736_ ? 2 : 0;
            i = j + k;
        }

        return 16 + (this.index * 2 + i) * 9;
    }

    public static HeartType forPlayer(Player p_168733_) {
        HeartType gui$hearttype;
        if (p_168733_.hasEffect(DMMobEffects.MUMMY_CURSE.get())){
            gui$hearttype = CURSED;
        }else
        if (p_168733_.hasEffect(MobEffects.POISON)) {
            gui$hearttype = POISIONED;
        } else if (p_168733_.hasEffect(MobEffects.WITHER)) {
            gui$hearttype = WITHERED;
        } else if (p_168733_.isFullyFrozen()) {
            gui$hearttype = FROZEN;
        }else{
            gui$hearttype = NORMAL;
        }

        return gui$hearttype;
    }
}
