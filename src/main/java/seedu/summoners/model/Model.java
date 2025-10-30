package seedu.summoners.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.summoners.commons.core.GuiSettings;
import seedu.summoners.model.player.Name;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.Team;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Player> PREDICATE_SHOW_ALL_PLAYERS = unused -> true;
    Predicate<Team> PREDICATE_SHOW_ALL_TEAMS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' summoners book file path.
     */
    Path getSummonersBookFilePath();

    /**
     * Sets the user prefs' summoners book file path.
     */
    void setSummonersBookFilePath(Path summonersBookFilePath);

    /**
     * Replaces summoners book data with the data in {@code summonersBook}.
     */
    void setSummonersBook(ReadOnlySummonersBook summonersBook);

    /** Returns the SummonersBook */
    ReadOnlySummonersBook getSummonersBook();

    /**
     * Returns true if a player with the same identity as {@code player} exists in the summoners book.
     */
    boolean hasPlayer(Player player);

    /**
     * Returns true if a team with the same identity as {@code team} exists in the summoners book.
     */
    boolean hasTeam(Team team);

    /**
     * Returns true if the given player is currently in any team.
     */
    boolean isPlayerInAnyTeam(Player player);

    /**
     * Deletes the given player.
     * The player must exist in the summoners book.
     */
    void deletePlayer(Player target);

    /**
     * Deletes the given team.
     * The team must exist in the summoners book.
     */
    void deleteTeam(Team target);

    /**
     * Adds the given player.
     * {@code player} must not already exist in the summoners book.
     */
    void addPlayer(Player player);

    /**
     * Returns an {@code Optional<Player>} containing the player with the given {@code Name}, if present.
     *
     * @param name The name of the player to find.
     * @return An {@code Optional<Player>} containing the matching player, or an empty {@code Optional} if not found.
     */
    Optional<Player> findPlayerByName(Name name);

    /**
     * Adds the given team.
     * {@code team} must not already exist in the summoners book.
     */
    void addTeam(Team team);

    /**
     * Replaces the given player {@code target} with {@code editedPlayer}.
     * {@code target} must exist in the summoners book.
     * The player identity of {@code editedPlayer} must not be the same as another existing player in the summoners book.
     */
    void setPlayer(Player target, Player editedPlayer);

    /**
     * Replaces the given team {@code target} with {@code editedTeam}.
     * {@code target} must exist in the summoners book.
     * The team identity of {@code editedTeam} must not be the same as another existing team in the summoners book.
     */
    void setTeam(Team target, Team editedTeam);

    /** Returns an unmodifiable view of the filtered player list */
    ObservableList<Player> getFilteredPlayerList();

    /** Returns an unmodifiable view of the filtered team list */
    ObservableList<Team> getFilteredTeamList();

    /** Returns an unmodifiable view of the unassigned player list */
    ObservableList<Player> getUnassignedPlayerList();

    /**
     * Updates the filter of the filtered player list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPlayerList(Predicate<Player> predicate);

    /**
     * Updates the filter of the filtered team list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTeamList(Predicate<Team> predicate);
}
