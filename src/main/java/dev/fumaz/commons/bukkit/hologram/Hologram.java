package dev.fumaz.commons.bukkit.hologram;

import dev.fumaz.commons.bukkit.hologram.event.HologramAddEvent;
import dev.fumaz.commons.bukkit.hologram.event.HologramRemoveEvent;
import dev.fumaz.commons.bukkit.misc.Threads;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * Represents an invisible {@link ArmorStand} with a custom name,
 * that can have a custom action when clicked
 */
public class Hologram implements Listener {

    private final UUID uuid;
    private JavaPlugin plugin;
    private Consumer<PlayerInteractAtEntityEvent> onInteract;

    private Hologram(@Nullable JavaPlugin plugin, @NotNull UUID uuid) {
        this.plugin = plugin;
        this.uuid = uuid;
    }

    /**
     * Creates a hologram at a location, with the given text
     *
     * @param plugin   the plugin the hologram belongs to
     * @param text     the text of the hologram
     * @param location the location of the hologram
     * @return
     */
    public static Hologram create(@Nullable JavaPlugin plugin, @NotNull String text, @NotNull Location location) {
        Threads.catchAsync("hologram creation");

        ArmorStand stand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND, CreatureSpawnEvent.SpawnReason.CUSTOM);
        stand.setCustomName(text);
        stand.setCustomNameVisible(true);
        stand.setVisible(false);
        stand.setGravity(true);
        stand.setMarker(true);
        stand.setCanTick(false);
        stand.setCanMove(false);
        stand.setCollidable(false);
        stand.setDisabledSlots(EquipmentSlot.values());

        Hologram hologram = new Hologram(plugin, stand.getUniqueId());
        HologramAddEvent event = new HologramAddEvent(hologram);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            stand.remove();
            return null;
        }

        return hologram;
    }

    public static Hologram create(@NotNull String text, @NotNull Location location) {
        return create(null, text, location);
    }

    public Hologram consume(Consumer<ArmorStand> consumer) {
        consumer.accept(getArmorStand());
        return this;
    }

    public Hologram move(@NotNull Location location) {
        return consume(stand -> stand.teleport(location));
    }

    public Hologram marker() {
        return consume(stand -> stand.setMarker(true));
    }

    public Hologram name(@Nullable String name) {
        return consume(stand -> stand.setCustomName(name));
    }

    public Hologram plugin(@Nullable JavaPlugin plugin) {
        this.plugin = plugin;
        removeInteract();

        return this;
    }

    public Hologram setInteract(@Nullable Consumer<PlayerInteractAtEntityEvent> consumer) {
        if (consumer == null) {
            return removeInteract();
        }

        if (plugin == null) {
            throw new IllegalArgumentException("Cannot register interaction with null plugin");
        }

        onInteract = consumer;
        HandlerList.unregisterAll(this); // Avoid registering twice
        Bukkit.getPluginManager().registerEvents(this, plugin);

        return this;
    }

    public Hologram removeInteract() {
        onInteract = null;
        HandlerList.unregisterAll(this);

        return this;
    }

    public void remove() {
        Threads.catchAsync("hologram removal");

        HologramRemoveEvent event = new HologramRemoveEvent(this);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        removeInteract();

        if (getArmorStand() != null) {
            getArmorStand().remove();
        }
    }

    public UUID getUUID() {
        return uuid;
    }

    public ArmorStand getArmorStand() {
        Entity entity = Bukkit.getEntity(uuid);

        if (!(entity instanceof ArmorStand)) {
            return null;
        }

        return (ArmorStand) entity;
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        if (!event.getRightClicked().getUniqueId().equals(uuid)) {
            return;
        }

        onInteract.accept(event);
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin() != plugin) {
            return;
        }

        remove(); // TODO Maybe add a flag so that if the plugin is disabled the event can't be cancelled
    }

}
