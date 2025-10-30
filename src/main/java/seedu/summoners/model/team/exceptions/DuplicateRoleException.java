package seedu.summoners.model.team.exceptions;

import seedu.summoners.model.player.Player;

/**
 * Signals that the operation would result in duplicate roles in a team.
 * Each team must have exactly one player for each of the five roles: Top, Jungle, Mid, ADC, Support.
 */
public class DuplicateRoleException extends RuntimeException {
    private final Player player1;
    private final Player player2;

    /**
     * Constructs a DuplicateRoleException with the two players who have conflicting roles.
     *
     * @param player1 The first player with the duplicate role.
     * @param player2 The second player with the duplicate role.
     */
    public DuplicateRoleException(Player player1, Player player2) {
        super("Operation would result in duplicate roles in the team. "
                + player1.getName() + " and " + player2.getName() + " both have the role: "
                + player1.getRole());
        this.player1 = player1;
        this.player2 = player2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}
