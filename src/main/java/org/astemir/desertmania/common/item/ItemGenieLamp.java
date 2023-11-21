package org.astemir.desertmania.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.astemir.desertmania.common.block.DMBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemGenieLamp extends BlockItem {


    public ItemGenieLamp() {
        super(DMBlocks.GENIE_LAMP.get(),new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS).stacksTo(1).rarity(Rarity.RARE));
    }

    @Override
    public Component getName(ItemStack p_41458_) {
        MutableComponent component = Component.translatable(this.getDescriptionId(p_41458_));
        CompoundTag tag = p_41458_.getTag();
        boolean isEmpty = false;
        if (tag != null) {
            if (tag.contains("BlockEntityTag")) {
                CompoundTag blockTag = tag.getCompound("BlockEntityTag");
                if (blockTag.contains("IsEmpty")) {
                    isEmpty = blockTag.getBoolean("IsEmpty");
                }
            }
        }
        if (isEmpty){
            component = component.append(" ").append(Component.translatable("name.desertmania.empty").withStyle(ChatFormatting.WHITE));
        }
        return component;
    }

    @Override
    public void appendHoverText(ItemStack p_40572_, @Nullable Level p_40573_, List<Component> p_40574_, TooltipFlag p_40575_) {
        super.appendHoverText(p_40572_, p_40573_, p_40574_, p_40575_);
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        super.readShareTag(stack, nbt);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext p_40578_, BlockState p_40579_) {
        return super.placeBlock(p_40578_, p_40579_);
    }
}
