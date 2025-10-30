package seedu.summoners.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.summoners.model.Model;
import seedu.summoners.model.SummonersBook;

/**
 * Clears the summoners book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "SummonersBook has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setSummonersBook(new SummonersBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
