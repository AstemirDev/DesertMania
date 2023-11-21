package org.astemir.desertmania.common.entity;

import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.common.animation.*;
import org.astemir.api.common.animation.objects.IAnimatedEntity;
import org.astemir.api.common.entity.EntityData;
import org.astemir.api.common.entity.IEventEntity;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.common.misc.ICustomRendered;
import org.astemir.api.common.misc.ParticleEmitter;
import org.astemir.api.math.MathUtils;
import org.astemir.api.math.components.Vector3;
import org.astemir.api.network.PacketArgument;
import org.astemir.desertmania.common.item.DMItems;
import javax.annotation.Nullable;



public class EntityFlyingCarpet extends Entity implements IAnimatedEntity, ICustomRendered, IEventEntity {

    public static final EntityData<Integer> SKIN =new EntityData<>(EntityFlyingCarpet.class,"SkinId", EntityDataSerializers.INT,0);

    public static Animation ANIMATION_REST = new Animation("animation.model.rest",0.04f).layer(0).loop();
    public static Animation ANIMATION_IDLE = new Animation("animation.model.idle",2.08f).layer(0).loop();
    public static Animation ANIMATION_FLY = new Animation("animation.model.flies",1.4f).layer(0).loop();


    private final AnimationFactory factory = new AnimationFactory(this,ANIMATION_REST,ANIMATION_IDLE,ANIMATION_FLY);


    private int lerpSteps;
    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private double lerpYRot;
    private double lerpXRot;

    private float speedMultiplier = 0.01f;


    public EntityFlyingCarpet(EntityType type, Level level) {
        super(type, level);
        this.blocksBuilding = true;
    }



    public EntityFlyingCarpet(Level p_38293_, double x, double y, double z) {
        this(DMEntities.FLYING_CARPET.get(), p_38293_);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    protected Vec3 getRelativePortalPosition(Direction.Axis p_38335_, BlockUtil.FoundRectangle p_38336_) {
        return LivingEntity.resetForwardDirectionOfRelativePortalPosition(super.getRelativePortalPosition(p_38335_, p_38336_));
    }


    @Override
    protected void checkFallDamage(double p_38307_, boolean p_38308_, BlockState p_38309_, BlockPos p_38310_) {
        if (!this.isPassenger()) {
            if (p_38308_) {
                if (this.fallDistance > 3.0F) {
                    this.resetFallDistance();
                    return;
                }
                this.resetFallDistance();
            }
        }
    }

    @Override
    public boolean hurt(DamageSource p_38319_, float p_38320_) {
        if (this.isInvulnerableTo(p_38319_)) {
            return false;
        } else if (!this.level.isClientSide && !this.isRemoved()) {
            if (p_38319_.getEntity() != null) {
                int skin = SKIN.get(this);
                if (skin == 0) {
                    spawnAtLocation(DMItems.BLUE_FLYING_CARPET.get());
                } else if (skin == 1) {
                    spawnAtLocation(DMItems.RED_FLYING_CARPET.get());
                } else if (skin == 2) {
                    spawnAtLocation(DMItems.GREEN_FLYING_CARPET.get());
                }
                this.discard();
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (player.isSecondaryUseActive()) {
            return InteractionResult.PASS;
        } else {
            if (!this.level.isClientSide) {
                return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
            } else {
                return InteractionResult.SUCCESS;
            }
        }
    }

    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }


    @Override
    public void tick() {
        super.tick();
        tickLerp();
        Entity control = getControllingPassenger();
        double deltaX = getDeltaMovement().x;
        double deltaY = getDeltaMovement().y;
        double deltaZ = getDeltaMovement().z;
        if (EntityUtils.isMovingByPlayer(this)){
            Player player = (Player)control;
            player.fallDistance = 0;
            float forward = player.zza;
            this.setYRot(player.getYRot());
            float limitedXRot = MathUtils.clamp(player.getXRot(),-10f,10f);
            setXRot(-limitedXRot);
            Vector3 input = Vector3.fromYawPitch(-getYRot(),-limitedXRot);
            deltaX += input.x * -forward / 30;
            deltaZ += input.z*-forward/30;
            speedMultiplier = MathUtils.lerp(speedMultiplier,1,0.1f);
            if (forward > 0 || forward < 0) {
                deltaY += input.y / 25;
                factory.play(ANIMATION_FLY);
                playClientEvent(0);
            }
        }else{
            speedMultiplier = MathUtils.lerp(speedMultiplier,0.01f,0.25f);
            factory.play(ANIMATION_IDLE);
        }
        if (control == null){
            setXRot(0);
            setYRot(0);
            deltaY = -0.1f;
            if (isOnGround()) {
                factory.play(ANIMATION_REST);
            }
        }
        setRot(this.getYRot(), this.getXRot());
        deltaX*=0.9f;
        deltaZ*=0.9f;
        deltaY*=0.9f;
        setDeltaMovement(deltaX,deltaY,deltaZ);
        move(MoverType.SELF, new Vec3(deltaX,deltaY,deltaZ));
    }

    @Override
    public float getStepHeight() {
        return 1;
    }

    private void tickLerp() {
        if (this.isControlledByLocalInstance()) {
            this.lerpSteps = 0;
            this.syncPacketPositionCodec(this.getX(), this.getY(), this.getZ());
        }

        if (this.lerpSteps > 0) {
            double d0 = this.getX() + (this.lerpX - this.getX()) / (double)this.lerpSteps;
            double d1 = this.getY() + (this.lerpY - this.getY()) / (double)this.lerpSteps;
            double d2 = this.getZ() + (this.lerpZ - this.getZ()) / (double)this.lerpSteps;
            double d3 = Mth.wrapDegrees(this.lerpYRot - (double)this.getYRot());
            this.setYRot(this.getYRot() + (float)d3 / (float)this.lerpSteps);
            this.setXRot(this.getXRot() + (float)(this.lerpXRot - (double)this.getXRot()) / (float)this.lerpSteps);
            --this.lerpSteps;
            this.setPos(d0, d1, d2);
            this.setRot(this.getYRot(), this.getXRot());
        }
    }

    @Override
    public void lerpTo(double p_38299_, double p_38300_, double p_38301_, float p_38302_, float p_38303_, int p_38304_, boolean p_38305_) {
        this.lerpX = p_38299_;
        this.lerpY = p_38300_;
        this.lerpZ = p_38301_;
        this.lerpYRot = p_38302_;
        this.lerpXRot = p_38303_;
        this.lerpSteps = 10;
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset();
    }

    @Override
    public boolean shouldRiderSit() {
        return true;
    }


    @Override
    public double getEyeY() {
        return this.getY() + 0.3F;
    }


    @Override
    protected void defineSynchedData() {
        SKIN.register(this);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_20052_) {
        SKIN.load(this,p_20052_);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_20139_) {
        SKIN.save(this,p_20139_);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return (entity.canBeCollidedWith() || entity.isPushable()) && !isPassengerOfSameVehicle(entity);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return true;
    }


    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        if (!level.isClientSide) {
            EntityFlyingCarpet copy = DMEntities.FLYING_CARPET.get().create(level);
            CompoundTag tag = new CompoundTag();
            this.addAdditionalSaveData(tag);
            copy.readAdditionalSaveData(tag);
            copy.copyPosition(passenger);
            level.addFreshEntity(copy);
        }
        this.remove(RemovalReason.DISCARDED);
    }

    @Override
    protected float getEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return sizeIn.height;
    }


    @Nullable
    public Entity getControllingPassenger() {
        for (Entity passenger : this.getPassengers()) {
            if (passenger instanceof Player) {
                Player player = (Player) passenger;
                return player;
            }
        }
        return null;
    }

    @Override
    public <K extends IDisplayArgument> AnimationFactory getAnimationFactory(K argument) {
        return factory;
    }

    @Override
    public void onHandleServerEvent(int event, PacketArgument[] arguments) {
    }

    @Override
    public void onHandleClientEvent(int event, PacketArgument[] arguments) {
        if (event == 0){
            Vector3 size = new Vector3(MathUtils.sin(tickCount), 0, 0).rotateAroundY(MathUtils.rad(-getYRot()));
            ParticleEmitter emitter = new ParticleEmitter(ParticleTypes.CLOUD);
            Vec3 view = getViewVector(0);
            emitter.emit(level, Vector3.from(position().add(view.multiply(-1, -1, -1).add(size.x, size.y, size.z))), new Vector3(-view.x, 0.5f, -view.z));
        }
    }
}
