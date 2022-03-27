package dev.fumaz.commons.bukkit.scoreboard;

public class StaticScoreboardEntry extends ScoreboardEntry {

    private Object key;
    private Object value;

    public StaticScoreboardEntry(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    public StaticScoreboardEntry(Object source, boolean key) {
        if (key) {
            this.key = source;
        } else {
            this.value = source;
        }
    }

    @Override
    public String getKey() {
        return String.valueOf(key);
    }

    @Override
    public String getValue() {
        return String.valueOf(value);
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
