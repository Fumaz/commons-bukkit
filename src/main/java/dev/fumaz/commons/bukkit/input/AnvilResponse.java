package dev.fumaz.commons.bukkit.input;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface AnvilResponse {

    static AnvilResponse close() {
        return new AnvilResponseClose();
    }

    static AnvilResponse text(String text) {
        return new AnvilResponseText(text);
    }

    static AnvilResponse openInventory(Inventory inventory) {
        return new AnvilResponseOpenInventory(inventory);
    }

    void execute(AnvilInput input, Player player);

}
