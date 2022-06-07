package dev.fumaz.commons.bukkit.teams;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

/**
 * A utility to make {@link Team}s easier
 *
 * @author Fumaz
 * @version 1.0
 * @since 1.0
 */
public final class Teams {

    private Teams() {
    }

    /**
     * Adds an entity to a team
     *
     * @param team   the team
     * @param entity the entity
     */
    public static void addEntry(@NotNull Team team, @NotNull Entity entity) {
        team.addEntry(getEntryString(entity));
    }

    /**
     * Removes an entity from a team
     *
     * @param team   the team
     * @param entity the entity
     */
    public static void removeEntry(@NotNull Team team, @NotNull Entity entity) {
        team.removeEntry(getEntryString(entity));
    }

    /**
     * Gets an entity's current team
     *
     * @param entity the entity
     * @return the team
     */
    public static Team getTeam(@NotNull Entity entity) {
        return Bukkit.getScoreboardManager().getMainScoreboard().getTeam(getEntryString(entity));
    }

    public static void removeFromTeam(@NotNull Entity entity) {
        Team team = getTeam(entity);

        if (team == null) {
            return;
        }

        removeEntry(team, entity);
    }

    private static String getEntryString(@NotNull Entity entity) {
        if (entity instanceof OfflinePlayer) {
            return entity.getName();
        }

        return entity.getUniqueId().toString();
    }

}
