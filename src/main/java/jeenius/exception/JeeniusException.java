package jeenius.exception;

/**
 * Represents an exception specific to the Jeenius application.
 */
public class JeeniusException extends Exception {
    /**
     * Creates a new JeeniusException with the specified error message.
     *
     * @param message The error message associated with the exception.
     */
    public JeeniusException(String message) {
        super(message);
    }
}
