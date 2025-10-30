package seedu.summoners.model.team.exceptions;

import seedu.summoners.model.player.Player;

/**
 * Signals that the operation would result in duplicate champions in a team.
 * Each team cannot have multiple players with the same champion.
 */
public class DuplicateChampionException extends RuntimeException {
    private final Player player1;
    private final Player player2;

    /**
     * Constructs a DuplicateChampionException with the two players who have conflicting champions.
     *
     * @param player1 The first player with the duplicate champion.
     * @param player2 The second player with the duplicate champion.
     */
    public DuplicateChampionException(Player player1, Player player2) {
        super("Operation would result in duplicate champions in the team. "
                + player1.getName() + " and " + player2.getName() + " both play: "
                + player1.getChampion());
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
