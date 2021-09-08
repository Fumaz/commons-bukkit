package dev.fumaz.commons.bukkit.misc;

import org.bukkit.Bukkit;

public final class Threads {

    private Threads() {
    }

    /**
     * Throws an exception if the current thread is not the main one
     *
     * @param operation the reason for the exception
     * @throws RuntimeException when the thread is not the primary one
     */
    public static void catchAsync(String operation) {
        if (Bukkit.isPrimaryThread()) {
            return;
        }

        throw new RuntimeException("Asynchronous " + operation + "!");
    }

    /**
     * Throws an exception if the current thread is the main one
     *
     * @param operation the reason for the exception
     * @throws RuntimeException when the thread is the primary one
     */
    public static void catchSync(String operation) {
        if (!Bukkit.isPrimaryThread()) {
            return;
        }

        throw new RuntimeException("Synchronous " + operation + "!");
    }

}
