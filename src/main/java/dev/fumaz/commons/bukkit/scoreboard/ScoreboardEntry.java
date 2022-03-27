package dev.fumaz.commons.bukkit.scoreboard;

import org.bukkit.scoreboard.Team;

public abstract class ScoreboardEntry {

    private Team team;

    public abstract String getKey();

    public abstract String getValue();

    public boolean hasKey() {
        return getKey() != null;
    }

    public boolean hasValue() {
        return getValue() != null;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

}
