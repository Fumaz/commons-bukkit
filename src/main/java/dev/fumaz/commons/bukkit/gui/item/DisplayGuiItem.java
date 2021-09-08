package dev.fumaz.commons.bukkit.gui.item;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * A {@link GuiItem} that has no click handlers, but may be interacted with.
 */
public class DisplayGuiItem implements GuiItem {

    private final Supplier<ItemStack> supplier;
    private final boolean interactable;

    public DisplayGuiItem(@Nullable ItemStack itemStack) {
        this(itemStack, false);
    }

    public DisplayGuiItem(@NotNull Supplier<ItemStack> supplier) {
        this(supplier, false);
    }

    public DisplayGuiItem(@Nullable ItemStack itemStack, boolean interactable) {
        this.supplier = () -> itemStack;
        this.interactable = interactable;
    }

    public DisplayGuiItem(@NotNull Supplier<ItemStack> supplier, boolean interactable) {
        this.supplier = supplier;
        this.interactable = interactable;
    }

    @Nullable
    @Override
    public ItemStack getItemStack() {
        return supplier.get();
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (interactable) {
            return;
        }

        event.setCancelled(true);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof DisplayGuiItem)) {
            return false;
        }

        DisplayGuiItem item = (DisplayGuiItem) object;

        if (item.interactable != interactable) {
            return false;
        }

        ItemStack currentItemStack = getItemStack();
        ItemStack otherItemStack = item.getItemStack();

        if (currentItemStack == otherItemStack) {
            return true;
        }

        if (currentItemStack == null || otherItemStack == null) {
            return false;
        }

        return currentItemStack.isSimilar(otherItemStack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplier, interactable);
    }

    @Override
    public String toString() {
        return "DisplayGuiItem{" +
                "supplier=" + supplier +
                ", interactable=" + interactable +
                ", item=" + supplier.get() +
                '}';
    }

}
