package ch.zhaw.pm2.autochess.board.exceptions;

public class MinionNotOnBoardException extends BoardManagerException {
    /**
     * Default constructor
     * @param message Error message
     */
    public MinionNotOnBoardException(String message) {
        super(message);
    }
    /**
     * Constructor with extra parameter
     * @param message Error message
     * @param thrown {@link Throwable}
     */
    public MinionNotOnBoardException(String message, Throwable thrown) {
        super(message, thrown);
    }

}
