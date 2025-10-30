package seedu.summoners.logic.teammatcher;

/**
 * Signals that there are insufficient players to form a complete team.
 */
public class InsufficientPlayersException extends Exception {

    /**
     * Constructs an InsufficientPlayersException with the specified message.
     * @param message The detailed message.
     */
    public InsufficientPlayersException(String message) {
        super(message);
    }
}
