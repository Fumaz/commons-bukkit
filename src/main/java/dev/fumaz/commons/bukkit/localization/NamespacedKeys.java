package dev.fumaz.commons.bukkit.localization;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang.WordUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * A utility to work with {@link NamespacedKey}s
 *
 * @author Fumaz
 * @version 1.0
 * @since 1.0
 */
public final class NamespacedKeys {

    private NamespacedKeys() {
    }

    /**
     * Creates a {@link NamespacedKey} with the given {@link Plugin} and name,
     * fixing any naming issues.
     *
     * @param plugin the {@link Plugin}
     * @param name   the name
     * @return the {@link NamespacedKey}
     */
    public static NamespacedKey create(@NotNull Plugin plugin, @NotNull String name) {
        name = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);

        return new NamespacedKey(plugin, name);
    }

    /**
     * Creates a {@link NamespacedKey} with the given {@link Plugin} and an {@link Object}'s class name
     *
     * @param plugin the {@link Plugin}
     * @param object the {@link Object}
     * @return the {@link NamespacedKey}
     */
    public static NamespacedKey create(@NotNull Plugin plugin, @NotNull Object object) {
        return NamespacedKeys.create(plugin, object.getClass().getSimpleName());
    }

    /**
     * Returns the beautified name of a given {@link NamespacedKey}
     *
     * @param key the {@link NamespacedKey}
     * @return the printable name
     */
    public static String getDisplayName(@NotNull NamespacedKey key) {
        return WordUtils.capitalizeFully(key.getKey().replace("_", " "));
    }

}
