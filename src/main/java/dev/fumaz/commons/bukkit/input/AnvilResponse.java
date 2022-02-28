package dev.fumaz.commons.bukkit.input;

import org.bukkit.entity.Player;

public interface AnvilResponse {

    static AnvilResponse close() {
        return new AnvilResponseClose();
    }

    static AnvilResponse text(String text) {
        return new AnvilResponseText(text);
    }

    void execute(AnvilInput input, Player player);

}
