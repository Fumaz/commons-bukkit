package dev.fumaz.commons.bukkit.gui.item;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * An empty {@link GuiItem}
 */
public final class EmptyGuiItem implements GuiItem {

    private final static EmptyGuiItem INSTANCE = new EmptyGuiItem();

    private EmptyGuiItem() {
    }

    public static EmptyGuiItem get() {
        return INSTANCE;
    }

    @Override
    public @Nullable ItemStack getItemStack() {
        return null;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
    }

    @Override
    public boolean equals(Object other) {
        return other == this;
    }

}
