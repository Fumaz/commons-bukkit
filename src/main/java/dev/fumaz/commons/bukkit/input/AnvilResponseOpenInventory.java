package dev.fumaz.commons.bukkit.input;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class AnvilResponseOpenInventory implements AnvilResponse{

    private final Inventory inventory;

    public AnvilResponseOpenInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void execute(AnvilInput input, Player player) {
        input.allowClose();
        input.close(player);
        player.openInventory(inventory);
    }

}
