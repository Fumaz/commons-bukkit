package dev.fumaz.commons.bukkit.update;

import dev.fumaz.commons.bukkit.network.Networks;
import org.bukkit.plugin.java.JavaPlugin;

public class AutoUpdater {

    private final static String VERSION_URL = "https://api.spigotmc.org/legacy/update.php?resource=%s";
    private final static String RESOURCE_URL = "https://www.spigotmc.org/resources/%s";

    public static void alert(JavaPlugin plugin, int resourceID) {
        if (!check(plugin, resourceID)) {
            return;
        }

        String newest = getNewestVersion(resourceID);
        plugin.getLogger().info("An update is available! Version: " + newest);
        plugin.getLogger().info("Please download it from " + String.format(RESOURCE_URL, resourceID));
    }

    public static boolean check(JavaPlugin plugin, int resourceID) {
        String version = plugin.getDescription().getVersion();
        String newest = getNewestVersion(resourceID);

        return !version.equalsIgnoreCase(newest);
    }

    private static String getNewestVersion(int resourceID) {
        return Networks.fetchString(String.format(VERSION_URL, resourceID));
    }

}
