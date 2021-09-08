package dev.fumaz.commons.bukkit.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Fumaz
 * @version 1.0
 * @since 1.0
 */
public final class Jsons {

    @NotNull
    public static JsonElement fromFile(@NotNull File file) {
        try (FileReader reader = new FileReader(file)) {
            return new JsonParser().parse(reader);
        } catch (IOException e) {
            return new JsonObject();
        }
    }

}
