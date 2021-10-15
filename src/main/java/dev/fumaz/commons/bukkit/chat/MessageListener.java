package dev.fumaz.commons.bukkit.chat;

@FunctionalInterface
public interface MessageListener {

    MessageResult onMessage(String message);

}
