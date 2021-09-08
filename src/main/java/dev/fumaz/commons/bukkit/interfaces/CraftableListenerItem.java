package dev.fumaz.commons.bukkit.interfaces;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A {@link ListenerItem} that can be crafted with a {@link org.bukkit.inventory.Recipe}
 */
public interface CraftableListenerItem extends ListenerItem, CraftableItem {

    @Override
    default void register(JavaPlugin plugin) {
        // We need to do this without calling super methods cuz otherwise it breaks, idk why
        Bukkit.getPluginManager().registerEvents(this, plugin);
        Bukkit.removeRecipe(getKey());
        Bukkit.addRecipe(getRecipe());
    }

    @Override
    default void unregister() {
        HandlerList.unregisterAll(this);
        Bukkit.removeRecipe(getKey());
    }

    @Override
    default void register() {
        throw new UnsupportedOperationException("Listener item should be registered with a JavaPlugin");
    }

}
