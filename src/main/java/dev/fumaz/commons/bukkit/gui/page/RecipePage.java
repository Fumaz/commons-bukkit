package dev.fumaz.commons.bukkit.gui.page;

import com.google.common.collect.Iterators;
import dev.fumaz.commons.bukkit.gui.Gui;
import dev.fumaz.commons.bukkit.gui.item.DisplayGuiItem;
import dev.fumaz.commons.bukkit.gui.item.GuiItem;
import dev.fumaz.commons.bukkit.recipe.RecipeDisplayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

/**
 * A page that displays a {@link Recipe}
 */
public class RecipePage extends Page {

    private final Recipe recipe;
    private final GuiItem filler;
    private final RecipeDisplayer<?> displayer;
    private GuiItem back;

    public RecipePage(Gui gui, Recipe recipe, GuiItem filler, GuiItem back) {
        super(gui);
        this.recipe = recipe;
        this.displayer = RecipeDisplayer.create(recipe);
        this.filler = filler;
        this.back = back;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setBack(@Nullable GuiItem back) {
        this.back = back;
    }

    @Override
    public void updateInventory(Inventory inventory) {
        super.fill(filler);

        if (back != null) {
            super.setItem(49, back);
        }

        int[] slots = new int[]{
                11, 12, 13,
                20, 21, 22,
                29, 30, 31
        };

        Iterator<ItemStack> ingredients = Iterators.forArray(displayer.getIngredients());
        for (int slot : slots) {
            ItemStack ingredient = ingredients.hasNext() ? ingredients.next() : null;

            super.setItem(slot, new DisplayGuiItem(ingredient));
        }

        super.setItem(24, new DisplayGuiItem(displayer.getResult()));
        super.updateInventory(inventory);
    }

}
