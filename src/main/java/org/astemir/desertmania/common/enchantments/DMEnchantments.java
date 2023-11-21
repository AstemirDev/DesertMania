package org.astemir.desertmania.common.enchantments;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.astemir.desertmania.DesertMania;

public class DMEnchantments {

    public static DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, DesertMania.MOD_ID);
    public static RegistryObject<Enchantment> WHIRLING = ENCHANTMENTS.register("whirling",EnchantmentWhirling::new);
}
