package org.astemir.desertmania.client.event;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.astemir.api.client.animation.Animator;
import org.astemir.api.client.event.HumanoidModelEvent;
import org.astemir.api.client.event.LivingTransformEvent;
import org.astemir.api.client.event.SkySetupEvent;
import org.astemir.api.common.animation.Animation;
import org.astemir.api.math.MathUtils;
import org.astemir.api.math.components.Color;
import org.astemir.api.math.components.Transform;
import org.astemir.desertmania.client.ClientDesertMania;
import org.astemir.desertmania.client.LevelRenderStateHandler;
import org.astemir.desertmania.client.handler.DMLevelClientHandler;
import org.astemir.desertmania.common.entity.EntityFlyingCarpet;
import org.astemir.desertmania.common.entity.camel.EntityCamel;
import org.astemir.desertmania.common.entity.genie.EntityGreenGenie;
import org.astemir.desertmania.common.entity.scarablord.EntityScarabLord;
import org.astemir.desertmania.common.entity.scarablord.ScarabLordAnimations;
import org.astemir.desertmania.common.item.DMItems;
import org.astemir.desertmania.common.misc.IDMPlayer;
import org.astemir.desertmania.common.utils.MiscUtils;


public class ClientEvents {

    public static final Color SANDSTORM_COLOR = new Color(182 / 255f, 151 / 255f, 104 / 255f, 1f);

    @SubscribeEvent
    public static void onSkyColorCalculate(SkySetupEvent.ComputeSkyColor.Pre e){
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        boolean resetSky = false;
        if (player != null) {
            Holder<Biome> currentBiome = minecraft.level.getBiome(player.blockPosition());
            if (currentBiome.is(Biomes.DESERT)) {
                if (DMLevelClientHandler.SANDSTORM_ENABLED) {
                    LevelRenderStateHandler.setSkyColor(SANDSTORM_COLOR, e.getPartialTick()/80f);
                }else{
                    resetSky = true;
                }
            } else {
                resetSky = true;
            }
            if (resetSky){
                LevelRenderStateHandler.setSkyColor(e.getColor(), e.getPartialTick()/80f);
            }
            LevelRenderStateHandler.applyToEvent(e);
        }
    }

    @SubscribeEvent
    public static void onAnimatePlayer(HumanoidModelEvent e){
        Entity entity = e.getEntity();
        if (entity instanceof Player player) {
            float ticks = ((float)(entity.tickCount))+Minecraft.getInstance().getPartialTick();
            IDMPlayer idmPlayer = IDMPlayer.asIDMPlayer(entity);
            if (idmPlayer.isSpinningWithScimitar()) {
                rotateModelPart(e.getLeftArm(), MathUtils.rad(120 + MathUtils.sin(ticks) * 10), MathUtils.rad(MathUtils.sin(ticks) * 5), MathUtils.rad(90));
                rotateModelPart(e.getRightArm(), MathUtils.rad(-100 + MathUtils.sin(ticks) * 10), MathUtils.rad(MathUtils.sin(ticks) * 5), MathUtils.rad(90));
            } else if (MiscUtils.isDoubleBlockingWithScimitar(entity)) {
                e.getLeftArm().xRot = MathUtils.rad(-66);
                e.getLeftArm().yRot = MathUtils.rad(23);
                e.getLeftArm().zRot = MathUtils.rad(-42);

                e.getRightArm().xRot = MathUtils.rad(-66);
                e.getRightArm().yRot = MathUtils.rad(-23);
                e.getRightArm().zRot = MathUtils.rad(42);

            } else if (MiscUtils.isBlockingWithScimitar(entity)) {
                if (player.getUseItem().is(DMItems.SCIMITAR.get()) && player.getUsedItemHand() == InteractionHand.MAIN_HAND) {
                    e.getRightArm().xRot = MathUtils.rad(-85);
                    e.getRightArm().yRot = MathUtils.rad(-40);
                } else if (player.getUseItem().is(DMItems.SCIMITAR.get()) && player.getUsedItemHand() == InteractionHand.OFF_HAND) {
                    e.getLeftArm().xRot = MathUtils.rad(-85);
                    e.getLeftArm().yRot = MathUtils.rad(40);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingScale(LivingTransformEvent.Scale e){
        LivingEntity entity = e.getEntityLiving();
        if (entity instanceof Player player) {
            PoseStack stack = e.getMatrixStack();
            IDMPlayer idmPlayer = (IDMPlayer) player;
            float partialTicks = Minecraft.getInstance().getPartialTick();
            float deltaTicks = ((float) (player.tickCount)) + partialTicks;
            if (idmPlayer.isSpinningWithScimitar()) {
                float rotation = deltaTicks % 360;
                stack.mulPose(Vector3f.XP.rotationDegrees(MathUtils.sin(deltaTicks / 2) * 10));
                stack.mulPose(Vector3f.YP.rotationDegrees(rotation * 70));
            }
            Entity vehicle = player.getVehicle();
            if (vehicle != null) {
                if (vehicle instanceof EntityCamel camel) {
                    Transform transform = Animator.INSTANCE.getTransformData(camel, "Body");
                    if (transform != null) {
                        if (camel.canBeMovedOrPushed()) {
                            stack.translate(transform.getPosition().x / 16, transform.getPosition().y / 16, 0.25f - transform.getPosition().z / 16);
                        } else {
                            stack.translate(0, 0, 0.25f);
                        }
                    }
                }
                if (vehicle instanceof EntityFlyingCarpet carpet) {
                    Transform transform = Animator.INSTANCE.getTransformData(carpet, "carpet");
                    if (transform != null) {
                        stack.translate(transform.getPosition().x / 16, (transform.getPosition().y / 16)+0.125f, transform.getPosition().z / 16);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRenderScreen(RenderGuiOverlayEvent.Post e){
        if (e.getOverlay().id().getPath().equals("vignette")) {
            if (ClientDesertMania.GENIE_CLIENT_HANDLER.isShowGenie()) {
                EntityGreenGenie genie = ClientDesertMania.GENIE_CLIENT_HANDLER.getGenie();
                genie.tickCount = Minecraft.getInstance().player.tickCount;
                int delta = Math.max(0,genie.tickCount- ClientDesertMania.GENIE_CLIENT_HANDLER.getStartTicks());
                if (delta < 80) {
                    float position = ClientDesertMania.GENIE_CLIENT_HANDLER.getPosition();
                    float nextPosition = ClientDesertMania.GENIE_CLIENT_HANDLER.getPosition(delta);
                    float newPosition = ClientDesertMania.GENIE_CLIENT_HANDLER.setPosition(MathUtils.lerp(position, nextPosition, Minecraft.getInstance().getPartialTick()));
                    e.getPoseStack().pushPose();
                    InventoryScreen.renderEntityInInventory(Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2, (int) newPosition, 150, 0, 0, genie);
                    e.getPoseStack().popPose();
                }else{
                    ClientDesertMania.GENIE_CLIENT_HANDLER.setShowGenie(false);
                }
            }

            if (ClientDesertMania.BOSS_TAUNT_CLIENT_HANDLER.isShowBoss()){
                Animation animation = ScarabLordAnimations.ANIMATION_ATTACK_LIFE_STEAL;
                EntityScarabLord boss = ClientDesertMania.BOSS_TAUNT_CLIENT_HANDLER.getBoss();
                boss.clientSideGhosting = true;
                boss.getAnimationFactory().setAnimation(animation, 0);
                boss.tickCount = Minecraft.getInstance().player.tickCount;
                int delta = Math.max(0,boss.tickCount- ClientDesertMania.BOSS_TAUNT_CLIENT_HANDLER.getStartTicks());
                if (delta < 80) {
                    float position = ClientDesertMania.BOSS_TAUNT_CLIENT_HANDLER.setPosition(MathUtils.lerp(ClientDesertMania.BOSS_TAUNT_CLIENT_HANDLER.getPosition(), ClientDesertMania.BOSS_TAUNT_CLIENT_HANDLER.getPosition(delta), Minecraft.getInstance().getPartialTick()));
                    float rotation = ClientDesertMania.BOSS_TAUNT_CLIENT_HANDLER.setRotation(MathUtils.lerp(ClientDesertMania.BOSS_TAUNT_CLIENT_HANDLER.getRotation(), ClientDesertMania.BOSS_TAUNT_CLIENT_HANDLER.getRotation(delta).y, Minecraft.getInstance().getPartialTick()));
                    e.getPoseStack().pushPose();
                    InventoryScreen.renderEntityInInventoryRaw(Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2, (int) position, 250, 0, MathUtils.rad(rotation), boss);
                    e.getPoseStack().popPose();
                }else{
                    ClientDesertMania.BOSS_TAUNT_CLIENT_HANDLER.setShowBoss(false);
                }
            }
        }
    }


    @SubscribeEvent
    public static void onFogColor(ViewportEvent.ComputeFogColor e){
        Holder<Biome> biome = Minecraft.getInstance().level.getBiome(Minecraft.getInstance().player.blockPosition());
        boolean resetFogColor = false;
        if (biome.is(Biomes.DESERT)){
            if (DMLevelClientHandler.SANDSTORM_ENABLED){
                LevelRenderStateHandler.setFogColor(SANDSTORM_COLOR, (float) e.getPartialTick()/80);
            }else{
                resetFogColor = true;
            }
        }else{
            resetFogColor = true;
        }
        if (resetFogColor){
            LevelRenderStateHandler.setFogColor(e.getRed(),e.getGreen(),e.getBlue(), 1);
        }
        LevelRenderStateHandler.applyToEvent(e);
    }

    @SubscribeEvent
    public static void onFogRender(ViewportEvent.RenderFog e){
        Player player = Minecraft.getInstance().player;
        Holder<Biome> biome = Minecraft.getInstance().level.getBiome(player.blockPosition());
        boolean resetFog = false;
        if (biome.is(Biomes.DESERT)){
            if (DMLevelClientHandler.SANDSTORM_ENABLED) {
                if (player.getItemBySlot(EquipmentSlot.HEAD).is(DMItems.SHEMAGH.get())) {
                    LevelRenderStateHandler.setNear(30, (float) e.getPartialTick()/80f);
                    LevelRenderStateHandler.setFar(80, (float) e.getPartialTick()/80f);
                }else{
                    LevelRenderStateHandler.setNear(10, (float) e.getPartialTick()/80f);
                    LevelRenderStateHandler.setFar(40, (float) e.getPartialTick()/80f);
                }
                e.setFogShape(FogShape.SPHERE);
            }else{
                resetFog = true;
            }
        }else{
           resetFog = true;
        }
        if (resetFog){
            LevelRenderStateHandler.setNear(-1, 1);
            LevelRenderStateHandler.setFar(-1, 1);
        }
        e.setCanceled(true);
        LevelRenderStateHandler.applyToEvent(e);
    }

    @SubscribeEvent
    public static void onComputeCameraAngles(ViewportEvent.ComputeCameraAngles e) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        IDMPlayer idmPlayer = (IDMPlayer)player;
        CameraType type = minecraft.options.getCameraType();
        if (idmPlayer.isSpinningWithScimitar() && type == CameraType.FIRST_PERSON) {
            float partialTicks = (float) e.getPartialTick();
            float deltaTicks = ((float)(player.tickCount))+partialTicks;
            float rotation = deltaTicks%360;
            e.setYaw(e.getYaw()+rotation*50);
            e.setPitch(e.getPitch()+MathUtils.sin(deltaTicks/5)*5);
        }
    }



    private static void rotateModelPart(ModelPart part, float x, float y, float z){
        part.xRot = x;
        part.yRot = y;
        part.zRot = z;
    }
}
