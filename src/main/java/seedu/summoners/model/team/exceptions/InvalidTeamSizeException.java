package seedu.summoners.model.team.exceptions;

/**
 * Signals that the team does not have exactly 5 players.
 * A valid team must have exactly 5 players.
 */
public class InvalidTeamSizeException extends RuntimeException {
    public InvalidTeamSizeException() {
        super("Invalid team size. A team must have exactly 5 players.");
    }

    public InvalidTeamSizeException(int actualSize) {
        super("Invalid team size: " + actualSize + ". A team must have exactly 5 players.");
    }
}
