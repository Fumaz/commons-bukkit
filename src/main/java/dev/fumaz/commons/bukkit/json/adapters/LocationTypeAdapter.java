package dev.fumaz.commons.bukkit.json.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.lang.reflect.Type;

/**
 * @author Fumaz
 * @version 1.0
 * @since 1.0
 */
public class LocationTypeAdapter implements JsonSerializer<Location>, JsonDeserializer<Location> {

    @Override
    public JsonElement serialize(Location location, Type type, JsonSerializationContext context) {
        if (location == null) {
            return null;
        }

        JsonObject object = new JsonObject();

        if (location.getWorld() != null) {
            object.addProperty("world", location.getWorld().getName());
        }

        object.addProperty("x", location.getX());
        object.addProperty("y", location.getY());
        object.addProperty("z", location.getZ());
        object.addProperty("yaw", location.getYaw());
        object.addProperty("pitch", location.getPitch());

        return object;
    }

    @Override
    public Location deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (jsonElement == null) {
            return null;
        }

        JsonObject object = jsonElement.getAsJsonObject();

        double x = object.get("x").getAsDouble();
        double y = object.get("y").getAsDouble();
        double z = object.get("z").getAsDouble();
        float yaw = object.get("yaw").getAsFloat();
        float pitch = object.get("pitch").getAsFloat();

        World world = null;
        if (object.has("world")) {
            world = Bukkit.getWorld(object.get("world").getAsString());
        }

        return new Location(world, x, y, z, yaw, pitch);
    }

}
