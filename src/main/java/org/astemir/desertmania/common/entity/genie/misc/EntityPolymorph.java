package org.astemir.desertmania.common.entity.genie.misc;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.common.animation.Animation;
import org.astemir.api.common.animation.AnimationFactory;
import org.astemir.api.common.animation.objects.IAnimatedEntity;
import org.astemir.api.common.entity.IEventEntity;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.common.misc.ICustomRendered;
import org.astemir.api.common.misc.ParticleEmitter;
import org.astemir.api.math.components.Vector3;
import org.astemir.api.network.PacketArgument;
import org.astemir.desertmania.common.particle.GlowingDustParticleOptions;
import org.jetbrains.annotations.Nullable;


public class EntityPolymorph extends PathfinderMob implements ICustomRendered, IAnimatedEntity, IEventEntity {

    public static EntityDataAccessor<CompoundTag> ORIGINAL_DATA = SynchedEntityData.defineId(EntityPolymorph.class, EntityDataSerializers.COMPOUND_TAG);

    private final AnimationFactory animationFactory = new AnimationFactory(this,ANIMATION_IDLE,ANIMATION_WALK,ANIMATION_TRANSFORM,ANIMATION_SPAWN){
        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation == ANIMATION_TRANSFORM){
                if (!isDeadOrDying()) {
                    Entity entity = EntityType.create(entityData.get(ORIGINAL_DATA), level).get();
                    entity.moveTo(getX(), getY(), getZ(), getYRot(), getXRot());
                    level.addFreshEntity(entity);
                    remove(RemovalReason.DISCARDED);
                }
            }
        }
    };
    public static final Animation ANIMATION_IDLE = new Animation("animation.model.idle",2.08f).layer(0).loop();
    public static final Animation ANIMATION_WALK = new Animation("animation.model.walk",1.04f).layer(0).loop();
    public static final Animation ANIMATION_TRANSFORM = new Animation("animation.model.transform",0.68f).layer(1);
    public static final Animation ANIMATION_SPAWN = new Animation("animation.model.spawn",0.32f).layer(1);

    private final int MAX_LIFE = 140;
    private int lifeTicks = 0;


    public EntityPolymorph(EntityType<? extends PathfinderMob> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.4D));
        this.goalSelector.addGoal(3, new PanicGoal(this, 0.6D));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Player.class, 8.0F, 0.6D, 0.6D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 10.0F));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(ORIGINAL_DATA,new CompoundTag());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag p_21484_) {
        super.addAdditionalSaveData(p_21484_);
        if (entityData.get(ORIGINAL_DATA) != null) {
            p_21484_.put("OriginalData", entityData.get(ORIGINAL_DATA));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_21450_) {
        super.readAdditionalSaveData(p_21450_);
        if (p_21450_.contains("OriginalData")){
            CompoundTag compoundTag = p_21450_.getCompound("OriginalData");
            entityData.set(ORIGINAL_DATA,compoundTag);
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (lifeTicks > MAX_LIFE-10){
            new ParticleEmitter(new GlowingDustParticleOptions(GenieColors.PURPLE.toVector3f(), 1)).count(32).size(new Vector3(0.5f,0.5f,0.5f)).emit(level, Vector3.from(getEyePosition().add(0,-0.25f,0)), new Vector3(0, 0, 0));
        }
        if (lifeTicks < 2){
            new ParticleEmitter(new GlowingDustParticleOptions(GenieColors.PURPLE.toVector3f(), 1)).count(32).size(new Vector3(0.5f,0.5f,0.5f)).emit(level, Vector3.from(getEyePosition().add(0,-0.25f,0)), new Vector3(0, 0, 0));
            animationFactory.play(ANIMATION_SPAWN);
        }
        lifeTicks++;
        if (lifeTicks > MAX_LIFE){
            animationFactory.play(ANIMATION_TRANSFORM);
        }
        if (EntityUtils.isMoving(this,-0.05f,0.05f)){
            animationFactory.play(ANIMATION_WALK);
        }else{
            animationFactory.play(ANIMATION_IDLE);
        }
    }


    @Override
    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
        dropFromOtherLootTable(pSource,pRecentlyHit);
    }

    protected void dropFromOtherLootTable(DamageSource pDamageSource, boolean pHitByPlayer) {
        if (entityData.get(ORIGINAL_DATA) != null) {
            EntityType originalType = EntityType.by(entityData.get(ORIGINAL_DATA)).get();
            ResourceLocation resourcelocation = originalType.getDefaultLootTable();
            LootTable loottable = this.level.getServer().getLootTables().get(resourcelocation);
            LootContext.Builder builder = this.createLootContext(pHitByPlayer, pDamageSource);
            LootContext ctx = builder.create(LootContextParamSets.ENTITY);
            loottable.getRandomItems(ctx).forEach(this::spawnAtLocation);
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SHEEP_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SHEEP_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SHEEP_DEATH;
    }

    @Override
    public float getVoicePitch() {
        return super.getVoicePitch()*1.5f;
    }

    @Override
    public void onHandleServerEvent(int event, PacketArgument[] arguments) {}

    @Override
    public void onHandleClientEvent(int event, PacketArgument[] arguments) {
        new ParticleEmitter(new GlowingDustParticleOptions(GenieColors.PURPLE.toVector3f(), 1)).count(32).size(new Vector3(0.5f,0.5f,0.5f)).emit(level, Vector3.from(getEyePosition()), new Vector3(0, 0, 0));
    }

    public void setEntityData(CompoundTag data) {
        getEntityData().set(ORIGINAL_DATA,data);
    }

    @Override
    public <K extends IDisplayArgument> AnimationFactory getAnimationFactory(K argument) {
        return animationFactory;
    }
}
