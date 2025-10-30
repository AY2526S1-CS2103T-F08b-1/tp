package seedu.summoners.model;

import javafx.collections.ObservableList;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.Team;

/**
 * Unmodifiable view of an summoners book
 */
public interface ReadOnlySummonersBook {

    /**
     * Returns an unmodifiable view of the players list.
     * This list will not contain any duplicate players.
     */
    ObservableList<Player> getPlayerList();

    /**
     * Returns an unmodifiable view of the teams list.
     * This list will not contain any duplicate teams.
     */
    ObservableList<Team> getTeamList();

}
