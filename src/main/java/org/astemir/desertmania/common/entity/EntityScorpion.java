package org.astemir.desertmania.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.common.action.ActionController;
import org.astemir.api.common.action.ActionStateMachine;
import org.astemir.api.common.action.IActionListener;
import org.astemir.api.common.animation.Animation;
import org.astemir.api.common.animation.AnimationFactory;
import org.astemir.api.common.animation.AnimationList;
import org.astemir.api.common.animation.objects.IAnimatedEntity;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.common.misc.ICustomRendered;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.desertmania.common.entity.fenick.EntityAbstractFenick;
import org.astemir.desertmania.common.sound.DMSounds;
import org.jetbrains.annotations.Nullable;

public class EntityScorpion extends Monster implements ICustomRendered, IAnimatedEntity {


    public static final Animation ANIMATION_IDLE = new Animation("animation.model.idle",2.08f).smoothness(4).layer(0).loop();
    public static final Animation ANIMATION_WALK = new Animation("animation.model.walk",0.96f).smoothness(1.5f).layer(0).loop();
    public static final Animation ANIMATION_ATTACK_1 = new Animation("animation.model.attack1",0.52f).layer(2).priority(2);
    public static final Animation ANIMATION_ATTACK_2 = new Animation("animation.model.attack2",0.52f).layer(2).priority(2);

    public AnimationFactory animationFactory = new AnimationFactory(this,ANIMATION_IDLE,ANIMATION_WALK,ANIMATION_ATTACK_1,ANIMATION_ATTACK_2){
        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation == ANIMATION_ATTACK_1){
                if (EntityUtils.hasTarget(EntityScorpion.this)) {
                    getTarget().hurt(DamageSource.mobAttack(EntityScorpion.this), (float) getAttributeValue(Attributes.ATTACK_DAMAGE));
                }
            }
            if (animation == ANIMATION_ATTACK_2){
                if (EntityUtils.hasTarget(EntityScorpion.this)) {
                    getTarget().hurt(DamageSource.mobAttack(EntityScorpion.this), (float) getAttributeValue(Attributes.ATTACK_DAMAGE));
                    getTarget().addEffect(new MobEffectInstance(MobEffects.POISON,200,3));
                }
            }
        }
    };


    public EntityScorpion(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this,0.5f,false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this,0.7f));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, EntityAbstractFenick.class, false));
    }

    @Override
    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
        this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (EntityUtils.isMoving(this,-0.15f,0.15f)) {
            animationFactory.play(ANIMATION_WALK);
        }else{
            animationFactory.play(ANIMATION_IDLE);
        }
    }


    @Override
    public boolean doHurtTarget(Entity p_21372_) {
        if (!animationFactory.isPlaying(ANIMATION_ATTACK_1,ANIMATION_ATTACK_2)){
            if (RandomUtils.doWithChance(getRandom(),25)){
                animationFactory.play(ANIMATION_ATTACK_2);
            }else{
                animationFactory.play(ANIMATION_ATTACK_1);
            }
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
    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return DMSounds.SCORPION_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return DMSounds.SCORPION_AMBIENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return DMSounds.SCORPION_DEATH.get();
    }

    @Override
    public AnimationFactory getAnimationFactory() {
        return animationFactory;
    }


    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    public <K extends IDisplayArgument> AnimationFactory getAnimationFactory(K argument) {
        return animationFactory;
    }
}
