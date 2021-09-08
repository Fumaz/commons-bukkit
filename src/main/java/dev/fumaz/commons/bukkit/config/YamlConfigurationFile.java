package dev.fumaz.commons.bukkit.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * A configuration file containing YAML data
 */
public class YamlConfigurationFile implements ConfigurationFile {

    private final JavaPlugin plugin;
    private final File file;
    private YamlConfiguration yaml;

    public YamlConfigurationFile(JavaPlugin plugin, String path) {
        this(plugin, new File(plugin.getDataFolder(), path));
    }

    public YamlConfigurationFile(JavaPlugin plugin, File file) {
        this.plugin = plugin;
        this.file = file;
        load();
    }

    @Override
    public void load() {
        if (!file.exists()) {
            saveDefault();
        }

        yaml = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public void save() {
        try {
            yaml.save(file);
        } catch (IOException e) {
            throw new ConfigurationSaveException("Could not save YML configuration: " + file.getName(), e);
        }
    }

    @Override
    public void saveDefault() {
        try {
            plugin.saveResource(file.getName(), true);
        } catch (IllegalArgumentException e) {
            plugin.getLogger().info("Could not save default YML configuration: " + file.getName());
        }
    }

    @Override
    public File getFile() {
        return file;
    }

    public YamlConfiguration getYaml() {
        return yaml;
    }

    public void consume(Consumer<YamlConfiguration> consumer) {
        consumer.accept(yaml);
    }

    public void consumeAndSave(Consumer<YamlConfiguration> consumer) {
        consume(consumer);
        save();
    }

}
