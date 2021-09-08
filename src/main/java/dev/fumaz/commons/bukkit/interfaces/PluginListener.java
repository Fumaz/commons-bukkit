package dev.fumaz.commons.bukkit.interfaces;

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

}
