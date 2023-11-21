package org.astemir.desertmania.common.entity.desertworm;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.common.action.Action;
import org.astemir.api.common.action.ActionController;
import org.astemir.api.common.action.ActionStateMachine;
import org.astemir.api.common.animation.Animation;
import org.astemir.api.common.animation.AnimationFactory;
import org.astemir.api.common.entity.EntityData;
import org.astemir.api.common.entity.EntityPredicates;
import org.astemir.api.common.entity.ISkillsMob;
import org.astemir.api.common.entity.ai.EntityTask;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.common.gfx.GFXBlackInOut;
import org.astemir.api.common.gfx.GFXSoftShake;
import org.astemir.api.common.gfx.PlayerGFXEffectManager;
import org.astemir.api.common.misc.ParticleEmitter;
import org.astemir.api.common.world.WorldUtils;
import org.astemir.api.math.MathUtils;
import org.astemir.api.math.components.Color;
import org.astemir.api.math.components.Vector3;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.api.network.PacketArgument;
import org.astemir.desertmania.common.block.DMBlocks;
import org.astemir.desertmania.common.entity.DMEntities;
import org.astemir.desertmania.common.sound.DMSounds;
import org.astemir.desertmania.common.utils.MiscUtils;

import java.util.List;

public class EntityDesertWorm extends Monster implements ISkillsMob,PlayerRideableJumping, IEntityAdditionalSpawnData {


    public static final int EVENT_UPDATE_SIZE = 0;
    public static final int EVENT_DIG_GROUND_HIDE = 1;
    public static final int EVENT_DIG_GROUND_ATTACK = 2;
    public static final int EVENT_THROW_RIDER = 4;

    public static EntityData<Boolean> IS_HIDDEN = new EntityData<>(EntityDesertWorm.class,"IsHidden", EntityDataSerializers.BOOLEAN,false);
    public static EntityData<Boolean> IS_STUNNED = new EntityData<>(EntityDesertWorm.class,"IsStunned", EntityDataSerializers.BOOLEAN,false);
    public static EntityData<Float> SCALE = new EntityData<>(EntityDesertWorm.class,"Scale", EntityDataSerializers.FLOAT,1f);


    private final AnimationFactory animationFactory = new AnimationFactory(this,ANIMATION_IDLE,ANIMATION_IDLE_STUN,ANIMATION_ATTACK_FROM_UNDERGROUND,ANIMATION_EMERGE,ANIMATION_HIDE,ANIMATION_ROAR,ANIMATION_RESISTANCE,ANIMATION_THROW,ANIMATION_ATTACK_ON_SURFACE);

    public static final Animation ANIMATION_IDLE = new Animation("animation.model.idle",2.08f).loop().layer(0);
    public static final Animation ANIMATION_IDLE_STUN = new Animation("animation.model.stun",4.16f).loop().layer(0);
    public static final Animation ANIMATION_ATTACK_FROM_UNDERGROUND = new Animation("animation.model.attack1",0.56f).layer(0).priority(2);
    public static final Animation ANIMATION_ATTACK_ON_SURFACE = new Animation("animation.model.attack2",0.76f).layer(1).priority(1);
    public static final Animation ANIMATION_EMERGE = new Animation("animation.model.up",0.8f).loop(Animation.Loop.HOLD_ON_LAST_FRAME).layer(1).priority(1);
    public static final Animation ANIMATION_HIDE = new Animation("animation.model.down",0.72f).loop(Animation.Loop.HOLD_ON_LAST_FRAME).layer(1).priority(1);
    public static final Animation ANIMATION_ROAR = new Animation("animation.model.roar",1.52f).layer(1).priority(1);
    public static final Animation ANIMATION_RESISTANCE = new Animation("animation.model.resistance",0.64f).layer(1).priority(1);
    public static final Animation ANIMATION_THROW = new Animation("animation.model.throw",0.76f).layer(1).priority(1);


    public ActionController actionController = new ActionController(this,"actionController",ACTION_ATTACK_FROM_UNDERGROUND,ACTION_ATTACK_ON_SURFACE,ACTION_EMERGE,ACTION_HIDE,ACTION_ROAR,ACTION_RESISTANCE,ACTION_THROW){
        @Override
        public void onActionBegin(Action state) {
            if (state == ACTION_ROAR){
                float scale = SCALE.get(EntityDesertWorm.this);
                level.playSound(null, blockPosition(), DMSounds.DESERT_WORM_GROWL.get(), SoundSource.HOSTILE, 1*scale, RandomUtils.randomFloat(0.9f, 1.1f)*(1/scale));
                level.playSound(null, blockPosition(), DMSounds.DESERT_WORM_GROWL.get(), SoundSource.HOSTILE, 0.5f*scale, RandomUtils.randomFloat(0.6f, 0.8f)*(1/scale));
                level.playSound(null, blockPosition(), DMSounds.DESERT_WORM_GROWL.get(), SoundSource.HOSTILE, 0.35f*scale, RandomUtils.randomFloat(1.5f, 1.65f)*(1/scale));
                animationFactory.play(ANIMATION_ROAR);
            }
            if (state == ACTION_STUN){
                stunController.playAction(ACTION_STUN);
                level.playSound(null, blockPosition(), DMSounds.DESERT_WORM_STUN.get(), SoundSource.HOSTILE, 1, RandomUtils.randomFloat(0.9f, 1.1f));
                IS_STUNNED.set(EntityDesertWorm.this,true);
            }
            if (state == ACTION_ATTACK_FROM_UNDERGROUND){
                level.playSound(null, blockPosition(), SoundEvents.EVOKER_FANGS_ATTACK, SoundSource.HOSTILE, 1, RandomUtils.randomFloat(0.5f, 0.6f));
                animationFactory.play(ANIMATION_ATTACK_FROM_UNDERGROUND);
            }
            if (state == ACTION_ATTACK_ON_SURFACE){
                level.playSound(null, blockPosition(), SoundEvents.EVOKER_FANGS_ATTACK, SoundSource.HOSTILE, 1, RandomUtils.randomFloat(0.5f, 0.6f));
                animationFactory.play(ANIMATION_ATTACK_ON_SURFACE);
            }
            if (state == ACTION_RESISTANCE){
                level.playSound(null, blockPosition(), DMSounds.DESERT_WORM_HURT.get(), SoundSource.HOSTILE, 1, RandomUtils.randomFloat(0.9f, 1.1f));
                animationFactory.play(ANIMATION_RESISTANCE);
            }
            if (state == ACTION_HIDE){
                animationFactory.play(ANIMATION_HIDE);
            }
            if (state == ACTION_THROW){
                animationFactory.play(ANIMATION_THROW);
            }
            if (state == ACTION_EMERGE){
                animationFactory.play(ANIMATION_EMERGE);
            }
        }


        @Override
        public void onActionEnd(Action state) {
            if (state == ACTION_EMERGE){
                Entity rider = getFirstPassenger();
                if (rider != null) {
                    rider.stopRiding();
                    playClientEvent(EVENT_THROW_RIDER);
                }
                updateSize();
                animationFactory.stop(ANIMATION_EMERGE);
            }
            if (state == ACTION_THROW){
                Entity rider = getFirstPassenger();
                if (rider != null){
                    rider.stopRiding();
                    playClientEvent(EVENT_THROW_RIDER);
                }
            }
            if (state == ACTION_HIDE){
                if (successes >= 6){
                    BlockPos pos = getRandomDesertPosition();
                    teleportTo(pos.getX(),pos.getY(),pos.getZ());
                    actionController.playAction(ACTION_EMERGE);
                }
                IS_HIDDEN.set(EntityDesertWorm.this,true);
                animationFactory.stop(ANIMATION_IDLE);
            }
            if (state == ACTION_STUN){
                IS_STUNNED.set(EntityDesertWorm.this,false);
                animationFactory.play(ANIMATION_RESISTANCE);
            }
            if (state == ACTION_ATTACK_ON_SURFACE){
                if (EntityUtils.hasTarget(EntityDesertWorm.this)){
                    float scale = SCALE.get(EntityDesertWorm.this);
                    if (getTarget().distanceTo(EntityDesertWorm.this) < 4*scale) {
                        getTarget().hurt(DamageSource.mobAttack(EntityDesertWorm.this), (float) getAttributeValue(Attributes.ATTACK_DAMAGE)*SCALE.get(EntityDesertWorm.this));
                    }
                }
            }
            if (state == ACTION_ATTACK_FROM_UNDERGROUND){
                if (EntityUtils.hasTarget(EntityDesertWorm.this)){
                    float scale = SCALE.get(EntityDesertWorm.this);
                    List<LivingEntity> entities = EntityUtils.getEntities(LivingEntity.class, level, blockPosition(), (int)(2*scale), EntityPredicates.exclude(EntityDesertWorm.this));
                    for (LivingEntity entity : entities) {
                        if (MiscUtils.isDoubleBlockingWithScimitar(entity)){
                            actionController.playAction(ACTION_EMERGE);
                            stunController.playAction(ACTION_STUN);
                            level.playSound(null, blockPosition(), SoundEvents.ANVIL_PLACE, SoundSource.HOSTILE, 1, RandomUtils.randomFloat(0.5f, 0.6f));
                        }else{
                            entity.hurt(DamageSource.mobAttack(EntityDesertWorm.this), (float) getAttributeValue(Attributes.ATTACK_DAMAGE) * 4*SCALE.get(EntityDesertWorm.this));
                        }
                    }
                }
            }
        }

        @Override
        public void onActionTick(Action state, int ticks) {
            if (state == ACTION_ATTACK_FROM_UNDERGROUND){
                playClientEvent(EVENT_DIG_GROUND_ATTACK);
                updateSize();
            }
            if (state == ACTION_EMERGE && ticks == state.getLength()-1){
                IS_HIDDEN.set(EntityDesertWorm.this,false);
            }
            if (state == ACTION_EMERGE || state == ACTION_HIDE){
                playClientEvent(EVENT_DIG_GROUND_HIDE);
                updateSize();
                if (ticks % 2 == 0) {
                    level.playSound(null, blockPosition(), getBlockStateOn().getSoundType().getBreakSound(), SoundSource.HOSTILE, 1, RandomUtils.randomFloat(0.5f, 0.6f));
                }
            }
        }
    };
    public static final Action ACTION_ATTACK_FROM_UNDERGROUND = new Action(0,"undergroundAttack",0.56f);
    public static final Action ACTION_ATTACK_ON_SURFACE = new Action(1,"surfaceAttack",0.76f);
    public static final Action ACTION_EMERGE = new Action(2,"emerge",0.8f);
    public static final Action ACTION_HIDE = new Action(3,"hide",0.72f);
    public static final Action ACTION_ROAR = new Action(4,"roar",1.52f);
    public static final Action ACTION_RESISTANCE = new Action(5,"resistance",0.64f);
    public static final Action ACTION_THROW = new Action(6,"throw",0.76f);

    public ActionController stunController = new ActionController(this,"stunController",ACTION_STUN);
    public static final Action ACTION_STUN = new Action(0,"stun",80);

    public ActionStateMachine stateMachine = new ActionStateMachine(actionController,stunController);

    private final EntityTask hideTask = new EntityTask(this,100) {
        @Override
        public void run() {
            if (!IS_HIDDEN.get(EntityDesertWorm.this)) {
                actionController.playAction(ACTION_HIDE);
            }
        }
    }.shouldStopAfterRun();

    private int successes = 0;

    public EntityDesertWorm(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        setPathfindingMalus(BlockPathTypes.WATER,-1);
        setPathfindingMalus(BlockPathTypes.WATER_BORDER,-1);
        this.moveControl = new MoveControl(this){
            @Override
            public void setWantedPosition(double p_24984_, double p_24985_, double p_24986_, double p_24987_) {
                BlockState state = level.getBlockState(new BlockPos(p_24984_,p_24985_,p_24986_).below());
                if (!canMoveInBlock(state)){
                    return;
                }
                if (IS_HIDDEN.get(EntityDesertWorm.this) && !actionController.is(ACTION_ATTACK_FROM_UNDERGROUND) && !actionController.is(ACTION_EMERGE) && !stunController.is(ACTION_STUN)) {
                    super.setWantedPosition(p_24984_, p_24985_, p_24986_, p_24987_);
                }
            }

            private boolean canMoveInBlock(BlockState state){
                return WorldUtils.isBlock(state, Blocks.AIR,Blocks.SAND, DMBlocks.DUNE_SAND.get(),Blocks.RED_SAND);
            }

        };
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new DesertWormPanicLightGoal(this,8,1.4,1.4));
        this.goalSelector.addGoal(2, new DesertWormAttackGoal(this,1.4,true));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this,1.4f));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Villager.class, false));
        this.targetSelector.addGoal(2,new NearestAttackableTargetGoal<>(this, Animal.class,false,(animal)-> animal.getType() != DMEntities.MEERKAT.get()));
    }

    @Override
    protected void doPush(Entity p_20971_) {
        if (!IS_HIDDEN.get(this)) {
            super.doPush(p_20971_);
        }
    }

    @Override
    protected void pushEntities() {
        if (!IS_HIDDEN.get(this)) {
            super.pushEntities();
        }
    }

    @Override
    public void push(Entity p_21294_) {
        if (!IS_HIDDEN.get(this)){
            super.push(p_21294_);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (isInWater()){
            hurt(DamageSource.GENERIC,1000);
        }
        hideTask.update();
        ridingTick();
        float scale = SCALE.get(this);
        float width = 1.5f*scale;
        ParticleEmitter emitter = new ParticleEmitter(ParticleEmitter.block(getBlockStateOn())).size(new Vector3(width, 0, width)).count((int) (8*scale));
        emitter.emit(level, Vector3.from(position()), new Vector3(0, 0.1f, 0));
        if (!IS_STUNNED.get(this) && getFirstPassenger() == null) {
            boolean needToHide = !EntityUtils.hasTarget(this);
            if (EntityUtils.hasTarget(this)){
                needToHide = getTarget().distanceTo(this) > 4;
            }
            if (needToHide && !IS_HIDDEN.get(this) && !actionController.is(ACTION_HIDE)) {
                if (hideTask.isCancelled()) {
                    hideTask.wakeUp();
                }
            }
        }
        if (!IS_HIDDEN.get(this)){
            if (IS_STUNNED.get(this)){
                animationFactory.play(ANIMATION_IDLE_STUN);
            }else{
                if (actionController.isNoAction() && getTarget() != null && RandomUtils.doWithChance(3)){
                    actionController.playAction(ACTION_ROAR);
                }
                animationFactory.play(ANIMATION_IDLE);
            }
        }else{
            if (animationFactory.getPlayingAnimations().isEmpty()){
                animationFactory.play(ANIMATION_HIDE);
            }
            ParticleEmitter groundParticle = new ParticleEmitter(ParticleEmitter.block(getBlockStateOn())).size(new Vector3(0.5f*scale, 0.25f*scale, 0.5f*scale)).count((int) (16*scale));
            Vector3 offset = new Vector3(MathUtils.sin(tickCount/4)*width,0,MathUtils.cos(tickCount/4)*width);
            groundParticle.emit(level, Vector3.from(position()).add(offset), new Vector3(MathUtils.sin(tickCount)*4*scale,1, MathUtils.cos(tickCount)*4*scale));

            if (EntityUtils.isMoving(this,-0.15f,0.15f)) {
                Item item = getBlockStateOn().getBlock().asItem();
                if (item != null && !getBlockStateOn().isAir()) {
                    ParticleEmitter groundParticle2 = new ParticleEmitter(ParticleEmitter.item(item.getDefaultInstance())).size(new Vector3(2*scale, 0.25f*scale, 2*scale)).count((int) (16*scale));
                    Vec3 viewVec = getViewVector(0).multiply(-0.25f, 0, -0.25f);
                    groundParticle2.emit(level, Vector3.from(position()), new Vector3(viewVec.x, 0.25f*scale, viewVec.z));
                }
            }
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        IS_HIDDEN.register(this);
        IS_STUNNED.register(this);
        SCALE.register(this);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag p_21484_) {
        super.addAdditionalSaveData(p_21484_);
        IS_HIDDEN.save(this,p_21484_);
        IS_STUNNED.save(this,p_21484_);
        SCALE.save(this,p_21484_);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_21450_) {
        super.readAdditionalSaveData(p_21450_);
        IS_HIDDEN.load(this,p_21450_);
        IS_STUNNED.load(this,p_21450_);
        SCALE.load(this,p_21450_);
    }

    @Override
    public boolean doHurtTarget(Entity p_21372_) {
        if (actionController.isNoAction() && stunController.isNoAction() && getFirstPassenger() == null){
            float scale = SCALE.get(this);
            if (IS_HIDDEN.get(this)){
                actionController.playAction(ACTION_ATTACK_FROM_UNDERGROUND);
                for (ServerPlayer entity : EntityUtils.getEntities(ServerPlayer.class, level, blockPosition(), 4*(int)scale)) {
                    PlayerGFXEffectManager.addEffect(entity,new GFXSoftShake(0.25f,40),true);
                }
                level.playSound(null, blockPosition(), DMSounds.DESERT_WORM_DIG.get(), SoundSource.HOSTILE, 1, RandomUtils.randomFloat(0.9f, 1.1f));
                getNavigation().stop();
            }else{
                actionController.playAction(ACTION_ATTACK_ON_SURFACE);
            }
        }
        return false;
    }


    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) {
        if (IS_HIDDEN.get(this) && p_21016_.getEntity() != null && !actionController.is(ACTION_ATTACK_FROM_UNDERGROUND)){
            return false;
        }
        if (p_21016_ == DamageSource.CRAMMING || p_21016_ == DamageSource.IN_WALL || p_21016_ == DamageSource.FALL || p_21016_ == DamageSource.CACTUS){
            return false;
        }
        return super.hurt(p_21016_, p_21017_);
    }

    @Override
    public float getVoicePitch() {
        float scale = SCALE.get(this);
        return super.getVoicePitch()*(1/scale);
    }

    public BlockPos getRandomDesertPosition(){
        int range = (int) (500*SCALE.get(this));
        int minRange = (int) (100*SCALE.get(this));
        BlockPos randomPos = blockPosition().offset(RandomUtils.randomInt(-range,range)+minRange,0,RandomUtils.randomInt(-range,range)+minRange);
        if (level instanceof ServerLevel){
             BlockPos pos = ((ServerLevel) level).findClosestBiome3d((biome)->{
                 return biome.is(Biomes.DESERT);
             },randomPos,6400,32,64).getFirst();
            if (level.getBlockState(pos.below()).isSolidRender(level,pos.below()) && !level.getBlockState(pos.above()).isSolidRender(level,pos.above())){
                return pos;
            }else{
                return getRandomDesertPosition();
            }
        }
        return null;
    }

    @Override
    protected InteractionResult mobInteract(Player p_21472_, InteractionHand p_21473_) {
        if (IS_STUNNED.get(this)){
            p_21472_.startRiding(this);
            stunController.setNoState();
            IS_STUNNED.set(this,false);
            animationFactory.play(ANIMATION_RESISTANCE);
        }
        return super.mobInteract(p_21472_, p_21473_);
    }

    @Override
    public EntityDimensions getDimensions(Pose p_219392_) {
        EntityDimensions entitydimensions = super.getDimensions(p_219392_);
        float scale = SCALE.get(this);
        float defaultWidth = entitydimensions.width*scale;
        float defaultHeight = entitydimensions.height*scale;
        if (IS_HIDDEN.get(this)){
            return EntityDimensions.fixed(defaultWidth, 0.01f);
        }
        if (actionController.is(ACTION_EMERGE) || actionController.is(ACTION_ATTACK_FROM_UNDERGROUND)){
            float progress = 1f-((float)actionController.getTicks()/(float)actionController.getActionState().getLength());
            return EntityDimensions.fixed(defaultWidth, defaultHeight*progress);
        }else
        if (actionController.is(ACTION_HIDE)){
            int ticks = actionController.getTicks();
            float progress = ((float)ticks/(float)actionController.getActionState().getLength());
            return EntityDimensions.fixed(defaultWidth, defaultHeight*progress);
        }
        return EntityDimensions.fixed(defaultWidth,defaultHeight);
    }

    public void ridingTick(){
        Entity rider = getFirstPassenger();
        if (rider != null) {
            if (successes < 6) {
                if (tickCount % 10 == 0 && actionController.isNoAction()) {
                    if (rider instanceof ServerPlayer) {
                        PlayerGFXEffectManager.addEffect((ServerPlayer) rider, new GFXSoftShake(1, 80), true);
                    }
                    actionController.playAction(ACTION_RESISTANCE);
                }
            }else{
                if (successes == 6) {
                    successes = 7;
                    if (rider instanceof ServerPlayer) {
                        PlayerGFXEffectManager.addEffect((ServerPlayer) rider, new GFXBlackInOut(Color.BLACK, 0.35f), true);
                    }
                    actionController.playAction(ACTION_HIDE);
                }
            }
        }
    }


    @Override
    public void handleStartJump(int p_21695_) {
        Entity rider = getFirstPassenger();
        if (rider != null){
            if (p_21695_ < 90){
                actionController.playAction(ACTION_THROW);
                successes = 0;
            }else{
                successes++;
                if (actionController.isNoAction()) {
                    if (rider instanceof ServerPlayer) {
                        PlayerGFXEffectManager.addEffect((ServerPlayer) rider, new GFXSoftShake(1, 80), true);
                    }
                    actionController.playAction(ACTION_RESISTANCE);
                }
                level.playSound(null, blockPosition(), SoundEvents.NOTE_BLOCK_BELL, SoundSource.PLAYERS, 1, RandomUtils.randomFloat(1.9f, 2));
            }
        }
    }


    public void updateSize(){
        playClientEvent(EVENT_UPDATE_SIZE);
        refreshDimensions();
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset();
    }

    @Override
    public void move(MoverType p_19973_, Vec3 p_19974_) {
        super.move(p_19973_, p_19974_);
    }

    @Override
    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
        level.playSound(null, blockPosition(), p_20136_.getSoundType().getBreakSound(), SoundSource.HOSTILE, 1, RandomUtils.randomFloat(0.5f, 0.6f));
    }

    @Override
    public float getStepHeight() {
        return 1;
    }


    @Override
    public boolean canJump() {
        return successes < 6;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public <K extends IDisplayArgument> AnimationFactory getAnimationFactory(K argument) {
        return animationFactory;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return DMSounds.DESERT_WORM_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return DMSounds.DESERT_WORM_DEATH.get();
    }

    @Override
    public void handleStopJump() {
    }

    @Override
    public void onPlayerJump(int p_21696_) {
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        updateSize();
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        updateSize();
    }

    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    public void onHandleServerEvent(int event, PacketArgument[] arguments) {

    }

    @Override
    public void onHandleClientEvent(int event, PacketArgument[] arguments) {
        float scale = SCALE.get(this);
        if (event == EVENT_UPDATE_SIZE){
            refreshDimensions();
        }
        if (event == EVENT_DIG_GROUND_HIDE){
            Vec3 view = getViewVector(0);
            float maxHeight = 3*scale+RandomUtils.randomFloat(-0.15f,0.2f);
            float progress = 1f-((float)actionController.getTicks()/(float)actionController.getActionState().getLength());
            ParticleEmitter groundParticle = new ParticleEmitter(ParticleEmitter.block(getBlockStateOn())).size(new Vector3(1.5f*scale, maxHeight*progress, 1.5f*scale)).count((int) (128*scale));
            groundParticle.emit(level, Vector3.from(position()), new Vector3(view.x, maxHeight*progress, view.z));
        }
        if (event == EVENT_DIG_GROUND_ATTACK){
            float maxHeight = 4*scale+RandomUtils.randomFloat(-0.15f,0.2f);
            float progress = 1f-((float)actionController.getTicks()/(float)actionController.getActionState().getLength());
            float randomX = RandomUtils.randomFloat(-1f*scale,1f*scale);
            float randomZ = RandomUtils.randomFloat(-1f*scale,1f*scale);
            ParticleEmitter groundParticle = new ParticleEmitter(ParticleEmitter.block(getBlockStateOn())).size(new Vector3(1.5f*scale+randomX, maxHeight*progress, 1.5f*scale+randomZ)).count((int) (128*scale));
            groundParticle.emit(level, Vector3.from(position()), new Vector3(randomX, maxHeight*progress, randomZ));
        }
        if (event == EVENT_THROW_RIDER){
            Player player = EntityUtils.getEntity(Player.class,level,blockPosition(),2);
            Vec3 viewVec = getViewVector(0);
            player.push(viewVec.x,2,viewVec.z);
        }
    }

    @Override
    public ActionStateMachine getActionStateMachine() {
        return stateMachine;
    }
}
