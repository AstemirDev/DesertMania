package org.astemir.desertmania;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.astemir.api.IClientLoader;
import org.astemir.api.SkillsForgeMod;
import org.astemir.api.common.event.EventManager;
import org.astemir.api.common.item.ToolActionResult;
import org.astemir.desertmania.client.ClientDesertMania;
import org.astemir.desertmania.common.block.DMBlocks;
import org.astemir.desertmania.common.blockentity.DMBlockEntities;
import org.astemir.desertmania.common.commands.DMCommandsRegisterEvents;
import org.astemir.desertmania.common.effect.DMMobEffects;
import org.astemir.desertmania.common.enchantments.DMEnchantments;
import org.astemir.desertmania.common.entity.DMEntities;
import org.astemir.desertmania.common.entity.boat.DMBoatType;
import org.astemir.desertmania.common.event.AnvilEvents;
import org.astemir.desertmania.common.event.EntityEvents;
import org.astemir.desertmania.common.event.MiscEvents;
import org.astemir.desertmania.common.item.DMItems;
import org.astemir.desertmania.common.misc.DMBoatDispenseItemBehavior;
import org.astemir.desertmania.common.misc.DMRecipeSerializers;
import org.astemir.desertmania.common.misc.DMWoodType;
import org.astemir.desertmania.common.particle.DMParticles;
import org.astemir.desertmania.common.sound.DMSounds;
import org.astemir.desertmania.common.world.generation.features.DMFeatures;
import org.astemir.desertmania.common.world.generation.structures.DMStructures;

import static org.astemir.desertmania.DesertMania.MOD_ID;

@Mod(MOD_ID)
public class DesertMania extends SkillsForgeMod {

    public static final String MOD_ID = "desertmania";
    public static DesertMania INSTANCE;

    public DesertMania() {
        INSTANCE = this;
        EventManager.registerForgeEventClass(DMCommandsRegisterEvents.class);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        DMParticles.PARTICLE_TYPES.register(bus);
        DMSounds.SOUNDS.register(bus);
        DMBlocks.BLOCKS.register(bus);
        DMBlockEntities.BLOCK_ENTITIES.register(bus);
        DMItems.ITEMS.register(bus);
        DMMobEffects.EFFECTS.register(bus);
        DMEnchantments.ENCHANTMENTS.register(bus);
        DMEntities.ENTITIES.register(bus);
        DMFeatures.FEATURES.register(bus);
        DMFeatures.Configured.CONFIGURED_FEATURES.register(bus);
        DMFeatures.Placed.PLACED_FEATURES.register(bus);
        DMStructures.STRUCTURES.register(bus);
        DMStructures.Types.STRUCTURE_TYPES.register(bus);
        DMStructures.PieceTypes.STRUCTURE_PIECE_TYPES.register(bus);
        DMStructures.Sets.STRUCTURE_SETS.register(bus);
        DMRecipeSerializers.RECIPE_SERIALIZERS.register(bus);
    }

    @Override
    protected void onCommonSetup(FMLCommonSetupEvent event) {
        DispenserBlock.registerBehavior(DMItems.PALM_PLANKS_BOAT.get(), new DMBoatDispenseItemBehavior(DMBoatType.PALM));
        DispenserBlock.registerBehavior(DMItems.PALM_PLANKS_CHEST_BOAT.get(), new DMBoatDispenseItemBehavior(DMBoatType.PALM,true));
        EventManager.registerForgeEventClass(EntityEvents.class);
        EventManager.registerForgeEventClass(MiscEvents.class);
        EventManager.registerForgeEventClass(AnvilEvents.class);
        WoodType.register(DMWoodType.PALM);
        ToolActionResult.addVariant(ToolActions.AXE_STRIP,DMBlocks.PALM_WOOD.get(),DMBlocks.PALM_WOOD_STRIPPED.get());
        ToolActionResult.addVariant(ToolActions.AXE_STRIP,DMBlocks.PALM_LOG.get(),DMBlocks.PALM_LOG_STRIPPED.get());
        ToolActionResult.addVariant(ToolActions.HOE_TILL,DMBlocks.OASIS_DIRT.get(),DMBlocks.OASIS_FARMLAND.get());
    }

    @Override
    protected void onClientSetup(FMLClientSetupEvent event) {
        super.onClientSetup(event);
    }

    @Override
    public IClientLoader getClientLoader() {
        return new ClientDesertMania();
    }
}
