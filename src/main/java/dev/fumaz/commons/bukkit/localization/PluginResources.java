package dev.fumaz.commons.bukkit.localization;

import dev.fumaz.commons.localization.Resources;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * A utility to work with jar resources
 *
 * @author Fumaz
 * @version 1.0
 * @since 1.0
 */
public final class PluginResources {

    private PluginResources() {
    }

    /**
     * Saves all resources in the specified path to the plugin's data folder
     *
     * @param plugin the plugin
     * @param path   the path
     */
    public static void saveResourcesInPath(JavaPlugin plugin, String path) {
        PluginResources.saveResourcesInPath(plugin, path, false);
    }

    /**
     * Saves all resources in the specified path to the plugin's data folder
     *
     * @param plugin  the plugin
     * @param path    the path
     * @param replace whether to replace the files if they already exist
     */
    public static void saveResourcesInPath(JavaPlugin plugin, String path, boolean replace) {
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }

        String finalPath = path;
        Resources.getResourcesInPath(plugin.getClass().getClassLoader(), path).forEach(resource -> {
            if (!replace && new File(plugin.getDataFolder(), finalPath + "/" + resource).exists()) {
                return;
            }

            plugin.saveResource(finalPath + "/" + resource, replace);
        });
    }

}
