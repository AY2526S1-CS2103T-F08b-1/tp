package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHAMPION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CPM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GD15;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KDA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddStatsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddStatsCommandParser implements Parser<AddStatsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddStatsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CPM, PREFIX_GD15, PREFIX_KDA);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStatsCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_CPM, PREFIX_GD15, PREFIX_KDA)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStatsCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_RANK, PREFIX_ROLE, PREFIX_CHAMPION);

        String cpm = argMultimap.getValue(PREFIX_CPM).get();
        String gd15 = argMultimap.getValue(PREFIX_GD15).get();
        String kda = argMultimap.getValue(PREFIX_KDA).get();

        if (cpm.isEmpty() || gd15.isEmpty() || kda.isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, AddStatsCommand.MESSAGE_EMPTY_FIELDS));
        }

        return new AddStatsCommand(index, cpm, gd15, kda);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
