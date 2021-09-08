package dev.fumaz.commons.bukkit.interfaces;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * A class that has an {@link ItemStack}
 */
public interface Item extends Supplier<ItemStack> {

    @NotNull
    ItemStack getItemStack();

    @Override
    default ItemStack get() {
       return getItemStack();
    }

    // TODO Implement isItem, hasItem, etc methods

}
