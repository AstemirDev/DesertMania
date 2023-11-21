package org.astemir.desertmania.common.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.common.action.Action;
import org.astemir.api.common.action.ActionController;
import org.astemir.api.common.action.ActionStateMachine;
import org.astemir.api.common.animation.Animation;
import org.astemir.api.common.animation.AnimationFactory;
import org.astemir.api.common.entity.EntityPredicates;
import org.astemir.api.common.entity.ISkillsMob;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.common.misc.ParticleEmitter;
import org.astemir.api.math.components.Vector3;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.api.network.PacketArgument;


public class EntityCactupine extends PathfinderMob implements ISkillsMob {


    public static final Animation ANIMATION_IDLE = new Animation("animation.model.idle",2.08f).layer(0).loop();
    public static final Animation ANIMATION_WALK = new Animation("animation.model.walk",0.8f).layer(0).loop();
    public static final Animation ANIMATION_CACTUS = new Animation("animation.model.cactus",0.04f).layer(0).loop();
    public static final Animation ANIMATION_ROLL = new Animation("animation.model.roll",0.72f).layer(0).loop();
    public static final Animation ANIMATION_HIDE = new Animation("animation.model.hide",0.52f).layer(1).loop(Animation.Loop.HOLD_ON_LAST_FRAME).priority(1);
    public static final Animation ANIMATION_GET_OUT = new Animation("animation.model.get_out",0.6f).layer(1).loop(Animation.Loop.HOLD_ON_LAST_FRAME).priority(1);


    public AnimationFactory animationFactory = new AnimationFactory(this,ANIMATION_IDLE,ANIMATION_WALK,ANIMATION_CACTUS,ANIMATION_ROLL,ANIMATION_HIDE,ANIMATION_GET_OUT){
        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation == ANIMATION_GET_OUT || animation == ANIMATION_HIDE){
                animationFactory.stop(animation);
            }
        }
    };

    public ActionController idleController = new ActionController(this,"stateController",ACTION_ROLL,ACTION_CACTUS);
    public static Action ACTION_ROLL = new Action(0,"roll",-1);
    public static Action ACTION_CACTUS = new Action(1,"cactus",-1);

    public ActionController actionController = new ActionController(this,"actionController",ACTION_HIDE,ACTION_SHOW){
        @Override
        public void onActionBegin(Action state) {
            if (state == ACTION_HIDE){
                animationFactory.play(ANIMATION_HIDE);
                navigation.stop();
                setXxa(0);
                setZza(0);
            }
            if (state == ACTION_SHOW){
                animationFactory.play(ANIMATION_GET_OUT);
            }
        }

        @Override
        public void onActionEnd(Action state) {
            if (state == ACTION_HIDE){
                idleController.playAction(ACTION_CACTUS);
            }
            if (state == ACTION_SHOW){
                idleController.setNoState();
            }
        }
    };
    public static Action ACTION_HIDE = new Action(0,"hide",0.52f);
    public static Action ACTION_SHOW = new Action(1,"show",0.6f);

    public ActionStateMachine stateMachine = new ActionStateMachine(actionController,idleController);

    public EntityCactupine(EntityType<? extends PathfinderMob> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        moveControl = new MoveControl(this){
            @Override
            public void tick() {
                if (!idleController.is(ACTION_CACTUS)) {
                    super.tick();
                }
            }
        };
        maxUpStep = 1;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this,0.4f));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }


    @Override
    public void aiStep() {
        super.aiStep();
        if (!isNoAi()) {
            if (idleController.isNoAction()) {
                if (RandomUtils.doWithChance(1)) {
                    actionController.playAction(ACTION_HIDE);
                }
            }
            if (idleController.is(ACTION_CACTUS)) {
                if (RandomUtils.doWithChance(1)) {
                    idleController.playAction(ACTION_ROLL);
                }
            }
        }
        if (idleController.is(ACTION_ROLL)){
            animationFactory.play(ANIMATION_ROLL);
            Vec3 view = getViewVector(0);
            Vec3 movement = new Vec3(view.x*0.5f,getDeltaMovement().y,view.z*0.5f);
            setDeltaMovement(movement);
            new ParticleEmitter(ParticleEmitter.block(level.getBlockState(getOnPos()))).size(new Vector3(0.5f,0.1f+ RandomUtils.randomFloat(0.1f,0.25f),0.5f)).count(8).emit(level, Vector3.from(position()),new Vector3(-movement.x*2,0.25f,-movement.z*2));
            for (LivingEntity entity : EntityUtils.getEntities(LivingEntity.class, level, blockPosition(), 1, EntityPredicates.exclude(this))) {
                EntityUtils.damageEntity(this, entity);
            }
            if (RandomUtils.doWithChance(1)){
                idleController.playAction(ACTION_CACTUS);
                actionController.playAction(ACTION_SHOW);
            }
        }else
        if (idleController.is(ACTION_CACTUS)){
            animationFactory.play(ANIMATION_CACTUS);
        }else{
            if (EntityUtils.isMoving(this,-0.15f,0.15f)) {
                animationFactory.play(ANIMATION_WALK);
            }else{
                animationFactory.play(ANIMATION_IDLE);
            }
        }
    }



    @Override
    protected InteractionResult mobInteract(Player p_21472_, InteractionHand p_21473_) {
        ItemStack itemstack = p_21472_.getItemInHand(p_21473_);
        if (!itemstack.isEmpty() && itemstack.is(Items.SWEET_BERRIES)) {
            if (!this.level.isClientSide) {
                this.playSound(SoundEvents.FOX_EAT, 1.0F, 1.5F);
            }
            if (!p_21472_.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            if (!level.isClientSide) {
                playClientEvent(0);
            }
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
        return super.mobInteract(p_21472_, p_21473_);
    }

    @Override
    public void onHandleServerEvent(int event, PacketArgument[] arguments) {}

    @Override
    public void onHandleClientEvent(int event, PacketArgument[] arguments) {
        if (event == 0){
            for(int i = 0; i < 20; ++i) {
                double d0 = this.random.nextGaussian() * 0.01D;
                double d1 = this.random.nextGaussian() * 0.01D;
                double d2 = this.random.nextGaussian() * 0.01D;
                this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 0.2D, this.getRandomZ(1.0D), d0, d1, d2);
            }
        }
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
        return SoundEvents.FOX_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.FOX_DEATH;
    }


    @Override
    public <K extends IDisplayArgument> AnimationFactory getAnimationFactory(K argument) {
        return animationFactory;
    }

    @Override
    public ActionStateMachine getActionStateMachine() {
        return stateMachine;
    }
}
