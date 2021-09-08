package dev.fumaz.commons.bukkit.mojang;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.JsonObject;
import dev.fumaz.commons.bukkit.network.Networks;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public final class UUIDFetcher {

    private static final String PROFILE_URL = "https://api.mojang.com/users/profiles/minecraft/%s";
    private static final Cache<String, UUID> CACHE = CacheBuilder.newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();

    private UUIDFetcher() {
    }

    /**
     * Returns an {@link UUID} from a given username, making a request to the mojang API if necessary<br>
     * This method will cache values by default
     *
     * @param username the username
     * @return the {@link UUID}, or null
     */
    @Nullable
    public static UUID fetch(String username) {
        UUID uuid = CACHE.getIfPresent(username.toLowerCase());

        if (uuid == null) {
            uuid = UUIDFetcher.fetchWithoutCache(username);

            if (uuid != null) {
                CACHE.put(username.toLowerCase(), uuid);
            }
        }

        return uuid;
    }

    /**
     * Returns an {@link UUID} from a given username, always making a request to the mojang API<br>
     * This method does not use any sort of caching
     *
     * @param username the username
     * @return the {@link UUID}, or null
     */
    @Nullable
    public static UUID fetchWithoutCache(String username) {
        try {
            JsonObject json = Networks.fetchJSON(String.format(PROFILE_URL, username));
            String id = json.get("id").getAsString();

            return new UUID(
                    new BigInteger(id.substring(0, 16), 16).longValue(),
                    new BigInteger(id.substring(16), 16).longValue()
            );
        } catch (Exception e) {
            return null;
        }
    }

}
