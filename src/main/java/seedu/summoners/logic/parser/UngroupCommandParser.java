package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UngroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UngroupCommand object.
 */
public class UngroupCommandParser implements Parser<UngroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UngroupCommand
     * and returns an UngroupCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public UngroupCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UngroupCommand.MESSAGE_USAGE));
        }

        // Check if the argument is "all"
        if (trimmedArgs.equalsIgnoreCase("all")) {
            return new UngroupCommand();
        }

        // Otherwise, parse as an index
        try {
            Index index = ParserUtil.parseIndex(trimmedArgs);
            return new UngroupCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UngroupCommand.MESSAGE_USAGE), pe);
        }
    }
}
