package ch.zhaw.pm2.autochess.Hero.exceptions;

/**
 * Custom exception super class for exceptions in hero class
 */
public abstract class HeroException extends Exception {
    /**
     * Default constructor
     * @param message Error message
     */
    public HeroException(String message) {
        super(message);
    }

    /**
     * Constructor with extra parameter
     * @param message Error message
     * @param thrown {@link Throwable}
     */
    public HeroException(String message, Throwable thrown) {
        super(message, thrown);
    }
}

