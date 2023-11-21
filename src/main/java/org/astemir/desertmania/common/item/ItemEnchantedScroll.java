package org.astemir.desertmania.common.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.commands.EnchantCommand;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ItemEnchantedScroll extends Item {

    public ItemEnchantedScroll() {
        super(new Item.Properties().tab(CreativeModeTab.TAB_MISC).rarity(Rarity.RARE).stacksTo(1));
    }

    public boolean isFoil(ItemStack pStack) {
        return !getEnchantments(pStack).isEmpty();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return true;
    }

    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }

    public static ListTag getEnchantments(ItemStack pEnchantedBookStack) {
        CompoundTag compoundtag = pEnchantedBookStack.getTag();
        return compoundtag != null ? compoundtag.getList("StoredEnchantments", 10) : new ListTag();
    }

    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        ItemStack.appendEnchantmentNames(pTooltip, getEnchantments(pStack));
    }

    public static void addEnchantment(ItemStack pStack, EnchantmentInstance pInstance) {
        ListTag listtag = getEnchantments(pStack);
        boolean flag = true;
        ResourceLocation resourcelocation = EnchantmentHelper.getEnchantmentId(pInstance.enchantment);

        for(int i = 0; i < listtag.size(); ++i) {
            CompoundTag compoundtag = listtag.getCompound(i);
            ResourceLocation resourcelocation1 = EnchantmentHelper.getEnchantmentId(compoundtag);
            if (resourcelocation1 != null && resourcelocation1.equals(resourcelocation)) {
                if (EnchantmentHelper.getEnchantmentLevel(compoundtag) < pInstance.level) {
                    EnchantmentHelper.setEnchantmentLevel(compoundtag, pInstance.level);
                }

                flag = false;
                break;
            }
        }

        if (flag) {
            listtag.add(EnchantmentHelper.storeEnchantment(resourcelocation, pInstance.level));
        }

        pStack.getOrCreateTag().put("StoredEnchantments", listtag);
    }

    public static void clearEnchantments(ItemStack pStack) {
        pStack.getOrCreateTag().put("StoredEnchantments", new ListTag());
    }
}
