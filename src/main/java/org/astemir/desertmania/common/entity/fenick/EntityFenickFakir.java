package org.astemir.desertmania.common.entity.fenick;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.common.action.Action;
import org.astemir.api.common.action.ActionController;
import org.astemir.api.common.action.ActionStateMachine;
import org.astemir.api.common.animation.Animation;
import org.astemir.api.common.animation.AnimationFactory;
import org.astemir.api.common.entity.EntityPredicates;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.common.entity.utils.TradeUtils;
import org.astemir.api.common.world.WorldUtils;
import org.astemir.api.math.components.Vector3;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.api.network.PacketArgument;
import org.astemir.desertmania.common.entity.ai.GoalLookForItem;
import org.astemir.desertmania.common.item.DMItems;

public class EntityFenickFakir extends EntityAbstractFenickTrader {


    public static final Animation ANIMATION_IDLE = new Animation("animation.model.idle",2.08f).layer(0).loop();
    public static final Animation ANIMATION_DANCE = new Animation("animation.model.dance",0.8f).layer(0).loop();
    public static final Animation ANIMATION_WALK = new Animation("animation.model.walk",1.6f).layer(0).loop();
    public static final Animation ANIMATION_RUN = new Animation("animation.model.run",0.56f).layer(0).loop();
    public static final Animation ANIMATION_ATTACK_1 = new Animation("animation.model.attack1",1.52f).layer(1).priority(1);
    public static final Animation ANIMATION_ATTACK_2 = new Animation("animation.model.attack2",0.48f).layer(1).priority(1);
    public static final Animation ANIMATION_FLAME = new Animation("animation.model.flame",2.52f).layer(1).priority(1);
    public static final Animation ANIMATION_BOW = new Animation("animation.model.bow",1.04f).layer(1).priority(1);
    public static final Animation ANIMATION_JUMP = new Animation("animation.model.jump",0.24f).layer(1).priority(1);

    public AnimationFactory animationFactory = new AnimationFactory(this,ANIMATION_IDLE,ANIMATION_DANCE,ANIMATION_WALK,ANIMATION_RUN,ANIMATION_JUMP,ANIMATION_ATTACK_1,ANIMATION_ATTACK_2,ANIMATION_FLAME,ANIMATION_BOW){

        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation == ANIMATION_FLAME){
                lookControl.setLookAt(EntityFenickFakir.this);
                animationFactory.play(ANIMATION_BOW);
                Vector3 pos = Vector3.from(getEyePosition()).add(Vector3.from(getViewVector(0)));
                lookControl.setLookAt(pos.x,pos.y,pos.z);
            }
        }

        @Override
        public void onAnimationTick(Animation animation, int tick) {
            if (animation == ANIMATION_FLAME){
                Vector3 pos = Vector3.from(getEyePosition()).add(Vector3.from(getViewVector(0)));
                lookControl.setLookAt(pos.x,pos.y+1,pos.z);
            }
            if (animation == ANIMATION_ATTACK_1 || animation == ANIMATION_FLAME && tick > 11){
                Vec3 view = getViewVector(2);
                for (LivingEntity entity : EntityUtils.getEntities(LivingEntity.class, level, blockPosition().offset(view.x,view.y,view.z), 1, EntityPredicates.exclude(EntityFenickFakir.this))) {
                    entity.setSecondsOnFire(10);
                }
                if (tick % 3 == 0){
                    level.playSound(null,blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.HOSTILE,0.25f,RandomUtils.randomFloat(0.5f,0.6f));
                }
                playClientEvent(0);
            }
        }
    };

    public ActionController moveController = new ActionController(this,"moveController",ACTION_RUN);
    public static final Action ACTION_RUN = new Action(0,"run",-1f);

    public ActionStateMachine stateMachine = new ActionStateMachine(moveController);

    private MerchantOffers offers;

    public EntityFenickFakir(EntityType<? extends EntityAbstractFenickTrader> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }


    @Override
    protected void registerGoals() {
        TradeUtils.addDefaultTraderGoals(this);
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this,1.2f,false){
            @Override
            public void start() {
                super.start();
                TradeUtils.stopTrading(EntityFenickFakir.this);
                moveController.playAction(ACTION_RUN);
            }

            @Override
            protected double getAttackReachSqr(LivingEntity p_25556_) {
                return 10;
            }

            @Override
            public void stop() {
                super.stop();
                moveController.setNoState();
            }
        });
        this.goalSelector.addGoal(2,new GoalLookForItem(this,0.6f,4,(itemEntity)-> FenickTradeSystem.isItemValuableEnough(itemEntity.getItem())));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this,0.4f));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false){
            @Override
            public boolean canUse() {
                return super.canUse() && WorldUtils.isNight(level);
            }
        });
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!EntityUtils.hasTarget(this)){
            if (RandomUtils.doWithChance(0.5f)){
                animationFactory.play(ANIMATION_FLAME);
            }
        }
        if (EntityUtils.isMoving(this,-0.05f,0.05f)){
            if (moveController.is(ACTION_RUN)){
                animationFactory.play(ANIMATION_RUN);
            }else {
                animationFactory.play(ANIMATION_WALK);
            }
        }else{
            if (!isDancing()) {
                animationFactory.play(ANIMATION_IDLE);
            }else{
                animationFactory.play(ANIMATION_DANCE);
            }
        }
    }


    @Override
    public boolean doHurtTarget(Entity p_21372_) {
        if (!animationFactory.isPlaying(ANIMATION_ATTACK_1)){
            animationFactory.play(ANIMATION_ATTACK_1);
        }
        return false;
    }

    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) {
        if (p_21016_ == DamageSource.CACTUS){
            return false;
        }
        return super.hurt(p_21016_, p_21017_);
    }

    @Override
    public <K extends IDisplayArgument> AnimationFactory getAnimationFactory(K argument) {
        return animationFactory;
    }

    @Override
    public MerchantOffers getOffers() {
        if (offers == null){
            offers = FenickTradeSystem.offersGenerate();
            offers.add(new MerchantOffer(FenickTradeSystem.generateBuyStack(), DMItems.FIRE_TRAP.get().getDefaultInstance(),1,0,0));
        }
        return offers;
    }

    @Override
    public void setOffers(MerchantOffers offers) {
        this.offers = offers;
    }

    @Override
    public void notifyTrade(MerchantOffer p_45305_) {
        playSound(getNotifyTradeSound());
        p_45305_.increaseUses();
    }


    @Override
    public void onHandleClientEvent(int event, PacketArgument[] arguments) {
        if (event == 0){
            Vec3 motion = getDeltaMovement();
            double factor = motion.length();
            if (Math.abs(factor) > 0.2f){
                factor*=2;
            }
            double motX = motion.x;
            double motZ = motion.z;
            for (int j = 0; j < 8; j++) {
                Vec3 viewDir = getViewVector(-1+j);
                level.addParticle(ParticleTypes.FLAME, getX() + viewDir.x+ motX+RandomUtils.randomFloat(-0.25f,0.25f), getEyeY()+viewDir.y, getZ() + viewDir.z+motZ+RandomUtils.randomFloat(-0.25f,0.25f), viewDir.x / 3-factor, viewDir.y / 3-factor, viewDir.z / 3-factor);
            }
        }
    }

    @Override
    public ActionStateMachine getActionStateMachine() {
        return stateMachine;
    }
}
