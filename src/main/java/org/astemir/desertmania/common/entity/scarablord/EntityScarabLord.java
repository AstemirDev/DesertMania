package org.astemir.desertmania.common.entity.scarablord;

import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.common.action.Action;
import org.astemir.api.common.action.ActionController;
import org.astemir.api.common.action.ActionStateMachine;
import org.astemir.api.common.animation.Animation;
import org.astemir.api.common.animation.AnimationFactory;
import org.astemir.api.common.entity.ClientSideValue;
import org.astemir.api.common.entity.EntityData;
import org.astemir.api.common.entity.EntityPredicates;
import org.astemir.api.common.entity.ISkillsMob;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.common.gfx.GFXRoughShake;
import org.astemir.api.common.gfx.PlayerGFXEffectManager;
import org.astemir.api.common.item.ItemUtils;
import org.astemir.api.common.item.RandomEnchanter;
import org.astemir.api.common.misc.ParticleEmitter;
import org.astemir.api.math.MathUtils;
import org.astemir.api.math.collection.Couple;
import org.astemir.api.math.components.Color;
import org.astemir.api.math.components.Vector3;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.api.network.PacketArgument;
import org.astemir.desertmania.common.block.DMBlocks;
import org.astemir.desertmania.common.enchantments.DMEnchantments;
import org.astemir.desertmania.common.entity.DMEntities;
import org.astemir.desertmania.common.entity.EntityMummy;
import org.astemir.desertmania.common.entity.scarab.EntityScarab;
import org.astemir.desertmania.common.entity.scarablord.ai.ScarabLordMeleeAttackGoal;
import org.astemir.desertmania.common.entity.scarablord.ai.ScarabLordRandomWalkGoal;
import org.astemir.desertmania.common.item.DMItems;
import org.astemir.desertmania.common.particle.GlowingDustParticleOptions;
import org.astemir.desertmania.common.sound.DMSounds;

import javax.annotation.Nullable;

public class EntityScarabLord extends Monster implements ISkillsMob {


    public static final int EVENT_SWORD_ATTACK = 0;
    public static final int EVENT_RAGE = 1;
    public static final int EVENT_LIFE_STEAL = 2;
    public static final int EVENT_SPAWN = 3;
    public static final int EVENT_SPAWN_MOB = 4;

    public boolean clientSideGhosting = false;


    public static RandomEnchanter.EnchantmentMap ENCHANTMENTS = RandomEnchanter.newEnchantmentMap().
            add(RandomEnchanter.enchantment(DMEnchantments.WHIRLING.get()),100).
            add(RandomEnchanter.enchantment(Enchantments.UNBREAKING).levels(new Couple<>(80.0,2),new Couple<>(50.0,3)),80).
            add(RandomEnchanter.enchantment(Enchantments.SWEEPING_EDGE).levels(new Couple<>(40.0,2),new Couple<>(20.0,3)),20).
            add(RandomEnchanter.enchantment(Enchantments.VANISHING_CURSE).levels(new Couple<>(100.0,1)),20).
            add(RandomEnchanter.enchantment(Enchantments.FIRE_ASPECT).levels(new Couple<>(90.0,1),new Couple<>(20.0,2)),5).
            add(RandomEnchanter.enchantment(Enchantments.KNOCKBACK).levels(new Couple<>(90.0,1),new Couple<>(20.0,2)),10).
            add(RandomEnchanter.enchantment(Enchantments.SHARPNESS).levels(new Couple<>(50.0,3),new Couple<>(30.0,4),new Couple<>(15.0,5)).conflicts(Enchantments.BANE_OF_ARTHROPODS,Enchantments.SMITE),20).
            add(RandomEnchanter.enchantment(Enchantments.SMITE).levels(new Couple<>(50.0,3),new Couple<>(30.0,4),new Couple<>(15.0,5)).conflicts(Enchantments.SMITE,Enchantments.SHARPNESS),20).
            add(RandomEnchanter.enchantment(Enchantments.BANE_OF_ARTHROPODS).levels(new Couple<>(50.0,3),new Couple<>(30.0,4),new Couple<>(15.0,5)).conflicts(Enchantments.SHARPNESS,Enchantments.SMITE),20);

    public static final EntityData<Integer> STADIUM = new EntityData<>(EntityScarabLord.class,"Stadium", EntityDataSerializers.INT,0);

    private final ServerBossEvent bossHealthbar = new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.NOTCHED_12);

    private final AnimationFactory animationFactory = new AnimationFactory(this,ScarabLordAnimations.getAnimations()){
        @Override
        public void onAnimationTick(Animation animation, int tick) {
            LivingEntity target = getTarget();
            if (animation == ScarabLordAnimations.ANIMATION_SPAWN){
                Player player = EntityUtils.getEntity(Player.class,level,blockPosition(),20);
                if (player != null) {
                    getLookControl().setLookAt(player);
                }
                if (tick == 22){
                    EntityScarabLord.this.playSound(getAmbientSound(), EntityScarabLord.this.getSoundVolume(), EntityScarabLord.this.getVoicePitch());
                }
            }
            if (target != null){
                boolean canDamage = distanceTo(target) < 5;
                if (animation == ScarabLordAnimations.ANIMATION_ATTACK_ONCE && tick == 12){
                    playClientEvent(EVENT_SWORD_ATTACK);
                    if (canDamage) {
                        EntityUtils.damageEntity(EntityScarabLord.this, target);
                    }
                    getNavigation().stop();
                    level.playSound(null, blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.HOSTILE, 1, RandomUtils.randomFloat(0.9f,1.1f));
                }else
                if (animation == ScarabLordAnimations.ANIMATION_ATTACK_DOUBLE && (tick == 9 || tick == 17)){
                    playClientEvent(EVENT_SWORD_ATTACK);
                    if (canDamage) {
                        target.invulnerableTime = 0;
                        EntityUtils.damageEntity(EntityScarabLord.this, target);
                    }
                    getNavigation().stop();
                    level.playSound(null, blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.HOSTILE, 1, RandomUtils.randomFloat(0.9f,1.1f));
                }else
                if (animation == ScarabLordAnimations.ANIMATION_ATTACK_SPREAD && tick == 12){
                    playClientEvent(EVENT_SWORD_ATTACK);
                    if (canDamage) {
                        EntityUtils.damageEntity(EntityScarabLord.this, target, 1.25f, true);
                    }
                    getNavigation().stop();
                    level.playSound(null, blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.HOSTILE, 1, RandomUtils.randomFloat(0.9f,1.1f));
                }else
                if (animation == ScarabLordAnimations.ANIMATION_ATTACK_FLY && tick == 6){
                    if (canDamage) {
                        EntityUtils.damageEntity(EntityScarabLord.this, target, 1.5f);
                    }
                }
            }
        }
    };

    private final ActionController<EntityScarabLord> actionController = new ActionController<>(this,"actionController",ScarabLordActions.getActions()){
        @Override
        public void onActionBegin(Action state) {
            if (state == ScarabLordActions.ACTION_RAGE){
                animationFactory.play(ScarabLordAnimations.ANIMATION_RAGE);
            }
            if (state == ScarabLordActions.ACTION_MOVEMENT_DOWN){
                animationFactory.play(ScarabLordAnimations.ANIMATION_IDLE_DOWN);
            }else
            if (state == ScarabLordActions.ACTION_MOVEMENT_FLY){
                animationFactory.play(ScarabLordAnimations.ANIMATION_FLY_IDLE);
            }
            if (state == ScarabLordActions.ACTION_SPAWN){
                level.playSound(null, blockPosition(), DMSounds.DESERT_WORM_DIG.get(), SoundSource.HOSTILE, 1,RandomUtils.randomFloat(0.6f,0.8f));
            }
        }

        @Override
        public void onActionEnd(Action state) {
            if (state == ScarabLordActions.ACTION_RAGE){
                animationFactory.stop(ScarabLordAnimations.ANIMATION_RAGE);
                movementController.playAction(ScarabLordActions.ACTION_MOVEMENT_FLY);
                getNavigation().stop();
            }
            if (state == ScarabLordActions.ACTION_STAND_UP){
                movementController.setNoState();
                getNavigation().stop();
            }
            if (state == ScarabLordActions.ACTION_SPAWN){
                movementController.playAction(ScarabLordActions.ACTION_MOVEMENT_DOWN);
                getNavigation().stop();
            }
        }

        @Override
        public void onActionTick(Action state, int ticks) {
            if (state == ScarabLordActions.ACTION_SPAWN){
                playClientEvent(EVENT_SPAWN);
            }
            if (state == ScarabLordActions.ACTION_AMULET){
                if (ticks % 5 == 0){
                    heal(2f);
                }
            }
            if (state == ScarabLordActions.ACTION_LIFE_STEAL){
                level.playSound(null, blockPosition(), DMSounds.SCARAB_LORD_LIFE_STEAL.get(), SoundSource.HOSTILE, 0.5f,RandomUtils.randomFloat(0.9f,1.1f));
            }
            if (state == ScarabLordActions.ACTION_RAGE && ticks == 23){
                playClientEvent(EVENT_RAGE);
                for (int i = 0;i<RandomUtils.randomInt(2,4);i++) {
                    spawnAtLocation(DMItems.CLOTH.get());
                }
                Entity target = getTarget();
                for (ServerPlayer entity : EntityUtils.getEntities(ServerPlayer.class, level, blockPosition(), 10)) {
                    PlayerGFXEffectManager.addEffect(entity,new GFXRoughShake(0.5f,140),false);
                }
                for (EntityScarab entityScarab : EntityUtils.getEntities(EntityScarab.class, level, blockPosition(), 10)) {
                    entityScarab.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,400,2,true,true));
                    entityScarab.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,400,2,true,true));
                    entityScarab.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,400,2,true,true));
                    if (target != null) {
                        Vector3 dir = Vector3.from(entityScarab.position()).direction(Vector3.from(target.position()));
                        entityScarab.push(dir.x, 0.7f, dir.z);
                    }
                }
                for (EntityMummy entityMummy : EntityUtils.getEntities(EntityMummy.class, level, blockPosition(), 10)) {
                    entityMummy.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,400,2,true,true));
                    entityMummy.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,400,2,true,true));
                    entityMummy.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,400,2,true,true));
                }
                addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,300,2));
                for (float i = 0;i<1;i+=0.25f) {
                    playSound(DMSounds.SCARAB_LORD_SCREAM.get(),i+0.25f,RandomUtils.randomFloat(0.9f,1.1f)*i+0.25f);
                    level.playSound(null, blockPosition(), SoundEvents.WOOL_BREAK, SoundSource.HOSTILE, i, 1f+i);
                }
            }
        }
    };
    private final ActionController<EntityScarabLord> movementController = new ActionController<>(this,"moveController",ScarabLordActions.getMovementActions());

    public ActionStateMachine stateMachine = new ActionStateMachine(actionController,movementController);

    public ClientSideValue flyingOffset = new ClientSideValue(0,0.3f);

    public EntityScarabLord(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        moveControl = new ScarabLordMoveControl(this);
        setPersistenceRequired();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        STADIUM.register(this);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, @org.jetbrains.annotations.Nullable SpawnGroupData p_21437_, @org.jetbrains.annotations.Nullable CompoundTag p_21438_) {
        actionController.playAction(ScarabLordActions.ACTION_SPAWN);
        for (ServerPlayer entity : EntityUtils.getEntities(ServerPlayer.class, level, blockPosition(), 10)) {
            PlayerGFXEffectManager.addEffect(entity,new GFXRoughShake(0.25f,140),false);
        }
        return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_31474_) {
        super.readAdditionalSaveData(p_31474_);
        STADIUM.load(this,p_31474_);
        if (this.hasCustomName()) {
            this.bossHealthbar.setName(this.getDisplayName());
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag p_21484_) {
        super.addAdditionalSaveData(p_21484_);
        STADIUM.save(this,p_21484_);
    }

    @Override
    public void setCustomName(@Nullable Component p_31476_) {
        super.setCustomName(p_31476_);
        this.bossHealthbar.setName(this.getDisplayName());
    }

    @Override
    protected void customServerAiStep() {
        float healthPercent = getHealth()/getMaxHealth();
        healthTick(healthPercent);
        this.bossHealthbar.setProgress(healthPercent);
        super.customServerAiStep();
    }

    @Override
    public void startSeenByPlayer(ServerPlayer p_31483_) {
        super.startSeenByPlayer(p_31483_);
        this.bossHealthbar.addPlayer(p_31483_);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer p_31488_) {
        super.stopSeenByPlayer(p_31488_);
        this.bossHealthbar.removePlayer(p_31488_);
    }

    @Override
    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
        if (movementController.is(ScarabLordActions.ACTION_MOVEMENT_RUN,ActionController.NO_ACTION)) {
            playSound(DMSounds.SCARAB_LORD_STEP.get(),0.5f, RandomUtils.randomFloat(0.9f,1.1f));
        }else
        if (movementController.is(ScarabLordActions.ACTION_MOVEMENT_DOWN)){
            playSound(DMSounds.SCARAB_STEP.get(),0.5f,RandomUtils.randomFloat(0.5f,0.6f));
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        flyingOffset.update(0.1f);
        if (ScarabLordActions.isFlying(this)){
            flyingOffset.setTo(1);
        }else{
            flyingOffset.setTo(0);
        }
        if (actionController.is(ScarabLordActions.ACTION_LIFE_STEAL)){
            LivingEntity target = getTarget();
            if (target == null){
                actionController.setNoState();
                return;
            }
            double distance = target.distanceTo(this);
            if (distance < 15) {
                for (int i = 0; i < 8; i++) {
                    int random = RandomUtils.randomInt(10, 20);
                    float p = ((float) (actionController.getTicks() % random)) / random;
                    float d1 = (float) (distance - (distance * p));
                    Vector3 targetPos = Vector3.from(target.position().add(0, 1, 0).add(RandomUtils.randomFloat(-0.5f, 0.5f), RandomUtils.randomFloat(-0.5f, 0.5f), RandomUtils.randomFloat(-0.5f, 0.5f)));
                    Vector3 myPos = Vector3.from(getEyePosition().add(0, 1.4f, 0).add(RandomUtils.randomFloat(-0.5f, 0.5f), RandomUtils.randomFloat(-0.5f, 0.5f), RandomUtils.randomFloat(-0.5f, 0.5f)));
                    Vector3 direction = targetPos.direction(myPos).mul(d1, d1, d1);
                    Vector3 pos = targetPos.add(direction);
                    playClientEvent(EVENT_LIFE_STEAL, new PacketArgument(PacketArgument.ArgumentType.VEC3, pos.toVec3()));
                }
                if (tickCount % 50 == 0){
                    if (getTarget() instanceof Player){
                        Player player = (Player) getTarget();
                        cursePlayer(player);
                    }
                }
                if (tickCount % 5 == 0){
                    heal(1.5f);
                    target.addEffect(new MobEffectInstance(MobEffects.WITHER,40,3,true,false));
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,40,2,true,false));
                }
                getLookControl().setLookAt(target);
            }else{
                actionController.setNoState();
            }
        }
        float ticks = (float)tickCount;
        if (movementController.is(ScarabLordActions.ACTION_MOVEMENT_WHIRL)){
            ParticleEmitter emitter = new ParticleEmitter(ParticleTypes.SWEEP_ATTACK).count(2).size(new Vector3(0.5f, 0.5f, 0.5f));
            emitter.emit(level, Vector3.from(position().add(0,0.5f,0)).add(MathUtils.cos(ticks / 2f) * 1.5f, 1f, MathUtils.sin(ticks / 2f) * 1.5f), new Vector3(0, 0, 0));
            if (tickCount % 5 == 0) {
                level.playSound(null, blockPosition(), DMSounds.SCIMITAR_WHIRL.get(), SoundSource.HOSTILE, 1, RandomUtils.randomFloat(0.9f,1.1f));
                level.playSound(null, blockPosition(), DMSounds.SCIMITAR_WHIRL.get(), SoundSource.HOSTILE, 0.9f, RandomUtils.randomFloat(0.7f,0.9f));
                level.playSound(null, blockPosition(), DMSounds.SCIMITAR_WHIRL.get(), SoundSource.HOSTILE, 0.5f, RandomUtils.randomFloat(0.5f,1.6f));
            }
            if (tickCount % 5 == 0) {
                for (LivingEntity entity : EntityUtils.getEntities(LivingEntity.class, level, blockPosition(), 2, EntityPredicates.exclude(this).and((entity) -> entity.getType() != DMEntities.SCARAB.get()))) {
                    EntityUtils.damageEntity(this, entity);
                }
            }
        }
        if (movementController.is(ScarabLordActions.ACTION_MOVEMENT_DOWN) && EntityUtils.isMoving(this,-0.15f,0.15f)){
            if (tickCount % 5 == 0) {
                for (LivingEntity entity : EntityUtils.getEntities(LivingEntity.class, level, blockPosition(), 2, EntityPredicates.exclude(this).and((entity) -> entity.getType() != DMEntities.SCARAB.get()))) {
                    EntityUtils.damageEntity(this, entity);
                }
            }
        }
        if (movementController.is(ScarabLordActions.ACTION_MOVEMENT_FLY)){
            level.playSound(null,blockPosition(), DMSounds.SCARAB_FLY.get(), SoundSource.HOSTILE,0.1f,RandomUtils.randomFloat(0.5f,0.8f));
        }
        if (actionController.is(ScarabLordActions.ACTION_AMULET)){
            ParticleEmitter emitter = new ParticleEmitter(new GlowingDustParticleOptions(new Vector3f(190/255f,112/255f,228f/255f),0.5f)).size(new Vector3(0.2f,0.2f,0.2f));
            Vec3 viewVec = getViewVector(0);
            emitter.emit(level, Vector3.from(position()).add((float)viewVec.x,2f,(float)viewVec.z),new Vector3(0,0,0));
        }
        ScarabLordAnimations.idleAnimation(this);
    }

    private void healthTick(float percent){
        int stadium = STADIUM.get(this);
        if (stadium == 0){
            if (percent <= 0.75f){
                movementController.setNoState();
                STADIUM.set(this,1);
                actionController.playAction(ScarabLordActions.ACTION_STAND_UP);
            }else{
                if (tickCount % 50 == 0){
                    spawnMob(DMEntities.SCARAB.get(),EntityScarab.class,10,20,3);
                }
            }
        }else
        if (stadium == 1){
            if (percent <= 0.4f){
                movementController.setNoState();
                STADIUM.set(this,2);
                actionController.playAction(ScarabLordActions.ACTION_RAGE);
            }else{
                if (!ScarabLordAnimations.isAttacking(this) && RandomUtils.doWithChance(70) && tickCount % 200 == 0 && EntityUtils.hasTarget(this)){
                    movementController.playAction(ScarabLordActions.ACTION_MOVEMENT_WHIRL,RandomUtils.randomInt(100,200));
                }
            }
        }else
        if (stadium == 2){
            if (tickCount % 100 == 0){
                spawnMob(DMEntities.MUMMY.get(),EntityMummy.class,10,10,3);
            }
            if (tickCount % 50 == 0){
                spawnMob(DMEntities.SCARAB.get(),EntityScarab.class,10,4,3);
            }
            if (movementController.is(ScarabLordActions.ACTION_MOVEMENT_FLY)) {
                if (tickCount % 100 == 0 && RandomUtils.doWithChance(50)) {
                    if (RandomUtils.doWithChance(50)) {
                        actionController.playAction(ScarabLordActions.ACTION_AMULET, RandomUtils.randomInt(100, 200));
                    }else{
                        if (EntityUtils.hasTarget(this)) {
                            actionController.playAction(ScarabLordActions.ACTION_LIFE_STEAL, RandomUtils.randomInt(100, 200));
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        for (int i = 0;i<4;i++){
            ItemStack stack = ENCHANTMENTS.enchant(DMItems.SCIMITAR.get().getDefaultInstance(),5);
            stack.setDamageValue((int) (stack.getMaxDamage()*RandomUtils.randomFloat(0.3f,0.5f)));
            spawnAtLocation(stack);
        }
        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
    }

    private void cursePlayer(Player player){
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(DMItems.SHEMAGH.get())){
            return;
        }
        for (int i = 0; i < player.getInventory().items.size(); i++) {
            ItemStack stack = player.getInventory().getItem(i);

            if (random.nextInt(5) == 0) {
                if (ItemUtils.isFood(stack) && stack.getItem() != Items.ROTTEN_FLESH) {
                    player.getInventory().setItem(i, new ItemStack(Items.ROTTEN_FLESH, stack.getCount()));
                }
                if (stack.getItem() instanceof PotionItem){
                    PotionUtils.setPotion(stack, Potions.POISON);
                }
            }

            if (random.nextInt(10) == 0) {
                if (stack.getItem() instanceof Vanishable || stack.getItem() instanceof TieredItem) {
                    if (stack.getItem() instanceof ArmorItem) {
                        if (stack.getEnchantmentLevel(Enchantments.BINDING_CURSE) == 0) {
                            stack.enchant(Enchantments.BINDING_CURSE, 1);
                        }
                    }
                    if (stack.getEnchantmentLevel(Enchantments.VANISHING_CURSE) == 0) {
                        stack.enchant(Enchantments.VANISHING_CURSE, 1);
                    }
                }
            }
        }
    }


    private <T extends Mob> void spawnMob(EntityType<T> type,Class<T> mobClass,int attempts,int maxCount,int radius){
        int count = EntityUtils.getEntities(mobClass,level,blockPosition(),32).size();
        if (count < maxCount) {
            T mob = type.create(level);
            BlockPos pos = new BlockPos(getX()+RandomUtils.randomFloat(-radius,radius+1), getY(), getZ()+RandomUtils.randomFloat(-radius,radius+1));
            for (int i = 0;i<attempts;i++) {
                if (!level.getBlockState(pos).isSolidRender(level, pos) && !level.getBlockState(pos).isSolidRender(level, pos.above())) {
                    mob.setPos(pos.getX(), pos.getY(), pos.getZ());
                    level.addFreshEntity(mob);
                    playClientEvent(EVENT_SPAWN_MOB,PacketArgument.create(PacketArgument.ArgumentType.VEC3,new Vec3(pos.getX(),pos.getY(),pos.getZ())));
                    break;
                }
            }
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new ScarabLordMeleeAttackGoal(this,false));
        this.goalSelector.addGoal(3, new ScarabLordRandomWalkGoal(this));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 12.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this){
            @Override
            public boolean canUse() {
                return super.canUse() && !(actionController.is(ScarabLordActions.ACTION_LIFE_STEAL,ScarabLordActions.ACTION_AMULET));
            }
        });
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Villager.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, false));
    }

    @Override
    public void onHandleClientEvent(int event, PacketArgument[] arguments) {
        if (event == EVENT_RAGE){
            for (int i = 0;i<10;i++) {
                ParticleEmitter emitter = new ParticleEmitter(ParticleEmitter.item(DMBlocks.LOESS.get().asItem().getDefaultInstance())).count(8).size(new Vector3(1f, 2.25f, 1f));
                float speedX = RandomUtils.randomFloat(-0.2f, 0.2f);
                float speedY = RandomUtils.randomFloat(0.2f,0.3f);
                float speedZ = RandomUtils.randomFloat(-0.2f, 0.2f);
                emitter.emit(level, Vector3.from(position()), new Vector3(speedX,speedY,speedZ));
            }
        }
        if (event == EVENT_SWORD_ATTACK){
            ParticleEmitter emitter = new ParticleEmitter(ParticleTypes.SWEEP_ATTACK).count(2).size(new Vector3(0.5f,0.5f,0.5f));
            Vec3 viewVec = getViewVector(0).multiply(2,1,2);
            emitter.emit(level, Vector3.from(position()).add((float)viewVec.x,1f,(float)viewVec.z),new Vector3(0,0,0));
        }
        if (event == EVENT_LIFE_STEAL){
            Vector3 pos = Vector3.from(arguments[0].asVec3());
            ParticleEmitter lifeStealEmitter = new ParticleEmitter(new GlowingDustParticleOptions(Color.BLACK.toVector3f(), RandomUtils.randomFloat(0.25f,2f))).count(1);
            lifeStealEmitter.emit(level, pos, new Vector3(0, 0, 0));
        }
        if (event == EVENT_SPAWN){
            float maxHeight = 1;
            float progress = MathUtils.progressOfTime(actionController.getTicks(),actionController.getActionState().getLength());
            ParticleEmitter groundParticle = new ParticleEmitter(ParticleEmitter.block(getBlockStateOn())).size(new Vector3(1.5f+RandomUtils.randomFloat(0.25f,0.5f), maxHeight*progress, 1.5f+RandomUtils.randomFloat(0.25f,0.5f))).count(128);
            groundParticle.emit(level, Vector3.from(position()), new Vector3(RandomUtils.randomFloat(-1,1), maxHeight, RandomUtils.randomFloat(-1,1)));
        }
        if (event == EVENT_SPAWN_MOB){
            ParticleEmitter emitter = new ParticleEmitter(ParticleTypes.CLOUD).count(20).size(new Vector3(1, 1, 1));
            emitter.emit(level, Vector3.from(arguments[0].asVec3()), new Vector3(0, 0, 0));
        }
    }

    @Override
    public boolean hurt(DamageSource source, float damage) {
        if (source == DamageSource.MAGIC || source == DamageSource.LAVA || source == DamageSource.IN_WALL || source == DamageSource.FALL || source == DamageSource.CRAMMING || source == DamageSource.CACTUS){
            return false;
        }
        if (actionController.is(ScarabLordActions.ACTION_AMULET)){
            if (RandomUtils.doWithChance(50)){
                actionController.setNoState();
            }
        }
        if (source.getEntity() != null){
            if (source.getEntity().getType() == EntityType.WARDEN || source.getEntity().getType() == EntityType.IRON_GOLEM){
                damage = damage*0.25f;
            }
            forceAttackTaunt(source.getEntity());
        }
        return super.hurt(source, damage);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        ScarabLordAnimations.attackAnimation(this);
        forceAttackTaunt(entity);
        return false;
    }

    public void forceAttackTaunt(Entity entity){
        if (entity != null) {
            if (entity instanceof Player) {
                if (!EntityUtils.canBeTargeted((Player) entity)) {
                    return;
                }
            }
            if (entity instanceof LivingEntity) {
                for (EntityScarab entityScarab : EntityUtils.getEntities(EntityScarab.class, level, blockPosition(), 10)) {
                    entityScarab.setTarget((LivingEntity) entity);
                }
                for (EntityMummy entityMummy : EntityUtils.getEntities(EntityMummy.class, level, blockPosition(), 10)) {
                    entityMummy.setTarget((LivingEntity) entity);
                }
            }
        }
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
    }

    @Override
    public boolean causeFallDamage(float p_148750_, float p_148751_, DamageSource p_148752_) {
        return false;
    }

    @Override
    protected void checkFallDamage(double p_27754_, boolean p_27755_, BlockState p_27756_, BlockPos p_27757_) {
    }

    @Override
    public float getStepHeight() {
        return 1f;
    }

    @Override
    public void playAmbientSound() {
        if (tickCount > 80) {
            this.playSound(getAmbientSound(), 1, this.getVoicePitch());
            this.playSound(getAmbientSound(), 0.5f, this.getVoicePitch()*1.25f);
            this.playSound(getAmbientSound(), 0.25f, this.getVoicePitch()*0.75f);
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return DMSounds.SCARAB_LORD_IDLE.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return DMSounds.SCARAB_LORD_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return DMSounds.SCARAB_LORD_DEATH.get();
    }

    public ActionController<EntityScarabLord> getActionController() {
        return actionController;
    }

    public ActionController<EntityScarabLord> getMovementController() {
        return movementController;
    }

    @Override
    public <K extends IDisplayArgument> AnimationFactory getAnimationFactory(K argument) {
        return animationFactory;
    }

    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    public ActionStateMachine getActionStateMachine() {
        return stateMachine;
    }
}
