package org.astemir.desertmania.common.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.common.action.Action;
import org.astemir.api.common.action.ActionController;
import org.astemir.api.common.action.ActionStateMachine;
import org.astemir.api.common.animation.Animation;
import org.astemir.api.common.animation.AnimationFactory;
import org.astemir.api.common.animation.AnimationList;
import org.astemir.api.common.entity.EntityPredicates;
import org.astemir.api.common.entity.ISkillsMob;
import org.astemir.api.common.entity.ai.AITaskSystem;
import org.astemir.api.common.entity.ai.ICustomAIEntity;
import org.astemir.api.common.entity.ai.goals.GoalConditionalWaterAvoidingStroll;
import org.astemir.api.common.entity.ai.tasks.AITask;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.api.network.PacketArgument;
import org.astemir.desertmania.common.sound.DMSounds;

import javax.annotation.Nullable;
import java.util.List;

public class EntityMeerkat extends Animal implements ISkillsMob, ICustomAIEntity {

    private final AnimationFactory animationFactory = new AnimationFactory(this,ANIMATION_IDLE,ANIMATION_WALK,ANIMATION_STAND,ANIMATION_WATCH,ANIMATION_UP,ANIMATION_DOWN,ANIMATION_SNIFF);

    public static Animation ANIMATION_IDLE = new Animation("animation.model.idle",2.08f).layer(0).loop();
    public static Animation ANIMATION_WALK = new Animation("animation.model.walk",1.04f).layer(0).loop();
    public static Animation ANIMATION_STAND = new Animation("animation.model.stand",2.08f).layer(0).loop();
    public static Animation ANIMATION_WATCH = new Animation("animation.model.watch",1.44f).layer(0);
    public static Animation ANIMATION_UP = new Animation("animation.model.up",0.44f).layer(0).loop(Animation.Loop.HOLD_ON_LAST_FRAME);
    public static Animation ANIMATION_DOWN = new Animation("animation.model.down",0.56f).layer(0).loop(Animation.Loop.HOLD_ON_LAST_FRAME);
    public static Animation ANIMATION_SNIFF = new Animation("animation.model.sniff",0.96f).layer(1).priority(1);


    public ActionController idleController = new ActionController(this,"idleController",ACTION_IDLE_STAND);
    public static Action ACTION_IDLE_STAND = new Action(0,"stand",-1);

    public ActionController actionController = new ActionController(this,"actionController",ACTION_STAND_UP,ACTION_STAND_DOWN){
        @Override
        public void onActionBegin(Action state) {
            if (state == ACTION_STAND_DOWN) {
                animationFactory.play(ANIMATION_DOWN);
            }else
            if (state == ACTION_STAND_UP) {
                animationFactory.play(ANIMATION_UP);
            }
        }
        @Override
        public void onActionEnd(Action state) {
            if (state == ACTION_STAND_UP){
                idleController.playAction(ACTION_IDLE_STAND);
            }else
            if (state == ACTION_STAND_DOWN){
                idleController.setNoState();
            }
        }
    };
    public static Action ACTION_STAND_UP = new Action(0,"up",0.44f);
    public static Action ACTION_STAND_DOWN = new Action(1,"down",0.56f);

    public ActionStateMachine stateMachine = new ActionStateMachine(idleController,actionController);

    public EntityMeerkat(EntityType<? extends Animal> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        moveControl = new MoveControl(this){
            @Override
            public void setWantedPosition(double p_24984_, double p_24985_, double p_24986_, double p_24987_) {
                if (canMove()) {
                    super.setWantedPosition(p_24984_, p_24985_, p_24986_, p_24987_);
                }
            }
        };
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (actionController.isNoAction()) {
            if (EntityUtils.isMoving(this, -0.15f, 0.15f) && idleController.isNoAction()) {
                animationFactory.play(ANIMATION_WALK);
            } else {
                if (idleController.is(ACTION_IDLE_STAND)) {
                    if (!animationFactory.isPlaying(ANIMATION_WATCH)) {
                        animationFactory.play(ANIMATION_STAND);
                        if (RandomUtils.doWithChance(random, 2f)) {
                            animationFactory.play(ANIMATION_WATCH);
                        }
                    }

                    if (RandomUtils.doWithChance(random, 0.5f)) {
                        actionController.playAction(ACTION_STAND_DOWN);
                    }
                } else {
                    animationFactory.play(ANIMATION_IDLE);
                    if (RandomUtils.doWithChance(random,0.5f)){
                        actionController.playAction(ACTION_STAND_UP);
                    }
                }
            }
        }
    }


    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.SPIDER_EYE.asItem());
    }

    public boolean canMove(){
        return actionController.isNoAction() && idleController.isNoAction();
    }

    public void forceStopStanding(){
        if (!actionController.is(ACTION_STAND_DOWN)) {
            if (actionController.is(ACTION_STAND_UP) || idleController.is(ACTION_IDLE_STAND)) {
                actionController.playAction(ACTION_STAND_DOWN);
            }
        }
    }

    @Override
    public InteractionResult mobInteract(Player p_21472_, InteractionHand p_21473_) {
        ItemStack itemstack = p_21472_.getItemInHand(p_21473_);
        if (!itemstack.isEmpty() && itemstack.is(Items.SPIDER_EYE)) {
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
    public boolean hurt(DamageSource p_27567_, float p_27568_) {
        if (p_27567_ == DamageSource.CACTUS){
            return false;
        }
        forceStopStanding();
        return super.hurt(p_27567_, p_27568_);
    }


    @Override
    public <K extends IDisplayArgument> AnimationFactory getAnimationFactory(K argument) {
        return animationFactory;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return DMSounds.MEERKAT_IDLE.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return DMSounds.MEERKAT_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return DMSounds.MEERKAT_HURT.get();
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return DMEntities.MEERKAT.get().create(p_146743_);
    }

    @Override
    public ActionStateMachine getActionStateMachine() {
        return stateMachine;
    }

    @Override
    public void onHandleClientEvent(int event, PacketArgument[] arguments) {
        if (event == 0){
            for(int i = 0; i < 10; ++i) {
                double d0 = this.random.nextGaussian() * 0.01D;
                double d1 = this.random.nextGaussian() * 0.01D;
                double d2 = this.random.nextGaussian() * 0.01D;
                this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 0.2D, this.getRandomZ(1.0D), d0, d1, d2);
            }
        }
    }

    private AITaskSystem aiTaskSystem;

    @Override
    public void setupAI() {
        aiTaskSystem = new AITaskSystem(this);
        aiTaskSystem.registerTask(new AITask(1).layer(0).priority(5).setGoal(new PanicGoal(this,1)));
        aiTaskSystem.registerTask(new AITask(2).layer(0).priority(4).setGoal(new FollowParentGoal(this, 0.6D)));
        aiTaskSystem.registerTask(new AITask(3).layer(0).priority(3).setGoal(new FollowBrothersGoal(this, 0.5D)));
        aiTaskSystem.registerTask(new AITask(5).layer(0).priority(2).setGoal(new GoalConditionalWaterAvoidingStroll(this, 0.5D,()->idleController.isNoAction() && actionController.isNoAction())));
        aiTaskSystem.registerTask(new AITask(6).layer(1).priority(1).setGoal(new LookAtPlayerGoal(this, Player.class, 6.0F)));
        aiTaskSystem.registerTask(new AITask(7).layer(1).priority(1).setGoal(new RandomLookAroundGoal(this)));
    }

    @Override
    public AITaskSystem getAISystem() {
        return aiTaskSystem;
    }

    public class FollowBrothersGoal extends Goal {

        private final Animal animal;
        @Nullable
        private Animal brother;
        private final double speedModifier;
        private int timeToRecalcPath;

        public FollowBrothersGoal(Animal p_25319_, double p_25320_) {
            this.animal = p_25319_;
            this.speedModifier = p_25320_;
        }

        public boolean canUse() {
            List<? extends Animal> list = EntityUtils.getEntities(this.animal.getClass(),level,blockPosition(),4, EntityPredicates.exclude(animal));
            Animal animal = null;
            double d0 = Double.MAX_VALUE;
            for(Animal animal1 : list) {
                double d1 = this.animal.distanceToSqr(animal1);
                if (!(d1 > d0)) {
                    d0 = d1;
                    animal = animal1;
                }
            }

            if (animal == null) {
                return false;
            } else if (d0 < 20.0D) {
                return false;
            } else {
                this.brother = animal;
                return true;
            }
        }

        public boolean canContinueToUse() {
            if (this.animal.getAge() >= 0) {
                return false;
            } else if (!this.brother.isAlive()) {
                return false;
            } else {
                double d0 = this.animal.distanceToSqr(this.brother);
                return !(d0 < 20.0D) && !(d0 > 256.0D);
            }
        }

        public void start() {
            this.timeToRecalcPath = 0;
        }

        public void stop() {
            this.brother = null;
        }

        public void tick() {
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = this.adjustedTickDelay(20);
                this.animal.getNavigation().moveTo(this.brother, this.speedModifier);
            }
        }
    }
}
