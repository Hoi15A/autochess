package ch.zhaw.pm2.autochess.minion.exceptions;

/**
 * Custom exception super class for exceptions in minion class
 */
public abstract class MinionException extends Exception{
    /**
     * Default constructor
     * @param message Error message
     */
    public MinionException(String message) {
        super(message);
    }

    /**
     * Constructor with extra parameter
     * @param message Error message
     * @param thrown {@link Throwable}
     */
    public MinionException(String message, Throwable thrown) {
        super(message, thrown);
    }
}
