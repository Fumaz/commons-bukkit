package dev.fumaz.commons.bukkit.scoreboard;

import java.util.function.Supplier;

public class DynamicScoreboardEntry extends ScoreboardEntry {

    private final Supplier<Object> key;
    private final Supplier<Object> value;

    public DynamicScoreboardEntry(Supplier<Object> key, Supplier<Object> value) {
        this.key = key;
        this.value = value;
    }

    public DynamicScoreboardEntry(Supplier<Object> source, boolean key) {
        if (key) {
            this.key = source;
            this.value = null;
        } else {
            this.value = source;
            this.key = null;
        }
    }

    @Override
    public String getKey() {
        return key != null ? String.valueOf(key.get()) : null;
    }

    @Override
    public String getValue() {
        return value != null ? String.valueOf(value.get()) : null;
    }

    @Override
    public boolean hasKey() {
        return key != null;
    }

    @Override
    public boolean hasValue() {
        return value != null;
    }

}
