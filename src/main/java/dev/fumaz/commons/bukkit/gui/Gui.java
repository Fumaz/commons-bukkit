package dev.fumaz.commons.bukkit.gui;

import dev.fumaz.commons.bukkit.gui.item.GuiItem;
import dev.fumaz.commons.bukkit.gui.page.Page;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * An inventory that can be displayed to players and interacted with.
 */
public class Gui implements Listener {

    private final JavaPlugin plugin;
    private final Inventory inventory;
    private final String title;
    private final int slots;

    private boolean interactable;
    private boolean registered;
    private Page page;

    public Gui(JavaPlugin plugin, String name, InventoryType type) {
        this(plugin, name, type, false);
    }

    public Gui(JavaPlugin plugin, String name, InventoryType type, boolean interactable) {
        this(plugin, name, type.getDefaultSize(), interactable, Bukkit.createInventory(null, type.getDefaultSize(), name));
    }

    public Gui(JavaPlugin plugin, String name, int rows) {
        this(plugin, name, rows, false);
    }

    public Gui(JavaPlugin plugin, String name, int rows, boolean interactable) {
        this(plugin, name, rows * 9, interactable, Bukkit.createInventory(null, rows * 9, name));
    }

    public Gui(JavaPlugin plugin, String name, int slots, boolean interactable, Inventory inventory) {
        this.plugin = plugin;
        this.title = name;
        this.slots = slots;
        this.interactable = interactable;
        this.inventory = inventory;
        this.registered = false;
    }

    public boolean isInteractable() {
        return interactable;
    }

    public void setInteractable(boolean interactable) {
        this.interactable = interactable;
    }

    public String getTitle() {
        return title;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getSlots() {
        return slots;
    }

    public int getRows() {
        return slots / 9;
    }

    public void setPage(Page page) {
        getViewers().forEach(page::onClose);
        page.onClose(null);

        inventory.clear();
        this.page = page;
        this.page.updateInventory(inventory);
    }

    public void show(Player player) {
        if (!registered) {
            Bukkit.getPluginManager().registerEvents(this, plugin);
            registered = true;
        }

        player.openInventory(inventory);
        player.updateInventory();
    }

    public List<Player> getViewers() {
        return inventory.getViewers()
                .stream()
                .map(viewer -> (Player) viewer)
                .collect(Collectors.toList());
    }

    public boolean hasViewers() {
        return !inventory.getViewers().isEmpty();
    }

    public void update() {
        page.updateInventory(inventory);
    }

    public boolean isInventory(@Nullable Inventory inventory) {
        return this.inventory.equals(inventory);
    }

    public boolean isInventory(@NotNull InventoryView view) {
        return isInventory(view.getTopInventory()) || isInventory(view.getBottomInventory());
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!isInventory(event.getView())) {
            return;
        }

        page.onClose((Player) event.getPlayer());

        if (hasViewers()) {
            return;
        }

        HandlerList.unregisterAll(this);
        registered = false;
    }

    @EventHandler(ignoreCancelled = true)
    public void onClick(InventoryClickEvent event) {
        if (!isInventory(event.getView())) {
            return;
        }

        int slot = event.getRawSlot();

        if (slot >= getSlots() && interactable) {
            return;
        }

        if (!interactable) {
            event.setCancelled(true);
        }

        GuiItem item = page.getItem(slot);
        if (item == null) {
            return;
        }

        item.onClick(event);
    }

    @EventHandler(ignoreCancelled = true)
    public void onDrag(InventoryDragEvent event) {
        if (!isInventory(event.getView())) {
            return;
        }

        if (interactable) {
            return;
        }

        event.setCancelled(true);
    }

}
