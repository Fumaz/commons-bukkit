package dev.fumaz.commons.bukkit.config;

import com.google.common.io.Files;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * An interface for configuration files
 *
 * @author Fumaz
 * @version 1.0
 * @since 1.0
 */
public interface ConfigurationFile {

    static ConfigurationFile load(JavaPlugin plugin, String resource) {
        return ConfigurationFile.load(plugin, new File(plugin.getDataFolder(), resource));
    }

    static ConfigurationFile load(JavaPlugin plugin, File file) {
        String extension = Files.getFileExtension(file.getName()).toLowerCase();

        if (extension.equals("yml") || extension.equals("yaml")) {
            return new YamlConfigurationFile(plugin, file);
        }

        if (extension.equals("json")) {
            return new JsonConfigurationFile(plugin, file);
        }

        throw new ConfigurationException("File format is not supported: " + file.getName());
    }

    /**
     * Loads the configuration from the file, replacing the current one in-memory.
     */
    void load();

    /**
     * Saves the current in-memory configuration to the file, overwriting it.
     */
    void save();

    /**
     * Saves the default configuration (jar resource) to the file.
     */
    void saveDefault();

    File getFile();

}
