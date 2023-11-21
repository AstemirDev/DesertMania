package org.astemir.desertmania.common.blockentity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.block.DMBlocks;

public class DMBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES,DesertMania.MOD_ID);

    public static final RegistryObject<BlockEntityType<BlockEntityGenieLamp>> GENIE_LAMP = BLOCK_ENTITIES.register("genie_lamp",()->BlockEntityType.Builder.of(BlockEntityGenieLamp::new, DMBlocks.GENIE_LAMP.get()).build(null));
    public static final RegistryObject<BlockEntityType<BlockEntityDMSign>> SIGN = BLOCK_ENTITIES.register("sign",()->BlockEntityType.Builder.of(BlockEntityDMSign::new, DMBlocks.PALM_PLANKS_SIGN.get(),DMBlocks.PALM_PLANKS_WALL_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<BlockEntityBasket>> BASKET = BLOCK_ENTITIES.register("basket",()->BlockEntityType.Builder.of(BlockEntityBasket::new, DMBlocks.BASKET.get()).build(null));
    public static final RegistryObject<BlockEntityType<BlockEntityBossSpawn>> BOSS_SPAWN = BLOCK_ENTITIES.register("boss_spawn",()->BlockEntityType.Builder.of(BlockEntityBossSpawn::new, DMBlocks.BOSS_SPAWN.get()).build(null));
    public static final RegistryObject<BlockEntityType<BlockEntityMagicBasket>> MAGIC_BASKET = BLOCK_ENTITIES.register("magic_basket",()->BlockEntityType.Builder.of(BlockEntityMagicBasket::new, DMBlocks.MAGIC_BASKET.get()).build(null));
    public static final RegistryObject<BlockEntityType<BlockEntitySunAltar>> SUN_ALTAR = BLOCK_ENTITIES.register("sun_altar",()->BlockEntityType.Builder.of(BlockEntitySunAltar::new, DMBlocks.SUN_ALTAR.get()).build(null));

}
