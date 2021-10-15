package dev.fumaz.commons.bukkit.chat;

import dev.fumaz.commons.bukkit.interfaces.PluginListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class MessageHandler implements PluginListener {

    private final JavaPlugin plugin;
    private final UUID uuid;
    private final MessageListener listener;

    private MessageHandler(JavaPlugin plugin, UUID uuid, MessageListener listener) {
        this.plugin = plugin;
        this.uuid = uuid;
        this.listener = listener;
    }

    public static MessageHandler create(JavaPlugin plugin, UUID uuid, MessageListener listener) {
        MessageHandler handler = new MessageHandler(plugin, uuid, listener);
        handler.register();

        return handler;
    }

    public static MessageHandler create(JavaPlugin plugin, Player player, MessageListener listener) {
        return create(plugin, player.getUniqueId(), listener);
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (!event.getPlayer().getUniqueId().equals(uuid)) {
            return;
        }

        event.setCancelled(true);
        MessageResult result = listener.onMessage(event.getMessage());

        if (result != MessageResult.DONE) {
            return;
        }

        unregister();
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event) {
        if (!event.getPlayer().getUniqueId().equals(uuid)) {
            return;
        }

        unregister();
    }

    @Override
    public @NotNull JavaPlugin getPlugin() {
        return plugin;
    }

}
