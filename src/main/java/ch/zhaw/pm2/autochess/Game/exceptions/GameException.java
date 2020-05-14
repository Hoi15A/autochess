package ch.zhaw.pm2.autochess.Game.exceptions;

/**
 * Custom exception super class for exceptions in game class
 */
public abstract class GameException extends Exception {
    /**
     * Default constructor
     * @param message Error message
     */
    public GameException(String message) {
        super(message);
    }

    /**
     * Constructor with extra parameter
     * @param message Error message
     * @param thrown {@link Throwable}
     */
    public GameException(String message, Throwable thrown) {
        super(message, thrown);
    }
}
