package org.astemir.desertmania.common.entity.fenick;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.common.action.Action;
import org.astemir.api.common.action.ActionController;
import org.astemir.api.common.action.ActionStateMachine;
import org.astemir.api.common.animation.Animation;
import org.astemir.api.common.animation.AnimationFactory;
import org.astemir.api.common.entity.EntityData;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.common.entity.utils.TradeUtils;
import org.astemir.api.common.world.WorldUtils;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.desertmania.common.entity.ai.GoalLookForItem;
import org.astemir.desertmania.common.item.DMItems;
import org.jetbrains.annotations.Nullable;

public class EntityFenick extends EntityAbstractFenickTrader {


    public static EntityData<Integer> SKIN = new EntityData<>(EntityAbstractFenickTrader.class,"SkinId", EntityDataSerializers.INT,0);
    public static EntityData<Boolean> MASKED = new EntityData<>(EntityAbstractFenickTrader.class,"IsMasked", EntityDataSerializers.BOOLEAN,false);

    public static final Animation ANIMATION_IDLE = new Animation("animation.model.idle",2.08f).smoothness(4).layer(0).loop();
    public static final Animation ANIMATION_DANCE = new Animation("animation.model.dance",0.8f).layer(0).loop();
    public static final Animation ANIMATION_WALK = new Animation("animation.model.walk",1.12f).smoothness(4).layer(0).loop();
    public static final Animation ANIMATION_RUN = new Animation("animation.model.run",0.56f).smoothness(4).layer(0).loop();

    public static final Animation ANIMATION_ATTACK = new Animation("animation.model.attack",0.52f).layer(1).priority(1);
    public static final Animation ANIMATION_WEAR_MASK = new Animation("animation.model.wear_mask",0.44f).layer(1).priority(1);
    public static final Animation ANIMATION_JUMP = new Animation("animation.model.jump",0.24f).layer(1).priority(1);

    public AnimationFactory animationFactory = new AnimationFactory(this,ANIMATION_IDLE,ANIMATION_DANCE,ANIMATION_WALK,ANIMATION_RUN,ANIMATION_JUMP,ANIMATION_WEAR_MASK,ANIMATION_ATTACK){

        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation == ANIMATION_WEAR_MASK){
                MASKED.set(EntityFenick.this,!MASKED.get(EntityFenick.this));
            }
            if (animation == ANIMATION_ATTACK){
                if (EntityUtils.hasTarget(EntityFenick.this)) {
                    if (distanceTo(getTarget()) < 2) {
                        getTarget().hurt(DamageSource.mobAttack(EntityFenick.this), (float) getAttributeValue(Attributes.ATTACK_DAMAGE));
                    }
                }
            }
        }
    };

    public ActionController moveController = new ActionController(this,"moveController",ACTION_RUN);
    public static final Action ACTION_RUN = new Action(0,"run",-1f);

    public ActionController actionController = new ActionController(this,"actionController",ACTION_JUMP){
        @Override
        public void onActionBegin(Action state) {
            if (state == ACTION_JUMP){
                animationFactory.play(ANIMATION_JUMP);
            }
        }

        @Override
        public void onActionTick(Action state, int ticks) {
            if (state == ACTION_JUMP){
                if (ticks == 3) {
                    animationFactory.play(ANIMATION_ATTACK);
                    getJumpControl().jump();
                }
            }
        }
    };
    public static final Action ACTION_JUMP = new Action(0,"jump",0.24f);

    public ActionStateMachine stateMachine = new ActionStateMachine(moveController,actionController);

    private final FenickInventory inventory = new FenickInventory();

    private MerchantOffers offers;

    public EntityFenick(EntityType<? extends EntityAbstractFenickTrader> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, @Nullable SpawnGroupData p_21437_, @Nullable CompoundTag p_21438_) {
        SKIN.set(this, RandomUtils.randomInt(0,3));
        setItemInHand(InteractionHand.MAIN_HAND, DMItems.SCIMITAR.get().getDefaultInstance());
        return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
    }


    @Override
    protected void registerGoals() {
        TradeUtils.addDefaultTraderGoals(this);
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this,0.7f,false){
            @Override
            public void start() {
                super.start();
                TradeUtils.stopTrading(EntityFenick.this);
                moveController.playAction(ACTION_RUN);
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
    protected void dropCustomDeathLoot(DamageSource p_21385_, int p_21386_, boolean p_21387_) {
        super.dropCustomDeathLoot(p_21385_, p_21386_, p_21387_);
        for (ItemStack stack : inventory.getInventory()) {
            spawnAtLocation(stack);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        SKIN.register(this);
        MASKED.register(this);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag p_21484_) {
        super.addAdditionalSaveData(p_21484_);
        SKIN.save(this,p_21484_);
        MASKED.save(this,p_21484_);
        inventory.save(p_21484_);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_21450_) {
        super.readAdditionalSaveData(p_21450_);
        SKIN.load(this,p_21450_);
        MASKED.load(this,p_21450_);
        inventory.load(p_21450_);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        boolean masked = MASKED.get(this);
        if (WorldUtils.isDay(level)){
            if (masked){
                animationFactory.play(ANIMATION_WEAR_MASK);
            }
        }else{
            if (!masked){
                animationFactory.play(ANIMATION_WEAR_MASK);
            }
        }
        if (EntityUtils.hasTarget(this)){
            if (tickCount % 150 == 0 && onGround){
                if (EntityUtils.isEntityLookingAt(getTarget(),this)){
                    actionController.playAction(ACTION_JUMP);
                }
            }
        }
        if (EntityUtils.isMoving(this,-0.15f,0.15f)){
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
        if (!animationFactory.isPlaying(ANIMATION_ATTACK)){
            animationFactory.play(ANIMATION_ATTACK);
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
    protected void jumpFromGround() {
        if (getTarget() != null) {
            Vec3 vec3 = this.getDeltaMovement();
            Vec3 vec = getViewVector(0).multiply(1, 0, 1);
            this.setDeltaMovement(vec3.x + vec.x, 0.5f, vec3.z + vec.z);
            this.hasImpulse = true;
        }else{
            super.jumpFromGround();
        }
    }


    @Override
    protected void pickUpItem(ItemEntity p_21471_) {
        ItemStack stack = p_21471_.getItem();
        if (inventory.canPickupMoreItems() && FenickTradeSystem.isItemValuableEnough(stack)) {
            onItemPickup(p_21471_);
            take(p_21471_, stack.getCount());
            p_21471_.discard();
            TradeUtils.stopTrading(this);
            inventory.addItem(stack);
            getOffers().add(FenickTradeSystem.itemToOffer(stack));
        }
    }

    @Override
    public boolean canPickUpLoot() {
        return true;
    }

    @Override
    public <K extends IDisplayArgument> AnimationFactory getAnimationFactory(K argument) {
        return animationFactory;
    }

    @Override
    public MerchantOffers getOffers() {
        if (offers == null){
            offers = FenickTradeSystem.offersGenerate();
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
        if (inventory.removeItem(p_45305_.getResult())){
            offers.remove(p_45305_);
        }
    }

    @Override
    public ActionStateMachine getActionStateMachine() {
        return stateMachine;
    }
}
