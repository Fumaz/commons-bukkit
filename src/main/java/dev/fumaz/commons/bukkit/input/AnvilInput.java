package dev.fumaz.commons.bukkit.input;

import dev.fumaz.commons.bukkit.interfaces.FListener;
import dev.fumaz.commons.bukkit.item.ItemBuilder;
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

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class AnvilInput implements FListener {

    private AnvilInventory inventory;
    private String title;
    private String text;
    private ItemStack itemLeft;
    private ItemStack itemRight;
    private BiFunction<Player, String, AnvilResponse> onComplete;
    private BiConsumer<Player, AnvilInventory> onClose;
    private BiConsumer<Player, AnvilInventory> onLeftClickInput;
    private BiConsumer<Player, AnvilInventory> onRightClickInput;
    private boolean preventClose;

    public AnvilInput(JavaPlugin plugin) {
        this.itemLeft = new ItemStack(Material.PAPER, 1);
        this.title = null;
        this.onComplete = null;
    }

    public AnvilInput title(String title) {
        this.title = title;
        return this;
    }

    public AnvilInput onComplete(BiFunction<Player, String, AnvilResponse> onComplete) {
        this.onComplete = onComplete;
        return this;
    }

    public AnvilInput preventClose() {
        this.preventClose = true;
        return this;
    }

    public AnvilInput allowClose() {
        this.preventClose = false;
        return this;
    }

    public AnvilInput itemLeft(ItemStack item) {
        this.itemLeft = item;

        return this;
    }

    public AnvilInput itemRight(ItemStack item) {
        this.itemRight = item;

        return this;
    }

    public AnvilInput text(String text) {
        this.text = text;
        return this;
    }

    public AnvilInput onLeftInputClick(BiConsumer<Player, AnvilInventory> onLeftClickInput) {
        this.onLeftClickInput = onLeftClickInput;
        return this;
    }

    public AnvilInput onRightInputClick(BiConsumer<Player, AnvilInventory> onRightClickInput) {
        this.onRightClickInput = onRightClickInput;
        return this;
    }

    public void close(Player player) {
        player.closeInventory();

        if (inventory != null && inventory.getViewers().isEmpty()) {
            unregister();
        }
    }

    public void open(Player player) {
        close(player);

        inventory = (AnvilInventory) Bukkit.createInventory(null, InventoryType.ANVIL, title);
        inventory.setFirstItem(itemLeft);
        inventory.setSecondItem(itemRight);
        inventory.setRepairCost(0);
        inventory.setMaximumRepairCost(0);

        if (text != null) {
            inventory.setFirstItem(ItemBuilder.copy(itemLeft)
                    .displayName(text)
                    .build());
        }

        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (inventory == null) {
            return;
        }

        if (event.getInventory() != inventory) {
            return;
        }

        if (onClose != null) {
            onClose.accept((Player) event.getPlayer(), (AnvilInventory) event.getInventory());
        }

        if (preventClose) {
            event.getPlayer().openInventory(event.getInventory());
            return;
        }

        unregister();
    }

    @EventHandler
    public void onRename(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        InventoryView view = event.getView();

        if (inventory == null) {
            return;
        }

        if (view.getTopInventory() != inventory) {
            return;
        }

        if (event.getSlot() != 2) {
            event.setCancelled(true);
            return;
        }

        String text = ((AnvilInventory) event.getView().getTopInventory()).getRenameText();
        AnvilResponse response = onComplete.apply(player, text);
        response.execute(this, player);
    }

}
