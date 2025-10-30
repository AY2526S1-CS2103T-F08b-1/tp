package seedu.summoners.model;

import static java.util.Objects.requireNonNull;
import static seedu.summoners.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.summoners.commons.core.GuiSettings;
import seedu.summoners.commons.core.LogsCenter;
import seedu.summoners.model.player.Name;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.Team;

/**
 * Represents the in-memory model of the summoners book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final SummonersBook summonersBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Player> filteredPlayers;
    private final FilteredList<Team> filteredTeams;

    /**
     * Initializes a ModelManager with the given summonersBook and userPrefs.
     */
    public ModelManager(ReadOnlySummonersBook summonersBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(summonersBook, userPrefs);

        logger.fine("Initializing with summoners book: " + summonersBook + " and user prefs " + userPrefs);

        this.summonersBook = new SummonersBook(summonersBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPlayers = new FilteredList<>(this.summonersBook.getPlayerList());
        filteredTeams = new FilteredList<>(this.summonersBook.getTeamList());
    }

    public ModelManager() {
        this(new SummonersBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getSummonersBookFilePath() {
        return userPrefs.getSummonersBookFilePath();
    }

    @Override
    public void setSummonersBookFilePath(Path summonersBookFilePath) {
        requireNonNull(summonersBookFilePath);
        userPrefs.setSummonersBookFilePath(summonersBookFilePath);
    }

    //=========== SummonersBook ================================================================================

    @Override
    public void setSummonersBook(ReadOnlySummonersBook summonersBook) {
        this.summonersBook.resetData(summonersBook);
    }

    @Override
    public ReadOnlySummonersBook getSummonersBook() {
        return summonersBook;
    }

    //=========== Player-level Operations ====================================================================

    @Override
    public boolean hasPlayer(Player player) {
        requireNonNull(player);
        return summonersBook.hasPlayer(player);
    }

    @Override
    public boolean isPlayerInAnyTeam(Player player) {
        requireNonNull(player);
        return summonersBook.isPlayerInAnyTeam(player);
    }

    @Override
    public void deletePlayer(Player target) {
        summonersBook.removePlayer(target);
    }

    @Override
    public void addPlayer(Player player) {
        summonersBook.addPlayer(player);
        updateFilteredPlayerList(PREDICATE_SHOW_ALL_PLAYERS);
    }

    @Override
    public void setPlayer(Player target, Player editedPlayer) {
        requireAllNonNull(target, editedPlayer);

        summonersBook.setPlayer(target, editedPlayer);
    }

    /**
     * Returns an {@code Optional<Player>} containing the player with the given {@code Name}, if present.
     * Delegates the search to the underlying {@code SummonersBook}.
     *
     * @param name The name of the player to find.
     * @return An {@code Optional<Player>} containing the matching player, or an empty {@code Optional} if not found.
     */
    @Override
    public Optional<Player> findPlayerByName(Name name) {
        requireNonNull(name);
        return summonersBook.findPlayerByName(name);
    }

    //=========== Team-level Operations ======================================================================

    @Override
    public boolean hasTeam(Team team) {
        requireNonNull(team);
        return summonersBook.hasTeam(team);
    }

    @Override
    public void deleteTeam(Team target) {
        summonersBook.removeTeam(target);
    }

    @Override
    public void addTeam(Team team) {
        summonersBook.addTeam(team);
        updateFilteredTeamList(PREDICATE_SHOW_ALL_TEAMS);
    }

    @Override
    public void setTeam(Team target, Team editedTeam) {
        requireAllNonNull(target, editedTeam);

        summonersBook.setTeam(target, editedTeam);
    }

    //=========== Filtered Player List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Player} backed by the internal list of
     * {@code versionedSummonersBook}
     */
    @Override
    public ObservableList<Player> getFilteredPlayerList() {
        return filteredPlayers;
    }

    @Override
    public void updateFilteredPlayerList(Predicate<Player> predicate) {
        requireNonNull(predicate);
        filteredPlayers.setPredicate(predicate);
    }

    //=========== Filtered Team List Accessors ===============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Player} backed by the internal list of
     * {@code versionedSummonersBook}
     */
    @Override
    public ObservableList<Team> getFilteredTeamList() {
        return filteredTeams;
    }

    @Override
    public void updateFilteredTeamList(Predicate<Team> predicate) {
        requireNonNull(predicate);
        filteredTeams.setPredicate(predicate);
    }

    //=========== Unassigned Player List Accessors ===============================================================

    /**
     * Returns an unmodifiable view of the list of unassigned {@code Player} backed by the internal list of
     * {@code versionedSummonersBook}
     */
    @Override
    public ObservableList<Player> getUnassignedPlayerList() {
        return summonersBook.getUnassignedPlayerList();
    }

    //=========== Equals ======================================================================================
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return summonersBook.equals(otherModelManager.summonersBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPlayers.equals(otherModelManager.filteredPlayers)
                && filteredTeams.equals(otherModelManager.filteredTeams);
    }
}
