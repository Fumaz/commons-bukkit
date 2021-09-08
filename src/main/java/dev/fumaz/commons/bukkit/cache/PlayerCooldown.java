package dev.fumaz.commons.bukkit.cache;

import dev.fumaz.commons.cache.Cooldown;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * A cooldown specifically designed for {@link Player}s.
 * Uses {@link UUID}s under the hood.
 */
public class PlayerCooldown extends Cooldown<UUID> {

    public PlayerCooldown(long delay, TimeUnit unit) {
        super(delay, unit);
    }

    public PlayerCooldown(long millis) {
        super(millis);
    }

    public void put(Player player) {
        put(player.getUniqueId());
    }

    public boolean has(Player player) {
        return has(player.getUniqueId());
    }

    public void invalidate(Player player) {
        invalidate(player.getUniqueId());
    }

    public long get(Player player, TimeUnit unit) {
        return get(player.getUniqueId(), unit);
    }

}
