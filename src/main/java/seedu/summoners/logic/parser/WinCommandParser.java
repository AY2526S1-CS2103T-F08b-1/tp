package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.WinCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new WinCommand object
 */
public class WinCommandParser implements Parser<WinCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the WinCommand
     * and returns a WinCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public WinCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new WinCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, WinCommand.MESSAGE_USAGE), pe);
        }
    }
}
