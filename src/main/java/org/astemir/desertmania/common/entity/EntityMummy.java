package org.astemir.desertmania.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.*;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.common.animation.Animation;
import org.astemir.api.common.animation.AnimationFactory;
import org.astemir.api.common.animation.objects.IAnimatedEntity;
import org.astemir.api.common.entity.EntityData;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.common.misc.ICustomRendered;
import org.astemir.api.math.components.Vector2;
import org.astemir.api.math.components.Vector3;
import org.astemir.desertmania.common.entity.fenick.EntityAbstractFenick;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;

public class EntityMummy extends Monster implements ICustomRendered, IAnimatedEntity {

    public static EntityData<Boolean> DANCING = new EntityData<>(EntityMummy.class,"IsDancing", EntityDataSerializers.BOOLEAN,false);

    public static final Animation ANIMATION_IDLE = new Animation("animation.model.idle",2.08f).layer(0).loop();
    public static final Animation ANIMATION_DANCE = new Animation("animation.model.dance",0.8f).layer(0).loop();
    public static final Animation ANIMATION_JUMP = new Animation("animation.model.walk",0.8f).layer(0);
    public static final Animation ANIMATION_ATTACK = new Animation("animation.model.attack",0.4f).layer(0);

    public AnimationFactory animationFactory = new AnimationFactory(this,ANIMATION_IDLE,ANIMATION_JUMP,ANIMATION_ATTACK,ANIMATION_DANCE){
        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation == ANIMATION_ATTACK){
                if (EntityUtils.hasTarget(EntityMummy.this)) {
                    getTarget().hurt(DamageSource.mobAttack(EntityMummy.this), (float) getAttributeValue(Attributes.ATTACK_DAMAGE));
                }
            }
        }

        @Override
        public void onAnimationTick(Animation animation, int tick) {
            if (animation == ANIMATION_JUMP && tick == 6f){
                getJumpControl().jump();
            }
        }
    };
    private final DynamicGameEventListener<EntityMummy.JukeboxListener> dynamicJukeboxListener;



    @Nullable
    private BlockPos jukebox;

    protected EntityMummy(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        PositionSource positionsource = new EntityPositionSource(this, this.getEyeHeight());
        moveControl = new MummyMoveControl(this);
        dynamicJukeboxListener = new DynamicGameEventListener<>(new EntityMummy.JukeboxListener(positionsource, 6));
        maxUpStep = 1;
    }

    @Override
    public void updateDynamicGameEventListener(BiConsumer<DynamicGameEventListener<?>, ServerLevel> p_218348_) {
        Level level = this.level;
        if (level instanceof ServerLevel serverlevel) {
            p_218348_.accept(this.dynamicJukeboxListener, serverlevel);
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this,0.5f,false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this,0.4f));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, EntityAbstractFenick.class, false));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        DANCING.register(this);
    }


    @Override
    public void aiStep() {
        super.aiStep();
        if (isDancing() && tickCount % 20 == 0){
            setDeltaMovement(0,0.25f,0);
        }
        if (this.isDancing() && (this.jukebox == null || !this.jukebox.closerToCenterThan(this.position(), 6) || !this.level.getBlockState(this.jukebox).is(Blocks.JUKEBOX)) && this.tickCount % 20 == 0) {
            DANCING.set(this,false);
            this.jukebox = null;
        }

        if (!animationFactory.isPlaying(ANIMATION_ATTACK)) {
            if (!animationFactory.isPlaying(ANIMATION_JUMP)) {
                if (!isDancing()) {
                    animationFactory.play(ANIMATION_IDLE);
                }else{
                    animationFactory.play(ANIMATION_DANCE);
                }
            }
        }
    }


    @Override
    public boolean doHurtTarget(Entity p_21372_) {
        if (!animationFactory.isPlaying(ANIMATION_ATTACK)){
            animationFactory.play(ANIMATION_ATTACK);
        }
        return false;
    }

    @Override
    protected void jumpFromGround() {
        Vec3 vec3 = this.getDeltaMovement();
        Vec3 vec = getViewVector(0).multiply(0.3f,0,0.3f);
        this.setDeltaMovement(vec3.x+vec.x, (double)this.getJumpPower()/2f, vec3.z+vec.z);
        this.hasImpulse = true;
    }




    public void setJukeboxPlaying(BlockPos p_240102_, boolean p_240103_) {
        if (p_240103_) {
            if (!this.isDancing()) {
                this.jukebox = p_240102_;
                DANCING.set(this,true);
                hasImpulse = false;
                setDeltaMovement(0,0,0);
                setZza(0);
                setXxa(0);
                getNavigation().stop();
            }
        } else if (p_240102_.equals(this.jukebox) || this.jukebox == null) {
            this.jukebox = null;
            DANCING.set(this,false);
        }
    }

    public boolean isDancing() {
        return DANCING.get(this);
    }


    @Override
    public <K extends IDisplayArgument> AnimationFactory getAnimationFactory(K argument) {
        return animationFactory;
    }

    protected int getJumpDelay() {
        return 16;
    }

    static class MummyMoveControl extends MoveControl {

        private float yRot;
        private int jumpDelay;
        private final EntityMummy mummy;
        private boolean isAggressive;

        public MummyMoveControl(EntityMummy p_33668_) {
            super(p_33668_);
            this.mummy = p_33668_;
            this.yRot = 180.0F * p_33668_.getYRot() / (float)Math.PI;
        }

        public void setDirection(float p_33673_, boolean p_33674_) {
            this.yRot = p_33673_;
            this.isAggressive = p_33674_;
        }

        public void tick() {
            if (mummy.isDancing()){
                return;
            }
            if (EntityUtils.hasTarget(mummy)){
                isAggressive = true;
                Vector2 rot = Vector3.from(mummy.position()).direction(Vector3.from(mummy.getTarget().position())).yawPitchDeg();
                setDirection(-rot.x,true);
            }else{
                isAggressive = false;
            }
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
            this.mob.yHeadRot = this.mob.getYRot();
            this.mob.yBodyRot = this.mob.getYRot();
            if (this.operation != MoveControl.Operation.MOVE_TO) {
                this.mob.setZza(0.0F);
            } else {
                this.operation = MoveControl.Operation.WAIT;
                if (this.mob.isOnGround() && !mummy.animationFactory.isPlaying(ANIMATION_JUMP) && !mummy.animationFactory.isPlaying(ANIMATION_ATTACK)) {
                    this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                    if (this.jumpDelay-- <= 0) {
                        this.jumpDelay = this.mummy.getJumpDelay();
                        if (this.isAggressive) {
                            this.jumpDelay /= 3;
                        }
                        this.mummy.animationFactory.play(ANIMATION_JUMP);
                    } else {
                        this.mummy.xxa = 0.0F;
                        this.mummy.zza = 0.0F;
                        this.mob.setSpeed(0.0F);
                    }
                } else {
                    this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                }
            }
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return SoundEvents.HUSK_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.HUSK_DEATH;
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    class JukeboxListener implements GameEventListener {

        private final PositionSource listenerSource;
        private final int listenerRadius;

        public JukeboxListener(PositionSource p_239448_, int p_239449_) {
            this.listenerSource = p_239448_;
            this.listenerRadius = p_239449_;
        }

        public PositionSource getListenerSource() {
            return this.listenerSource;
        }

        public int getListenerRadius() {
            return this.listenerRadius;
        }

        public boolean handleGameEvent(ServerLevel p_240002_, GameEvent.Message p_240003_) {
            if (p_240003_.gameEvent() == GameEvent.JUKEBOX_PLAY) {
                EntityMummy.this.setJukeboxPlaying(new BlockPos(p_240003_.source()), true);
                return true;
            } else if (p_240003_.gameEvent() == GameEvent.JUKEBOX_STOP_PLAY) {
                EntityMummy.this.setJukeboxPlaying(new BlockPos(p_240003_.source()), false);
                return true;
            } else {
                return false;
            }
        }
    }
}
