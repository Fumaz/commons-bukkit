package dev.fumaz.commons.bukkit.text;

import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public final class Chats {

    /**
     * Sends a list of messages to a {@link CommandSender}
     *
     * @param sender   the {@link CommandSender} to send the messages to
     * @param messages the list of messages
     */
    public static void message(CommandSender sender, List<String> messages) {
        messages.forEach(sender::sendMessage);
    }

    /**
     * Sends a list of messages to a {@link CommandSender}
     *
     * @param sender   the {@link CommandSender} to send the messages to
     * @param messages the list of messages
     */
    public static void message(CommandSender sender, String... messages) {
        Chats.message(sender, Arrays.asList(messages));
    }

}
