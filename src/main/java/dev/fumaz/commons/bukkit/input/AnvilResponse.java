package dev.fumaz.commons.bukkit.input;

import org.bukkit.entity.Player;

public interface AnvilResponse {

    static AnvilResponse close() {
        return new AnvilResponseClose();
    }

    void execute(AnvilInput input, Player player);

}
