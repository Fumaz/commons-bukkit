package dev.fumaz.commons.bukkit.misc;

import dev.fumaz.commons.text.TextFormatting;
import dev.fumaz.commons.text.figlet.FigletFonts;
import dev.fumaz.commons.text.figlet.FigletRenderer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public final class Logging {

    private Logging() {
    }

    /**
     * Prints out a splash screen for a plugin with the default color (pink)
     *
     * @param plugin the plugin
     */
    public static void splash(@NotNull JavaPlugin plugin) {
        Logging.splash(plugin, ChatColor.LIGHT_PURPLE);
    }

    /**
     * Prints out a splash screen for a plugin
     *
     * @param plugin the plugin
     * @param color  the color of the text
     */
    public static void splash(@NotNull JavaPlugin plugin, ChatColor color) {
        Logger logger = plugin.getLogger();
        PluginDescriptionFile description = plugin.getDescription();
        FigletRenderer figlet = FigletFonts.STANDARD.renderer();

        String authors = color + String.join(ChatColor.RESET + ", " + color, description.getAuthors());
        String website = description.getWebsite() == null ? "" : ChatColor.RESET + " (" + color + description.getWebsite() + ChatColor.RESET + ")";

        TextFormatting.prefix(color.toString(), figlet.renderLines(plugin.getName())).forEach(msg -> logger.info(msg.replace("\n", "")));
        logger.info("");
        logger.info("Plugin version " + color + description.getVersion());
        logger.info("Made with " + ChatColor.RED + "<3" + ChatColor.RESET + " by " + authors + website);
        logger.info("");
    }

    public static Logger of(JavaPlugin plugin) {
        return plugin.getLogger();
    }

    public static Logger of(Class<? extends JavaPlugin> clazz) {
        return of(JavaPlugin.getPlugin(clazz));
    }

}
