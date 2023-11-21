package org.astemir.desertmania.common.utils;


import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import java.util.function.Predicate;

public abstract class TabSortUtils {


    public static void move(NonNullList<ItemStack> list, int indexToMoveFrom, int indexToMoveAt) {
        ItemStack item = list.get(indexToMoveFrom);
        list.remove(indexToMoveFrom);
        list.add(indexToMoveAt,item);
    }

    public static int getIndexOfItem(NonNullList<ItemStack> list, Item item){
        return getIndexOfItem(list,(need)->need == item);
    }

    public static int getIndexOfItem(NonNullList<ItemStack> list, Predicate<Item> predicate){
        for (int i = 0; i < list.size(); i++) {
            if (predicate.test(list.get(i).getItem())){
                return i;
            }
        }
        return -1;
    }
}
