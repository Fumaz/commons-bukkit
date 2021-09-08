package dev.fumaz.commons.bukkit.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public final class Inventories {

    public static void updateForEveryone(Inventory inventory) {
        inventory.getViewers().forEach(viewer -> ((Player) viewer).updateInventory());
    }

}
