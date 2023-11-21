package org.astemir.desertmania.mixin.common;


import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.astemir.api.common.entity.IEventEntity;
import org.astemir.api.network.PacketArgument;
import org.astemir.desertmania.common.misc.IDMPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Player.class})
public abstract class MixinPlayer extends LivingEntity implements IDMPlayer, IEventEntity {



    @Shadow public abstract SoundSource getSoundSource();

    @Shadow public abstract float getSpeed();

    private static final int EVENT_SCIMITAR_SPIN_SYNC = 0;

    private int scimitarSpinTicks = 0;

    private boolean spinningWithScimitar = false;

    protected MixinPlayer(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }


    @Inject(method = "aiStep",at = @At("TAIL"))
    public void aiStep(CallbackInfo ci){
        if (scimitarSpinTicks > 0){
            --this.scimitarSpinTicks;
        }
        syncScimitarSpin(scimitarSpinTicks > 0);
    }

    @Override
    public void startScimitarAttack(int ticks) {
        this.scimitarSpinTicks = ticks;
        syncScimitarSpin(true);
    }

    @Override
    public boolean isSpinningWithScimitar() {
        return spinningWithScimitar;
    }

    @Override
    public void onHandleClientEvent(int event, PacketArgument[] arguments) {
        if (event == EVENT_SCIMITAR_SPIN_SYNC){
            spinningWithScimitar = arguments[0].asBoolean();
        }
    }


    private void syncScimitarSpin(boolean value){
        if (spinningWithScimitar != value) {
            spinningWithScimitar = value;
            playClientEvent(EVENT_SCIMITAR_SPIN_SYNC, PacketArgument.create(PacketArgument.ArgumentType.BOOL, value));
        }
    }

    @Override
    public void setScimitarSpinTicks(int ticks) {
        this.scimitarSpinTicks = ticks;
    }
}
