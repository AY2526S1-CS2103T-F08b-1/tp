package seedu.summoners.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.summoners.model.Model.PREDICATE_SHOW_ALL_PLAYERS;

import seedu.summoners.model.Model;

/**
 * Lists all players in the summoners book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all players";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPlayerList(PREDICATE_SHOW_ALL_PLAYERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
