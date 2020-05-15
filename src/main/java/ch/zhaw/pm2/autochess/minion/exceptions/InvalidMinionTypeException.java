package ch.zhaw.pm2.autochess.minion.exceptions;

/**
 * Thrown if the given minion type is not valid
 */
public class InvalidMinionTypeException extends MinionException {
        /**
         * Default constructor
         * @param message Error message
         */
        public InvalidMinionTypeException(String message) {
            super(message);
        }
        /**
         * Constructor with extra parameter
         * @param message Error message
         * @param thrown {@link Throwable}
         */
        public InvalidMinionTypeException(String message, Throwable thrown) {
            super(message, thrown);
        }
}
