package dev.fumaz.commons.bukkit.hologram.event;

import dev.fumaz.commons.bukkit.hologram.Hologram;
import org.bukkit.event.Event;

/**
 * Represents an event involving a {@link Hologram}
 */
public abstract class HologramEvent extends Event {

    private final Hologram hologram;

    public HologramEvent(Hologram hologram) {
        this.hologram = hologram;
    }

    public Hologram getHologram() {
        return hologram;
    }

}
