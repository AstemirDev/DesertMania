package org.astemir.desertmania.common.misc;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public interface IDMPlayer {


    default Player asPlayer(){
        return (Player)this;
    }

    static IDMPlayer asIDMPlayer(Entity entity){
        return (IDMPlayer) entity;
    }

    void startScimitarAttack(int ticks);

    void setScimitarSpinTicks(int ticks);

    boolean isSpinningWithScimitar();
}
