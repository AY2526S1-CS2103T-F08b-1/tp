package seedu.summoners.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.summoners.commons.util.ToStringBuilder;
import seedu.summoners.model.player.Name;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.player.UniquePlayerList;
import seedu.summoners.model.team.Team;
import seedu.summoners.model.team.UniqueTeamList;

/**
 * Wraps all data at the summoners-book level
 * Duplicates are not allowed (by .isSamePlayer comparison)
 */
public class SummonersBook implements ReadOnlySummonersBook {

    private final UniquePlayerList players;
    private final UniqueTeamList teams;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        players = new UniquePlayerList();
        teams = new UniqueTeamList();
    }

    public SummonersBook() {}

    /**
     * Creates an SummonersBook using the Players in the {@code toBeCopied}
     */
    public SummonersBook(ReadOnlySummonersBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the player list with {@code players}.
     * {@code players} must not contain duplicate players.
     */
    public void setPlayers(List<Player> players) {
        this.players.setPlayers(players);
    }

    /**
     * Replaces the contents of the team list with {@code teams}.
     * {@code teams} must not contain duplicate teams.
     */
    public void setTeams(List<Team> teams) {
        this.teams.setTeams(teams);
    }

    /**
     * Resets the existing data of this {@code SummonersBook} with {@code newData}.
     */
    public void resetData(ReadOnlySummonersBook newData) {
        requireNonNull(newData);

        setPlayers(newData.getPlayerList());
        setTeams(newData.getTeamList());
    }

    //// player-level operations

    /**
     * Returns true if a player with the same identity as {@code player} exists in the summoners book.
     */
    public boolean hasPlayer(Player player) {
        requireNonNull(player);
        return players.contains(player);
    }

    /**
     * Returns true if a team with the same identity as {@code team} exists in the summoners book.
     */
    public boolean hasTeam(Team team) {
        requireNonNull(team);
        return teams.contains(team);
    }

    /**
     * Returns true if the given player is currently in any team.
     */
    public boolean isPlayerInAnyTeam(Player player) {
        requireNonNull(player);
        return teams.isPlayerInAnyTeam(player);
    }

    /**
     * Adds a player to the summoners book.
     * The player must not already exist in the summoners book.
     */
    public void addPlayer(Player p) {
        players.add(p);
    }

    /**
     * Adds a team to the summoners book.
     * The team must not already exist in the summoners book.
     */
    public void addTeam(Team t) {
        teams.add(t);
    }

    /**
     * Replaces the given player {@code target} in the list with {@code editedPlayer}.
     * {@code target} must exist in the summoners book.
     * The player identity of {@code editedPlayer} must not be the same as
     * another existing player in the summoners book.
     */
    public void setPlayer(Player target, Player editedPlayer) {
        requireNonNull(editedPlayer);
        players.setPlayer(target, editedPlayer);
    }

    /**
     * Returns an {@code Optional<Player>} containing the player with the given {@code Name}, if present
     * in the summoners book.
     *
     * @param name The name of the player to find.
     * @return An {@code Optional<Player>} containing the matching player, or an empty {@code Optional} if not found.
     */
    public Optional<Player> findPlayerByName(Name name) {
        requireNonNull(name);
        return players.asUnmodifiableObservableList()
                .stream()
                .filter(p -> p.getName().equals(name))
                .findFirst();
    }

    /**
     * Replaces the given team {@code target} in the list with {@code editedTeam}.
     * {@code target} must exist in the summoners book.
     * The team identity of {@code editedTeam} must not be the same as another existing team in the summoners book.
     */
    public void setTeam(Team target, Team editedTeam) {
        requireNonNull(editedTeam);
        teams.setTeam(target, editedTeam);
    }

    /**
     * Removes {@code key} from this {@code SummonersBook}.
     * {@code key} must exist in the summoners book.
     */
    public void removePlayer(Player key) {
        players.remove(key);
    }

    /**
     * Removes {@code key} from this {@code SummonersBook}.
     * {@code key} must exist in the summoners book.
     */
    public void removeTeam(Team key) {
        teams.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("players", players)
                .add("teams", teams)
                .toString();
    }

    @Override
    public ObservableList<Player> getPlayerList() {
        return players.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Team> getTeamList() {
        return teams.asUnmodifiableObservableList();
    }

    /**
     * Returns an observable list of players who are not currently in any team.
     * @return Observable list of unassigned players.
     */
    public ObservableList<Player> getUnassignedPlayerList() {
        return players.asUnmodifiableObservableList()
                .filtered(player -> !teams.isPlayerInAnyTeam(player));
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SummonersBook)) {
            return false;
        }

        SummonersBook otherSummonersBook = (SummonersBook) other;
        return players.equals(otherSummonersBook.players)
                && teams.equals(otherSummonersBook.teams);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(players, teams);
    }
}
