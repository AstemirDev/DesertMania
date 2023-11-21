package org.astemir.desertmania.common.misc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.astemir.desertmania.common.item.DMItems;

import java.util.function.Predicate;


public class AncientAmuletRecipe extends CustomRecipe {

    public AncientAmuletRecipe(ResourceLocation pId) {
        super(pId);
    }


    @Override
    public boolean matches(CraftingContainer pInv, Level pLevel) {
        ItemStack amuletStack = getItemStackMatches(pInv, (stack)->{
            if (stack.is(DMItems.ANCIENT_AMULET.get())){
                if (stack.isDamaged()){
                    return true;
                }
            }
            return false;
        });
        ItemStack scarabPiece = getItemStackMatches(pInv,(stack)-> stack.is(DMItems.GOLDEN_SCARAB_LEFT_PART.get()) || stack.is(DMItems.GOLDEN_SCARAB_RIGHT_PART.get()));
        return amuletStack != null && scarabPiece != null;
    }

    @Override
    public ItemStack assemble(CraftingContainer pInv) {
        return DMItems.ANCIENT_AMULET.get().getDefaultInstance();
    }

    public ItemStack getItemStackMatches(CraftingContainer container,Predicate<ItemStack> predicate){
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (predicate.test(stack)){
                return stack;
            }
        }
        return null;
    }


    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= 2;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return DMRecipeSerializers.ANCIENT_AMULET.get();
    }
}
