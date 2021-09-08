package dev.fumaz.commons.bukkit.recipe;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

/**
 * This displayer is for a {@link ShapedRecipe}, the simplest kind of recipe.<br>
 */
public class ShapedRecipeDisplayer extends RecipeDisplayer<ShapedRecipe> {

    public ShapedRecipeDisplayer(ShapedRecipe recipe) {
        super(recipe);
    }

    @Override
    public ItemStack[] getIngredients() {
        ItemStack[] ingredients = new ItemStack[9];
        String[] rows = recipe.getShape();

        for (int row = 0; row < 3; row++) {
            char[] chars = rows.length > row ? rows[row].toCharArray() : new char[0];

            for (int column = 0; column < 3; column++) {
                Character c = chars.length > column ? chars[column] : null;

                if (c == null) {
                    ingredients[(row * 3) + column] = null;
                    continue;
                }

                ItemStack mapped = recipe.getIngredientMap().get(c);
                ingredients[(row * 3) + column] = mapped;
            }
        }

        return ingredients;
    }

}
