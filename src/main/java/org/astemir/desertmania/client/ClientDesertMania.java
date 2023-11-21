package org.astemir.desertmania.client;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import org.astemir.api.IClientLoader;
import org.astemir.api.client.ModelUtils;
import org.astemir.api.client.registry.ArmorModelsRegistry;
import org.astemir.api.client.render.SkillsRendererItem;
import org.astemir.api.common.event.EventManager;
import org.astemir.api.common.handler.WorldEventHandler;
import org.astemir.desertmania.client.event.ClientEvents;
import org.astemir.desertmania.client.handler.BossClientEventHandler;
import org.astemir.desertmania.client.handler.GenieClientEventHandler;
import org.astemir.desertmania.client.handler.DMLevelClientHandler;
import org.astemir.desertmania.client.render.armor.shemagh.ModelWrapperShemagh;
import org.astemir.desertmania.client.render.block.basket.ModelBasket;
import org.astemir.desertmania.client.render.block.basket.RendererBasket;
import org.astemir.desertmania.client.render.block.genielamp.RendererGenieLamp;
import org.astemir.desertmania.client.render.block.magicbasket.ModelMagicBasket;
import org.astemir.desertmania.client.render.block.magicbasket.RendererMagicBasket;
import org.astemir.desertmania.client.render.block.sunaltar.ModelSunAltar;
import org.astemir.desertmania.client.render.block.sunaltar.RendererSunAltar;
import org.astemir.desertmania.client.render.entity.arrow.RendererStingArrow;
import org.astemir.desertmania.client.render.entity.boat.RendererDMBoat;
import org.astemir.desertmania.client.render.entity.cactupine.RendererCactupine;
import org.astemir.desertmania.client.render.entity.camel.RendererCamel;
import org.astemir.desertmania.client.render.entity.carpet.RendererFlyingCarpet;
import org.astemir.desertmania.client.render.entity.desertworm.RendererDesertWorm;
import org.astemir.desertmania.client.render.entity.fakir.RendererFenickFakir;
import org.astemir.desertmania.client.render.entity.fenick.RendererFenick;
import org.astemir.desertmania.client.render.entity.genie.other.cloud.RendererCloud;
import org.astemir.desertmania.client.render.entity.genie.other.glove.RendererBoxGlove;
import org.astemir.desertmania.client.render.entity.genie.other.polymorph.RendererPolymorph;
import org.astemir.desertmania.client.render.entity.goldenscarab.RendererGoldenScarab;
import org.astemir.desertmania.client.render.entity.mage.RendererFenickMage;
import org.astemir.desertmania.client.render.entity.genie.RendererGenie;
import org.astemir.desertmania.client.render.entity.genie.other.charge.RendererCharge;
import org.astemir.desertmania.client.render.entity.meerkat.RendererMeerkat;
import org.astemir.desertmania.client.render.entity.mummy.RendererMummy;
import org.astemir.desertmania.client.render.entity.scarab.RendererScarab;
import org.astemir.desertmania.client.render.entity.scarablord.RendererScarabLord;
import org.astemir.desertmania.client.render.entity.scorpion.RendererScorpion;
import org.astemir.desertmania.common.blockentity.DMBlockEntities;
import org.astemir.desertmania.common.entity.DMEntities;
import org.astemir.desertmania.common.item.DMItems;
import org.astemir.desertmania.common.misc.DMWoodType;

public class ClientDesertMania implements IClientLoader {

    public static DMLevelClientHandler WORLD_CLIENT_HANDLER = new DMLevelClientHandler();
    public static GenieClientEventHandler GENIE_CLIENT_HANDLER = new GenieClientEventHandler();
    public static BossClientEventHandler BOSS_TAUNT_CLIENT_HANDLER = new BossClientEventHandler();

    @Override
    public void load() {
        loadMisc();
        loadHandlers();
        loadRenderers();
    }

    public void loadMisc(){
        EventManager.registerForgeEventClass(ClientEvents.class);
        Sheets.addWoodType(DMWoodType.PALM);
    }

    public void loadHandlers(){
        WorldEventHandler eventHandler = WorldEventHandler.getInstance();
        eventHandler.addClientListener("desertmania_client_handler",WORLD_CLIENT_HANDLER);
        eventHandler.addClientListener("desertmania_boss_handler",BOSS_TAUNT_CLIENT_HANDLER);
        eventHandler.addClientListener("desertmania_genie_handler",GENIE_CLIENT_HANDLER);
    }


    public void loadRenderers(){
        ArmorModelsRegistry.addModel(DMItems.SHEMAGH.get(),new ModelWrapperShemagh());
        SkillsRendererItem.addModel(DMItems.BASKET.get(), ModelUtils.createItemModel(ModelBasket.MODEL,ModelBasket.TEXTURE));
        SkillsRendererItem.addModel(DMItems.MAGIC_BASKET.get(),ModelUtils.createItemModel(ModelMagicBasket.MODEL,ModelMagicBasket.TEXTURE));
        SkillsRendererItem.addModel(DMItems.SUN_ALTAR.get(),ModelUtils.createItemModel(ModelSunAltar.MODEL,ModelSunAltar.TEXTURE));
        BlockEntityRenderers.register(DMBlockEntities.GENIE_LAMP.get(), RendererGenieLamp::new);
        BlockEntityRenderers.register(DMBlockEntities.BASKET.get(), RendererBasket::new);
        BlockEntityRenderers.register(DMBlockEntities.MAGIC_BASKET.get(), RendererMagicBasket::new);
        BlockEntityRenderers.register(DMBlockEntities.SUN_ALTAR.get(), RendererSunAltar::new);
        BlockEntityRenderers.register(DMBlockEntities.SIGN.get(), SignRenderer::new);
        EntityRenderers.register(DMEntities.BOAT.get(), (context)->new RendererDMBoat(context,false));
        EntityRenderers.register(DMEntities.CHEST_BOAT.get(), (context)->new RendererDMBoat(context,true));
        EntityRenderers.register(DMEntities.MEERKAT.get(), RendererMeerkat::new);
        EntityRenderers.register(DMEntities.CAMEL.get(), RendererCamel::new);
        EntityRenderers.register(DMEntities.DESERT_WORM.get(), RendererDesertWorm::new);
        EntityRenderers.register(DMEntities.MUMMY.get(), RendererMummy::new);
        EntityRenderers.register(DMEntities.SCARAB.get(), RendererScarab::new);
        EntityRenderers.register(DMEntities.GOLDEN_SCARAB.get(), RendererGoldenScarab::new);
        EntityRenderers.register(DMEntities.FLYING_CARPET.get(), RendererFlyingCarpet::new);
        EntityRenderers.register(DMEntities.FENICK.get(), RendererFenick::new);
        EntityRenderers.register(DMEntities.FENICK_MAGE.get(), RendererFenickMage::new);
        EntityRenderers.register(DMEntities.SCARAB_LORD.get(), RendererScarabLord::new);
        EntityRenderers.register(DMEntities.BLUE_GENIE.get(), RendererGenie::new);
        EntityRenderers.register(DMEntities.GREEN_GENIE.get(), RendererGenie::new);
        EntityRenderers.register(DMEntities.RED_GENIE.get(), RendererGenie::new);
        EntityRenderers.register(DMEntities.PURPLE_GENIE.get(), RendererGenie::new);
        EntityRenderers.register(DMEntities.POLYMORPH.get(), RendererPolymorph::new);
        EntityRenderers.register(DMEntities.GENIE_CHARGE.get(), RendererCharge::new);
        EntityRenderers.register(DMEntities.SCORPION.get(), RendererScorpion::new);
        EntityRenderers.register(DMEntities.FAKIR.get(), RendererFenickFakir::new);
        EntityRenderers.register(DMEntities.CACTUPINE.get(), RendererCactupine::new);
        EntityRenderers.register(DMEntities.STING_ARROW.get(), RendererStingArrow::new);
        EntityRenderers.register(DMEntities.CLOUD.get(), RendererCloud::new);
        EntityRenderers.register(DMEntities.BOX_GLOVE.get(), RendererBoxGlove::new);
    }
}
