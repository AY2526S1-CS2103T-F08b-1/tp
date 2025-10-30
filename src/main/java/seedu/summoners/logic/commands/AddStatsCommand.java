package seedu.summoners.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.summoners.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.summoners.logic.parser.CliSyntax.PREFIX_CPM;
import static seedu.summoners.logic.parser.CliSyntax.PREFIX_GD15;
import static seedu.summoners.logic.parser.CliSyntax.PREFIX_KDA;
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
 * Adds a new set of performance statistics (CPM, GD@15, KDA) to a specified player,
 * and updates any team containing that player to reference the edited player.
 * <p>
 * The command targets a player by index in the currently displayed player list.
 * If the player is part of a team shown in the current team list, that team is updated
 * to point to the edited player instance, preserving team identity and order.
 * </p>
 *
 * <p><b>Usage:</b></p>
 * <pre>
 *   {@code addStats INDEX }{ @code PREFIX_CPM }{@code <cpm> }
 *                       { @code PREFIX_GD15 }{@code <gd15> }
 *                       { @code PREFIX_KDA }{@code <kda> }
 *   e.g. addStats 1 c/10.2 g/2400 k/2.6
 * </pre>
 */
public class AddStatsCommand extends Command {
    /** Primary command word for adding statistics. */
    public static final String COMMAND_WORD = "addStats";

    /** Usage string describing parameters and an example. */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add new performance stats of the player identified "
            + "by the index number used in the displayed player list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_CPM + "CPM "
            + PREFIX_GD15 + "GD15 "
            + PREFIX_KDA + "KDA\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_CPM + "10.2 "
            + PREFIX_GD15 + "2400 "
            + PREFIX_KDA + "2.6";

    /** Success message format used in the result. */
    public static final String MESSAGE_RECORD_SUCCESS = "Stats recorded: %1$s";
    public static final String MESSAGE_EMPTY_FIELDS = "All fields are required and cannot be blank";


    private final Index index;
    private final String cpm;
    private final String gd15;
    private final String kda;

    /**
     * Constructs an {@code AddStatsCommand}.
     *
     * @param index Index of the target player in the current filtered player list. Must be non-null.
     * @param cpm   CS per minute as a string (validated by {@link Stats#isValidStats(String, String, String)}).
     * @param gd15  Gold difference at 15 minutes as a string.
     * @param kda   KDA as a string.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public AddStatsCommand(Index index, String cpm, String gd15, String kda) {
        requireNonNull(index);
        requireAllNonNull(cpm, gd15, kda);

        this.index = index;
        this.cpm = cpm;
        this.gd15 = gd15;
        this.kda = kda;
    }

    /**
     * Executes the command: appends a new statistics entry to the selected player, and if that
     * player appears in a displayed team, updates that team to reference the edited player.
     * <ul>
     *   <li>Validates the index against the current filtered player list.</li>
     *   <li>Builds an edited {@link Player} with updated {@link Stats}.</li>
     *   <li>Updates the model's player and, if applicable, the owning team.</li>
     *   <li>Refreshes both player and team filtered lists.</li>
     * </ul>
     *
     * @param model The model in which the update is performed. Must be non-null.
     * @return Command result with a success message describing the edited player.
     * @throws CommandException if the index is invalid or the model rejects the update.
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
        Player editedPlayer = createEditedPlayer(playerToEdit, cpm, gd15, kda);

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
     * Creates a new {@link Player} based on {@code playerToEdit} with updated {@link Stats}
     * produced by appending the given CPM, GD@15, and KDA values.
     * <p>
     * Identity (ID) and other attributes are preserved; wins and losses are also carried over.
     * </p>
     *
     * @param playerToEdit The original player to update. Must be non-null.
     * @param cpm          CS per minute as a string.
     * @param gd15         Gold difference at 15 minutes as a string.
     * @param kda          KDA as a string.
     * @return A new {@code Player} instance with the updated stats.
     * @throws IllegalArgumentException if the stats values are invalid.
     */
    private static Player createEditedPlayer(Player playerToEdit, String cpm, String gd15, String kda) {
        assert playerToEdit != null;

        String id = playerToEdit.getId();
        Name updatedName = playerToEdit.getName();
        Rank updatedRank = playerToEdit.getRank();
        Role updatedRole = playerToEdit.getRole();
        Champion updatedChampion = playerToEdit.getChampion();
        Set<Tag> updatedTags = playerToEdit.getTags();
        int wins = playerToEdit.getWins();
        int losses = playerToEdit.getLosses();

        Stats updatedStats = playerToEdit.getStats().addLatestStats(cpm, gd15, kda);

        // Preserve id from the original player
        return new Player(id, updatedName, updatedRole, updatedRank, updatedChampion, updatedTags,
                wins, losses, updatedStats);
    }

    /**
     * Produces a new {@link Team} identical to {@code teamToEdit} except that
     * {@code playerToEdit} is replaced by {@code editedPlayer} in the team roster.
     * Preserves team identity (ID) and its win–loss record.
     *
     * @param teamToEdit    The team currently containing {@code playerToEdit}. Must contain that player.
     * @param playerToEdit  The original player entry to replace.
     * @param editedPlayer  The new player entry to insert.
     * @return A new {@code Team} reflecting the player update.
     * @throws AssertionError if the team does not contain {@code playerToEdit}.
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddStatsCommand)) {
            return false;
        }

        AddStatsCommand otherAddStatsCommand = (AddStatsCommand) other;
        return index.equals(otherAddStatsCommand.index)
                && cpm.equals(otherAddStatsCommand.cpm)
                && gd15.equals(otherAddStatsCommand.gd15)
                && kda.equals(otherAddStatsCommand.kda);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("cpm", cpm)
                .add("gd15", gd15)
                .add("kda", kda)
                .toString();
    }
}
