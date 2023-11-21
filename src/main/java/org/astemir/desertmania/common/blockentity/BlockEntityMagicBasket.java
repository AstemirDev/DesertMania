package org.astemir.desertmania.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.astemir.api.common.misc.ICustomRendered;

public class BlockEntityMagicBasket extends RandomizableContainerBlockEntity implements ICustomRendered {

    private NonNullList<ItemStack> items = NonNullList.withSize(128, ItemStack.EMPTY);


    public BlockEntityMagicBasket(BlockPos pPos, BlockState pBlockState) {
        super(DMBlockEntities.MAGIC_BASKET.get(), pPos, pBlockState);
    }

    @Override
    public void setItems(NonNullList<ItemStack> pItemStacks) {
        this.items = pItemStacks;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    public boolean isFull(){
        for (ItemStack item : items) {
            if (item.isEmpty()){
                return false;
            }
        }
        return true;
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(pTag)) {
            ContainerHelper.loadAllItems(pTag, this.items);
        }
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (!this.trySaveLootTable(pTag)) {
            ContainerHelper.saveAllItems(pTag, this.items);
        }
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.desertmania.magic_basket");
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return null;
    }

    @Override
    public int getContainerSize() {
        return 128;
    }
}
