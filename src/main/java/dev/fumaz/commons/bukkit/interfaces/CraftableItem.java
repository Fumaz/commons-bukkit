package dev.fumaz.commons.bukkit.interfaces;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link KeyedItem} that can be crafted with a {@link Recipe}
 */
public interface CraftableItem extends KeyedItem {

    @NotNull
    Recipe getRecipe();

    default void unregister() {
        Bukkit.removeRecipe(getKey());
    }

    default void register() {
        unregister(); // We don't Bukkit to throw a fit in case of a dupe
        Bukkit.addRecipe(getRecipe());
    }

}
