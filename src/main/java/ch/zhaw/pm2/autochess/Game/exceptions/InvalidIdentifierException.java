package ch.zhaw.pm2.autochess.Game.exceptions;

/**
 * Thrown if the given no Hero has the specified ID
 */
public class InvalidIdentifierException extends GameException{
    /**
     * Default constructor
     * @param message Error message
     */
    public InvalidIdentifierException(String message) {
        super(message);
    }
    /**
     * Constructor with extra parameter
     * @param message Error message
     * @param thrown {@link Throwable}
     */
    public InvalidIdentifierException(String message, Throwable thrown) {
        super(message, thrown);
    }
}

