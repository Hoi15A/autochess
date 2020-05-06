package ch.zhaw.pm2.autochess.Game.exceptions;

/**
 * Thrown if the given type is invalid
 */
public class InvalidTypeException extends GameException{
    /**
     * Default constructor
     * @param message Error message
     */
    public InvalidTypeException(String message) {
        super(message);
    }
    /**
     * Constructor with extra parameter
     * @param message Error message
     * @param thrown {@link Throwable}
     */
    public InvalidTypeException(String message, Throwable thrown) {
        super(message, thrown);
    }
}
