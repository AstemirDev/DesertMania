package org.astemir.desertmania.common.entity.fenick;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FenickInventory {

    public CopyOnWriteArrayList<ItemStack> inventory = new CopyOnWriteArrayList<>();
    private final int maxCount = 8;


    public boolean canPickupMoreItems(){
        return inventory.size() < maxCount;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void addItem(ItemStack stack){
        if (inventory.size() < maxCount) {
            this.inventory.add(stack);
        }
    }

    public CopyOnWriteArrayList<ItemStack> getInventory() {
        return inventory;
    }

    public boolean removeItem(ItemStack stack){
        for (ItemStack itemStack : inventory) {
            if (itemStack.sameItem(stack)){
                inventory.remove(itemStack);
                return true;
            }
        }
        return false;
    }

    public void save(CompoundTag tag){
        ListTag listTag = new ListTag();
        for (int i = 0; i < inventory.size(); i++) {
            listTag.add(i,inventory.get(i).save(new CompoundTag()));
        }
        tag.put("Inventory",listTag);
    }


    public void load(CompoundTag tag) {
        inventory.clear();
        if (tag.contains("Inventory")) {
            ListTag tags = tag.getList("Inventory", 10);
            for (Tag tag1 : tags) {
                inventory.add(ItemStack.of((CompoundTag) tag1));
            }
        }
    }

}
