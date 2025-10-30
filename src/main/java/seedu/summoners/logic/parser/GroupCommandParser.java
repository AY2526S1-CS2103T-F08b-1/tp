package seedu.address.logic.parser;

import seedu.address.logic.commands.GroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GroupCommand object.
 */
public class GroupCommandParser implements Parser<GroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GroupCommand
     * and returns a GroupCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public GroupCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (!trimmedArgs.isEmpty()) {
            throw new ParseException(
                    "The group command does not take any arguments.\n" + GroupCommand.MESSAGE_USAGE);
        }

        return new GroupCommand();
    }
}
