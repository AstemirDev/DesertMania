package org.astemir.desertmania.mixin.common;

import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import org.astemir.desertmania.common.event.LivingSwingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({LivingEntity.class})
public abstract class MixinLivingEntity extends Entity implements net.minecraftforge.common.extensions.IForgeLivingEntity{

    @Shadow public abstract ItemStack getItemInHand(InteractionHand pHand);

    @Shadow public boolean swinging;
    @Shadow public int swingTime;

    @Shadow protected abstract int getCurrentSwingDuration();

    @Shadow public InteractionHand swingingArm;

    @Shadow public abstract void swing(InteractionHand pHand);

    public MixinLivingEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    /**
     * @author
     * @reason Can't handle it
     */
    @Overwrite
    public void swing(InteractionHand pHand, boolean pUpdateSelf) {
        ItemStack stack = this.getItemInHand(pHand);
        LivingEntity livingEntity = (LivingEntity)(Object)(this);
        if (!stack.isEmpty() && stack.onEntitySwing(livingEntity)) return;
        if (!this.swinging || this.swingTime >= this.getCurrentSwingDuration() / 2 || this.swingTime < 0) {
            LivingSwingEvent swingEvent = new LivingSwingEvent(livingEntity,pHand,true,-1);
            if (!MinecraftForge.EVENT_BUS.post(swingEvent)) {
                this.swingTime = swingEvent.getSwingTime();
                this.swinging = swingEvent.isSwinging();
                this.swingingArm = swingEvent.getHand();
                if (this.level instanceof ServerLevel) {
                    ClientboundAnimatePacket clientboundanimatepacket = new ClientboundAnimatePacket(this, swingEvent.getHand() == InteractionHand.MAIN_HAND ? 0 : 3);
                    ServerChunkCache serverchunkcache = ((ServerLevel) this.level).getChunkSource();
                    if (pUpdateSelf) {
                        serverchunkcache.broadcastAndSend(this, clientboundanimatepacket);
                    } else {
                        serverchunkcache.broadcast(this, clientboundanimatepacket);
                    }
                }
            }
        }

    }
}
