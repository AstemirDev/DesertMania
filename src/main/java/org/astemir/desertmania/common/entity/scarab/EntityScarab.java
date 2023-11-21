package org.astemir.desertmania.common.entity.scarab;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.common.action.Action;
import org.astemir.api.common.action.ActionController;
import org.astemir.api.common.action.ActionStateMachine;
import org.astemir.api.common.animation.Animation;
import org.astemir.api.common.animation.AnimationFactory;
import org.astemir.api.common.animation.AnimationList;
import org.astemir.api.common.entity.ISkillsMob;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.api.network.PacketArgument;
import org.astemir.desertmania.common.sound.DMSounds;

public class EntityScarab extends Monster implements ISkillsMob {


    public static final Animation ANIMATION_IDLE = new Animation("animation.model.idle",0.8f).speed(1.5f).layer(0).loop();
    public static final Animation ANIMATION_FLY = new Animation("animation.model.fly",1.04f).layer(0).loop();
    public static final Animation ANIMATION_WALK = new Animation("animation.model.walk",0.48f).smoothness(1).speed(1.65f).layer(0).loop();
    public static final Animation ANIMATION_ATTACK = new Animation("animation.model.attack",0.32f).speed(1.25f).layer(1).priority(1);

    public AnimationFactory animationFactory = new AnimationFactory(this,ANIMATION_IDLE,ANIMATION_FLY,ANIMATION_WALK,ANIMATION_ATTACK){

        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation == ANIMATION_ATTACK){
                if (EntityUtils.hasTarget(EntityScarab.this)) {
                    getTarget().hurt(DamageSource.mobAttack(EntityScarab.this), (float) getAttributeValue(Attributes.ATTACK_DAMAGE));
                }
            }
        }
    };

    public ActionController moveController = new ActionController(this,"moveController",ACTION_FLY);
    public static final Action ACTION_FLY = new Action(0,"fly",-1);

    public ActionStateMachine stateMachine = new ActionStateMachine(moveController);

    public EntityScarab(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        xpReward = 0;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this,0.9f,false));
        this.goalSelector.addGoal(2,new FollowKingGoal(this,0.8f,2,8));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this,0.7f));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

    @Override
    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
        if (!moveController.is(ACTION_FLY)) {
            playSound(DMSounds.SCARAB_STEP.get(),0.2f, RandomUtils.randomFloat(0.9f,1.1f));
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (animationFactory.isPlaying(ANIMATION_FLY)){
            level.playSound(null,blockPosition(), DMSounds.SCARAB_FLY.get(), SoundSource.HOSTILE,0.05f,RandomUtils.randomFloat(0.9f,1.1f));
        }
        if (EntityUtils.hasTarget(this)){
            if (Math.abs(getTarget().getY()-getY()) >= 1.5f){
                if (!moveController.is(ACTION_FLY)) {
                    moveController.playAction(ACTION_FLY);
                    moveControl = new FlyingMoveControl(this,20,true);
                    navigation = new FlyingPathNavigation(this,level);
                }
            }else{
                if (!moveController.isNoAction() && tickCount % 40 == 0) {
                    moveController.setNoState();
                    moveControl = new MoveControl(this);
                    navigation = new GroundPathNavigation(this, level);
                    setYya(0.0F);
                    setZza(0.0F);
                    setNoGravity(false);
                }
            }
        }else{
            if (!moveController.isNoAction()) {
                moveController.setNoState();
                moveControl = new MoveControl(this);
                navigation = new GroundPathNavigation(this, level);
                setYya(0.0F);
                setZza(0.0F);
                setNoGravity(false);
            }
        }
        if (moveController.is(ACTION_FLY)){
            animationFactory.play(ANIMATION_FLY);
        }else {
            if (EntityUtils.isMoving(this,-0.15f,0.15f)) {
                animationFactory.play(ANIMATION_WALK);
            }else{
                animationFactory.play(ANIMATION_IDLE);
            }
        }
    }

    @Override
    public boolean doHurtTarget(Entity p_21372_) {
        if (!animationFactory.isPlayingLayer(ANIMATION_ATTACK)){
            animationFactory.play(ANIMATION_ATTACK);
        }
        return false;
    }

    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) {
        if (p_21016_ == DamageSource.CACTUS || p_21016_ == DamageSource.CRAMMING){
            return false;
        }
        return super.hurt(p_21016_, p_21017_);
    }

    @Override
    public boolean causeFallDamage(float p_148750_, float p_148751_, DamageSource p_148752_) {
        return false;
    }

    @Override
    protected void checkFallDamage(double p_27754_, boolean p_27755_, BlockState p_27756_, BlockPos p_27757_) {
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return SoundEvents.WART_BLOCK_BREAK;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WART_BLOCK_BREAK;
    }

    @Override
    public LookControl getLookControl() {
        return super.getLookControl();
    }

    @Override
    public <K extends IDisplayArgument> AnimationFactory getAnimationFactory(K argument) {
        return animationFactory;
    }

    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    public void onHandleServerEvent(int event, PacketArgument[] arguments) {}

    @Override
    public void onHandleClientEvent(int event, PacketArgument[] arguments) {}

    @Override
    public ActionStateMachine getActionStateMachine() {
        return stateMachine;
    }
}
