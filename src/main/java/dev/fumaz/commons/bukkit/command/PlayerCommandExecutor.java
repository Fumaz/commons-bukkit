package dev.fumaz.commons.bukkit.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * A command that only a {@link Player} can use
 */
public interface PlayerCommandExecutor extends VoidCommandExecutor {

    @Override
    default void onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(getConsoleMessage());
            return;
        }

        onCommand((Player) sender, command, args);
    }

    default String getConsoleMessage() {
        return ChatColor.RED + "[!] This command can only be used by players!";
    }

    void onCommand(@NotNull Player player, @NotNull Command command, @NotNull String[] args);

}
