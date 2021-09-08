package dev.fumaz.commons.bukkit.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * A command that only {@link ConsoleCommandSender} can use
 */
public interface ConsoleCommandExecutor extends VoidCommandExecutor {

    @Override
    default void onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String[] args) {
        if (!(sender instanceof ConsoleCommandSender)) {
            sender.sendMessage(getPlayerMessage());
            return;
        }

        onCommand((ConsoleCommandSender) sender, command, args);
    }

    default String getPlayerMessage() {
        return ChatColor.RED + "[!] This command can only be used by console!";
    }

    void onCommand(@NotNull ConsoleCommandSender console, @NotNull Command command, @NotNull String[] args);

}
