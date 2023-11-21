package org.astemir.desertmania.common.utils;


import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.astemir.api.common.gfx.GFXBlackOut;
import org.astemir.api.common.gfx.PlayerGFXEffectManager;
import org.astemir.api.common.handler.WorldEventHandler;
import org.astemir.api.math.components.Color;
import org.astemir.api.network.NetworkUtils;
import org.astemir.api.network.messages.ClientMessageWorldPosEvent;
import org.astemir.desertmania.client.handler.GenieClientEventHandler;
import org.astemir.desertmania.common.enchantments.DMEnchantments;
import org.astemir.desertmania.common.item.DMItems;
import org.astemir.desertmania.common.misc.IDMPlayer;
import org.astemir.desertmania.common.world.WorldEvents;


public class MiscUtils {


    public static void showBossTaunt(Player player){
        player.level.playSound(null,player.blockPosition(),SoundEvents.ELDER_GUARDIAN_CURSE, SoundSource.HOSTILE,1,0.75f);
        if (player.level.isClientSide){
            return;
        }
        if (player instanceof ServerPlayer serverPlayer) {
            PlayerGFXEffectManager.addEffect(serverPlayer,new GFXBlackOut(Color.PURPLE,0.15f),false);
            NetworkUtils.sendToPlayer(serverPlayer, new ClientMessageWorldPosEvent(player.getOnPos(), WorldEvents.EVENT_BOSS_TAUNT));
        }
    }

    public static void showJackpot(Player player){
        if (player.level.isClientSide){
            return;
        }
        if (player instanceof ServerPlayer serverPlayer) {
            WorldEventHandler.playClientEvent(serverPlayer,player.level,player.getOnPos(), GenieClientEventHandler.EVENT_GENIE_JACKPOT.getValue());
        }
    }

    public static boolean canUseScimitarWhirling(Entity entity){
        if (entity instanceof Player player) {
            ItemStack mainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
            ItemStack offHand = player.getItemInHand(InteractionHand.OFF_HAND);
            if (mainHand.is(DMItems.SCIMITAR.get()) && offHand.is(DMItems.SCIMITAR.get())){
                return mainHand.getEnchantmentLevel(DMEnchantments.WHIRLING.get()) > 0 && offHand.getEnchantmentLevel(DMEnchantments.WHIRLING.get()) > 0;
            }
        }
        return false;
    }

    public static boolean isUsingScimitarWhirling(Entity entity){
        if (entity instanceof Player player) {
            return player.getUseItem().is(DMItems.SCIMITAR.get()) && canUseScimitarWhirling(entity) && IDMPlayer.asIDMPlayer(entity).isSpinningWithScimitar();
        }
        return false;
    }

    public static boolean isBlockingWithScimitar(Entity entity){
        if (canUseScimitarWhirling(entity)){
            return false;
        }
        if (entity instanceof Player) {
            Player player = (Player) entity;
            return player.getUseItem().is(DMItems.SCIMITAR.get());
        }
        return false;
    }

    public static boolean isDoubleBlockingWithScimitar(Entity entity){
        if (canUseScimitarWhirling(entity)){
            return false;
        }
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (player.getUseItem().is(DMItems.SCIMITAR.get())) {
                ItemStack mainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
                ItemStack offHand = player.getItemInHand(InteractionHand.OFF_HAND);
                return mainHand.is(DMItems.SCIMITAR.get()) && offHand.is(DMItems.SCIMITAR.get());
            }
        }
        return false;
    }
}
