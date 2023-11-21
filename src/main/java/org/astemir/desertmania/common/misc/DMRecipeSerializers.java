package org.astemir.desertmania.common.misc;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.astemir.desertmania.DesertMania;

public class DMRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, DesertMania.MOD_ID);
    public static final RegistryObject<SimpleRecipeSerializer<?>> ANCIENT_AMULET = RECIPE_SERIALIZERS.register("ancient_amulet", ()->new SimpleRecipeSerializer<>(AncientAmuletRecipe::new));


}
