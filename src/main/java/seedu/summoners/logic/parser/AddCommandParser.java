package seedu.summoners.logic.parser;

import static seedu.summoners.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.summoners.logic.parser.CliSyntax.PREFIX_CHAMPION;
import static seedu.summoners.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.summoners.logic.parser.CliSyntax.PREFIX_RANK;
import static seedu.summoners.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.summoners.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.summoners.logic.commands.AddCommand;
import seedu.summoners.logic.parser.exceptions.ParseException;
import seedu.summoners.model.player.Champion;
import seedu.summoners.model.player.Name;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.player.Rank;
import seedu.summoners.model.player.Role;
import seedu.summoners.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_RANK, PREFIX_ROLE, PREFIX_CHAMPION, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_CHAMPION, PREFIX_RANK, PREFIX_ROLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_RANK, PREFIX_ROLE, PREFIX_CHAMPION);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Rank rank = ParserUtil.parseRank(argMultimap.getValue(PREFIX_RANK).get());
        Role role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());
        Champion champion = ParserUtil.parseChampion(argMultimap.getValue(PREFIX_CHAMPION).get());
        Set<Tag> tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Player player = new Player(name, role, rank, champion, tags);

        return new AddCommand(player);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
