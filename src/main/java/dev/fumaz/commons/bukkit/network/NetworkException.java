package dev.fumaz.commons.bukkit.network;

public class NetworkException extends RuntimeException {

    /**
     * An exception that occurred whilst performing a networking operation.
     *
     * @param message a short explanation of the exception being thrown
     */
    public NetworkException(String message) {
        super(message);
    }

    /**
     * An exception that occurred whilst performing a networking operation,
     * caused by another exception.
     *
     * @param message a short explanation of the exception being thrown
     * @param cause   the exception that caused this exception
     */
    public NetworkException(String message, Throwable cause) {
        super(message, cause);
    }

}
