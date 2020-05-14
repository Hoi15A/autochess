package ch.zhaw.pm2.autochess.Hero.exceptions;

/**
 * Thrown if the given hero type does not exist
 */
public class InvalidHeroTypeException extends HeroException {
    /**
     * Default constructor
     * @param message Error message
     */
    public InvalidHeroTypeException(String message) {
        super(message);
    }
    /**
     * Constructor with extra parameter
     * @param message Error message
     * @param thrown {@link Throwable}
     */
    public InvalidHeroTypeException(String message, Throwable thrown) {
        super(message, thrown);
    }
}