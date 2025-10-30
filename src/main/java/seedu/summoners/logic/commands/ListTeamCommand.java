package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TEAMS;

import seedu.address.model.Model;

/**
 * Lists all teams in the address book.
 */
public class ListTeamCommand extends Command {

    public static final String COMMAND_WORD = "listteam";
    public static final String MESSAGE_SUCCESS = "Listed all teams";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTeamList(PREDICATE_SHOW_ALL_TEAMS); // clear filters
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
