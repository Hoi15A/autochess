package ch.zhaw.pm2.autochess.Minion.exceptions;

/**
 * Thrown if the given minion type is not valid
 */
public class InvalidMinionAttributeModifierException extends MinionException {
        /**
         * Default constructor
         * @param message Error message
         */
        public InvalidMinionAttributeModifierException(String message) {
            super(message);
        }
        /**
         * Constructor with extra parameter
         * @param message Error message
         * @param thrown {@link Throwable}
         */
        public InvalidMinionAttributeModifierException(String message, Throwable thrown) {
            super(message, thrown);
        }
}
