package dev.fumaz.commons.bukkit.gui.page;

import com.google.common.collect.ImmutableList;
import dev.fumaz.commons.bukkit.gui.Gui;
import dev.fumaz.commons.bukkit.gui.item.ClickableGuiItem;
import dev.fumaz.commons.bukkit.gui.item.DisplayGuiItem;
import dev.fumaz.commons.bukkit.gui.item.EmptyGuiItem;
import dev.fumaz.commons.bukkit.gui.item.GuiItem;
import dev.fumaz.commons.bukkit.item.ColorableMaterials;
import dev.fumaz.commons.bukkit.item.ItemBuilder;
import dev.fumaz.commons.bukkit.item.MaterialColors;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A page that can have a large amount of items and<br>
 * the user can cycle through them as if they were in multiple pages<br>
 * <br>
 * <b>A single instance shouldn't be used for multiple players!</b><br>
 * TODO: Add fillPage() and fillEmptyPage()
 */
public class ListPage extends Page {

    public final static GuiItem DEFAULT_FILLER = new DisplayGuiItem(ColorableMaterials.STAINED_GLASS_PANE.withColor(MaterialColors.LIGHT_GRAY));

    public final static ItemStack DEFAULT_PREVIOUS = ItemBuilder.copy(ColorableMaterials.STAINED_GLASS_PANE.withColor(MaterialColors.PINK))
            .displayName(ChatColor.LIGHT_PURPLE + "Previous Page")
            .build();
    public final static ItemStack DEFAULT_NEXT = ItemBuilder.copy(ColorableMaterials.STAINED_GLASS_PANE.withColor(MaterialColors.PINK))
            .displayName(ChatColor.LIGHT_PURPLE + "Next Page")
            .build();

    private final GuiItem filler;
    private final List<GuiItem> items;
    private final Map<Integer, GuiItem> pinnedItems;
    private int page;
    private boolean infinite;
    private GuiItem back;
    private ItemStack previousPage;
    private ItemStack nextPage;

    public ListPage(Gui gui) {
        this(gui, DEFAULT_FILLER);
    }

    public ListPage(Gui gui, GuiItem filler) {
        this(gui, filler, null);
    }

    public ListPage(Gui gui, GuiItem filler, GuiItem back) {
        super(gui);

        this.filler = filler;
        this.back = back;
        this.items = new ArrayList<>();
        this.pinnedItems = new HashMap<>(getSlots());
        this.page = 0;
        this.infinite = false;
        this.previousPage = DEFAULT_PREVIOUS;
        this.nextPage = DEFAULT_NEXT;
    }

    public void setBack(@Nullable GuiItem back) {
        this.back = back;
    }

    public void setPreviousPage(ItemStack previousPage) {
        this.previousPage = previousPage;
    }

    public void setNextPage(ItemStack nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isInfinite() {
        return infinite;
    }

    public void setInfinite(boolean infinite) {
        this.infinite = infinite;
    }

    @Override
    public void onClose(@Nullable Player player) {
        super.onClose(player);
        page = 0;
    }

    @Override
    public void addItem(@Nullable GuiItem item) {
        items.add(item);
    }

    @Override
    public void removeItem(@NotNull GuiItem item) {
        items.removeIf(item::equals);
    }

    @Override
    public void removeItemExact(@Nullable GuiItem item) {
        items.removeIf(other -> other == item);
    }

    @Unmodifiable
    @NotNull
    public List<GuiItem> getAllItems() {
        return ImmutableList.copyOf(items);
    }

    public void setPinnedItem(int slot, @Nullable GuiItem item) {
        pinnedItems.put(slot, item);
    }

    public void setPinnedItem(int row, int column, @Nullable GuiItem item) {
        setPinnedItem((row * 9) + column, item);
    }

    public void addPinnedItem(@Nullable GuiItem item) {
        if (getFirstEmpty() == -1) {
            return;
        }

        pinnedItems.put(getFirstEmpty(), item);
    }

    public void removePinnedItem(int slot) {
        setPinnedItem(slot, null);
    }

    public void removePinnedItem(@NotNull GuiItem item) {
        pinnedItems.entrySet()
                .stream()
                .filter(entry -> item.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .forEach(pinnedItems::remove);
    }

    public void removePinnedItemExact(@Nullable GuiItem item) {
        pinnedItems.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == item)
                .map(Map.Entry::getKey)
                .forEach(pinnedItems::remove);
    }

    public void clearPinnedItems() {
        pinnedItems.clear();
    }

    public int getFirstPinnedEmpty() {
        return IntStream.range(0, getSlots())
                .filter(this::isPinnedEmpty)
                .findFirst()
                .orElse(-1);
    }

    public GuiItem getPinnedItem(int slot) {
        return pinnedItems.get(slot);
    }

    public boolean isPinnedEmpty(int slot) {
        return getPinnedItem(slot) == null;
    }

    @Override
    public void clear() {
        items.clear();
    }

    @Override
    public int getFirstEmpty() {
        return items.size();
    }

    public void setItems(List<GuiItem> items) {
        this.items.clear();
        this.items.addAll(items);
    }

    public void setItems(GuiItem... items) {
        this.items.clear();
        this.items.addAll(Arrays.asList(items));
    }

    public List<GuiItem> getPage(int page) {
        return items.subList(page * getItemSlots(), Math.min((page * getItemSlots()) + getItemSlots(), items.size()));
    }

    public int getItemSlots() {
        int slots = getSlots();

        Set<Integer> filler = getFillerSlots();
        filler.addAll(pinnedItems.entrySet()
                .stream().filter(entry -> entry.getValue() != null)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet()));

        return getSlots() - filler.size();
    }

    public int getPages() {
        if (items.size() % getItemSlots() == 0) {
            return items.size() / getItemSlots();
        }

        return (items.size() / getItemSlots()) + 1;
    }

    public void previousPage(Inventory inventory) {
        page = Math.max(page - 1, 0);
        updateInventory(inventory);
    }

    public void nextPage(Inventory inventory) {
        page = Math.min(page + 1, getPages() - 1);
        updateInventory(inventory);
    }

    @Override
    public void updateInventory(Inventory inventory) {
        super.clear();

        getFillerSlots().forEach(slot -> super.setItem(slot, filler));

        if (back != null) {
            super.setItem(4, back);
        }

        pinnedItems.forEach(super::setItem);

        if (page > 0 || (getPages() > 1 && isInfinite())) {
            super.setItem(0, new ClickableGuiItem(previousPage, event -> previousPage(inventory)));
        }

        if (page < (getPages() - 1) || (getPages() > 1 && isInfinite())) {
            super.setItem(8, new ClickableGuiItem(nextPage, event -> nextPage(inventory)));
        }

        List<GuiItem> i = getPage(page);
        i.forEach(item -> {
            // For some reason simply calling super#addItem() doesn't work. I'm not sure why.
            super.setItem(super.getFirstEmpty(), item);
        });

        super.updateInventory(inventory);
    }

    private Set<Integer> getFillerSlots() {
        int total = getSlots();
        int rows = total / 9;

        if (total <= 9 || (filler == null || filler instanceof EmptyGuiItem)) {
            return Collections.emptySet();
        }

        Set<Integer> slots = new HashSet<>();
        IntStream.range(0, rows).forEach(row -> {
            IntStream.range(0, 9).forEach(column -> {
                if (row != 0 && row != (rows - 1) && column != 0 && column != 8) {
                    return;
                }

                slots.add((row * 9) + column);
            });
        });

        return slots;
    }

}
