package ch.zhaw.pm2.autochess.Board.exceptions;

public abstract class BoardManagerException extends Exception{
    /**
     * Default constructor
     * @param message Error message
     */
    public BoardManagerException(String message) {
        super(message);
    }

    /**
     * Constructor with extra parameter
     * @param message Error message
     * @param thrown {@link Throwable}
     */
    public BoardManagerException(String message, Throwable thrown) {
        super(message, thrown);
    }
}

