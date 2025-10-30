package seedu.summoners.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.summoners.commons.core.index.Index;
import seedu.summoners.logic.Messages;
import seedu.summoners.logic.commands.exceptions.CommandException;
import seedu.summoners.model.Model;
import seedu.summoners.model.team.Team;

/**
 * Displays the details of a specific {@code Team} in a popup window.
 * <p>Uses the provided list index to compute a friendly label
 * such as "Team 1", which is shown instead of the internal team ID.</p>
 */
public class ViewTeamCommand extends Command {
    public static final String COMMAND_WORD = "viewTeam";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows team details in a popup window.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Viewing Team: %1$s";

    private final Index index;

    public ViewTeamCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Team> teams = model.getFilteredTeamList();
        if (index.getZeroBased() >= teams.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }
        Team team = teams.get(index.getZeroBased());
        return CommandResult.showTeamDetail(String.format(MESSAGE_SUCCESS, Messages.format(team)), team);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewTeamCommand && index.equals(((ViewTeamCommand) other).index));
    }
}

