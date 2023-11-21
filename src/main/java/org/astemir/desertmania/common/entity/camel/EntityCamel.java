package org.astemir.desertmania.common.entity.camel;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LlamaSpit;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.common.action.Action;
import org.astemir.api.common.action.ActionController;
import org.astemir.api.common.action.ActionStateMachine;
import org.astemir.api.common.animation.Animation;
import org.astemir.api.common.animation.AnimationFactory;
import org.astemir.api.common.animation.AnimationList;
import org.astemir.api.common.entity.EntityData;
import org.astemir.api.common.entity.ISkillsMob;
import org.astemir.api.common.entity.ai.EntityTask;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.common.world.WorldUtils;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.api.network.PacketArgument;
import org.astemir.desertmania.common.block.DMBlocks;
import org.astemir.desertmania.common.entity.DMEntities;
import org.astemir.desertmania.common.entity.ai.GoalCamelBreed;
import org.astemir.desertmania.common.item.DMItems;
import org.astemir.desertmania.common.sound.DMSounds;

import javax.annotation.Nullable;
import java.util.UUID;


public class EntityCamel extends AbstractHorse implements ISkillsMob,RangedAttackMob {

    protected static final Block[] SAND_BLOCKS = new Block[]{Blocks.SAND, DMBlocks.DUNE_SAND.get()};

    private static final AttributeModifier SLOW_FALLING = new AttributeModifier(UUID.fromString("A5B6CF2A-2F7C-31EF-9022-7C3E7D5E6ABA"), "Slow falling acceleration reduction", -0.07, AttributeModifier.Operation.ADDITION);

    public static EntityData<Integer> DECORATION_COLOR = new EntityData<>(EntityCamel.class,"Decoration", EntityDataSerializers.INT,-1);
    public static EntityData<Boolean> IS_ZOMBIE = new EntityData<>(EntityCamel.class,"IsZombie", EntityDataSerializers.BOOLEAN,false);
    public static EntityData<CompoundTag> LIQUID = new EntityData<>(EntityCamel.class,"Liquid", EntityDataSerializers.COMPOUND_TAG,new CompoundTag());

    private final AnimationFactory animationFactory = new AnimationFactory(this,ANIMATION_IDLE,ANIMATION_IDLE_LIE,ANIMATION_WALK,ANIMATION_RUN,ANIMATION_LIE_DOWN,ANIMATION_STAND_UP,ANIMATION_ATTACK,ANIMATION_MILKING){
        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation == ANIMATION_ATTACK){
                EntityCamel camel = (EntityCamel) getAnimated();
                Entity target = getTarget();
                if (target != null) {
                    LlamaSpit llamaspit = new LlamaSpit(EntityType.LLAMA_SPIT, camel.level);
                    llamaspit.setOwner(camel);
                    llamaspit.setPos(getX() - (double) (getBbWidth() + 1.0F) * 0.5D * (double) Mth.sin(yBodyRot * ((float) Math.PI / 180F)), getEyeY() - (double) 0.1F, getZ() + (double) (getBbWidth() + 1.0F) * 0.5D * (double) Mth.cos(yBodyRot * ((float) Math.PI / 180F)));
                    double d0 = target.getX() - camel.getX();
                    double d1 = target.getY(0.3333333333333333D) - llamaspit.getY();
                    double d2 = target.getZ() - camel.getZ();
                    double d3 = Math.sqrt(d0 * d0 + d2 * d2) * (double) 0.2F;
                    llamaspit.shoot(d0, d1 + d3, d2, 1.5F, 10.0F);
                    if (!camel.isSilent()) {
                        camel.level.playSound(null, camel.getX(), camel.getY(), camel.getZ(), SoundEvents.LLAMA_SPIT, camel.getSoundSource(), 1.0F, 1.0F + (camel.random.nextFloat() - camel.random.nextFloat()) * 0.2F);
                    }
                    camel.level.addFreshEntity(llamaspit);
                }
            }
        }
    };

    public static Animation ANIMATION_IDLE = new Animation("animation.model.idle",2.08f).smoothness(4).layer(0).loop();
    public static Animation ANIMATION_IDLE_LIE = new Animation("animation.model.lie",2.08f).layer(0).loop().priority(2);
    public static Animation ANIMATION_WALK = new Animation("animation.model.walk",2.08f).smoothness(4).layer(0).loop();
    public static Animation ANIMATION_RUN = new Animation("animation.model.run",0.72f).smoothness(4).layer(0).loop();
    public static Animation ANIMATION_LIE_DOWN = new Animation("animation.model.down",0.84f).layer(0).loop(Animation.Loop.HOLD_ON_LAST_FRAME).priority(2);
    public static Animation ANIMATION_STAND_UP = new Animation("animation.model.up",0.88f).layer(0).loop(Animation.Loop.HOLD_ON_LAST_FRAME).priority(2);
    public static Animation ANIMATION_ATTACK = new Animation("animation.model.attack",0.72f).layer(1).priority(3);
    public static Animation ANIMATION_MILKING = new Animation("animation.model.milking",0.64f).layer(1).priority(1);

    public ActionController idleController = new ActionController(this,"idleController",ACTION_IDLE_LIE);
    public static Action ACTION_IDLE_LIE = new Action(0,"lie",-1);

    public ActionController actionController = new ActionController(this,"actionController",ACTION_STAND_UP,ACTION_LIE_DOWN){
        @Override
        public void onActionBegin(Action state) {
            if (state == ACTION_LIE_DOWN) {
                animationFactory.play(ANIMATION_LIE_DOWN);
            }else
            if (state == ACTION_STAND_UP) {
                animationFactory.play(ANIMATION_STAND_UP);
            }
        }

        @Override
        public void onActionEnd(Action state) {
            if (state == ACTION_LIE_DOWN){
                idleController.playAction(ACTION_IDLE_LIE);
                updateSize();
            }else
            if (state == ACTION_STAND_UP){
                idleController.setNoState();
                updateSize();
            }
        }

        @Override
        public void onActionTick(Action state, int ticks) {
            if (state == ACTION_STAND_UP || state == ACTION_LIE_DOWN){
                updateSize();
            }
        }
    };
    public static Action ACTION_STAND_UP = new Action(0,"up",0.88f);
    public static Action ACTION_LIE_DOWN = new Action(1,"down",0.84f);

    private final ActionStateMachine stateMachine = new ActionStateMachine(idleController,actionController);

    private boolean sprinting = false;

    private final EntityTask sitOrStandTask = new EntityTask(this,200) {
        @Override
        public void run() {
            if (!EntityUtils.hasTarget(EntityCamel.this)) {
                if (!EntityUtils.isMoving(EntityCamel.this, -0.1f, 0.1f)) {
                    if (canBeMovedOrPushed()) {
                        if (!actionController.is(ACTION_LIE_DOWN)) {
                            if (RandomUtils.doWithChance(5f)) {
                                actionController.playAction(ACTION_LIE_DOWN);
                            }
                        }
                    } else {
                        if (!actionController.is(ACTION_STAND_UP)) {
                            if (RandomUtils.doWithChance(5f)) {
                                actionController.playAction(ACTION_STAND_UP);
                            }
                        }
                    }
                }
            }
        }
    };

    public EntityCamel(EntityType<? extends AbstractHorse> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        moveControl = new MoveControl(this){
            @Override
            public void setWantedPosition(double p_24984_, double p_24985_, double p_24986_, double p_24987_) {
                if (canBeMovedOrPushed()) {
                    super.setWantedPosition(p_24984_, p_24985_, p_24986_, p_24987_);
                }
            }
        };
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 0.6D));
        this.goalSelector.addGoal(2, new GoalCamelBreed(this, 0.6D));
        this.goalSelector.addGoal(3, new RangedAttackGoal(this, 1.25D, 40, 20.0F));
        this.goalSelector.addGoal(3, new TemptGoal(this, 0.6D, Ingredient.of(DMItems.CAMEL_THORN.get()), false));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.5D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        DECORATION_COLOR.register(this);
        IS_ZOMBIE.register(this);
        LIQUID.register(this);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag p_21484_) {
        super.addAdditionalSaveData(p_21484_);
        DECORATION_COLOR.save(this,p_21484_);
        IS_ZOMBIE.save(this,p_21484_);
        LIQUID.save(this,p_21484_);
        if (!this.inventory.getItem(1).isEmpty()) {
            p_21484_.put("DecorItem", this.inventory.getItem(1).save(new CompoundTag()));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_21450_) {
        super.readAdditionalSaveData(p_21450_);
        DECORATION_COLOR.load(this,p_21450_);
        IS_ZOMBIE.load(this,p_21450_);
        LIQUID.load(this,p_21450_);
        if (p_21450_.contains("DecorItem", 10)) {
            this.inventory.setItem(1, ItemStack.of(p_21450_.getCompound("DecorItem")));
        }
        this.updateContainerEquipment();
    }

    @Override
    public void containerChanged(Container p_30760_) {
        CamelDecoration oldDecoration = getDecoration();
        super.containerChanged(p_30760_);
        CamelDecoration newDecoration = getDecoration();
        if (this.tickCount > 20 && newDecoration != null && newDecoration != oldDecoration) {
            this.playSound(SoundEvents.LLAMA_SWAG, 0.5F, 0.75F);
        }
    }

    @Override
    protected void updateContainerEquipment() {
        if (!this.level.isClientSide) {
            super.updateContainerEquipment();
            setDecoration(getDyeColor(this.inventory.getItem(1)));
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        CamelLiquid liquid = getLiquid();
        ItemStack handStack = player.getItemInHand(hand);
        if (liquid.canBeFilledWith(handStack)){
            CamelLiquid.LiquidType stackLiquid = liquid.getItemLiquid(handStack);
            CamelLiquid.PotionContainer stackPotion = liquid.getItemPotion(handStack);
            boolean canFill = liquid.getLiquidType() == CamelLiquid.LiquidType.EMPTY || stackLiquid == liquid.getLiquidType();
            if (stackPotion != null){
                if (stackPotion.isHarmful() && !IS_ZOMBIE.get(this)){
                    canFill = false;
                }
            }
            if (canFill) {
                if (liquid.getPotionContainer() != null) {
                    if (!liquid.getPotionContainer().getCustomEffects().isEmpty()) {
                        canFill = liquid.getPotionContainer().getCustomEffects().equals(stackPotion.getCustomEffects());
                    }else{
                        canFill = liquid.getPotionContainer().getVanillaEffect().equals(stackPotion.getVanillaEffect());
                    }
                }
            }
            int newAmount = liquid.getAmount() + liquid.getLiquidAmount(handStack);
            if (newAmount <= CamelLiquid.MAX_AMOUNT && canFill) {
                player.addItem(liquid.getFillResult(handStack));
                animationFactory.play(ANIMATION_MILKING);
                playSound(SoundEvents.COW_MILK);
                liquid.setLiquidType(stackLiquid);
                liquid.setAmount(newAmount);
                liquid.setPotionContainer(stackPotion);
                setLiquid(liquid);
                handStack.shrink(1);
                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }
        }else
        if (liquid.canBeCollectedWith(handStack)){
            int newAmount = liquid.getAmount() - liquid.getLiquidAmount(handStack);
            if (newAmount >= 0){
                animationFactory.play(ANIMATION_MILKING);
                playSound(SoundEvents.COW_MILK);
                player.addItem(liquid.getCollectResult(handStack));
                liquid.setAmount(newAmount);
                setLiquid(liquid);
                handStack.shrink(1);
                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }
        }

        if (handStack.isEmpty() && !isBaby()) {
            doPlayerRide(player);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }

        if (this.isFood(handStack)) {
            int i = this.getAge();
            if (!this.level.isClientSide && i == 0 && this.canFallInLove()) {
                this.usePlayerItem(player, hand, handStack);
                this.setInLove(player);
                return InteractionResult.SUCCESS;
            }

            if (this.isBaby()) {
                this.usePlayerItem(player, hand, handStack);
                this.ageUp(getSpeedUpSecondsWhenFeeding(-i), true);
                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }

            if (this.level.isClientSide) {
                return InteractionResult.CONSUME;
            }
        }

        return InteractionResult.FAIL;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_30555_, DifficultyInstance p_30556_, MobSpawnType p_30557_, @org.jetbrains.annotations.Nullable SpawnGroupData p_30558_, @org.jetbrains.annotations.Nullable CompoundTag p_30559_) {
        setLiquid(new CamelLiquid());
        return super.finalizeSpawn(p_30555_, p_30556_, p_30557_, p_30558_, p_30559_);
    }

    @Nullable
    public LivingEntity getControllingPassenger() {
        Entity entity = this.getFirstPassenger();
        if (entity instanceof LivingEntity) {
            return (LivingEntity)entity;
        }
        return null;
    }

    @Override
    public EntityDimensions getDimensions(Pose p_219392_) {
        EntityDimensions entitydimensions = super.getDimensions(p_219392_);
        float defaultHeight = 2;
        if (isBaby()){
            defaultHeight = 0.75f;
        }
        if (actionController.is(ACTION_STAND_UP)){
            float ticks = actionController.getTicks();
            if (ticks == 0){
                ticks = 1;
            }
            float length = ACTION_STAND_UP.getLength();
            float size = defaultHeight-(ticks/length);
            if (defaultHeight <= 0.75f){
                size = defaultHeight+defaultHeight/2-(ticks/length);
            }
            return EntityDimensions.fixed(entitydimensions.width, size);
        }
        if (actionController.is(ACTION_LIE_DOWN)){
            float ticks = actionController.getTicks();
            if (ticks == 0){
                ticks = 1;
            }
            float length = ACTION_LIE_DOWN.getLength();
            float size = defaultHeight/2+(ticks/length);
            return EntityDimensions.fixed(entitydimensions.width, size);
        }
        return EntityDimensions.fixed(entitydimensions.width, defaultHeight);
    }

    public boolean isMoving(LivingEntity entity, float min, float max){
        float limbSwingAmount = 0.0F;
        boolean shouldSit = entity.isPassenger()
                && (entity.getVehicle() != null && entity.getVehicle().shouldRiderSit());
        if (!shouldSit && entity.isAlive()) {
            limbSwingAmount = Mth.lerp(0.1f, entity.animationSpeedOld, entity.animationSpeed);
            if (limbSwingAmount > 1.0F) {
                limbSwingAmount = 1.0F;
            }
        }
        return !(limbSwingAmount > min && limbSwingAmount < max) || (EntityUtils.isMovingByPlayer(entity) && canBeControlledByPlayer());
    }

    @Override
    public void aiStep() {
        super.aiStep();
        sitOrStandTask.update();
        if (canBeMovedOrPushed()) {
            if (level.isClientSide) {
                boolean isClientSideSprinting = getControllingPassenger() != null && getControllingPassenger().isSprinting();
                if (sprinting != isClientSideSprinting) {
                    this.sprinting = isClientSideSprinting;
                    playServerEvent(0, PacketArgument.create(PacketArgument.ArgumentType.BOOL, sprinting));
                }
            }
            if (isMoving(this, -0.1f, 0.1f)) {
                if (sprinting){
                    animationFactory.play(ANIMATION_RUN);
                }else {
                    animationFactory.play(ANIMATION_WALK);
                }
            } else {
                animationFactory.play(ANIMATION_IDLE);
            }
        }else{
            if (isLying() && !actionController.is(ACTION_STAND_UP)){
                animationFactory.play(ANIMATION_IDLE_LIE);
            }
        }
    }


    @Override
    public void travel(Vec3 p_30633_) {
        if (this.isAlive()) {
            LivingEntity livingentity = this.getControllingPassenger();
            if (this.isVehicle() && livingentity != null && canBeControlledByPlayer()) {
                if (canBeMovedOrPushed()) {
                    this.setYRot(livingentity.getYRot());
                    this.yRotO = this.getYRot();
                    this.setXRot(livingentity.getXRot() * 0.5F);
                    this.setRot(this.getYRot(), this.getXRot());
                    this.yBodyRot = this.getYRot();
                    this.yHeadRot = this.yBodyRot;
                    float f = livingentity.xxa * 0.5F;
                    float f1 = livingentity.zza;
                    if (f1 <= 0.0F) {
                        f1 *= 0.25F;
                        this.gallopSoundCounter = 0;
                    }

                    if (this.playerJumpPendingScale > 0.0F && !this.isJumping() && this.onGround) {
                        double d0 = this.getCustomJump() * (double) this.playerJumpPendingScale * (double) this.getBlockJumpFactor();
                        double d1 = d0 + this.getJumpBoostPower();
                        Vec3 vec3 = this.getDeltaMovement();
                        this.setDeltaMovement(vec3.x, d1, vec3.z);
                        this.setIsJumping(true);
                        this.hasImpulse = true;
                        net.minecraftforge.common.ForgeHooks.onLivingJump(this);
                        if (f1 > 0.0F) {
                            float f2 = Mth.sin(this.getYRot() * ((float) Math.PI / 180F));
                            float f3 = Mth.cos(this.getYRot() * ((float) Math.PI / 180F));
                            this.setDeltaMovement(this.getDeltaMovement().add(-0.4F * f2 * this.playerJumpPendingScale, 0.0D, 0.4F * f3 * this.playerJumpPendingScale));
                        }

                        this.playerJumpPendingScale = 0.0F;
                    }

                    this.flyingSpeed = this.getSpeed() * 0.1F;
                    if (this.isControlledByLocalInstance()) {
                        float speed = (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED)*0.15f;
                        if (livingentity.isSprinting()){
                            speed = (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED)*0.3f;
                        }
                        if (WorldUtils.isBlock(SAND_BLOCKS,getBlockStateOn())){
                            speed = speed*1.5f;
                        }
                        this.setSpeed(speed);
                        travelBase(new Vec3(f, p_30633_.y, f1));
                    } else if (livingentity instanceof Player) {
                        this.setDeltaMovement(Vec3.ZERO);
                    }
                    if (this.onGround) {
                        this.playerJumpPendingScale = 0.0F;
                        this.setIsJumping(false);
                    }
                    this.calculateEntityAnimation(this, false);
                    this.tryCheckInsideBlocks();
                }else{
                    if (Math.abs(livingentity.xxa) > 0 || Math.abs(livingentity.zza) > 0) {
                        forceStandUp();
                    }
                }
            }else {
                this.flyingSpeed = 0.02F;
                travelBase(p_30633_);
            }
        }
    }

    public CamelLiquid getLiquid(){
        CompoundTag tag = LIQUID.get(this);
        if (tag == null || tag.isEmpty()){
            return new CamelLiquid();
        }else {
            return CamelLiquid.load(tag);
        }
    }

    public void setLiquid(CamelLiquid liquid){
        LIQUID.set(this,liquid.save());
    }

    public void travelBase(Vec3 vec){
        if (this.isEffectiveAi() || (this.isControlledByLocalInstance() && canBeControlledByPlayer())) {
            double d0 = 0.08D;
            AttributeInstance gravity = this.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
            boolean flag = this.getDeltaMovement().y <= 0.0D;
            if (flag && this.hasEffect(MobEffects.SLOW_FALLING)) {
                if (!gravity.hasModifier(SLOW_FALLING)) gravity.addTransientModifier(SLOW_FALLING);
                this.resetFallDistance();
            } else if (gravity.hasModifier(SLOW_FALLING)) {
                gravity.removeModifier(SLOW_FALLING);
            }
            d0 = gravity.getValue();

            FluidState fluidstate = this.level.getFluidState(this.blockPosition());
            if ((this.isInWater() || (this.isInFluidType(fluidstate) && fluidstate.getFluidType() != net.minecraftforge.common.ForgeMod.LAVA_TYPE.get())) && this.isAffectedByFluids() && !this.canStandOnFluid(fluidstate)) {
                if (this.isInWater() || (this.isInFluidType(fluidstate) && !this.moveInFluid(fluidstate, vec, d0))) {
                    double d9 = this.getY();
                    float f4 = this.isSprinting() ? 0.9F : this.getWaterSlowDown();
                    float f5 = 0.02F;
                    float f6 = (float) EnchantmentHelper.getDepthStrider(this);
                    if (f6 > 3.0F) {
                        f6 = 3.0F;
                    }

                    if (!this.onGround) {
                        f6 *= 0.5F;
                    }

                    if (f6 > 0.0F) {
                        f4 += (0.54600006F - f4) * f6 / 3.0F;
                        f5 += (this.getSpeed() - f5) * f6 / 3.0F;
                    }

                    if (this.hasEffect(MobEffects.DOLPHINS_GRACE)) {
                        f4 = 0.96F;
                    }

                    f5 *= (float)this.getAttribute(net.minecraftforge.common.ForgeMod.SWIM_SPEED.get()).getValue();
                    this.moveRelative(f5, vec);
                    this.move(MoverType.SELF, this.getDeltaMovement());
                    Vec3 vec36 = this.getDeltaMovement();
                    if (this.horizontalCollision && this.onClimbable()) {
                        vec36 = new Vec3(vec36.x, 0.2D, vec36.z);
                    }

                    this.setDeltaMovement(vec36.multiply(f4, 0.8F, f4));
                    Vec3 vec32 = this.getFluidFallingAdjustedMovement(d0, flag, this.getDeltaMovement());
                    this.setDeltaMovement(vec32);
                    if (this.horizontalCollision && this.isFree(vec32.x, vec32.y + (double)0.6F - this.getY() + d9, vec32.z)) {
                        this.setDeltaMovement(vec32.x, 0.3F, vec32.z);
                    }
                }
            } else if (this.isInLava() && this.isAffectedByFluids() && !this.canStandOnFluid(fluidstate)) {
                double d8 = this.getY();
                this.moveRelative(0.02F, vec);
                this.move(MoverType.SELF, this.getDeltaMovement());
                if (this.getFluidHeight(FluidTags.LAVA) <= this.getFluidJumpThreshold()) {
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.5D, 0.8F, 0.5D));
                    Vec3 vec33 = this.getFluidFallingAdjustedMovement(d0, flag, this.getDeltaMovement());
                    this.setDeltaMovement(vec33);
                } else {
                    this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
                }

                if (!this.isNoGravity()) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -d0 / 4.0D, 0.0D));
                }

                Vec3 vec34 = this.getDeltaMovement();
                if (this.horizontalCollision && this.isFree(vec34.x, vec34.y + (double)0.6F - this.getY() + d8, vec34.z)) {
                    this.setDeltaMovement(vec34.x, 0.3F, vec34.z);
                }
            } else if (this.isFallFlying()) {
                Vec3 vec3 = this.getDeltaMovement();
                if (vec3.y > -0.5D) {
                    this.fallDistance = 1.0F;
                }
                Vec3 vec31 = this.getLookAngle();
                float f = this.getXRot() * ((float)Math.PI / 180F);
                double d1 = Math.sqrt(vec31.x * vec31.x + vec31.z * vec31.z);
                double d3 = vec3.horizontalDistance();
                double d4 = vec31.length();
                double d5 = Math.cos(f);
                d5 = d5 * d5 * Math.min(1.0D, d4 / 0.4D);
                vec3 = this.getDeltaMovement().add(0.0D, d0 * (-1.0D + d5 * 0.75D), 0.0D);
                if (vec3.y < 0.0D && d1 > 0.0D) {
                    double d6 = vec3.y * -0.1D * d5;
                    vec3 = vec3.add(vec31.x * d6 / d1, d6, vec31.z * d6 / d1);
                }

                if (f < 0.0F && d1 > 0.0D) {
                    double d10 = d3 * (double)(-Mth.sin(f)) * 0.04D;
                    vec3 = vec3.add(-vec31.x * d10 / d1, d10 * 3.2D, -vec31.z * d10 / d1);
                }

                if (d1 > 0.0D) {
                    vec3 = vec3.add((vec31.x / d1 * d3 - vec3.x) * 0.1D, 0.0D, (vec31.z / d1 * d3 - vec3.z) * 0.1D);
                }

                this.setDeltaMovement(vec3.multiply(0.99F, 0.98F, 0.99F));
                this.move(MoverType.SELF, this.getDeltaMovement());
                if (this.horizontalCollision && !this.level.isClientSide) {
                    double d11 = this.getDeltaMovement().horizontalDistance();
                    double d7 = d3 - d11;
                    float f1 = (float)(d7 * 10.0D - 3.0D);
                    if (f1 > 0.0F) {
                        this.playSound(f1 > 4 ? this.getFallSounds().big() : this.getFallSounds().small(), 1.0F, 1.0F);
                        this.hurt(DamageSource.FLY_INTO_WALL, f1);
                    }
                }

                if (this.onGround && !this.level.isClientSide) {
                    this.setSharedFlag(7, false);
                }
            } else {
                BlockPos blockpos = this.getBlockPosBelowThatAffectsMyMovement();
                float f2 = this.level.getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).getFriction(level, this.getBlockPosBelowThatAffectsMyMovement(), this);
                float f3 = this.onGround ? f2 * 0.91F : 0.91F;
                Vec3 vec35 = this.handleRelativeFrictionAndCalculateMovement(vec, f2);
                double d2 = vec35.y;
                if (this.hasEffect(MobEffects.LEVITATION)) {
                    d2 += (0.05D * (double)(this.getEffect(MobEffects.LEVITATION).getAmplifier() + 1) - vec35.y) * 0.2D;
                    this.resetFallDistance();
                } else if (this.level.isClientSide && !this.level.hasChunkAt(blockpos)) {
                    if (this.getY() > (double)this.level.getMinBuildHeight()) {
                        d2 = -0.1D;
                    } else {
                        d2 = 0.0D;
                    }
                } else if (!this.isNoGravity()) {
                    d2 -= d0;
                }

                if (this.shouldDiscardFriction()) {
                    this.setDeltaMovement(vec35.x, d2, vec35.z);
                } else {
                    this.setDeltaMovement(vec35.x * (double)f3, d2 * (double)0.98F, vec35.z * (double)f3);
                }
            }
        }
        this.calculateEntityAnimation(this, this instanceof FlyingAnimal);
    }


    @Override
    public boolean causeFallDamage(float p_149499_, float p_149500_, DamageSource p_149501_) {
        int i = this.calculateFallDamage(p_149499_, p_149500_);
        if (i <= 0) {
            return false;
        } else {
            this.hurt(p_149501_, (float)i);
            if (this.isVehicle()) {
                for(Entity entity : this.getIndirectPassengers()) {
                    entity.hurt(p_149501_, (float)i);
                }
            }
            this.playBlockFallSound();
            return true;
        }
    }

    @Override
    protected void playStepSound(BlockPos p_30584_, BlockState p_30585_) {
        if (tickCount % 2 == 0) {
            if (WorldUtils.isBlock(SAND_BLOCKS,p_30585_)) {
                playSound(DMSounds.CAMEL_STEP_SAND.get(), 1, 1);
            } else {
                playSound(DMSounds.CAMEL_STEP.get(), 1, 1);
            }
        }
    }


    @Override
    public void onHandleServerEvent(int event, PacketArgument[] arguments) {
        if (event == 0) {
            sprinting = arguments[0].asBoolean();
        }
    }

    @Override
    public void onHandleClientEvent(int event, PacketArgument[] arguments) {
        if (event == 0){
            refreshDimensions();
        }
    }

    public void updateSize(){
        playClientEvent(0);
        refreshDimensions();
    }

    public void forceStandUp(){
        if (!canBeMovedOrPushed()) {
            if (!actionController.is(ACTION_STAND_UP)) {
                actionController.playAction(ACTION_STAND_UP);
            }
        }
    }

    @Override
    public boolean hurt(DamageSource p_27567_, float p_27568_) {
        if (p_27567_ == DamageSource.CACTUS){
            return false;
        }
        forceStandUp();
        return super.hurt(p_27567_, p_27568_);
    }


    @Override
    public <K extends IDisplayArgument> AnimationFactory getAnimationFactory(K argument) {
        return animationFactory;
    }


    @Override
    public void onPlayerJump(int p_30591_) {
        if (p_30591_ < 0) {
            p_30591_ = 0;
        }
        if (p_30591_ >= 90) {
            this.playerJumpPendingScale = 1.0F;
        } else {
            this.playerJumpPendingScale = 0.4F + 0.4F * (float)p_30591_ / 90.0F;
        }
    }

    @Override
    public void openCustomInventoryScreen(Player p_218808_) {
        if (!this.level.isClientSide && (!this.isVehicle() || this.hasPassenger(p_218808_))) {
            p_218808_.openHorseInventory(this, this.inventory);
        }
    }

    @Override
    public boolean isPushable() {
        return super.isPushable() && canBeMovedOrPushed();
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return DMEntities.CAMEL.get().create(p_146743_);
    }

    public boolean isLying(){
        return idleController.is(ACTION_IDLE_LIE);
    }

    public boolean canBeMovedOrPushed(){
        return !isLying() && actionController.isNoAction();
    }

    public CamelDecoration getDecoration(){
        return CamelDecoration.getFromId(DECORATION_COLOR.get(this));
    }

    public void setDecoration(DyeColor color){
        DECORATION_COLOR.set(this,CamelDecoration.getFromDye(color).getId());
    }

    @Override
    public void performRangedAttack(LivingEntity p_33317_, float p_33318_) {
        if (!isBaby()) {
            if (!animationFactory.isPlaying(ANIMATION_ATTACK)) {
                animationFactory.play(ANIMATION_ATTACK);
            }
        }
    }


    @Override
    protected boolean isImmobile() {
        return super.isImmobile();
    }

    @Nullable
    private static DyeColor getDyeColor(ItemStack p_30836_) {
        Block block = Block.byItem(p_30836_.getItem());
        return block instanceof WoolCarpetBlock ? ((WoolCarpetBlock)block).getColor() : null;
    }

    private boolean canBeControlledByPlayer(){
        return getDecoration() != CamelDecoration.NO_DECORATION;
    }

    @Override
    public double getPassengersRidingOffset() {
        return this.getBbHeight();
    }


    @Override
    public boolean isFood(ItemStack p_30644_) {
        return Ingredient.of(DMItems.CAMEL_THORN.get()).test(p_30644_);
    }

    @Override
    public boolean isEating() {
        return false;
    }

    @Override
    public boolean isStanding() {
        return false;
    }

    @Override
    public boolean canJump() {
        return true;
    }

    @Override
    public void setStanding(boolean p_30666_) {
    }

    protected void playGallopSound(SoundType p_30560_) {
    }

    @Override
    protected void playJumpSound() {
    }

    @Override
    public boolean canMate(Animal pOtherAnimal) {
        if (pOtherAnimal == this) {
            return false;
        } else if (pOtherAnimal.getClass() != this.getClass()) {
            return false;
        } else {
            return this.isInLove() && pOtherAnimal.isInLove();
        }
    }

    @Override
    public boolean canEatGrass() {
        return false;
    }

    @Override
    public boolean isSaddleable() {
        return false;
    }

    @Override
    public boolean canWearArmor() {
        return true;
    }

    @Override
    public boolean isWearingArmor() {
        return !this.inventory.getItem(1).isEmpty();
    }

    @Override
    public boolean isArmor(ItemStack p_30834_) {
        return p_30834_.is(ItemTags.WOOL_CARPETS);
    }

    @Override
    public boolean isBred() {
        return super.isBred();
    }

    @Override
    public void setBred(boolean pBreeding) {
        super.setBred(pBreeding);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return DMSounds.CAMEL_IDLE.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return DMSounds.CAMEL_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_30609_) {
        return DMSounds.CAMEL_HURT.get();
    }

    @Override
    public ActionStateMachine getActionStateMachine() {
        return stateMachine;
    }
}
