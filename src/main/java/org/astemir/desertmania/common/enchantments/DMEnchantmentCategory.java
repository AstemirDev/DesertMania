package org.astemir.desertmania.common.enchantments;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.item.ItemScimitar;

public class DMEnchantmentCategory {

    public static final EnchantmentCategory SCIMITAR = EnchantmentCategory.create(new ResourceLocation(DesertMania.MOD_ID,"scimitar").toString(),(item)->{
        return item instanceof ItemScimitar;
    });
}
