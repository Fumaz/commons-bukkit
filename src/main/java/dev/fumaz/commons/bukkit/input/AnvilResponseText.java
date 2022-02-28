package dev.fumaz.commons.bukkit.input;

import org.bukkit.entity.Player;

public class AnvilResponseText implements AnvilResponse{

    private final String text;

    public AnvilResponseText(String text) {
        this.text = text;
    }

    @Override
    public void execute(AnvilInput input, Player player) {
        input.text(text);
        input.open(player);
    }

}
