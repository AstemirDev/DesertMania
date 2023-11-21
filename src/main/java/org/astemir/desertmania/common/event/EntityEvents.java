package org.astemir.desertmania.common.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.common.misc.ParticleEmitter;
import org.astemir.api.math.components.Vector3;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.desertmania.common.block.DMBlocks;
import org.astemir.desertmania.common.blockentity.BlockEntityMagicBasket;
import org.astemir.desertmania.common.entity.DMEntities;
import org.astemir.desertmania.common.entity.desertworm.EntityDesertWorm;
import org.astemir.desertmania.common.entity.camel.EntityCamel;
import org.astemir.desertmania.common.entity.fenick.EntityAbstractFenick;
import org.astemir.desertmania.common.entity.genie.EntityAbstractGenie;
import org.astemir.desertmania.common.entity.genie.misc.EntityCloud;
import org.astemir.desertmania.common.entity.genie.misc.GenieColors;
import org.astemir.desertmania.common.entity.scarab.EntityGoldenScarab;
import org.astemir.desertmania.common.item.DMItems;
import org.astemir.desertmania.common.misc.IDMPlayer;
import org.astemir.desertmania.common.particle.GlowingDustParticleOptions;
import org.astemir.desertmania.common.sound.DMSounds;
import org.astemir.desertmania.common.utils.MiscUtils;

import java.util.Optional;

public class EntityEvents {


    @SubscribeEvent
    public static void onLivingSwing(LivingSwingEvent e){
        if (e.getLivingEntity() instanceof Player player) {
            ItemStack offhand = player.getItemInHand(InteractionHand.OFF_HAND);
            if (offhand.is(DMItems.SCIMITAR.get())) {
                InteractionHand previous = player.swingingArm != null ? player.swingingArm : InteractionHand.MAIN_HAND;
                if (previous == InteractionHand.MAIN_HAND){
                    e.setHand(InteractionHand.OFF_HAND);
                }else{
                    e.setHand(InteractionHand.MAIN_HAND);
                }
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerJump(LivingEvent.LivingJumpEvent e){
        if (e.getEntity() instanceof Player player){
            EntityCloud cloud = EntityUtils.getEntity(EntityCloud.class,player.level,player.blockPosition(),1);
            if (cloud != null) {
                Level level = e.getEntity().level;
                new ParticleEmitter(new GlowingDustParticleOptions(GenieColors.BLUE.toVector3f(), 1)).count(32).size(new Vector3(0.5f,0.5f,0.5f)).emit(level, Vector3.from(e.getEntity().position()), new Vector3(0, 0, 0));
                cloud.remove(Entity.RemovalReason.DISCARDED);
                player.setDeltaMovement(player.getDeltaMovement().x, 0.7f, player.getDeltaMovement().z);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityAttack(LivingAttackEvent e){
        Entity damager = e.getSource().getDirectEntity();
        if (e.getSource().getEntity() != null) {
            if (e.getEntity() instanceof Player) {
                Player player = (Player) e.getEntity();
                if (!IDMPlayer.asIDMPlayer(player).isSpinningWithScimitar() && player.getUseItem().getUseDuration()-player.getUseItemRemainingTicks() > 5) {
                    if (MiscUtils.isDoubleBlockingWithScimitar(player)) {
                        if (damager != null) {
                            damager.hurt(DamageSource.mobAttack(player), e.getAmount() * 0.5f);
                        }
                        ItemStack a = player.getItemInHand(InteractionHand.MAIN_HAND);
                        ItemStack b = player.getItemInHand(InteractionHand.OFF_HAND);
                        a.hurtAndBreak(2,player,(p)->p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                        b.hurtAndBreak(2,player,(p)->p.broadcastBreakEvent(InteractionHand.OFF_HAND));
                        player.getCooldowns().addCooldown(DMItems.SCIMITAR.get(), 140);
                        player.level.playSound(null, player.blockPosition(), DMSounds.SCIMITAR_BLOCK.get(), SoundSource.PLAYERS, 1, RandomUtils.randomFloat(0.9f,1.1f));
                        player.stopUsingItem();
                        e.setCanceled(true);
                    } else if (MiscUtils.isBlockingWithScimitar(player)) {
                        player.getCooldowns().addCooldown(DMItems.SCIMITAR.get(), 80);
                        player.getUseItem().hurtAndBreak(2,player,(p)->p.broadcastBreakEvent(p.getUsedItemHand()));
                        player.level.playSound(null, player.blockPosition(), DMSounds.SCIMITAR_BLOCK.get(), SoundSource.PLAYERS, 1, RandomUtils.randomFloat(0.9f,1.1f));
                        player.level.playSound(null, player.blockPosition(), DMSounds.SCIMITAR_BLOCK.get(), SoundSource.PLAYERS, 0.9f, RandomUtils.randomFloat(0.9f,1.1f));
                        player.stopUsingItem();
                        e.setCanceled(true);
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public static void onEntityDrop(LivingDropsEvent e){
       Entity entity = e.getSource().getEntity();
       if (entity != null) {
           if (e.getEntity().getType() != EntityType.PLAYER) {
               if (entity.getType() == DMEntities.DESERT_WORM.get()) {
                   e.setCanceled(true);
               }
           }
       }
        if (e.getEntity() instanceof Player player) {
            if (!e.getEntity().level.isClientSide) {
                Optional<ItemEntity> optionalItemEntity = e.getDrops().stream().filter((item) -> item.getItem().is(DMItems.MAGIC_BASKET.get())).findFirst();
                if (optionalItemEntity.isPresent()) {
                    NonNullList<ItemStack> items = NonNullList.create();
                    for (ItemEntity drop : e.getDrops()) {
                        if (!drop.getItem().sameItem(optionalItemEntity.get().getItem())) {
                            items.add(drop.getItem());
                        }
                    }
                    BlockPos pos = player.blockPosition();
                    player.level.setBlock(pos, DMBlocks.MAGIC_BASKET.get().defaultBlockState(), 4);
                    BlockEntityMagicBasket magicBasket = new BlockEntityMagicBasket(pos, DMBlocks.MAGIC_BASKET.get().defaultBlockState());
                    player.level.setBlockEntity(magicBasket);
                    magicBasket.setItems(items);
                    e.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityLogin(EntityJoinLevelEvent e){
        if (e.getEntity() instanceof Husk husk){
            husk.targetSelector.addGoal(1,new NearestAttackableTargetGoal(husk,EntityCamel.class,true,(entity)->{
                return !EntityCamel.IS_ZOMBIE.get((Entity) entity);
            }));
        }
        if (e.getEntity() instanceof PathfinderMob mob){
            if (mob instanceof Zombie || mob instanceof EntityAbstractFenick) {
                ((PathfinderMob) e.getEntity()).goalSelector.addGoal(1, new NearestAttackableTargetGoal(mob, EntityGoldenScarab.class, false));
            }
        }
    }

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent e){
        if (e.getEntity() instanceof Player player){
            EntityAbstractGenie genie = EntityUtils.getEntity(EntityAbstractGenie.class,player.level,player.blockPosition(),40);
            if (genie != null){
                player.setHealth(1);
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION,100,1));
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,100,1));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,100,2));
                genie.discard();
                e.setCanceled(true);
            }
        }
        if (e.getSource().getEntity() != null){
            if (e.getSource().getEntity().getType() == DMEntities.DESERT_WORM.get()){
                EntityDesertWorm worm = (EntityDesertWorm) e.getSource().getEntity();
                float newSize = EntityDesertWorm.SCALE.get(worm)+0.1f;
                if (newSize < 3) {
                    EntityDesertWorm.SCALE.set(worm, newSize);
                    worm.updateSize();
                }
            }
        }
        if (e.getEntity() instanceof EntityCamel){
            EntityCamel camel = (EntityCamel) e.getEntity();
            if (!EntityCamel.IS_ZOMBIE.get(camel)) {
                Entity killer = e.getSource().getEntity();
                if (killer != null) {
                    if (killer.getType() == EntityType.HUSK) {
                        Husk husk = ((Husk)killer);
                        camel.setHealth(camel.getMaxHealth());
                        EntityCamel.IS_ZOMBIE.set(camel,true);
                        husk.goalSelector.getRunningGoals().forEach((wrappedGoal) ->{
                            if (wrappedGoal.getGoal() instanceof MeleeAttackGoal){
                                wrappedGoal.stop();
                            }
                        });
                        camel.goalSelector.getRunningGoals().forEach((wrappedGoal) ->{
                            if (wrappedGoal.getGoal() instanceof RangedAttackGoal){
                                wrappedGoal.stop();
                            }
                        });
                        husk.targetSelector.getRunningGoals().forEach((wrappedGoal) -> wrappedGoal.stop());
                        camel.targetSelector.getRunningGoals().forEach((wrappedGoal) -> wrappedGoal.stop());
                        husk.setTarget(null);
                        husk.setAggressive(false);
                        husk.getNavigation().stop();
                        husk.setLastHurtByMob(null);
                        camel.setTarget(null);
                        camel.setAggressive(false);
                        camel.getNavigation().stop();
                        camel.setLastHurtByMob(null);
                        camel.getAnimationFactory().stop(EntityCamel.ANIMATION_ATTACK);
                        e.setCanceled(true);
                    }
                }
            }
        }
    }


}
