package org.astemir.desertmania.common.entity.scarab;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.common.animation.Animation;
import org.astemir.api.common.animation.AnimationFactory;
import org.astemir.api.common.animation.objects.IAnimatedEntity;
import org.astemir.api.common.misc.ICustomRendered;
import org.astemir.api.common.misc.ParticleEmitter;
import org.astemir.api.lib.shimmer.DynamicEntityLight;
import org.astemir.api.lib.shimmer.IDynamicLightEntity;
import org.astemir.api.math.MathUtils;
import org.astemir.api.math.components.Color;
import org.astemir.api.math.components.Vector3;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.desertmania.common.particle.GlowingDustParticleOptions;
import org.astemir.desertmania.common.sound.DMSounds;
import org.astemir.desertmania.common.world.generation.structures.DMStructures;

import java.util.Arrays;

public class EntityGoldenScarab extends PathfinderMob implements ICustomRendered, IAnimatedEntity, IDynamicLightEntity {


    public static final Animation ANIMATION_IDLE = new Animation("animation.model.idle",0.8f).speed(1.5f).layer(0).loop();
    public static final Animation ANIMATION_FLY = new Animation("animation.model.fly",1.04f).layer(0).loop();
    public static final Animation ANIMATION_WALK = new Animation("animation.model.walk",0.48f).speed(1.75f).layer(0).loop();
    public static final Animation ANIMATION_ATTACK = new Animation("animation.model.attack",0.32f).speed(1.25f).layer(1).priority(1);

    public AnimationFactory animationFactory = new AnimationFactory(this,ANIMATION_IDLE,ANIMATION_FLY,ANIMATION_WALK,ANIMATION_ATTACK);

    private BlockPos structurePos;


    @OnlyIn(Dist.CLIENT)
    private DynamicEntityLight entityLight;

    public EntityGoldenScarab(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        moveControl = new FlyingMoveControl(this,20,true);
        navigation = new FlyingPathNavigation(this,level);
        xpReward = 0;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
    }

    @Override
    public void aiStep() {
        super.aiStep();
        Vec3 view = getViewVector(0);
        ParticleEmitter.emit(new GlowingDustParticleOptions(Color.GOLD.toVector3f(), 0.8f), level, new Vector3(RandomUtils.randomFloat(-0.3f, 0.3f), RandomUtils.randomFloat(-0.2f, 0.2f), RandomUtils.randomFloat(-0.2f, 0.2f)), Vector3.from(position()).add(0, 0.25f, 0), new Vector3(-view.x * 2, 0, -view.z * 2), 2);
        ParticleEmitter.emit(new GlowingDustParticleOptions(Color.YELLOW.toVector3f(), 0.6f), level, new Vector3(RandomUtils.randomFloat(-0.2f, 0.2f), RandomUtils.randomFloat(-0.2f, 0.2f), RandomUtils.randomFloat(-0.2f, 0.2f)), Vector3.from(position()).add(0, 0.25f, 0), new Vector3(-view.x * 2, 0, -view.z * 2), 2);
        animationFactory.play(ANIMATION_FLY);
        if (animationFactory.isPlaying(ANIMATION_FLY)){
            level.playSound(null,blockPosition(), DMSounds.SCARAB_FLY.get(), SoundSource.HOSTILE,0.05f,RandomUtils.randomFloat(0.9f,1.1f));
        }
        if (level instanceof ServerLevel serverLevel) {
            if (structurePos == null) {
                Registry<Structure> registry = serverLevel.registryAccess().registryOrThrow(Registry.STRUCTURE_REGISTRY);
                Holder<Structure> holder = registry.getHolder(DMStructures.SCARAB_TEMPLE.getKey()).get();
                Pair<BlockPos, Holder<Structure>> pair = serverLevel.getChunkSource().getGenerator().findNearestMapStructure(serverLevel, HolderSet.direct(holder), blockPosition(), 200, false);
                if (pair.getFirst() != null){
                    structurePos = new BlockPos(pair.getFirst().getX(),getY(),pair.getFirst().getZ());
                }
            }
        }
        if (structurePos != null) {
            flyingSpeed = 0.01f;
            Vector3 pos = new Vector3(structurePos.getX(),getY(),structurePos.getZ());
            int randomDegreeX = 0;
            int randomDegreeY = 0;
            if (tickCount % 40 > 20){
                randomDegreeX = RandomUtils.randomInt(random,-90,90);
            }
            if (tickCount % 60 > 30){
                randomDegreeY = RandomUtils.randomElement(random, Arrays.asList(-90,90,-45,45));
            }
            Vector3 newVec = Vector3.from(getViewVector(0)).rotateAroundY(MathUtils.rad(randomDegreeX)).rotateAroundX(MathUtils.rad(randomDegreeY));
            Vector3 vec = Vector3.from(getDeltaMovement()).lerp(newVec,RandomUtils.randomFloat(0.05f,0.5f));
            if (getY()-level.getSeaLevel() > 10){
                vec.setY(-0.5f);
            }
            setDeltaMovement(vec.toVec3());
            navigation.moveTo(pos.x,pos.y,pos.z, 1);
        }

    }


    @Override
    public boolean doHurtTarget(Entity p_21372_) {
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
        return SoundEvents.METAL_BREAK;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.METAL_BREAK;
    }

    @Override
    public LookControl getLookControl() {
        return super.getLookControl();
    }

    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }


    @Override
    public void onClientRemoval() {
        entityLight.remove();
    }

    @Override
    public <K extends IDisplayArgument> AnimationFactory getAnimationFactory(K argument) {
        return animationFactory;
    }

    @Override
    public DynamicEntityLight getOrCreateLight() {
        if (entityLight == null){
            Color color = new Color(1,1,0.63f,1);
            entityLight = new DynamicEntityLight(color,2f).create();
        }
        return entityLight;
    }
}
