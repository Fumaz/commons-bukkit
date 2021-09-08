package dev.fumaz.commons.bukkit.mojang;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.JsonObject;
import dev.fumaz.commons.bukkit.network.Networks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public final class SkinFetcher {

    private static final String SESSION_URL = "https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false";
    private static final Cache<UUID, Skin> CACHE = CacheBuilder.newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();

    private SkinFetcher() {
    }

    /**
     * Returns a {@link Skin} from the given username<br>
     * This method will first fetch the {@link UUID} and then fetch the {@link Skin}
     *
     * @param username the username
     * @return the {@link Skin}, or null
     */
    @Nullable
    public static Skin fetch(@NotNull String username) {
        return fetch(UUIDFetcher.fetch(username));
    }

    /**
     * Returns a {@link Skin} from a given {@link UUID}, making a request to the mojang API if necessary<br>
     * This method will cache values by default
     *
     * @param uuid the {@link UUID}
     * @return the {@link Skin}, or null
     */
    @Nullable
    public static Skin fetch(@Nullable UUID uuid) {
        if (uuid == null) {
            return null;
        }

        Skin skin = CACHE.getIfPresent(uuid);

        if (skin == null) {
            skin = fetchWithoutCache(uuid);
            if (skin != null) {
                CACHE.put(uuid, skin);
            }
        }

        return skin;
    }

    /**
     * Returns a {@link Skin} from a given {@link UUID}, always making a request to the mojang API<br>
     * This method does not use any sort of caching
     *
     * @param uuid the {@link UUID}
     * @return the {@link Skin}, or null
     */
    @Nullable
    public static Skin fetchWithoutCache(@Nullable UUID uuid) {
        if (uuid == null) {
            return null;
        }

        try {
            JsonObject json = Networks.fetchJSON(String.format(SESSION_URL, uuid.toString().replace("-", "")));
            JsonObject property = json.getAsJsonArray("properties").get(0).getAsJsonObject();

            String name = json.get("name").getAsString();
            String value = property.get("value").getAsString();
            String signature = property.get("signature").getAsString();

            return new Skin(name, uuid, value, signature);
        } catch (Exception e) {
            return null;
        }
    }

}
