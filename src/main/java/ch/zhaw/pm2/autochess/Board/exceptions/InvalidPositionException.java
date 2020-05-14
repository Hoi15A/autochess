package ch.zhaw.pm2.autochess.Board.exceptions;

public class InvalidPositionException extends BoardManagerException{
    /**
     * Default constructor
     * @param message Error message
     */
    public InvalidPositionException(String message) {
        super(message);
    }
    /**
     * Constructor with extra parameter
     * @param message Error message
     * @param thrown {@link Throwable}
     */
    public InvalidPositionException(String message, Throwable thrown) {
        super(message, thrown);
    }
}
