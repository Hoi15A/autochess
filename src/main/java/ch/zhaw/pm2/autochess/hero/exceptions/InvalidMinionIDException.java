package ch.zhaw.pm2.autochess.hero.exceptions;

/**
 * Thrown if the given no Minion has the specified ID
 */
public class InvalidMinionIDException extends HeroException{
    /**
     * Default constructor
     * @param message Error message
     */
    public InvalidMinionIDException(String message) {
        super(message);
    }
    /**
     * Constructor with extra parameter
     * @param message Error message
     * @param thrown {@link Throwable}
     */
    public InvalidMinionIDException(String message, Throwable thrown) {
        super(message, thrown);
    }
}
