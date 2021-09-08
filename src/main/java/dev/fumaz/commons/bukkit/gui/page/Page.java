package dev.fumaz.commons.bukkit.gui.page;

import dev.fumaz.commons.bukkit.gui.Gui;
import dev.fumaz.commons.bukkit.gui.Inventories;
import dev.fumaz.commons.bukkit.gui.item.EmptyGuiItem;
import dev.fumaz.commons.bukkit.gui.item.GuiItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A page that can be displayed in a GUI
 */
public class Page {

    private final Gui gui;
    private final Map<Integer, GuiItem> items;

    private Consumer<Player> onClose = player -> {
    };

    public Page(Gui gui) {
        this.gui = gui;
        this.items = new HashMap<>(gui.getSlots());
    }

    @Unmodifiable
    public Map<Integer, GuiItem> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public void setItems(Map<Integer, GuiItem> items) {
        this.items.clear();
        this.items.putAll(items);
    }

    public Gui getGui() {
        return gui;
    }

    public void setItem(int slot, @Nullable GuiItem item) {
        items.put(slot, item);
    }

    public void setItem(int row, int column, @Nullable GuiItem item) {
        setItem((row * 9) + column, item);
    }

    public void addItem(@Nullable GuiItem item) {
        if (getFirstEmpty() == -1) {
            return;
        }

        items.put(getFirstEmpty(), item);
    }

    public void removeItem(int slot) {
        setItem(slot, null);
    }

    public void removeItem(int row, int column) {
        setItem(row, column, null);
    }

    public void removeItem(@NotNull GuiItem item) {
        items.entrySet()
                .stream()
                .filter(entry -> item.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .forEach(items::remove);
    }

    public void removeItemExact(@Nullable GuiItem item) {
        items.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == item)
                .map(Map.Entry::getKey)
                .forEach(items::remove);
    }

    public void fill(GuiItem item) {
        IntStream.range(0, getSlots())
                .forEach(slot -> setItem(slot, item));
    }

    public void fillEmpty(GuiItem item) {
        IntStream.range(0, getSlots())
                .filter(this::isEmpty)
                .forEach(slot -> setItem(slot, item));
    }

    public void clear() {
        fill(null);
    }

    @Nullable
    public GuiItem getItem(int slot) {
        return items.get(slot);
    }

    @Nullable
    public GuiItem getItem(int row, int column) {
        return getItem((row * 9) + column);
    }

    public boolean isEmpty(int slot) {
        return getItem(slot) == null;
    }

    public int getFirstEmpty() {
        return IntStream.range(0, getSlots())
                .filter(this::isEmpty)
                .findFirst()
                .orElse(-1);
    }

    public void setOnClose(Consumer<Player> consumer) {
        onClose = consumer;
    }

    public List<Integer> getEmptySlots() {
        return IntStream.range(0, getSlots())
                .filter(this::isEmpty)
                .boxed()
                .collect(Collectors.toList());
    }

    public int getSlots() {
        return gui.getSlots();
    }

    public void updateInventory(Inventory inventory) {
        IntStream.range(0, getSlots())
                .forEach(slot -> {
                    GuiItem item = getItem(slot);

                    if (item == null || item instanceof EmptyGuiItem) {
                        inventory.setItem(slot, null);
                        return;
                    }

                    inventory.setItem(slot, item.getItemStack());
                });

        Inventories.updateForEveryone(inventory);
    }

    public void onClose(@Nullable Player player) {
        onClose.accept(player);
    }

}
