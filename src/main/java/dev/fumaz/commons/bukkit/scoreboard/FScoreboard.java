package dev.fumaz.commons.bukkit.scoreboard;

import dev.fumaz.commons.math.Randoms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class FScoreboard {

    private final Scoreboard scoreboard;
    private final Objective objective;
    private final List<ScoreboardEntry> entries;


    private boolean changed;

    public FScoreboard() {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        this.objective = scoreboard.registerNewObjective("display", "dummy");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        this.entries = new ArrayList<>();

        this.changed = true;
    }

    public FScoreboard setTitle(String title) {
        objective.setDisplayName(title);
        changed = true;
        return this;
    }

    public FScoreboard show(Player player) {
        player.setScoreboard(scoreboard);
        return this;
    }

    public FScoreboard hide(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        return this;
    }

    public ScoreboardEntry getEntry(int index) {
        return entries.get(index);
    }

    public FScoreboard addEntry(int index, ScoreboardEntry entry) {
        if (entries.size() == 15 && (index > entries.size() || index < 0)) {
            throw new IllegalArgumentException("Index out of bounds");
        }

        entries.add(index, entry);
        changed = true;
        return this;
    }

    public FScoreboard addEntry(ScoreboardEntry entry) {
        return addEntry(entries.size(), entry);
    }

    public FScoreboard removeEntry(int index) {
        if (index < 0 || index >= entries.size()) {
            throw new IllegalArgumentException("Index out of bounds");
        }

        ScoreboardEntry entry = entries.remove(index);
        Team team = entry.getTeam();

        if (team != null) {
            team.getEntries().forEach(scoreboard::resetScores);
            team.unregister();
            changed = true;
        }

        return this;
    }

    public void clear() {
        IntStream.range(0, entries.size()).forEach(this::removeEntry);
        changed = true;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public List<ScoreboardEntry> getEntries() {
        return entries;
    }

    public Objective getObjective() {
        return objective;
    }

    public void update() {
        if (changed) {
            scoreboard.getEntries().forEach(scoreboard::resetScores);
            int rowID = entries.size();

            for (ScoreboardEntry entry : entries) {
                Team team = scoreboard.registerNewTeam(getRandomID());
                entry.setTeam(team);

                String entryID = ChatColor.values()[rowID].toString() + ChatColor.RESET;
                team.addEntry(entryID);

                updateTeamText(team, getFormattedEntry(entry));

                objective.getScore(entryID).setScore(rowID--);
            }

            changed = false;
        } else {
            entries.stream()
                    .filter(entry -> entry instanceof DynamicScoreboardEntry)
                    .forEach(entry -> updateTeamText(entry.getTeam(), getFormattedEntry(entry)));
        }
    }

    private String getRandomID() {
        StringBuilder stringBuilder = new StringBuilder();

        char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        for (int i = 0; i < 16; i++) {
            stringBuilder.append(Randoms.choice(chars));
        }

        return stringBuilder.toString();
    }

    private String getFormattedEntry(ScoreboardEntry entry) {
        StringBuilder text = new StringBuilder();

        if (!(entry instanceof BlankScoreboardEntry)) {
            if (entry.hasKey()) {
                text.append(getFormattedKey(entry));
            }

            if (entry.hasValue()) {
                if (text.length() != 0) {
                    text.append(" ");
                }

                text.append(getFormattedValue(entry));
            }
        }

        return text.toString();
    }

    private String getFormattedKey(ScoreboardEntry entry) {
        return optimizeColours(ChatColor.translateAlternateColorCodes('&', entry.getKey()));
    }

    private String getFormattedValue(ScoreboardEntry entry) {
        return optimizeColours(ChatColor.translateAlternateColorCodes('&', entry.getValue()));
    }

    private String optimizeColours(String string) {
        string = string.replaceAll("(§[a-f0-9]) *", "$1");
        string = string.replaceAll("(§[a-f0-9])(§[a-f0-9])+", "$2");

        return string;
    }

    private void updateTeamText(Team team, String text) {
        if (text.length() > 32) {
            text = text.substring(0, 32);
        }

        if (text.length() > 16) {
            String prefix = text.substring(0, 16);

            boolean colorSplit = prefix.endsWith("§");

            if (colorSplit) {
                prefix = prefix.substring(0, 15);
            }

            String lastColor = ChatColor.getLastColors(prefix);
            String suffix = (colorSplit ? "§" : lastColor) + text.substring(16, Math.min(text.length(), 32 - lastColor.length() - (colorSplit ? 1 : 0)));

            team.setPrefix(prefix);
            team.setSuffix(suffix);
        } else {
            team.setPrefix(text);
            team.setSuffix("");
        }
    }

}
