package seedu.summoners.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.summoners.commons.core.index.Index;
import seedu.summoners.commons.util.ToStringBuilder;
import seedu.summoners.logic.Messages;
import seedu.summoners.logic.commands.exceptions.CommandException;
import seedu.summoners.model.Model;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.Team;
import seedu.summoners.model.team.exceptions.DuplicateChampionException;
import seedu.summoners.model.team.exceptions.DuplicateRoleException;
import seedu.summoners.model.team.exceptions.InvalidTeamSizeException;

/**
 * Creates a team consisting of 5 existing players, manually provided by the user
 */
public class MakeGroupCommand extends Command {

    public static final String COMMAND_WORD = "makeGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a team of 5 players identified by the index "
            + "numbers used in the displayed player list.\n"
            + "Parameters: INDEX_1 INDEX_2 INDEX_3 INDEX_4 INDEX_5\n"
            + "Example: " + COMMAND_WORD + " 1 2 3 4 5";

    public static final String MESSAGE_MAKE_GROUP_SUCCESS = "New team created: %1$s";

    public static final String MESSAGE_INVALID_TEAM_SIZE = "Exactly %1$d index numbers must be provided.";
    public static final String MESSAGE_DUPLICATE_INDEX = "Duplicate index numbers found in the input.";
    public static final String MESSAGE_REUSED_PLAYER = "Player is already in another team.\n%1$s";

    private final List<Index> indexList;

    /**
     * Creates a MakeGroupCommand to create a team with the specified players.
     */
    public MakeGroupCommand(List<Index> indexList) {
        requireNonNull(indexList);
        this.indexList = indexList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Check number of indices
        if (indexList.size() != Team.TEAM_SIZE) {
            throw new CommandException(String.format(MESSAGE_INVALID_TEAM_SIZE, Team.TEAM_SIZE));
        }

        // Check for duplicates in provided index numbers
        long uniqueIndexCount = indexList.stream().map(Index::getZeroBased).distinct().count();
        if (uniqueIndexCount < indexList.size()) {
            throw new CommandException(MESSAGE_DUPLICATE_INDEX);
        }

        // Check if indices are valid
        List<Player> lastShownList = model.getFilteredPlayerList();
        boolean hasInvalidIndex = indexList.stream().anyMatch(index -> index.getZeroBased() >= lastShownList.size());
        if (hasInvalidIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_PLAYER_DISPLAYED_INDEX);
        }

        // Fetch stream of players from index
        List<Player> teamMembers = indexList.stream().map(index -> lastShownList.get(index.getZeroBased())).toList();

        // Check if players are not in other teams
        Optional<Player> reusedPlayerOptional = teamMembers.stream().filter(model::isPlayerInAnyTeam).findFirst();
        if (reusedPlayerOptional.isPresent()) {
            throw new CommandException(String.format(MESSAGE_REUSED_PLAYER,
                    Messages.format(reusedPlayerOptional.get())));
        }

        // Construct and check for duplicates
        try {
            // Team creation handles duplicate role, rank, and champion validations
            Team teamToAdd = new Team(teamMembers);
            model.addTeam(teamToAdd);
            return new CommandResult(String.format(MESSAGE_MAKE_GROUP_SUCCESS, Messages.format(teamToAdd)));
        } catch (InvalidTeamSizeException | DuplicateRoleException | DuplicateChampionException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof MakeGroupCommand
                && indexList.equals(((MakeGroupCommand) other).indexList));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("indexList", indexList)
                .toString();
    }
}
