package ch.zhaw.pm2.autochess.minion.exceptions;

/**
 * Thrown if the given minion attribute is not valid
 */
public class InvalidMinionAttributeException extends MinionException {
        /**
         * Default constructor
         * @param message Error message
         */
        public InvalidMinionAttributeException(String message) {
            super(message);
        }
        /**
         * Constructor with extra parameter
         * @param message Error message
         * @param thrown {@link Throwable}
         */
        public InvalidMinionAttributeException(String message, Throwable thrown) {
            super(message, thrown);
        }
}
