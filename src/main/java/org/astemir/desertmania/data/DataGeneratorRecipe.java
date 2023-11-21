package org.astemir.desertmania.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.astemir.api.data.recipe.RecipePattern;
import org.astemir.api.data.recipe.SkillsRecipeProvider;
import org.astemir.desertmania.common.block.DMBlocks;
import org.astemir.desertmania.common.item.DMItems;
import org.astemir.desertmania.common.misc.DMRecipeSerializers;

import java.util.function.Consumer;

public class DataGeneratorRecipe extends SkillsRecipeProvider {
    
    
    public DataGeneratorRecipe(DataGenerator p_125973_) {
        super(p_125973_);
        recipes();
        register(p_125973_);
    }


    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> p_176532_) {
        super.buildCraftingRecipes(p_176532_);
        SpecialRecipeBuilder.special(DMRecipeSerializers.ANCIENT_AMULET.get()).save(p_176532_,"desertmania:amulet_repair");
    }

    private void recipes(){
        stoneCut();
        wood();
        slabs();
        smelt();
        other();
    }

    private void stoneCut(){
        addRecipe(stoneCut(DMBlocks.CUT_LOESS.get(),1,DMBlocks.LOESS.get()));
        addRecipe(stoneCut(DMBlocks.LOESS_BRICKS_SYMBOLS.get(),1,DMBlocks.LOESS.get()));
        addRecipe(stoneCut(DMBlocks.SMOOTH_LOESS.get(),1,DMBlocks.LOESS.get()));
        addRecipe(stoneCut(DMBlocks.LOESS_BRICKS.get(),1,DMBlocks.LOESS.get()));
        addRecipe(stoneCut(DMBlocks.SMOOTH_LOESS_WALL.get(),1,DMBlocks.LOESS.get()));
        addRecipe(stoneCut(DMBlocks.LOESS_BRICKS_WALL.get(),1,DMBlocks.LOESS.get()));
        addRecipe(stoneCut(DMBlocks.CUT_LOESS_WALL.get(),1,DMBlocks.LOESS.get()));
        addRecipe(stoneCut(DMBlocks.SMOOTH_LOESS_STAIRS.get(),1,DMBlocks.LOESS.get()));
        addRecipe(stoneCut(DMBlocks.LOESS_BRICKS_STAIRS.get(),1,DMBlocks.LOESS.get()));
        addRecipe(stoneCut(DMBlocks.CUT_LOESS_STAIRS.get(),1,DMBlocks.LOESS.get()));
        addRecipe(stoneCut(DMBlocks.LOESS_BRICKS_SLAB.get(),2,DMBlocks.LOESS.get()));
        addRecipe(stoneCut(DMBlocks.SMOOTH_LOESS_SLAB.get(),2,DMBlocks.LOESS.get()));
        addRecipe(stoneCut(DMBlocks.CUT_LOESS_SLAB.get(),2,DMBlocks.LOESS.get()));
    }

    private void wood(){
        addRecipe(stairs(DMBlocks.PALM_PLANKS_STAIRS.get(),4,DMBlocks.PALM_PLANKS.get()));
        addRecipe(slab(DMBlocks.PALM_PLANKS_SLAB.get(),6,DMBlocks.PALM_PLANKS.get()));
        addRecipe(fence(DMBlocks.PALM_PLANKS_FENCE.get(),3,DMBlocks.PALM_PLANKS.get(), Items.STICK));
        addRecipe(fenceGate(DMBlocks.PALM_PLANKS_GATE.get(),1,DMBlocks.PALM_PLANKS.get(),Items.STICK));
        addRecipe(door(DMBlocks.PALM_PLANKS_DOOR.get(),3,DMBlocks.PALM_PLANKS.get()));
        addRecipe(sign(DMBlocks.PALM_PLANKS_SIGN.get(),3,DMBlocks.PALM_PLANKS.get(),Items.STICK));
        addRecipe(boat(DMItems.PALM_PLANKS_BOAT.get(),1,DMItems.PALM_PLANKS.get()));
        addRecipe(trapdoor(DMBlocks.PALM_PLANKS_TRAPDOOR.get(),2,DMBlocks.PALM_PLANKS.get()));
        addRecipe(pressurePlate(DMBlocks.PALM_PLANKS_PRESSURE_PLATE.get(),1,DMBlocks.PALM_PLANKS.get()));
        addRecipe(oneToOne(DMBlocks.PALM_PLANKS_BUTTON.get(),1,DMBlocks.PALM_PLANKS.get()));
        addRecipe(oneToOne(DMBlocks.PALM_PLANKS.get(),4,DMBlocks.PALM_LOG_STRIPPED.get()).name("desertmania:palm_planks_from_stripped_log"));
        addRecipe(oneToOne(DMBlocks.PALM_PLANKS.get(),4,DMBlocks.PALM_LOG.get()).name("desertmania:palm_planks_from_log"));
        addRecipe(oneToOne(DMBlocks.PALM_PLANKS.get(),4,DMBlocks.PALM_WOOD.get()).name("desertmania:palm_planks_from_wood"));
        addRecipe(oneToOne(DMBlocks.PALM_PLANKS.get(),4,DMBlocks.PALM_WOOD_STRIPPED.get()).name("desertmania:palm_planks_from_stripped_wood"));
        addRecipe(shapeless(DMItems.PALM_PLANKS_CHEST_BOAT.get(),1,DMItems.PALM_PLANKS_BOAT.get(),Items.CHEST));
    }

    private void slabs(){
        addRecipe(slab(DMBlocks.SMOOTH_LOESS_SLAB.get(),6,DMBlocks.SMOOTH_LOESS.get()));
        addRecipe(slab(DMBlocks.LOESS_BRICKS_SLAB.get(),6,DMBlocks.LOESS_BRICKS.get()));
        addRecipe(slab(DMBlocks.CUT_LOESS_SLAB.get(),6,DMBlocks.CUT_LOESS.get()));
    }

    private void smelt(){
        addRecipe(smeltingOrCooking(DMBlocks.DUNE_SAND.get(),1, Blocks.GLASS,200,0.3f));
        addRecipe(smeltingOrCooking(DMBlocks.SMOOTH_LOESS.get(),1,DMBlocks.LOESS.get(),200,0.3f));
    }

    private void other(){
        addRecipe(shapeless(DMItems.GOLDEN_SCARAB.get(),1,DMItems.GOLDEN_SCARAB_LEFT_PART.get(),DMItems.GOLDEN_SCARAB_RIGHT_PART.get()));
        addRecipe(shaped(DMItems.SHEMAGH.get(),1,new RecipePattern("###","# #","###").put("#",DMItems.CLOTH.get())));
        addRecipe(fourBlock(DMBlocks.LOESS.get(), 1,DMBlocks.DUNE_SAND.get()));
        addRecipe(fourBlock(DMBlocks.GIANT_LOESS_BRICK.get(),1,DMBlocks.LOESS_BRICKS.get()));
        addRecipe(fourBlock(DMBlocks.CUT_LOESS.get(),4,DMBlocks.LOESS.get()));
        addRecipe(stairs(DMBlocks.SMOOTH_LOESS_STAIRS.get(),4,DMBlocks.SMOOTH_LOESS.get()));
        addRecipe(stairs(DMBlocks.LOESS_BRICKS_STAIRS.get(),4,DMBlocks.LOESS_BRICKS.get()));
        addRecipe(stairs(DMBlocks.CUT_LOESS_STAIRS.get(),4,DMBlocks.CUT_LOESS.get()));
        addRecipe(wall(DMBlocks.SMOOTH_LOESS_WALL.get(),6,DMBlocks.SMOOTH_LOESS.get()));
        addRecipe(wall(DMBlocks.LOESS_BRICKS_WALL.get(),6,DMBlocks.LOESS_BRICKS.get()));
        addRecipe(wall(DMBlocks.CUT_LOESS_WALL.get(),6,DMBlocks.CUT_LOESS.get()));
        addRecipe(chiseled(DMBlocks.CHISELED_LOESS.get(),1,DMBlocks.SMOOTH_LOESS_SLAB.get()));
        addRecipe(shapeless(DMItems.STING_ARROW.get(),4,DMItems.STING.get(),Items.ARROW));
        addRecipe(shaped(DMItems.BASKET.get(),1,new RecipePattern("# #","# #","---").put("#",DMBlocks.PALM_PLANKS.get()).put("-",DMBlocks.PALM_PLANKS_SLAB.get())));
    }
}
