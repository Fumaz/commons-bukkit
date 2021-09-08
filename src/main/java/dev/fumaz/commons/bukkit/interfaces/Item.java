package dev.fumaz.commons.bukkit.interfaces;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * A class that has an {@link ItemStack}
 */
public interface Item {

    @NotNull
    ItemStack getItemStack();

    // TODO Implement isItem, hasItem, etc methods

}
