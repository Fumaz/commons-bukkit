package dev.fumaz.commons.bukkit.recipe;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

/**
 * This displayer is for a {@link ShapelessRecipe}.<br>
 * Since this type of recipe is shapeless, we simply resort to placing all the ingredients one after another,
 * and leaving the remaining slots empty
 */
public class ShapelessRecipeDisplayer extends RecipeDisplayer<ShapelessRecipe> {

    public ShapelessRecipeDisplayer(ShapelessRecipe recipe) {
        super(recipe);
    }

    @Override
    public ItemStack[] getIngredients() {
        ItemStack[] ingredients = new ItemStack[9];

        for (int i = 0; i < recipe.getIngredientList().size(); i++) {
            ingredients[i] = recipe.getIngredientList().get(i);
        }

        return ingredients;
    }

}
