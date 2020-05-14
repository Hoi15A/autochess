package ch.zhaw.pm2.autochess.Hero.exceptions;

public class InvalidHeroAttributeException extends HeroException {
    /**
     * Default constructor
     * @param message Error message
     */
    public InvalidHeroAttributeException(String message) {
        super(message);
    }
    /**
     * Constructor with extra parameter
     * @param message Error message
     * @param thrown {@link Throwable}
     */
    public InvalidHeroAttributeException(String message, Throwable thrown) {
        super(message, thrown);
    }
}
