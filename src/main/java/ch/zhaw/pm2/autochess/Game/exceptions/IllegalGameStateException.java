package ch.zhaw.pm2.autochess.Game.exceptions;

/**
 * Thrown if the given value would put the game in an illegal state
 */
public class IllegalGameStateException extends GameException{
    /**
     * Default constructor
     * @param message Error message
     */
    public IllegalGameStateException(String message) {
        super(message);
    }
    /**
     * Constructor with extra parameter
     * @param message Error message
     * @param thrown {@link Throwable}
     */
    public IllegalGameStateException(String message, Throwable thrown) {
        super(message, thrown);
    }
}
