package org.astemir.desertmania.common.event;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.common.event.BiomeModifyEvent;
import org.astemir.api.common.world.WorldUtils;
import org.astemir.api.math.components.Vector3;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.desertmania.common.effect.DMMobEffects;
import org.astemir.desertmania.common.effect.MobEffectMummyCurse;
import org.astemir.desertmania.common.enchantments.EnchantmentWhirling;
import org.astemir.desertmania.common.entity.DMEntities;
import org.astemir.desertmania.common.entity.genie.EntityBlueGenie;
import org.astemir.desertmania.common.entity.genie.misc.EntityCloud;
import org.astemir.desertmania.common.entity.scarab.EntityScarab;
import org.astemir.desertmania.common.misc.DMTags;
import org.astemir.desertmania.common.utils.MiscUtils;
import org.astemir.desertmania.common.world.generation.features.DMFeatures;
import org.astemir.desertmania.common.world.DMWorldData;
import org.astemir.desertmania.common.world.SandstormHandler;
import org.astemir.desertmania.common.world.generation.structures.DMStructures;


public class MiscEvents {

    @SubscribeEvent
    public static void onMobSpawn(BiomeModifyEvent e){
        if (e.getBiomeHolder().is(Biomes.DESERT)){
            e.getBuilder().getGenerationSettings().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, DMFeatures.Placed.DESERT_ROCK.getHolder().get());
            e.getBuilder().getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DMFeatures.Placed.DESERT_WEEDS.getHolder().get());
            e.getBuilder().getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DMFeatures.Placed.DESERT_PALM.getHolder().get());
            e.getBuilder().getGenerationSettings().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, DMFeatures.Placed.DESERT_DUNES.getHolder().get());
            e.getBuilder().getMobSpawnSettings().addSpawn(MobCategory.CREATURE,new MobSpawnSettings.SpawnerData(DMEntities.CAMEL.get(),10,1,1));
            e.getBuilder().getMobSpawnSettings().addSpawn(MobCategory.CREATURE,new MobSpawnSettings.SpawnerData(DMEntities.MEERKAT.get(),5,5,10));
            e.getBuilder().getMobSpawnSettings().addSpawn(MobCategory.MONSTER,new MobSpawnSettings.SpawnerData(DMEntities.DESERT_WORM.get(),10,1,1));
            e.getBuilder().getMobSpawnSettings().addSpawn(MobCategory.MONSTER,new MobSpawnSettings.SpawnerData(DMEntities.SCORPION.get(),30,1,2));
            e.getBuilder().getMobSpawnSettings().addSpawn(MobCategory.MONSTER,new MobSpawnSettings.SpawnerData(DMEntities.SCARAB.get(),60,2,4));
        }
    }



    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent e){
        Level level = e.getPlayer().getLevel();
        if (WorldUtils.isAtStructure(level, e.getPos(),DMStructures.Types.SCARAB_TEMPLE.get())){
            if (RandomUtils.doWithChance(e.getPlayer().getRandom(),75)) {
                MiscUtils.showBossTaunt(e.getPlayer());
                e.getPlayer().addEffect(new MobEffectInstance(DMMobEffects.MUMMY_CURSE.get(), 99999, 0, true, true));
                e.getPlayer().addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 200, 4, true, true));
            }
            if (e.getState().is(DMTags.SCARAB_SPAWN)) {
                if (RandomUtils.doWithChance(e.getPlayer().getRandom(), 5)) {
                    EntityScarab scarab = DMEntities.SCARAB.get().create(level);
                    Vec3 pos = Vec3.atBottomCenterOf(e.getPos());
                    scarab.setPos(pos.x,pos.y,pos.z);
                    level.addFreshEntity(scarab);
                }
            }
        }
    }


    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent e){
        if (e.phase == TickEvent.Phase.START){
            if (e.level.isClientSide){
                return;
            }
            DMWorldData worldData = DMWorldData.getInstance(e.level);
            worldData.getSandstorm().update(e.level);
            boolean isSandStorm = worldData.getSandstorm().isEnabled();
            if ((e.level.getGameTime() % 5000 == 0 && e.level.random.nextInt(10) == 0) && e.level.getGameRules().getBoolean(GameRules.RULE_WEATHER_CYCLE) && !isSandStorm){
                worldData.getSandstorm().start(e.level,RandomUtils.randomInt(2000,5000));
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHeal(LivingHealEvent e){
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (player.hasEffect(DMMobEffects.MUMMY_CURSE.get())) {
                e.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent e){
        if (e.phase == TickEvent.Phase.END){
            Player player = e.player;
            Level level = e.player.level;
            EnchantmentWhirling.use(player);
            EntityBlueGenie blueGenie = EntityUtils.getEntity(EntityBlueGenie.class, player.level, player.blockPosition(), 12);
            if (blueGenie != null && blueGenie.getOwner() != null) {
                if (blueGenie.getOwner().getUUID().equals(player.getUUID())) {
                    if (!player.isCreative() && !player.isFallFlying() && !player.isOnGround() && player.tickCount % 20 == 0 && !player.isInWater() && !player.isInLava()) {
                        EntityCloud cloud1 = DMEntities.CLOUD.get().create(player.level);
                        cloud1.moveTo(player.getX(), player.getY() - 1, player.getZ(), 90 - player.getYRot(), 0);
                        player.fallDistance = 0;
                        player.level.addFreshEntity(cloud1);
                    }
                }
            }
            if (player.hasEffect(DMMobEffects.MUMMY_CURSE.get())) {
                MobEffectMummyCurse.tickEffect(player);
            }
            if (!level.isClientSide){
                if (e.player.level.getBiome(e.player.blockPosition()).is(Biomes.DESERT)) {
                    SandstormHandler.sendUpdatePacket(e.player);
                }
            }


            if (!level.isClientSide) {
                DMWorldData worldData = DMWorldData.getInstance(level);
                if (worldData != null) {
                    if (worldData.getSandstorm().isEnabled()) {
                        for (ItemEntity entity : EntityUtils.getEntities(ItemEntity.class, level, player.blockPosition(), 32)) {
                            Vector3 dir = worldData.getSandstorm().getWindDirection();
                            if (SandstormHandler.canAffectEntity(entity,dir,entity.blockPosition(),level)) {
                                entity.hasImpulse = true;
                                entity.push(dir.x * 0.05f, 0.01f, dir.z * 0.05f);
                            }
                        }
                    }
                }
            }
        }
    }

}
