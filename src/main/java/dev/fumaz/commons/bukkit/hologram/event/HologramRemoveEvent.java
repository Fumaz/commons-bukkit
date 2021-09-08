package dev.fumaz.commons.bukkit.hologram.event;

import dev.fumaz.commons.bukkit.hologram.Hologram;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * This event is called before a {@link Hologram} gets removed, cancelling the event will result in the hologram not being removed
 */
public class HologramRemoveEvent extends HologramEvent implements Cancellable {

    private final static HandlerList handlerList = new HandlerList();
    private boolean cancelled = false;

    public HologramRemoveEvent(Hologram hologram) {
        super(hologram);
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

}
