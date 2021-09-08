package dev.fumaz.commons.bukkit.recipe;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * This class is used to get a displayable array for a recipe
 *
 * @param <T> the type of recipe
 */
public abstract class RecipeDisplayer<T extends Recipe> {

    protected final T recipe;

    public RecipeDisplayer(T recipe) {
        this.recipe = recipe;
    }

    /**
     * Creates a {@link RecipeDisplayer<T>} for a given {@link Recipe}
     *
     * @param recipe the {@link Recipe}
     * @param <T>    the type of {@link Recipe}
     * @return the {@link RecipeDisplayer<T>}
     */
    public static <T extends Recipe> RecipeDisplayer<?> create(T recipe) {
        if (recipe instanceof ShapedRecipe) {
            return new ShapedRecipeDisplayer((ShapedRecipe) recipe);
        }

        if (recipe instanceof ShapelessRecipe) {
            return new ShapelessRecipeDisplayer((ShapelessRecipe) recipe);
        }

        throw new IllegalArgumentException("Cannot display recipe type: " + recipe.getClass().getSimpleName());
    }

    /**
     * Returns a list of ingredients for a recipe, including empty slots<br>
     * Most of the time this should be a square array
     *
     * @return the ingredients
     */
    @NotNull
    public abstract ItemStack[] getIngredients();

    /**
     * Returns a list of ingredients for a recipe, including empty slots<br>
     * Most of the time this should be a square array
     *
     * @return the ingredients
     */
    @NotNull
    public List<ItemStack> getIngredientList() {
        return Arrays.asList(getIngredients());
    }

    /**
     * Returns the result of the recipe
     *
     * @return the result
     */
    @NotNull
    public ItemStack getResult() {
        return recipe.getResult();
    }

}
