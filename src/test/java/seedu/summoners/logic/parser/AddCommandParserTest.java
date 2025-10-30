package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHAMPION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Champion;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Rank;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private static final String WHITESPACE_PREAMBLE = " ";
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        Person expectedPerson = new PersonBuilder()
                .withName("Bob")
                .withRank("Gold")
                .withRole("Top")
                .withChampion("Garen")
                .withTags("friend")
                .build();

        // whitespace preamble
        assertParseSuccess(parser,
                WHITESPACE_PREAMBLE + "n/Bob rk/Gold rl/Top c/Garen t/friend",
                new AddCommand(expectedPerson));

        // multiple tags
        Person expectedPersonMultipleTags = new PersonBuilder()
                .withName("Bob")
                .withRank("Gold")
                .withRole("Top")
                .withChampion("Garen")
                .withTags("friend", "husband")
                .build();

        assertParseSuccess(parser,
                " n/Bob rk/Gold rl/Top c/Garen t/friend t/husband",
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validInput = " n/Bob rk/Gold rl/Top c/Garen t/friend";

        // multiple names
        assertParseFailure(parser, " n/Amy " + validInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple ranks
        assertParseFailure(parser, " rk/Silver " + validInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RANK));

        // multiple roles
        assertParseFailure(parser, " rl/Jungle " + validInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        // multiple champions
        assertParseFailure(parser, " c/Ashe " + validInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CHAMPION));
    }

    @Test
    public void parse_optionalFieldsMissing_success() throws Exception {
        Person expectedPerson = new PersonBuilder()
                .withName("Amy")
                .withRank("Platinum")
                .withRole("Mid")
                .withChampion("Ahri")
                .withTags()
                .build();

        // no tags
        assertParseSuccess(parser,
                " n/Amy rk/Platinum rl/Mid c/Ahri",
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name
        assertParseFailure(parser, " rk/Gold rl/Top c/Garen t/friend", expectedMessage);

        // missing rank
        assertParseFailure(parser, " n/Bob rl/Top c/Garen t/friend", expectedMessage);

        // missing role
        assertParseFailure(parser, " n/Bob rk/Gold c/Garen t/friend", expectedMessage);

        // missing champion
        assertParseFailure(parser, " n/Bob rk/Gold rl/Top t/friend", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, " n/B@b rk/Gold rl/Top c/Garen t/friend", Name.MESSAGE_CONSTRAINTS);

        // invalid rank
        assertParseFailure(parser, " n/Bob rk/InvalidRank rl/Top c/Garen t/friend", Rank.MESSAGE_CONSTRAINTS);

        // invalid role
        assertParseFailure(parser, " n/Bob rk/Gold rl/InvalidRole c/Garen t/friend", Role.MESSAGE_CONSTRAINTS);

        // invalid champion
        assertParseFailure(parser, " n/Bob rk/Gold rl/Top c/123 t/friend", Champion.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, " n/Bob rk/Gold rl/Top c/Garen t/#friend", Tag.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, " randomPreamble n/Bob rk/Gold rl/Top c/Garen t/friend",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
