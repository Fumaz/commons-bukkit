package dev.fumaz.commons.bukkit.interfaces;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.persistence.PersistentDataType;

/**
 * An advanced {@link KeyedItem} that is also a {@link FListener}
 */
public interface ListenerItem extends KeyedItem, FListener {

    default boolean isDroppable() {
        return true;
    }

    default boolean isMoveable() {
        return true;
    }

    default boolean shouldKeepOnDeath() {
        return false;
    }

    default boolean shouldGiveOnJoin() {
        return false;
    }

    @EventHandler
    default void onDrop(PlayerDropItemEvent event) {
        if (isDroppable() || !isItem(event.getItemDrop().getItemStack())) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    default void onMoveClick(InventoryClickEvent event) {
        if (isMoveable() || event.getClickedInventory() == null || !isItem(event.getClickedInventory().getItem(event.getSlot()))) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    default void onMove(InventoryMoveItemEvent event) {
        if (isMoveable() || !isItem(event.getItem())) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    default void onDeath(PlayerDeathEvent event) {
        if (!shouldKeepOnDeath()) {
            return;
        }

        if (!event.getDrops().removeIf(this::isItem)) {
            return;
        }

        event.getEntity().getPersistentDataContainer().set(getKey(), PersistentDataType.BYTE, (byte) 1);
    }

    @EventHandler
    default void onRespawn(PlayerRespawnEvent event) {
        if (!shouldKeepOnDeath()) {
            return;
        }

        if (!event.getPlayer().getPersistentDataContainer().has(getKey(), PersistentDataType.BYTE)) {
            return;
        }

        event.getPlayer().getPersistentDataContainer().remove(getKey());
        event.getPlayer().getInventory().addItem(getItemStack());
    }

    @EventHandler
    default void onJoin(PlayerJoinEvent event) {
        if (!shouldGiveOnJoin()) {
            return;
        }

        if (hasInInventory(event.getPlayer())) {
            return;
        }

        event.getPlayer().getInventory().addItem(getItemStack());
    }

}
