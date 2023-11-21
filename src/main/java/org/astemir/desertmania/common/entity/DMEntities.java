package org.astemir.desertmania.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.astemir.api.common.entity.EntityProperties;
import org.astemir.api.common.register.EntityRegistry;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.block.DMBlocks;
import org.astemir.desertmania.common.entity.boat.EntityDMBoat;
import org.astemir.desertmania.common.entity.boat.EntityDMChestBoat;
import org.astemir.desertmania.common.entity.camel.EntityCamel;
import org.astemir.desertmania.common.entity.desertworm.EntityDesertWorm;
import org.astemir.desertmania.common.entity.fenick.EntityFenickFakir;
import org.astemir.desertmania.common.entity.fenick.EntityFenick;
import org.astemir.desertmania.common.entity.fenick.EntityFenickMage;
import org.astemir.desertmania.common.entity.genie.EntityBlueGenie;
import org.astemir.desertmania.common.entity.genie.EntityPurpleGenie;
import org.astemir.desertmania.common.entity.genie.EntityRedGenie;
import org.astemir.desertmania.common.entity.genie.misc.EntityBoxGlove;
import org.astemir.desertmania.common.entity.genie.misc.EntityCloud;
import org.astemir.desertmania.common.entity.genie.misc.EntityGenieCharge;
import org.astemir.desertmania.common.entity.genie.EntityGreenGenie;
import org.astemir.desertmania.common.entity.genie.misc.EntityPolymorph;
import org.astemir.desertmania.common.entity.scarab.EntityGoldenScarab;
import org.astemir.desertmania.common.entity.scarab.EntityScarab;
import org.astemir.desertmania.common.entity.scarablord.EntityScarabLord;
import org.astemir.desertmania.common.world.DMWorldData;

public class DMEntities extends EntityRegistry {

    public static DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DesertMania.MOD_ID);

    public static RegistryObject<EntityType<EntityMeerkat>> MEERKAT = register(ENTITIES,"meerkat",new EntityProperties(EntityMeerkat::new,MobCategory.CREATURE,1,1,()-> Animal.createMobAttributes().add(Attributes.MAX_HEALTH,10).add(Attributes.MOVEMENT_SPEED,0.3f).build()).placement(new EntityProperties.SpawnPlacement(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new SpawnPlacements.SpawnPredicate() {
        @Override
        public boolean test(EntityType pEntityType, ServerLevelAccessor pServerLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
            return pRandom.nextInt(40) == 0;
        }
    })));
    public static RegistryObject<EntityType<EntityCamel>> CAMEL = register(ENTITIES,"camel",new EntityProperties(EntityCamel::new,MobCategory.CREATURE,1.5f,2,()-> Animal.createMobAttributes().add(Attributes.MAX_HEALTH,20).add(Attributes.MOVEMENT_SPEED,0.275f).add(Attributes.JUMP_STRENGTH,0.5f).build()).placement(new EntityProperties.SpawnPlacement(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new SpawnPlacements.SpawnPredicate() {
        @Override
        public boolean test(EntityType pEntityType, ServerLevelAccessor pServerLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
            return pRandom.nextInt(10) == 0;
        }
    })));
    public static RegistryObject<EntityType<EntityDesertWorm>> DESERT_WORM = register(ENTITIES,"desert_worm",new EntityProperties(EntityDesertWorm::new,MobCategory.MONSTER,2,2.5f,()-> Animal.createMobAttributes().add(Attributes.MAX_HEALTH,50).add(Attributes.MOVEMENT_SPEED,0.5f).add(Attributes.ATTACK_DAMAGE,4f).add(Attributes.ATTACK_KNOCKBACK).add(Attributes.KNOCKBACK_RESISTANCE,1).add(Attributes.FOLLOW_RANGE,48).build()).placement(new EntityProperties.SpawnPlacement(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new SpawnPlacements.SpawnPredicate() {
        @Override
        public boolean test(EntityType pEntityType, ServerLevelAccessor pServerLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
            if (!(pServerLevel.getBlockState(pPos.below()).is(DMBlocks.DUNE_SAND.get()) || pServerLevel.getBlockState(pPos.below()).is(Blocks.SAND))){
                return false;
            }
            return pRandom.nextInt(100) == 0 || DMWorldData.getInstance(pServerLevel.getLevel()).getSandstorm().isEnabled();
        }
    })));
    public static RegistryObject<EntityType<EntityScarab>> SCARAB = register(ENTITIES,"scarab",new EntityProperties(EntityScarab::new,MobCategory.MONSTER,0.5f,0.5f,()-> Monster.createMobAttributes().add(Attributes.MAX_HEALTH,10).add(Attributes.MOVEMENT_SPEED,0.45f).add(Attributes.FLYING_SPEED,0.75f).add(Attributes.ATTACK_DAMAGE,2f).add(Attributes.ATTACK_KNOCKBACK).add(Attributes.FOLLOW_RANGE,24).build()).placement(new EntityProperties.SpawnPlacement(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new SpawnPlacements.SpawnPredicate() {
        @Override
        public boolean test(EntityType pEntityType, ServerLevelAccessor pServerLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
            return DMWorldData.getInstance(pServerLevel.getLevel()).getSandstorm().isEnabled();
        }
    })));
    public static RegistryObject<EntityType<EntityFenick>> FENICK = register(ENTITIES,"fenick",new EntityProperties(EntityFenick::new,MobCategory.MONSTER,0.6f,1.375f,()-> Monster.createMobAttributes().add(Attributes.MAX_HEALTH,25).add(Attributes.MOVEMENT_SPEED,0.5f).add(Attributes.ATTACK_DAMAGE,3f).add(Attributes.ATTACK_KNOCKBACK).add(Attributes.FOLLOW_RANGE,48).build()));
    public static RegistryObject<EntityType<EntityFenickFakir>> FAKIR = register(ENTITIES,"fakir",new EntityProperties(EntityFenickFakir::new,MobCategory.MONSTER,0.6f,1.375f,()-> Monster.createMobAttributes().add(Attributes.MAX_HEALTH,40).add(Attributes.MOVEMENT_SPEED,0.25f).add(Attributes.ATTACK_DAMAGE,3f).add(Attributes.ATTACK_KNOCKBACK).add(Attributes.FOLLOW_RANGE,48).build()));
    public static RegistryObject<EntityType<EntityFenickMage>> FENICK_MAGE = register(ENTITIES,"fenick_mage",new EntityProperties(EntityFenickMage::new,MobCategory.CREATURE,2f,2f,()-> PathfinderMob.createMobAttributes().add(Attributes.MAX_HEALTH,40).add(Attributes.MOVEMENT_SPEED,0.5f).build()));
    public static RegistryObject<EntityType<EntityMummy>> MUMMY = register(ENTITIES,"mummy",new EntityProperties(EntityMummy::new,MobCategory.MONSTER,0.6f,1.55f,()-> Zombie.createMobAttributes().add(Attributes.MAX_HEALTH,25).add(Attributes.MOVEMENT_SPEED,0.5f).add(Attributes.ATTACK_DAMAGE,3f).add(Attributes.ATTACK_KNOCKBACK).add(Attributes.FOLLOW_RANGE,48).build()));
    public static RegistryObject<EntityType<EntityGoldenScarab>> GOLDEN_SCARAB = register(ENTITIES,"golden_scarab",new EntityProperties(EntityGoldenScarab::new,MobCategory.CREATURE,0.5f,0.5f,()-> Monster.createMobAttributes().add(Attributes.MAX_HEALTH,10).add(Attributes.MOVEMENT_SPEED,0.8f).add(Attributes.FLYING_SPEED,1.25f).add(Attributes.ATTACK_DAMAGE,2f).add(Attributes.ATTACK_KNOCKBACK).add(Attributes.FOLLOW_RANGE,24).build()));
    public static RegistryObject<EntityType<EntityScarabLord>> SCARAB_LORD = register(ENTITIES,"scarab_lord",new EntityProperties(EntityScarabLord::new,MobCategory.MONSTER,1.35f,2.5f,()-> Monster.createMobAttributes().add(Attributes.MAX_HEALTH,300).add(Attributes.MOVEMENT_SPEED,0.5f).add(Attributes.ATTACK_DAMAGE,6f).add(Attributes.ATTACK_KNOCKBACK,0.2f).add(Attributes.ARMOR,15).add(Attributes.KNOCKBACK_RESISTANCE,1).add(Attributes.FOLLOW_RANGE,128).build()));
    public static RegistryObject<EntityType<EntityFlyingCarpet>> FLYING_CARPET = ENTITIES.register("flying_carpet",()->EntityType.Builder.<EntityFlyingCarpet>of(EntityFlyingCarpet::new,MobCategory.MISC).sized(1,0.1f).build("flying_carpet"));
    public static RegistryObject<EntityType<EntityBlueGenie>> BLUE_GENIE = register(ENTITIES,"blue_genie",new EntityProperties(EntityBlueGenie::new,MobCategory.CREATURE,1f,1f,()-> Mob.createMobAttributes().add(Attributes.MAX_HEALTH,100).add(Attributes.MOVEMENT_SPEED,0.5f).add(Attributes.FLYING_SPEED,0.5f).add(Attributes.FLYING_SPEED,0.75f).add(Attributes.ATTACK_DAMAGE,2f).add(Attributes.ATTACK_KNOCKBACK).add(Attributes.FOLLOW_RANGE,24).build()));
    public static RegistryObject<EntityType<EntityGreenGenie>> GREEN_GENIE = register(ENTITIES,"green_genie",new EntityProperties(EntityGreenGenie::new,MobCategory.CREATURE,1f,1f,()-> Mob.createMobAttributes().add(Attributes.MAX_HEALTH,100).add(Attributes.MOVEMENT_SPEED,0.5f).add(Attributes.FLYING_SPEED,0.5f).add(Attributes.FLYING_SPEED,0.75f).add(Attributes.ATTACK_DAMAGE,2f).add(Attributes.ATTACK_KNOCKBACK).add(Attributes.FOLLOW_RANGE,24).build()));
    public static RegistryObject<EntityType<EntityRedGenie>> RED_GENIE = register(ENTITIES,"red_genie",new EntityProperties(EntityRedGenie::new,MobCategory.CREATURE,1f,1f,()-> Mob.createMobAttributes().add(Attributes.MAX_HEALTH,100).add(Attributes.MOVEMENT_SPEED,0.5f).add(Attributes.FLYING_SPEED,0.5f).add(Attributes.FLYING_SPEED,0.75f).add(Attributes.ATTACK_DAMAGE,2f).add(Attributes.ATTACK_KNOCKBACK).add(Attributes.FOLLOW_RANGE,24).build()));
    public static RegistryObject<EntityType<EntityPurpleGenie>> PURPLE_GENIE = register(ENTITIES,"purple_genie",new EntityProperties(EntityPurpleGenie::new,MobCategory.CREATURE,1f,1f,()-> Mob.createMobAttributes().add(Attributes.MAX_HEALTH,100).add(Attributes.MOVEMENT_SPEED,0.5f).add(Attributes.FLYING_SPEED,0.5f).add(Attributes.FLYING_SPEED,0.75f).add(Attributes.ATTACK_DAMAGE,2f).add(Attributes.ATTACK_KNOCKBACK).add(Attributes.FOLLOW_RANGE,24).build()));
    public static RegistryObject<EntityType<EntityPolymorph>> POLYMORPH = register(ENTITIES,"polymorph",new EntityProperties(EntityPolymorph::new,MobCategory.CREATURE,1f,1f,()-> Mob.createMobAttributes().add(Attributes.MAX_HEALTH,5).add(Attributes.MOVEMENT_SPEED,0.3f).add(Attributes.ATTACK_KNOCKBACK).add(Attributes.FOLLOW_RANGE,24).build()).noSummon());
    public static RegistryObject<EntityType<EntityScorpion>> SCORPION = register(ENTITIES,"scorpion",new EntityProperties(EntityScorpion::new,MobCategory.MONSTER,1.4f,0.9f,()-> Mob.createMobAttributes().add(Attributes.MAX_HEALTH,30).add(Attributes.MOVEMENT_SPEED,0.4f).add(Attributes.ATTACK_DAMAGE,2f).add(Attributes.ATTACK_KNOCKBACK).add(Attributes.FOLLOW_RANGE,24).build()).placement(new EntityProperties.SpawnPlacement(SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new SpawnPlacements.SpawnPredicate() {
        @Override
        public boolean test(EntityType pEntityType, ServerLevelAccessor pServerLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
            return pRandom.nextInt(10) == 0;
        }
    })));
    public static RegistryObject<EntityType<EntityCactupine>> CACTUPINE = register(ENTITIES,"cactupine",new EntityProperties(EntityCactupine::new,MobCategory.CREATURE,1.25f,0.9f,()-> Mob.createMobAttributes().add(Attributes.MAX_HEALTH,20).add(Attributes.MOVEMENT_SPEED,0.5f).add(Attributes.ATTACK_DAMAGE,2f).add(Attributes.ATTACK_KNOCKBACK).add(Attributes.FOLLOW_RANGE,24).build()));
    public static RegistryObject<EntityType<EntityDMBoat>> BOAT = ENTITIES.register("boat", ()->EntityType.Builder.<EntityDMBoat>of(EntityDMBoat::new, MobCategory.MISC).setCustomClientFactory(EntityDMBoat::new).sized(1.375F, 0.5625F).clientTrackingRange(10).noSummon().build("boat"));
    public static RegistryObject<EntityType<EntityDMChestBoat>> CHEST_BOAT = ENTITIES.register("chest_boat", ()->EntityType.Builder.<EntityDMChestBoat>of(EntityDMChestBoat::new, MobCategory.MISC).setCustomClientFactory(EntityDMChestBoat::new).sized(1.375F, 0.5625F).clientTrackingRange(10).noSummon().build("chest_boat"));
    public static RegistryObject<EntityType<EntityGenieCharge>> GENIE_CHARGE = ENTITIES.register("genie_charge", ()->EntityType.Builder.<EntityGenieCharge>of(EntityGenieCharge::new, MobCategory.MISC).sized(0.3125F, 0.3125F).clientTrackingRange(8).build("genie_charge"));
    public static RegistryObject<EntityType<EntityCloud>> CLOUD = ENTITIES.register("cloud", ()->EntityType.Builder.of(EntityCloud::new, MobCategory.MISC).sized(1f, 0.8f).clientTrackingRange(8).build("cloud"));
    public static RegistryObject<EntityType<EntityBoxGlove>> BOX_GLOVE = ENTITIES.register("box_glove", ()->EntityType.Builder.of(EntityBoxGlove::new, MobCategory.MISC).sized(1.25f, 1.25f).clientTrackingRange(8).build("box_glove"));
    public static RegistryObject<EntityType<EntityStingArrow>> STING_ARROW = ENTITIES.register("sting_arrow", ()->EntityType.Builder.<EntityStingArrow>of(EntityStingArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("sting_arrow"));


    public static <T extends Entity> RegistryObject<EntityType<T>> register(DeferredRegister<EntityType<?>> entities, String id, EntityProperties properties){
        return register(entities,DesertMania.MOD_ID,id,properties);
    }
}
