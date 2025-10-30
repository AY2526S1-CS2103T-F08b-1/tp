package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteStatsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@link DeleteStatsCommand}.
 * <p>
 * Expected format:
 * <pre>
 *   deleteStats INDEX
 *   e.g. deleteStats 1
 * </pre>
 * This parser only extracts the {@code INDEX} from the preamble (there are no prefixed arguments).
 * If the preamble cannot be parsed into a valid positive integer index, a {@link ParseException}
 * is thrown with the command usage message.
 */
public class DeleteStatsCommandParser implements Parser<DeleteStatsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of {@link DeleteStatsCommand}
     * and returns a new {@code DeleteStatsCommand} object for execution.
     *
     * @param args Raw argument string following the command word (e.g., {@code " 1"}).
     * @return A {@code DeleteStatsCommand} targeting the parsed person index.
     * @throws ParseException If the input does not contain a valid index in the preamble
     *                        (e.g., missing/zero/negative/non-numeric).
     */
    public DeleteStatsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            // Re-throw with a standardized message including command usage.
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStatsCommand.MESSAGE_USAGE), pe);
        }

        return new DeleteStatsCommand(index);
    }
}
