package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MakeGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MakeGroupCommand object
 */
public class MakeGroupCommandParser implements Parser<MakeGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of MakeGroupCommand
     * and returns a MakeGroupCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public MakeGroupCommand parse(String args) throws ParseException {
        try {
            List<Index> indices = ParserUtil.parseIndices(args);
            return new MakeGroupCommand(indices);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MakeGroupCommand.MESSAGE_USAGE), pe);
        }
    }
}
