package seedu.summoners.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.summoners.model.Model.PREDICATE_SHOW_ALL_PLAYERS;
import static seedu.summoners.model.Model.PREDICATE_SHOW_ALL_TEAMS;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.summoners.commons.core.index.Index;
import seedu.summoners.commons.util.ToStringBuilder;
import seedu.summoners.logic.Messages;
import seedu.summoners.logic.commands.exceptions.CommandException;
import seedu.summoners.model.Model;
import seedu.summoners.model.player.Champion;
import seedu.summoners.model.player.Name;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.player.Rank;
import seedu.summoners.model.player.Role;
import seedu.summoners.model.player.Stats;
import seedu.summoners.model.tag.Tag;
import seedu.summoners.model.team.Team;

/**
 * Deletes the latest performance statistics entry from the specified {@link Player}.
 * <p>
 * The target {@code Player} is identified by its index in the currently displayed player list.
 * If that player belongs to a team currently shown in the team list, the corresponding
 * {@link Team} entry is also updated to reference the edited {@code Player}.
 * </p>
 */
public class DeleteStatsCommand extends Command {

    /** Primary command word for deleting the latest stats of a player. */
    public static final String COMMAND_WORD = "deleteStats";

    /**
     * Usage string describing parameters and an example invocation.
     * <pre>
     * deleteStats INDEX
     * e.g. deleteStats 1
     * </pre>
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete the latest performance stats of"
            + " the player identified by the index number used in the displayed player list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    /** Success message format used in the {@link CommandResult}. */
    public static final String MESSAGE_RECORD_SUCCESS = "Latest stats deleted: %1$s";

    private final Index index;

    /**
     * Creates a {@code DeleteStatsCommand} targeting the player at the given {@code index}.
     *
     * @param index Index of the player in the filtered player list. Must be non-null.
     */
    public DeleteStatsCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    /**
     * Executes the command: removes the most recent statistics item from the selected player and,
     * if applicable, updates any team that contains that player to reference the edited instance.
     * <ul>
     *   <li>Validates the index against the current filtered player list.</li>
     *   <li>Builds an edited {@link Player} whose {@link Stats} has the latest entry removed.</li>
     *   <li>Updates the player (and the containing team, if any) in the {@link Model}.</li>
     *   <li>Refreshes both player and team filtered lists.</li>
     * </ul>
     *
     * @param model The model in which updates are applied. Must be non-null.
     * @return A {@link CommandResult} summarizing the operation.
     * @throws CommandException If the index is invalid (out of bounds).
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Player> lastShownPlayerList = model.getFilteredPlayerList();
        List<Team> lastShownTeamList = model.getFilteredTeamList();

        if (index.getZeroBased() >= lastShownPlayerList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PLAYER_DISPLAYED_INDEX);
        }

        Player playerToEdit = lastShownPlayerList.get(index.getZeroBased());
        Player editedPlayer = createEditedPlayer(playerToEdit);

        Optional<Team> teamToEditOptional = lastShownTeamList.stream()
                .filter(team -> team.hasPlayer(playerToEdit))
                .findFirst();

        if (teamToEditOptional.isPresent()) {
            Team teamToEdit = teamToEditOptional.get();
            Team editedTeam = createEditedTeam(teamToEdit, playerToEdit, editedPlayer);

            // Apply the updates only after successful validation.
            model.setPlayer(playerToEdit, editedPlayer);
            model.setTeam(teamToEdit, editedTeam);
        } else {
            model.setPlayer(playerToEdit, editedPlayer);
        }

        model.updateFilteredPlayerList(PREDICATE_SHOW_ALL_PLAYERS);
        model.updateFilteredTeamList(PREDICATE_SHOW_ALL_TEAMS);
        return new CommandResult(String.format(MESSAGE_RECORD_SUCCESS, Messages.format(editedPlayer)));
    }

    /**
     * Produces a new {@link Player} based on {@code playerToEdit} whose {@link Stats}
     * has the latest (most recently added) entry removed.
     * <p>
     * Identity (ID), name, role, rank, champion, tags, and win/loss record are preserved.
     * </p>
     *
     * @param playerToEdit The original player to update. Must be non-null.
     * @return A new {@code Player} with updated {@code Stats}.
     */
    private static Player createEditedPlayer(Player playerToEdit) {
        assert playerToEdit != null;

        String id = playerToEdit.getId();
        Name updatedName = playerToEdit.getName();
        Rank updatedRank = playerToEdit.getRank();
        Role updatedRole = playerToEdit.getRole();
        Champion updatedChampion = playerToEdit.getChampion();
        Set<Tag> updatedTags = playerToEdit.getTags();
        int wins = playerToEdit.getWins();
        int losses = playerToEdit.getLosses();

        Stats updatedStats = playerToEdit.getStats().deleteLatestStats();

        // Preserve id from the original player
        return new Player(id, updatedName, updatedRole, updatedRank, updatedChampion, updatedTags,
                wins, losses, updatedStats);
    }

    /**
     * Produces a new {@link Team} identical to {@code teamToEdit} except that
     * {@code playerToEdit} is replaced by {@code editedPlayer} in the team roster.
     * Team identity (ID) and win–loss record are preserved.
     *
     * @param teamToEdit   The team currently containing {@code playerToEdit}. Must contain that player.
     * @param playerToEdit The existing player entry to replace.
     * @param editedPlayer The new player entry to insert.
     * @return A new {@code Team} reflecting the player update.
     * @throws AssertionError If {@code teamToEdit} does not contain {@code playerToEdit}.
     */
    private static Team createEditedTeam(Team teamToEdit, Player playerToEdit, Player editedPlayer) {
        assert teamToEdit.hasPlayer(playerToEdit);

        String id = teamToEdit.getId();
        List<Player> updatedPlayerList = new ArrayList<>(teamToEdit.getPlayers());
        int playerIndex = updatedPlayerList.indexOf(playerToEdit);
        updatedPlayerList.set(playerIndex, editedPlayer);
        int wins = teamToEdit.getWins();
        int losses = teamToEdit.getLosses();

        return new Team(id, updatedPlayerList, wins, losses);
    }

    /**
     * Two {@code DeleteStatsCommand}s are equal if they target the same index.
     *
     * @param other The other object to compare.
     * @return {@code true} if both commands have the same target index; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteStatsCommand)) {
            return false;
        }

        DeleteStatsCommand otherDeleteStatsCommand = (DeleteStatsCommand) other;
        return index.equals(otherDeleteStatsCommand.index);
    }

    /**
     * Returns a string representation suitable for logging and debugging.
     *
     * @return String containing the command class and target index.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}
