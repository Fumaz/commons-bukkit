package dev.fumaz.commons.bukkit.mojang;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

/**
 * This class represents a player's skin
 */
public class Skin {

    private final static String PROPERTY_NAME = "textures";

    private final String name;
    private final UUID uuid;
    private final String value;
    private final String signature;

    public Skin(@Nullable String name, @Nullable UUID uuid, String value, String signature) {
        this.name = name;
        this.uuid = uuid;
        this.value = value;
        this.signature = signature;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getSignature() {
        return signature;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Skin)) {
            return false;
        }

        Skin skin = (Skin) other;
        return Objects.equals(name, skin.name) && Objects.equals(uuid, skin.uuid) && Objects.equals(value, skin.value) && Objects.equals(signature, skin.signature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, uuid, value, signature);
    }

    @Override
    public String toString() {
        return "Skin{" +
                "name='" + name + '\'' +
                ", uuid=" + uuid +
                ", value='" + value + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }

}
