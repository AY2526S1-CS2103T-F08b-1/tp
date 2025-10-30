package seedu.address.logic.teammatcher;

/**
 * Signals that there are insufficient persons to form a complete team.
 */
public class InsufficientPersonsException extends Exception {

    /**
     * Constructs an InsufficientPersonsException with the specified message.
     * @param message The detailed message.
     */
    public InsufficientPersonsException(String message) {
        super(message);
    }
}
