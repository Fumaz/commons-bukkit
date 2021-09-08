package dev.fumaz.commons.bukkit.gui.item;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * An item that can be displayed in a GUI Page
 */
public interface GuiItem {

    static GuiItemBuilder builder() {
        return new GuiItemBuilder();
    }

    /**
     * @return the itemstack that will be displayed in the GUI
     */
    @Nullable
    ItemStack getItemStack();

    /**
     * Handles a click event for this item
     *
     * @param event the click event
     */
    void onClick(InventoryClickEvent event);

}
