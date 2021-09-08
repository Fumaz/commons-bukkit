package dev.fumaz.commons.bukkit.misc;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// These should probably be moved to the ItemStacks class tbh
public final class Persistency {

    private Persistency() {
    }

    public static <T, Z> boolean has(@Nullable ItemStack item, @NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type) {
        return item != null && item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(key, type);
    }

    public static <T, Z> Z get(@Nullable ItemStack item, @NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type) {
        if (item == null || !item.hasItemMeta()) {
            return null;
        }

        return item.getItemMeta().getPersistentDataContainer().get(key, type);
    }

    public static <T, Z> Z getOrDefault(@Nullable ItemStack item, @NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type, @NotNull Z defaultValue) {
        if (item == null || !item.hasItemMeta()) {
            return defaultValue;
        }

        return item.getItemMeta().getPersistentDataContainer().getOrDefault(key, type, defaultValue);
    }

}
