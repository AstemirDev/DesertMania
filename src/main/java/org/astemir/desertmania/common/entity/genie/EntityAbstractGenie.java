package org.astemir.desertmania.common.entity.genie;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.common.action.Action;
import org.astemir.api.common.action.ActionController;
import org.astemir.api.common.animation.Animation;
import org.astemir.api.common.animation.AnimationFactory;
import org.astemir.api.common.entity.ISkillsMob;
import org.astemir.api.common.entity.ITamable;
import org.astemir.api.common.entity.ai.goals.GoalFollowOwner;
import org.astemir.api.common.entity.ai.goals.GoalOwnerHurtByTarget;
import org.astemir.api.common.entity.ai.goals.GoalOwnerHurtTarget;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.common.misc.ParticleEmitter;
import org.astemir.api.math.components.Color;
import org.astemir.api.math.components.Vector3;
import org.astemir.api.network.PacketArgument;
import org.astemir.desertmania.common.entity.genie.ai.GenieRangedAttackGoal;
import org.astemir.desertmania.common.entity.genie.misc.EntityGenieCharge;
import org.astemir.desertmania.common.entity.genie.misc.GenieAnimations;
import org.astemir.desertmania.common.entity.genie.misc.GenieColors;
import org.astemir.desertmania.common.particle.GlowingDustParticleOptions;
import javax.annotation.Nullable;
import java.util.UUID;

public abstract class EntityAbstractGenie extends PathfinderMob implements ISkillsMob,FlyingAnimal, RangedAttackMob, ITamable {


    private final AnimationFactory animationFactory = new AnimationFactory(this,getAnimations()){

        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation == GenieAnimations.ANIMATION_ATTACK){
                if (EntityUtils.hasTarget(EntityAbstractGenie.this)) {
                    if (onAttack(getTarget())) {
                        EntityGenieCharge charge = new EntityGenieCharge(level, EntityAbstractGenie.this, getTarget());
                        Vector3 dir = Vector3.from(position()).direction(Vector3.from(getTarget().position()));
                        setDeltaMovement(dir.x * 0.25, dir.y * 0.25, dir.z * 0.25);
                        ProjectileUtil.rotateTowardsMovement(charge, 1f);
                        level.addFreshEntity(charge);
                        playSound(SoundEvents.BLAZE_SHOOT);
                        playClientEvent(0);
                    }
                }
            }
        }
    };

    public ActionController spawnController = new ActionController(this,"spawnController",ACTION_SPAWN,ACTION_HIDE){

        @Override
        public void onActionBegin(Action state) {
            if (state == ACTION_SPAWN){
                getAnimationFactory().play(GenieAnimations.ANIMATION_SPAWN);
            }
            if (state == ACTION_HIDE){
                getAnimationFactory().play(GenieAnimations.ANIMATION_HIDE);
            }
        }

        @Override
        public void onActionEnd(Action state) {
            if (state == ACTION_SPAWN){
                getAnimationFactory().play(GenieAnimations.ANIMATION_IDLE);
            }
            if (state == ACTION_HIDE){
                remove(Entity.RemovalReason.CHANGED_DIMENSION);
            }
        }
    };
    public static Action ACTION_SPAWN = new Action(0,"spawn",2.08f);
    public static Action ACTION_HIDE = new Action(1,"hide",1.16f);

    @Nullable
    public UUID owner;

    protected EntityAbstractGenie(EntityType<? extends PathfinderMob> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        this.moveControl = new FlyingMoveControl(this, 20, true){
            @Override
            public void setWantedPosition(double p_24984_, double p_24985_, double p_24986_, double p_24987_) {
                if (canMove() && tickCount > 40) {
                    super.setWantedPosition(p_24984_, p_24985_, p_24986_, p_24987_);
                }
            }
        };
        setPersistenceRequired();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new GenieRangedAttackGoal(this, 1.0D, 20, 40,10.0F));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new GoalFollowOwner(this,1.0D,5,1,true));
        this.targetSelector.addGoal(1, new GoalOwnerHurtByTarget(this));
        this.targetSelector.addGoal(2, new GoalOwnerHurtTarget(this));
    }


    public void animation(){
        if (canMove()) {
            if (EntityUtils.isMoving(this, -0.15f, 0.15f)) {
                getAnimationFactory().play(GenieAnimations.ANIMATION_WALK_FLY);
            } else {
                getAnimationFactory().play(GenieAnimations.ANIMATION_IDLE);
            }
        }
        if (getAnimationFactory().isPlaying(GenieAnimations.ANIMATION_HIDE)){
            genieParticles();
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag p_21484_) {
        super.addAdditionalSaveData(p_21484_);
        if (owner != null) {
            p_21484_.putUUID("OwnerUniqueId", owner);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_21450_) {
        super.readAdditionalSaveData(p_21450_);
        if (p_21450_.contains("OwnerUniqueId")){
            owner = p_21450_.getUUID("OwnerUniqueId");
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (tickCount % 5 == 0){
            heal(5);
        }
    }

    private void genieParticles(){
        new ParticleEmitter(new GlowingDustParticleOptions(getGenieColor().toVector3f(), 1)).count(32).size(new Vector3(0.5f,0.5f,0.5f)).emit(level, Vector3.from(getEyePosition()), new Vector3(0, 0, 0));
    }

    @Override
    public void performRangedAttack(LivingEntity p_33317_, float p_33318_) {
        animationFactory.play(GenieAnimations.ANIMATION_ATTACK);
        lookControl.setLookAt(p_33317_);
    }


    public boolean onAttack(LivingEntity target){
        return true;
    }

    @Override
    public void onHandleServerEvent(int event, PacketArgument[] arguments) {
    }

    @Override
    public void onHandleClientEvent(int event, PacketArgument[] arguments) {
        if (event == 0){
            genieParticles();
        }
    }

    @Override
    protected PathNavigation createNavigation(Level p_218342_) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, p_218342_);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }


    protected void playStepSound(BlockPos p_218364_, BlockState p_218365_) {
    }

    protected void checkFallDamage(double p_218316_, boolean p_218317_, BlockState p_218318_, BlockPos p_218319_) {
    }

    private boolean canMove(){
        return !getAnimationFactory().isPlaying(GenieAnimations.ANIMATION_ATTACK,GenieAnimations.ANIMATION_CAST,GenieAnimations.ANIMATION_HIDE,GenieAnimations.ANIMATION_SPAWN,GenieAnimations.ANIMATION_SING);
    }

    @Override
    public boolean canAttackTarget(LivingEntity target, LivingEntity owner) {
        return !(target instanceof EntityAbstractGenie);
    }

    @Override
    public boolean isOrderedToSit() {
        return false;
    }

    @Override
    public boolean isTamed() {
        return owner != null;
    }

    public void setOwner(Player player){
        owner = player.getUUID();
    }

    @Override
    public Player getOwner() {
        if (owner != null) {
            return level.getPlayerByUUID(owner);
        }
        return null;
    }

    @Override
    public boolean isFlying() {
        return !isOnGround();
    }

    public ActionController getSpawnController() {
        return spawnController;
    }

    private Color getGenieColor(){
        return GenieColors.fromType(getType());
    }

    private Animation[] getAnimations(){
        return GenieAnimations.getAnimations();
    }

    @Override
    public <K extends IDisplayArgument> AnimationFactory getAnimationFactory(K argument) {
        return animationFactory;
    }

}
