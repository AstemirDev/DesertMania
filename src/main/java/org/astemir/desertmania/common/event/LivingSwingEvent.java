package org.astemir.desertmania.common.event;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class LivingSwingEvent extends Event {

    private final LivingEntity livingEntity;
    private InteractionHand hand;
    private boolean swinging = false;
    private int swingTime = -1;

    public LivingSwingEvent(LivingEntity livingEntity, InteractionHand hand, boolean swinging, int swingTime) {
        this.livingEntity = livingEntity;
        this.hand = hand;
        this.swinging = swinging;
        this.swingTime = swingTime;
    }

    public InteractionHand getHand() {
        return hand;
    }

    public void setHand(InteractionHand hand) {
        this.hand = hand;
    }

    public boolean isSwinging() {
        return swinging;
    }

    public void setSwinging(boolean swinging) {
        this.swinging = swinging;
    }

    public int getSwingTime() {
        return swingTime;
    }

    public void setSwingTime(int swingTime) {
        this.swingTime = swingTime;
    }

    public LivingEntity getLivingEntity() {
        return livingEntity;
    }
}
