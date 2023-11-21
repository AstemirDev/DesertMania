package org.astemir.desertmania.mixin.common;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.astemir.api.io.ResourceUtils;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.enchantments.DMEnchantmentCategory;
import org.astemir.desertmania.common.misc.TabSorting;
import org.astemir.desertmania.common.utils.TabSortUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Mixin({CreativeModeTab.class})
public abstract class MixinCreativeModeTab {


    @Shadow private EnchantmentCategory[] enchantmentCategories;

    /**
     * @author Astemir
     * @reason Applying to menu
     */
    @Overwrite
    public EnchantmentCategory[] getEnchantmentCategories() {
        if ((Object)this == CreativeModeTab.TAB_COMBAT){
            List<EnchantmentCategory> list = new ArrayList(Arrays.asList(enchantmentCategories));
            list.add(DMEnchantmentCategory.SCIMITAR);
            return list.toArray(new EnchantmentCategory[list.size()]);
        }
        return this.enchantmentCategories;
    }

    @Inject(method = "fillItemList", at = @At("TAIL"))
    private void onFillItems(NonNullList<ItemStack> list, CallbackInfo ci){
        for (ItemStack itemStack : list) {
            Item item = itemStack.getItem();
            boolean isModItem = ResourceUtils.getItemLocation(item).getNamespace().equals(DesertMania.MOD_ID);
            if (isModItem){
                for (TabSorting group : TabSorting.getGroups()) {
                    if (group.isItemOfGroup(item)){
                        int myId = TabSortUtils.getIndexOfItem(list,item);
                        int replaceId = TabSortUtils.getIndexOfItem(list,group.getItemReplacement())+1;
                        TabSortUtils.move(list,myId,replaceId);
                    }
                }
            }
        }
    }

}
