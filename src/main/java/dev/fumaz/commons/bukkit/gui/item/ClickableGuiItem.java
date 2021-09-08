package dev.fumaz.commons.bukkit.gui.item;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A {@link GuiItem} that has a consumer as its click handler.
 */
public class ClickableGuiItem implements GuiItem {

    private final Supplier<ItemStack> supplier;
    private final Consumer<InventoryClickEvent> consumer;

    public ClickableGuiItem(@Nullable ItemStack itemStack, @NotNull Consumer<InventoryClickEvent> consumer) {
        this(() -> itemStack, consumer);
    }

    public ClickableGuiItem(@NotNull Supplier<ItemStack> supplier, @NotNull Consumer<InventoryClickEvent> consumer) {
        this.supplier = supplier;
        this.consumer = consumer;
    }

    @Override
    public @Nullable ItemStack getItemStack() {
        return supplier.get();
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        consumer.accept(event);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof ClickableGuiItem)) {
            return false;
        }

        ClickableGuiItem clickable = (ClickableGuiItem) object;

        if (!consumer.equals(clickable.consumer)) {
            return false;
        }

        ItemStack first = getItemStack();
        ItemStack second = clickable.getItemStack();

        if (first == second) {
            return true;
        }

        if (first == null || second == null) {
            return false;
        }

        return first.isSimilar(second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplier, consumer);
    }

    @Override
    public String toString() {
        return "ClickableGuiItem{" +
                "supplier=" + supplier +
                ", consumer=" + consumer +
                ", item=" + supplier.get() +
                '}';
    }

}
