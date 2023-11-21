package org.astemir.desertmania.common.entity.genie.misc;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.common.animation.*;
import org.astemir.api.common.animation.objects.IAnimatedEntity;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.common.misc.ICustomRendered;
import org.astemir.api.common.misc.ParticleEmitter;
import org.astemir.api.math.components.Vector3;
import org.astemir.desertmania.common.entity.genie.EntityAbstractGenie;
import org.astemir.desertmania.common.particle.GlowingDustParticleOptions;

import java.util.function.Predicate;

public class EntityBoxGlove extends Entity implements IAnimatedEntity, ICustomRendered {

    public AnimationFactory animationFactory = new AnimationFactory(this,ATTACK){
        @Override
        public void onAnimationTick(Animation animation, int tick) {
            if (tick == 0){
                EntityBoxGlove boxGlove = (EntityBoxGlove) getAnimated();
                for (LivingEntity entity : EntityUtils.getEntities(LivingEntity.class, level, blockPosition(), 1,excludeAll(boxGlove.getOwner()))) {
                    Vec3 vec = getViewVector(0);
                    entity.hurt(DamageSource.mobAttack(getOwner()),10);
                    entity.setDeltaMovement(-vec.x*0.5f,1.5f,-vec.z*0.5f);
                    entity.hasImpulse = true;
                }
            }
        }
    };
    public static Animation ATTACK = new Animation("animation.model.attack",1.28f).loop(Animation.Loop.HOLD_ON_LAST_FRAME);

    private EntityAbstractGenie owner;

    public EntityBoxGlove(EntityType<? extends EntityBoxGlove> p_37319_, Level p_37320_) {
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


    protected void defineSynchedData() {
    }

    @Override
    public void tick() {
        super.tick();
        animationFactory.updateAnimations();
        animationFactory.play(ATTACK);
        if (tickCount > 30){
            new ParticleEmitter(new GlowingDustParticleOptions(GenieColors.RED.toVector3f(), 1)).count(32).size(new Vector3(1,1,1)).emit(level, Vector3.from(getEyePosition()), new Vector3(0, 0, 0));
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

    public EntityAbstractGenie getOwner() {
        return owner;
    }

    public void setOwner(EntityAbstractGenie owner) {
        this.owner = owner;
    }


    public static Predicate<Entity> excludeAll(Entity... entities){
        return entity ->{
            for (Entity otherEntity : entities) {
                if (otherEntity != null) {
                    if (otherEntity.getUUID().equals(entity.getUUID())) {
                        return false;
                    }
                }
            }
            return true;
        };
    }
}
