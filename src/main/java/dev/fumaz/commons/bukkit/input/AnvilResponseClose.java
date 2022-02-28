package dev.fumaz.commons.bukkit.input;

import org.bukkit.entity.Player;

public class AnvilResponseClose implements AnvilResponse {

    @Override
    public void execute(AnvilInput input, Player player) {
        input.allowClose();
        input.close(player);
    }

}
