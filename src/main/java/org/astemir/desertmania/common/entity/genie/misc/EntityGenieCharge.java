package org.astemir.desertmania.common.entity.genie.misc;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.common.animation.*;
import org.astemir.api.common.animation.objects.IAnimatedEntity;
import org.astemir.api.common.entity.IEventEntity;
import org.astemir.api.common.misc.ICustomRendered;
import org.astemir.api.common.misc.ParticleEmitter;
import org.astemir.api.math.components.Color;
import org.astemir.api.math.components.Vector3;
import org.astemir.api.network.PacketArgument;
import org.astemir.desertmania.common.entity.DMEntities;
import org.astemir.desertmania.common.entity.genie.EntityAbstractGenie;
import org.astemir.desertmania.common.particle.GlowingDustParticleOptions;

import javax.annotation.Nullable;
import java.util.UUID;

public class EntityGenieCharge extends Projectile implements IAnimatedEntity, ICustomRendered, IEventEntity {

    @Nullable
    private Entity finalTarget;
    @Nullable
    private UUID targetId;

    public AnimationFactory animationFactory = new AnimationFactory(this,IDLE);
    public static Animation IDLE = new Animation("animation.model.new",0.48f).smoothness(1).loop();

    public EntityGenieCharge(EntityType<? extends EntityGenieCharge> p_37319_, Level p_37320_) {
        super(p_37319_, p_37320_);
        this.noPhysics = true;
    }

    public EntityGenieCharge(Level p_37330_, LivingEntity p_37331_, Entity p_37332_) {
        this(DMEntities.GENIE_CHARGE.get(), p_37330_);
        this.setOwner(p_37331_);
        this.finalTarget = p_37332_;
        this.setPos(p_37331_.getX()+ p_37331_.getViewVector(0).x, p_37331_.getY()+0.35, p_37331_.getZ()+ p_37331_.getViewVector(0).z);
    }

    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    protected void addAdditionalSaveData(CompoundTag p_37357_) {
        super.addAdditionalSaveData(p_37357_);
        if (this.finalTarget != null) {
            p_37357_.putUUID("Target", this.finalTarget.getUUID());
        }
    }

    protected void readAdditionalSaveData(CompoundTag p_37353_) {
        super.readAdditionalSaveData(p_37353_);
        if (p_37353_.hasUUID("Target")) {
            this.targetId = p_37353_.getUUID("Target");
        }
    }

    protected void defineSynchedData() {
    }


    public void tick() {
        super.tick();
        Vec3 deltaMovement = getDeltaMovement();
        new ParticleEmitter(new GlowingDustParticleOptions(Color.YELLOW.toVector3f(),0.5f)).count(8).emit(level, Vector3.from(position().add(0,0.275f,0)),new Vector3(-deltaMovement.x,0,-deltaMovement.z));
        animationFactory.play(IDLE);
        if (!level.isClientSide){
            if ((finalTarget == null || finalTarget.isRemoved()) && targetId == null){
                discard();
            }
            if (this.finalTarget == null && this.targetId != null) {
                this.finalTarget = ((ServerLevel)this.level).getEntity(this.targetId);
                if (this.finalTarget == null) {
                    this.targetId = null;
                }
            }
        }
        if (!this.level.isClientSide && finalTarget != null) {
            float speed = Math.min(0.75f,((float)tickCount)/40f);
            Vector3 dir = Vector3.from(position()).direction(Vector3.from(finalTarget.position()));
            setDeltaMovement(dir.x*speed,dir.y*speed,dir.z*speed);
            hasImpulse = true;
            for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(0.5f,0.5f,0.5f))) {
                if (!(entity instanceof EntityAbstractGenie)) {
                    Entity owner = this.getOwner();
                    if (owner == null || !owner.getUUID().equals(entity.getUUID())) {
                        boolean flag = entity.hurt(DamageSource.indirectMobAttack(this, (LivingEntity) getOwner()).setProjectile(), 4.0F);
                        if (flag) {
                            this.doEnchantDamageEffects((LivingEntity) getOwner(), entity);
                        }
                        playClientEvent(0);
                        discard();
                        break;
                    }
                }
            }
            if (distanceToSqr(finalTarget) > 3) {
                ProjectileUtil.rotateTowardsMovement(this, 0.5F);
            }
        }

        this.checkInsideBlocks();
        Vec3 vec31 = this.getDeltaMovement();
        this.setPos(this.getX() + vec31.x, this.getY() + vec31.y, this.getZ() + vec31.z);
    }

    protected boolean canHitEntity(Entity p_37341_) {
        return super.canHitEntity(p_37341_) && !p_37341_.noPhysics;
    }

    public boolean isOnFire() {
        return false;
    }

    public boolean shouldRenderAtSqrDistance(double p_37336_) {
        return p_37336_ < 16384.0D;
    }

    public float getLightLevelDependentMagicValue() {
        return 1.0F;
    }

    protected void onHitEntity(EntityHitResult p_37345_) {
        super.onHitEntity(p_37345_);
    }

    protected void onHitBlock(BlockHitResult p_37343_) {
    }

    protected void onHit(HitResult p_37347_) {
        super.onHit(p_37347_);
        if (p_37347_.getType() != HitResult.Type.BLOCK) {
            this.discard();
        }
    }

    public boolean isPickable() {
        return true;
    }

    @Override
    public <K extends IDisplayArgument> AnimationFactory getAnimationFactory(K argument) {
        return animationFactory;
    }

    @Override
    public void onHandleServerEvent(int event, PacketArgument[] arguments) {
    }

    @Override
    public void onHandleClientEvent(int event, PacketArgument[] arguments) {
        if (event == 0){
            float f = 0.35f;
            new ParticleEmitter(new GlowingDustParticleOptions(Color.YELLOW.toVector3f(), 1)).count(8).size(new Vector3(f,f,f)).emit(level, Vector3.from(position().add(0,0.275f,0)), new Vector3(0, 0, 0));
        }
    }
}
