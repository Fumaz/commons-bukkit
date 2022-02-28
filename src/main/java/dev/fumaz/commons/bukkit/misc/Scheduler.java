package dev.fumaz.commons.bukkit.misc;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Scheduler {

    private final JavaPlugin plugin;

    private Scheduler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static Scheduler of(JavaPlugin plugin) {
        return new Scheduler(plugin);
    }

    @NotNull
    public <T> Future<T> callSyncMethod(@NotNull Callable<T> callable) {
        return Bukkit.getScheduler().callSyncMethod(plugin, callable);
    }

    public void cancelTask(int id) {
        Bukkit.getScheduler().cancelTask(id);
    }

    public void cancelTasks() {
        Bukkit.getScheduler().cancelTasks(plugin);
    }

    public boolean isCurrentlyRunning(int id) {
        return Bukkit.getScheduler().isCurrentlyRunning(id);
    }

    public boolean isQueued(int id) {
        return Bukkit.getScheduler().isQueued(id);
    }

    public List<BukkitWorker> getActiveWorkers() {
        return Bukkit.getScheduler().getActiveWorkers()
                .stream()
                .filter(worker -> worker.getOwner().equals(plugin))
                .collect(Collectors.toList());
    }

    public List<BukkitTask> getPendingTasks() {
        return Bukkit.getScheduler().getPendingTasks()
                .stream()
                .filter(task -> task.getOwner().equals(plugin))
                .collect(Collectors.toList());
    }

    public BukkitTask runTask(Runnable runnable) {
        return Bukkit.getScheduler().runTask(plugin, runnable);
    }

    public void runTask(Consumer<BukkitTask> task) {
        Bukkit.getScheduler().runTask(plugin, task);
    }

    public BukkitTask runTaskAsynchronously(Runnable runnable) {
        return Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    public void runTaskAsynchronously(Consumer<BukkitTask> task) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
    }

    public BukkitTask runTaskLater(Runnable runnable, long delay) {
        return Bukkit.getScheduler().runTaskLater(plugin, runnable, delay);
    }

    public void runTaskLater(Consumer<BukkitTask> task, long delay) {
        Bukkit.getScheduler().runTaskLater(plugin, task, delay);
    }

    public BukkitTask runTaskLaterAsynchronously(Runnable runnable, long delay) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay);
    }

    public void runTaskLaterAsynchronously(Consumer<BukkitTask> task, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task, delay);
    }

    public BukkitTask runTaskTimer(Runnable runnable, long delay, long period) {
        return Bukkit.getScheduler().runTaskTimer(plugin, runnable, delay, period);
    }

    public void runTaskTimer(Consumer<BukkitTask> task, long delay, long period) {
        Bukkit.getScheduler().runTaskTimer(plugin, task, delay, period);
    }

    public BukkitTask runTaskTimerAsynchronously(Runnable runnable, long delay, long period) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, delay, period);
    }

    public void runTaskTimerAsynchronously(Consumer<BukkitTask> task, long delay, long period) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, task, delay, period);
    }

    public Executor getMainThreadExecutor() {
        return Bukkit.getScheduler().getMainThreadExecutor(plugin);
    }

}
