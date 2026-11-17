package com.vomiter.mobsshields.data;

import com.vomiter.mobsshields.common.ShieldItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public final class ShieldRecipes extends RecipeProvider {
    public ShieldRecipes(PackOutput output) { super(output); }
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> recipes) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ShieldItems.HOGLIN_SHIELD.get())
                .pattern("BLB").pattern("LSL").pattern(" L ")
                .define('B', Items.BONE).define('L', Items.LEATHER).define('S', Items.SHIELD)
                .unlockedBy("has_shield", has(Items.SHIELD)).save(recipes);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ShieldItems.SQUID_SHIELD.get())
                .pattern("CIC").pattern("ISI").pattern(" C ")
                .define('C', Items.COPPER_INGOT).define('I', Items.INK_SAC).define('S', Items.SHIELD)
                .unlockedBy("has_ink_sac", has(Items.INK_SAC)).save(recipes);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ShieldItems.GOLDEN_HOGLIN_SHIELD.get())
                .pattern("GGG").pattern("GSG").pattern(" G ")
                .define('G', Tags.Items.INGOTS_GOLD).define('S', ShieldItems.HOGLIN_SHIELD.get())
                .unlockedBy("has_hoglin_shield", has(ShieldItems.HOGLIN_SHIELD.get())).save(recipes);
    }
}
