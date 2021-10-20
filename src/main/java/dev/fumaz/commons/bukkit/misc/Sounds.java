package dev.fumaz.commons.bukkit.misc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public final class Sounds {

    private Sounds() {
    }

    public static void play(Player player, Sound sound) {
        Sounds.play(player, player.getLocation(), sound);
    }

    public static void play(Player player, Location location, Sound sound) {
        player.playSound(location, sound, 1f, 1f);
    }

    public static void play(Location location, Sound sound) {
        Sounds.play(location.getWorld(), location, sound);
    }

    public static void play(World world, Location location, Sound sound) {
        world.playSound(location, sound, 1f, 1f);
    }

    public static void broadcast(Sound sound) {
        Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), sound, 1f, 1f));
    }

}
