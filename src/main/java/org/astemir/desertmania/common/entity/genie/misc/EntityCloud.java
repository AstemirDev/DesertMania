package org.astemir.desertmania.common.entity.genie.misc;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.common.animation.*;
import org.astemir.api.common.animation.objects.IAnimatedEntity;
import org.astemir.api.common.misc.ICustomRendered;
import org.astemir.api.common.misc.ParticleEmitter;
import org.astemir.api.math.components.Vector3;
import org.astemir.desertmania.common.particle.GlowingDustParticleOptions;


public class EntityCloud extends Entity implements IAnimatedEntity, ICustomRendered {

    public AnimationFactory animationFactory = new AnimationFactory(this,IDLE);
    public static Animation IDLE = new Animation("animation.model.idle",2.08f).loop();

    public EntityCloud(EntityType<? extends EntityCloud> p_37319_, Level p_37320_) {
        super(p_37319_, p_37320_);
        this.noPhysics = true;
    }


    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    public boolean canCollideWith(Entity pEntity) {
        return (pEntity.canBeCollidedWith() || pEntity.isPushable()) && !isPassengerOfSameVehicle(pEntity);
    }

    @Override
    public void push(Entity pEntity) {
        super.push(pEntity);
    }

    public boolean isPushable() {
        return false;
    }


    protected void defineSynchedData() {
    }

    @Override
    public void tick() {
        super.tick();
        animationFactory.play(IDLE);
        if (tickCount > 40){
            new ParticleEmitter(new GlowingDustParticleOptions(GenieColors.BLUE.toVector3f(), 1)).count(32).size(new Vector3(0.5f,0.5f,0.5f)).emit(level, Vector3.from(getEyePosition()), new Vector3(0, 0, 0));
            remove(RemovalReason.DISCARDED);
        }
    }

    public boolean isOnFire() {
        return false;
    }

    public boolean shouldRenderAtSqrDistance(double p_37336_) {
        return p_37336_ < 16384.0D;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
    }


    public boolean isPickable() {
        return true;
    }


    @Override
    public <K extends IDisplayArgument> AnimationFactory getAnimationFactory(K argument) {
        return animationFactory;
    }
}
