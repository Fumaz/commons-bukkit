package dev.fumaz.commons.bukkit.interfaces;

import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * An {@link FListener} with a {@link JavaPlugin}
 */
public interface PluginListener extends FListener {

    @NotNull
    JavaPlugin getPlugin();

    default void register() {
        register(getPlugin());
    }

    @EventHandler
    default void onDisable(PluginDisableEvent event) {
        if (event.getPlugin() != getPlugin()) {
            return;
        }

        unregister();
    }

}
