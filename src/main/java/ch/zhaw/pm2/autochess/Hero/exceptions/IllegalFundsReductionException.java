package ch.zhaw.pm2.autochess.Hero.exceptions;

/**
 * Thrown if the specified funds cannot be allocated
 */
public class IllegalFundsReductionException extends HeroException{
    /**
     * Default constructor
     * @param message Error message
     */
    public IllegalFundsReductionException(String message) {
        super(message);
    }
    /**
     * Constructor with extra parameter
     * @param message Error message
     * @param thrown {@link Throwable}
     */
    public IllegalFundsReductionException(String message, Throwable thrown) {
        super(message, thrown);
    }
}
