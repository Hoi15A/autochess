package ch.zhaw.pm2.autochess.hero.exceptions;

/**
 * Thrown if the given value is negative or exceeds allowed max
 */
public class IllegalValueException extends HeroException{
    /**
     * Default constructor
     * @param message Error message
     */
    public IllegalValueException(String message) {
        super(message);
    }
    /**
     * Constructor with extra parameter
     * @param message Error message
     * @param thrown {@link Throwable}
     */
    public IllegalValueException(String message, Throwable thrown) {
        super(message, thrown);
    }
}
