package dev.fumaz.commons.bukkit.interfaces;

import dev.fumaz.commons.bukkit.misc.Persistency;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

/**
 * An {@link Item} with a {@link NamespacedKey}
 */
public interface KeyedItem extends Item, Keyed {

    default boolean isItem(@Nullable ItemStack item) {
        return Persistency.has(item, getKey(), PersistentDataType.BYTE);
    }

    default boolean hasInInventory(@NotNull Player player) {
        return Stream.of(player.getInventory().getContents(), player.getInventory().getExtraContents(), player.getInventory().getArmorContents())
                .flatMap(Stream::of)
                .anyMatch(this::isItem);
    }

}
