package seedu.summoners.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.summoners.model.SummonersBook;
import seedu.summoners.model.Model;

/**
 * Clears the summoners book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Summoners book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setSummonersBook(new SummonersBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
