package seedu.summoners.model.team.exceptions;

import seedu.summoners.model.player.Player;

/**
 * Signals that the operation will result in a player being assigned to multiple teams.
 * A player can only be in one team at a time.
 */
public class PlayerAlreadyInTeamException extends RuntimeException {

    /**
     * Constructs a PlayerAlreadyInTeamException with a default message.
     */
    public PlayerAlreadyInTeamException() {
        super("Operation would result in a player being assigned to multiple teams");
    }

    /**
     * Constructs a PlayerAlreadyInTeamException with details about which player is already in a team.
     *
     * @param player The player who is already in a team.
     */
    public PlayerAlreadyInTeamException(Player player) {
        super(String.format("Player %s is already in a team and cannot be added to another team",
                player.getName()));
    }
}
