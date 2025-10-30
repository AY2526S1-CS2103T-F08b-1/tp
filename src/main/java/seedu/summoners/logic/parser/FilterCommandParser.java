package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHAMPION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCORE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FilterCommand.FilterPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Champion;
import seedu.address.model.person.Rank;
import seedu.address.model.person.Role;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ROLE, PREFIX_RANK, PREFIX_CHAMPION, PREFIX_SCORE);

        FilterPersonDescriptor filterPersonDescriptor = new FilterPersonDescriptor();

        parseRanksForFilter(argMultimap.getAllValues(PREFIX_RANK))
                .ifPresent(filterPersonDescriptor::setRanks);

        parseRolesForFilter(argMultimap.getAllValues(PREFIX_ROLE))
                .ifPresent(filterPersonDescriptor::setRoles);

        parseChampionsForFilter(argMultimap.getAllValues(PREFIX_CHAMPION))
                .ifPresent(filterPersonDescriptor::setChampions);

        parseScoreForFilter(argMultimap.getAllValues(PREFIX_SCORE))
                .ifPresent(filterPersonDescriptor::setScoreThreshold);

        if (!filterPersonDescriptor.isAnyFieldFiltered()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_NOT_FILTERED));
        }

        return new FilterCommand(filterPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> ranks} into a {@code Set<Rank>} if {@code ranks} is non-empty.
     * If {@code ranks} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Rank>} containing zero ranks.
     */
    private Optional<Set<Rank>> parseRanksForFilter(Collection<String> ranks) throws ParseException {
        assert ranks != null;

        if (ranks.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> rankSet = ranks.size() == 1 && ranks.contains("") ? Collections.emptySet() : ranks;
        return Optional.of(ParserUtil.parseRanks(rankSet));
    }

    /**
     * Parses {@code Collection<String> roles} into a {@code Set<Role>} if {@code roles} is non-empty.
     * If {@code roles} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Role>} containing zero roles.
     */
    private Optional<Set<Role>> parseRolesForFilter(Collection<String> roles) throws ParseException {
        assert roles != null;

        if (roles.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> roleSet = roles.size() == 1 && roles.contains("") ? Collections.emptySet() : roles;
        return Optional.of(ParserUtil.parseRoles(roleSet));
    }

    /**
     * Parses {@code Collection<String> champions} into a {@code Set<Champion>} if {@code champions} is non-empty.
     * If {@code champions} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Champion>} containing zero champions.
     */
    private Optional<Set<Champion>> parseChampionsForFilter(Collection<String> champions) throws ParseException {
        assert champions != null;

        if (champions.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> championSet = champions.size() == 1 && champions.contains("")
                ? Collections.emptySet()
                : champions;
        return Optional.of(ParserUtil.parseChampions(championSet));
    }

    /**
     * Parses {@code Collection<String> scores} into a {@code Float} if {@code scores} is non-empty.
     * If {@code scores} contain only one element which is an empty string, it will be parsed as empty.
     * Only the first score is considered; others are ignored.
     */
    private Optional<Float> parseScoreForFilter(Collection<String> scores) throws ParseException {
        assert scores != null;

        if (scores.isEmpty()) {
            return Optional.empty();
        }

        Collection<String> scoreSet = scores.size() == 1 && scores.contains("")
                ? Collections.emptySet()
                : scores;

        if (scoreSet.isEmpty()) {
            return Optional.empty();
        }

        // Use ParserUtil for validation and parsing
        String firstScore = scoreSet.iterator().next();
        return Optional.of(ParserUtil.parseScore(firstScore));
    }
}
