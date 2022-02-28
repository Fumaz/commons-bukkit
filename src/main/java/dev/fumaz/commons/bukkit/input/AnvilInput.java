package dev.fumaz.commons.bukkit.input;

import dev.fumaz.commons.bukkit.interfaces.FListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

public class AnvilInput implements FListener {

    private final AnvilInventory inventory;
    private ItemStack item;
    private Consumer<String> consumer;

    public AnvilInput(JavaPlugin plugin) {
        this.inventory = (AnvilInventory) Bukkit.createInventory(null, InventoryType.ANVIL);
        this.item = new ItemStack(Material.PAPER, 1);

        register(plugin);
        setup();
    }

    public AnvilInput setItem(ItemStack item) {
        this.item = item;
        setup();

        return this;
    }

    public void show(Player player, Consumer<String> consumer) {
        this.consumer = consumer;
        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory() != inventory) {
            return;
        }

        unregister();
    }

    @EventHandler
    public void onRename(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        InventoryView view = event.getView();

        if (view.getTopInventory() != inventory) {
            return;
        }

        if (event.getSlot() != 2) {
            event.setCancelled(true);
            return;
        }

        String name = ((AnvilInventory) event.getView().getTopInventory()).getRenameText();
        consumer.accept(name);
    }

    private void setup() {
        inventory.clear();
        inventory.setFirstItem(item);
    }

}
