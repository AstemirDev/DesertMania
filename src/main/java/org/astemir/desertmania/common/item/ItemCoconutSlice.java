package org.astemir.desertmania.common.item;


import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.level.Level;

import java.util.Iterator;

public class ItemCoconutSlice extends Item {

    public ItemCoconutSlice() {
        super(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.6f).build()).stacksTo(1).tab(CreativeModeTab.TAB_FOOD));
    }


    @Override
    public ItemStack finishUsingItem(ItemStack p_42923_, Level p_42924_, LivingEntity p_42925_) {
        if (!p_42924_.isClientSide) {
            p_42925_.removeAllEffects();
        }
        return this.isEdible() ? p_42925_.eat(p_42924_, p_42923_) : p_42923_;
    }

}
