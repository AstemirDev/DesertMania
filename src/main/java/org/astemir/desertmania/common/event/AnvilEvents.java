package org.astemir.desertmania.common.event;

import net.minecraft.nbt.ListTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.astemir.api.math.collection.Couple;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.desertmania.common.item.DMItems;
import org.astemir.desertmania.common.item.ItemEnchantedScroll;
import java.util.Map;

public class AnvilEvents {


    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent e){
        ItemStack tool = e.getLeft();
        ItemStack scroll = e.getRight();
        if (scroll.is(DMItems.ENCHANTED_SCROLL.get())){
            if (isItemEnchanted(tool) && !isItemEnchanted(scroll)){
                ItemStack result = tool.copy();
                Couple<Enchantment,Integer> first = getFirstEnchantment(tool);
                if (first != null) {
                    removeEnchantment(result, first.getKey());
                    e.setMaterialCost(1);
                    e.setCost(EnchantmentHelper.getEnchantmentCost(e.getPlayer().getRandom(),3,first.getValue(),tool));
                    e.setOutput(result);
                }
            }else
            if (isItemEnchanted(scroll)){
                ItemStack result = tool.copy();
                Couple<Enchantment,Integer> first = getFirstEnchantment(scroll);
                Couple<Enchantment,Integer> second = getFirstEnchantment(tool);
                if (first != null) {
                    if (second == null || second.getKey() != first.getKey()) {
                        int secondCost = 0;
                        if (second != null){
                            secondCost = second.getValue();
                        }
                        if (result.is(Items.ENCHANTED_BOOK)) {
                            EnchantedBookItem.addEnchantment(result, new EnchantmentInstance(first.getKey(), first.getValue()));
                        } else if (result.is(DMItems.ENCHANTED_SCROLL.get())) {
                            if (second != null){
                                e.setCanceled(true);
                            }
                            ItemEnchantedScroll.addEnchantment(result, new EnchantmentInstance(first.getKey(), first.getValue()));
                        } else {
                            result.enchant(first.getKey(), first.getValue());
                        }
                        e.setMaterialCost(1);
                        e.setCost(2*(first.getValue()+secondCost));
                        e.setOutput(result);
                    }
                }
            }
        }else
        if (scroll.is(Items.ENCHANTED_BOOK) && tool.is(DMItems.ENCHANTED_SCROLL.get())) {
            if (isItemEnchanted(tool)){
                e.setCanceled(true);
            }else {
                ItemStack result = tool.copy();
                Couple<Enchantment,Integer> first = getFirstEnchantment(scroll);
                if (first != null) {
                    ItemEnchantedScroll.addEnchantment(result, new EnchantmentInstance(first.getKey(), first.getValue()));
                    e.setMaterialCost(1);
                    e.setCost(2*first.getValue());
                    e.setOutput(result);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onAnvilResult(AnvilRepairEvent e){
        ItemStack tool = e.getLeft();
        ItemStack scroll = e.getRight();
        if (scroll.is(DMItems.ENCHANTED_SCROLL.get())){
            if (isItemEnchanted(tool) && !isItemEnchanted(scroll)){
                Couple<Enchantment,Integer> first = getFirstEnchantment(tool);
                if (first != null) {
                    boolean res = true;
                    if (res) {
                        ItemStack scrollEnchanted = scroll.copy();
                        ItemEnchantedScroll.addEnchantment(scrollEnchanted, new EnchantmentInstance(first.getKey(), first.getValue()));
                        e.getEntity().addItem(scrollEnchanted);
                    }else{
                        tool.hurtAndBreak(RandomUtils.randomInt(4,8),e.getEntity(), player -> {
                            player.broadcastBreakEvent(InteractionHand.MAIN_HAND);
                        });
                        scroll.shrink(1);
                        e.setCanceled(true);
                    }
                }
            }
        }
    }

    public static ItemStack removeEnchantment(ItemStack stack,Enchantment enchantment){
        Map<Enchantment,Integer> map = getEnchantments(stack);
        map.remove(enchantment);
        ListTag listtag = new ListTag();
        for(Map.Entry<Enchantment, Integer> entry : map.entrySet()) {
            Enchantment ench = entry.getKey();
            if (enchantment != null) {
                int i = entry.getValue();
                listtag.add(EnchantmentHelper.storeEnchantment(EnchantmentHelper.getEnchantmentId(ench), i));
                if (stack.is(Items.ENCHANTED_BOOK)) {
                    EnchantedBookItem.addEnchantment(stack, new EnchantmentInstance(ench, i));
                }else
                if (stack.is(DMItems.ENCHANTED_SCROLL.get())){
                    ItemEnchantedScroll.addEnchantment(stack, new EnchantmentInstance(ench, i));
                }
            }
        }
        if (listtag.isEmpty()) {
            stack.removeTagKey("Enchantments");
        } else if (!stack.is(Items.ENCHANTED_BOOK) && !stack.is(DMItems.ENCHANTED_SCROLL.get())) {
            stack.addTagElement("Enchantments", listtag);
        }
        return stack;
    }

    public static boolean isItemEnchanted(ItemStack scroll){
        return !EnchantmentHelper.getEnchantments(scroll).isEmpty() || !ItemEnchantedScroll.getEnchantments(scroll).isEmpty();
    }

    public static Map<Enchantment,Integer> getEnchantments(ItemStack stack){
        if (stack.getItem() == DMItems.ENCHANTED_SCROLL.get()){
            return EnchantmentHelper.deserializeEnchantments(ItemEnchantedScroll.getEnchantments(stack));
        }
        Map<Enchantment,Integer> map = EnchantmentHelper.getEnchantments(stack);
        return map;
    }

    public static int getEnchantmentLevel(ItemStack stack,Enchantment enchantment){
        return getEnchantments(stack).get(enchantment);
    }

    public static Couple<Enchantment,Integer> getFirstEnchantment(ItemStack stack){
        Map<Enchantment,Integer> map = getEnchantments(stack);
        if (map != null && !map.isEmpty()) {
            Map.Entry<Enchantment, Integer> entry = map.entrySet().iterator().next();
            return new Couple<>(entry.getKey(), entry.getValue());
        }
        return null;
    }
}
