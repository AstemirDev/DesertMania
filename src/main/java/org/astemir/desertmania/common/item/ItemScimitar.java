package org.astemir.desertmania.common.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.astemir.api.common.entity.utils.PlayerUtils;
import org.astemir.desertmania.client.misc.ScimitarClientExtension;
import org.astemir.desertmania.common.misc.IDMPlayer;
import org.astemir.desertmania.common.utils.MiscUtils;

import java.util.function.Consumer;

public class ItemScimitar extends SwordItem {

    public ItemScimitar() {
        super(Tiers.IRON, 3, -2.65F,new Properties().tab(CreativeModeTab.TAB_COMBAT));
    }


    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new ScimitarClientExtension());
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.CUSTOM;
    }

    @Override
    public int getUseDuration(ItemStack p_41454_) {
        return 72000;
    }


    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if (!PlayerUtils.isOnCooldown(player,this) && MiscUtils.isUsingScimitarWhirling(player)) {
            IDMPlayer.asIDMPlayer(player).setScimitarSpinTicks(20);
        }
    }

    @Override
    public void releaseUsing(ItemStack p_41412_, Level p_41413_, LivingEntity p_41414_, int p_41415_) {
        super.releaseUsing(p_41412_, p_41413_, p_41414_, p_41415_);
        if (MiscUtils.isUsingScimitarWhirling(p_41414_)) {
            PlayerUtils.cooldownItem(p_41414_,this,80);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_43099_, Player p_43100_, InteractionHand p_43101_) {
        ItemStack itemstack = p_43100_.getItemInHand(p_43101_);
        if (!p_43100_.getCooldowns().isOnCooldown(this)) {
            if (MiscUtils.canUseScimitarWhirling(p_43100_)) {
                Vec3 vec3 = p_43100_.getViewVector(0).multiply(0.7f, 0, 0.7f);
                IDMPlayer.asIDMPlayer(p_43100_).setScimitarSpinTicks(40);
                p_43100_.push(vec3.x, vec3.y, vec3.z);
            }
            p_43100_.startUsingItem(p_43101_);
            return InteractionResultHolder.consume(itemstack);
        }
        return InteractionResultHolder.fail(itemstack);
    }
}
