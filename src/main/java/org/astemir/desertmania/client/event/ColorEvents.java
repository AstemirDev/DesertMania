package org.astemir.desertmania.client.event;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.DustParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.client.particle.ParticleGlowingDust;
import org.astemir.desertmania.client.render.entity.boat.RendererDMBoat;
import org.astemir.desertmania.common.block.DMBlocks;
import org.astemir.desertmania.common.entity.boat.DMBoatType;
import org.astemir.desertmania.common.item.DMItems;
import org.astemir.desertmania.common.particle.DMParticles;

import java.awt.*;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = DesertMania.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ColorEvents {

    @SubscribeEvent
    public static void onRegisterParticle(RegisterParticleProvidersEvent e){
        e.register(DMParticles.GLOWING_DUST.get(), ParticleGlowingDust.Provider::new);
    }

    @SubscribeEvent
    public static void onRegisterItemColor(RegisterColorHandlersEvent.Item e){
        e.register((p_92672_, p_92673_) -> new Color(144, 161, 2, 255).hashCode(), DMItems.OASIS_GRASS_BLOCK.get());
        e.register((p_92672_, p_92673_) -> new Color(144, 161, 2, 255).hashCode(), DMItems.OASIS_TALL_GRASS.get());
        e.register((p_92672_, p_92673_) -> new Color(144, 161, 2, 255).hashCode(), DMItems.OASIS_GRASS.get());
        e.register((p_92672_, p_92673_) -> new Color(144, 161, 2, 255).hashCode(), DMItems.SHORT_DESERT_GRASS.get());

        e.register((p_92672_, p_92673_) -> new Color(122, 168, 12, 255).hashCode(), DMItems.SAND_GRASS.get());

        e.register((p_92672_, p_92673_) -> new Color(116, 140, 28, 255).hashCode(), DMItems.PALM_LEAVES.get());
    }

    @SubscribeEvent
    public static void onRegisterBlockColor(RegisterColorHandlersEvent.Block e){
        e.register((p_92567_, p_92568_, p_92569_, p_92570_) -> new Color(144, 161, 2, 255).hashCode(), DMBlocks.OASIS_GRASS_BLOCK.get());
        e.register((p_92567_, p_92568_, p_92569_, p_92570_) -> new Color(144, 161, 2, 255).hashCode(), DMBlocks.OASIS_TALL_GRASS.get());
        e.register((p_92567_, p_92568_, p_92569_, p_92570_) -> new Color(144, 161, 2, 255).hashCode(), DMBlocks.OASIS_GRASS.get());
        e.register((p_92567_, p_92568_, p_92569_, p_92570_) -> new Color(144, 161, 2, 255).hashCode(), DMBlocks.SHORT_DESERT_GRASS.get());

        e.register((p_92567_, p_92568_, p_92569_, p_92570_) -> new Color(122, 168, 12, 255).hashCode(), DMBlocks.SAND_GRASS.get());

        e.register((p_92567_, p_92568_, p_92569_, p_92570_) -> new Color(116, 140, 28, 255).hashCode(), DMBlocks.PALM_LEAVES.get());
    }

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions e){
        LayerDefinition layerdefinition18 = BoatModel.createBodyModel(false);
        LayerDefinition layerdefinition19 = BoatModel.createBodyModel(true);
        for(DMBoatType type : DMBoatType.values()) {
            e.registerLayerDefinition(RendererDMBoat.createBoatModelName(type),()->layerdefinition18);
            e.registerLayerDefinition(RendererDMBoat.createChestBoatModelName(type),()->layerdefinition19);
        }
    }

}
