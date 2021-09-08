package dev.fumaz.commons.bukkit.config;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.fumaz.commons.bukkit.json.Jsons;
import dev.fumaz.commons.bukkit.json.adapters.LocationTypeAdapter;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.function.Consumer;

/**
 * A configuration file containing JSON data
 */
public class JsonConfigurationFile implements ConfigurationFile {

    private final JavaPlugin plugin;
    private final File file;
    private GsonBuilder gson;

    private JsonElement json;

    public JsonConfigurationFile(JavaPlugin plugin, File file) {
        this(plugin, file, null);
    }

    public JsonConfigurationFile(JavaPlugin plugin, File file, GsonBuilder gson) {
        this.plugin = plugin;
        this.file = file;
        this.gson = gson;

        if (gson == null) {
            this.gson = createDefaultBuilder();
        }

        load();
    }

    @Override
    public void load() {
        json = Jsons.fromFile(file);
    }

    @Override
    public void save() {
        try (FileWriter writer = new FileWriter(file)) {
            gson.create().toJson(json, writer);
        } catch (IOException e) {
            throw new ConfigurationSaveException("Could not save JSON configuration: " + file.getName());
        }
    }

    @Override
    public void saveDefault() {
        try {
            plugin.saveResource(file.getName(), true);
        } catch (IllegalArgumentException e) {
            plugin.getLogger().info("Could not save default JSON configuration: " + file.getName());
        }
    }

    @Override
    public File getFile() {
        return file;
    }

    public JsonElement getJson() {
        return json;
    }

    public GsonBuilder getGsonBuilder() {
        return gson;
    }

    public <T extends JsonElement> T getJsonAs(Class<T> type) {
        return type.cast(json);
    }

    public void consume(Consumer<JsonElement> consumer) {
        consumer.accept(json);
    }

    public void consumeAndSave(Consumer<JsonElement> consumer) {
        consume(consumer);
        save();
    }

    public <T extends JsonElement> void consumeAs(Class<T> type, Consumer<T> consumer) {
        consumer.accept(getJsonAs(type));
    }

    public <T extends JsonElement> void consumeAsAndSave(Class<T> type, Consumer<T> consumer) {
        consumeAs(type, consumer);
        save();
    }

    public void consumeAsObject(Consumer<JsonObject> consumer) {
        consumer.accept(getJsonAs(JsonObject.class));
    }

    public void consumeAsObjectAndSave(Consumer<JsonObject> consumer) {
        consumeAsObject(consumer);
        save();
    }

    public void consumeGsonBuilder(Consumer<GsonBuilder> consumer) {
        consumer.accept(gson);
    }

    public JsonConfigurationFile registerTypeAdapter(Type type, Object adapter) {
        gson.registerTypeAdapter(type, adapter);
        return this;
    }

    private GsonBuilder createDefaultBuilder() {
        return new GsonBuilder()
                .registerTypeAdapter(Location.class, new LocationTypeAdapter())
                .enableComplexMapKeySerialization()
                .setPrettyPrinting()
                .serializeNulls();
    }

}
