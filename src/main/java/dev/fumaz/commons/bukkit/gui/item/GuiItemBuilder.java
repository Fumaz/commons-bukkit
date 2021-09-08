package dev.fumaz.commons.bukkit.gui.item;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A builder for different implementations of {@link GuiItem}
 */
public class GuiItemBuilder {

    private Supplier<ItemStack> itemSupplier;
    private Consumer<InventoryClickEvent> eventConsumer;
    private boolean interactable = false;

    public GuiItemBuilder() {
    }

    public GuiItemBuilder item(@Nullable ItemStack item) {
        if (item == null) {
            return item((Supplier<ItemStack>) null);
        }

        return item(() -> item);
    }

    public GuiItemBuilder item(@Nullable Supplier<ItemStack> supplier) {
        this.itemSupplier = supplier;
        return this;
    }

    public GuiItemBuilder onClick(@Nullable Consumer<InventoryClickEvent> consumer) {
        this.eventConsumer = consumer;
        return this;
    }

    public GuiItemBuilder interactable(boolean interactable) {
        this.interactable = interactable;
        return this;
    }

    public GuiItemBuilder interactable() {
        return interactable(true);
    }

    public GuiItem build() {
        if (itemSupplier == null) {
            return EmptyGuiItem.get();
        }

        if (eventConsumer == null) {
            return new DisplayGuiItem(itemSupplier, interactable);
        }

        return new ClickableGuiItem(itemSupplier, eventConsumer);
    }

}
