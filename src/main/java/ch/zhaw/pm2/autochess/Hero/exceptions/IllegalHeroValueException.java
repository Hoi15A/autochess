package ch.zhaw.pm2.autochess.Hero.exceptions;

/**
 * Thrown if the given value is negative or exceeds allowed max
 */
public class IllegalHeroValueException extends HeroException{
    /**
     * Default constructor
     * @param message Error message
     */
    public IllegalHeroValueException(String message) {
        super(message);
    }
    /**
     * Constructor with extra parameter
     * @param message Error message
     * @param thrown {@link Throwable}
     */
    public IllegalHeroValueException(String message, Throwable thrown) {
        super(message, thrown);
    }
}
