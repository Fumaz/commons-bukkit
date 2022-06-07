package dev.fumaz.commons.bukkit.teams;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 * A utility to get teams with certain chat colors.
 *
 * @author Fumaz
 * @version 1.0
 * @since 1.0
 */
public final class ColoredTeams {

    private ColoredTeams() {
    }

    /**
     * Returns a team with the specified color as prefix<br>
     * Creates a team if it doesn't already exist
     *
     * @param color the color
     * @return the team
     */
    public static Team getTeam(ChatColor color) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam(color.name());

        if (team == null) {
            team = scoreboard.registerNewTeam(color.name());
            team.setColor(color);
        }

        return team;
    }

    /**
     * Sets an entity's team color
     *
     * @param entity the entity
     * @param color  the color
     */
    public static void setColor(Entity entity, ChatColor color) {
        Teams.addEntry(getTeam(color), entity);
    }

    public static void removeColor(Entity entity) {
        Teams.removeFromTeam(entity);
    }

}
