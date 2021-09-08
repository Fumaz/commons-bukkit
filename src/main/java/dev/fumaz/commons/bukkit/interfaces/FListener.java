package dev.fumaz.commons.bukkit.interfaces;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A {@link Listener} that can register/unregister itself
 */
public interface FListener extends Listener {

    default void register(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    default void unregister() {
        HandlerList.unregisterAll(this);
    }

}
