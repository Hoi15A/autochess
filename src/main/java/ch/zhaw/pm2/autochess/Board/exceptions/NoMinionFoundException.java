package ch.zhaw.pm2.autochess.Board.exceptions;

public class NoMinionFoundException extends BoardManagerException {
    /**
     * Default constructor
     * @param message Error message
     */
    public NoMinionFoundException(String message) {
        super(message);
    }
    /**
     * Constructor with extra parameter
     * @param message Error message
     * @param thrown {@link Throwable}
     */
    public NoMinionFoundException(String message, Throwable thrown) {
        super(message, thrown);
    }

}
