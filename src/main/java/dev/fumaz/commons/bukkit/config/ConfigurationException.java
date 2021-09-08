package dev.fumaz.commons.bukkit.config;

/**
 * An exception that occured while handling a configuration file
 */
public class ConfigurationException extends RuntimeException {

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

}
