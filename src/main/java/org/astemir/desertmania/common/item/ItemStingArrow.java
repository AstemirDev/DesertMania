package org.astemir.desertmania.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.astemir.desertmania.common.entity.EntityStingArrow;

public class ItemStingArrow extends ArrowItem {

    public ItemStingArrow() {
        super(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));
    }

    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter) {
        EntityStingArrow arrow = new EntityStingArrow(level,shooter);
        return arrow;
    }
}
