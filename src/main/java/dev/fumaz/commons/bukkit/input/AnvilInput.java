package dev.fumaz.commons.bukkit.input;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AnvilInput {

    private final AnvilInventory inventory;
    private ItemStack item;

    public AnvilInput() {
        this.inventory = (AnvilInventory) Bukkit.createInventory(null, InventoryType.ANVIL);
        this.item = new ItemStack(Material.PAPER, 1);
    }

    private void setup() {
        inventory.clear();
        inventory.setFirstItem(item);
    }
     
}
