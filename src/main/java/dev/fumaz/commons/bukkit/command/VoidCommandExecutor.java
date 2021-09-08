package dev.fumaz.commons.bukkit.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link CommandExecutor} that always returns true<br>
 * This is useful because most of the time, you don't need bukkit's default behavior<br>
 * and will implement your own permissions/syntax system
 */
public interface VoidCommandExecutor extends CommandExecutor {

    @Override
    default boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        onCommand(sender, command, args);

        return true;
    }

    void onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String[] args);

}
