package dev.fumaz.commons.bukkit.config;

/**
 * An exception that occured while saving configuration to a file
 */
public class ConfigurationSaveException extends ConfigurationException {

    public ConfigurationSaveException(String message) {
        super(message);
    }

    public ConfigurationSaveException(String message, Throwable cause) {
        super(message, cause);
    }

}
