package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.team.Team;

/**
 * Removes a specific team or all teams from the address book.
 */
public class UngroupCommand extends Command {

    public static final String COMMAND_WORD = "ungroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a team by its index number or removes all teams.\n"
            + "Parameters: INDEX (must be a positive integer) or 'all'\n"
            + "Examples:\n"
            + COMMAND_WORD + " 1 (removes team 1)\n"
            + COMMAND_WORD + " all (removes all teams)";

    public static final String MESSAGE_SUCCESS = "Removed team: %1$s";
    public static final String MESSAGE_SUCCESS_ALL = "Successfully removed %1$d team(s). "
            + "All persons are now unassigned.";
    public static final String MESSAGE_NO_TEAMS = "No teams to remove.";

    private final Index targetIndex;
    private final boolean removeAll;

    /**
     * Creates an UngroupCommand to remove a specific team.
     * @param targetIndex Index of the team to remove.
     */
    public UngroupCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.removeAll = false;
    }

    /**
     * Creates an UngroupCommand to remove all teams.
     */
    public UngroupCommand() {
        this.targetIndex = null;
        this.removeAll = true;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Team> lastShownList = model.getFilteredTeamList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_NO_TEAMS);
        }

        if (removeAll) {
            assert targetIndex == null : "Target index should be null when removing all teams.";
            return executeRemoveAll(model, lastShownList);
        } else {
            assert targetIndex != null : "Target index should not be null when removing a single team.";
            return executeRemoveSingle(model, lastShownList);
        }
    }

    /**
     * Removes all teams from the model.
     *
     * @param model Model to remove teams from.
     * @param teamList List of teams to remove.
     * @return CommandResult with success message.
     */
    private CommandResult executeRemoveAll(Model model, List<Team> teamList) {
        int teamCount = teamList.size();

        // Create a copy of the team list to avoid concurrent modification
        List<Team> teamsToRemove = List.copyOf(teamList);

        // Remove all teams
        for (Team team : teamsToRemove) {
            model.deleteTeam(team);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS_ALL, teamCount));
    }

    /**
     * Removes a single team at the specified index.
     *
     * @param model Model to remove team from.
     * @param teamList List of teams currently displayed.
     * @return CommandResult with success message.
     * @throws CommandException If the index is invalid.
     */
    private CommandResult executeRemoveSingle(Model model, List<Team> teamList) throws CommandException {
        if (targetIndex.getZeroBased() >= teamList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }

        Team teamToDelete = teamList.get(targetIndex.getZeroBased());
        model.deleteTeam(teamToDelete);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(teamToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UngroupCommand)) {
            return false;
        }

        UngroupCommand otherCommand = (UngroupCommand) other;
        if (removeAll && otherCommand.removeAll) {
            return true;
        }

        if (!removeAll && !otherCommand.removeAll) {
            return targetIndex.equals(otherCommand.targetIndex);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetIndex, removeAll);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("removeAll", removeAll)
                .toString();
    }
}
