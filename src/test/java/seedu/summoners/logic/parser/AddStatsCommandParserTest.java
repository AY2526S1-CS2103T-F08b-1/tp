package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CPM_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.CPM_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GD15_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GD15_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.KDA_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CPM_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GD15_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_KDA_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KDA;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddStatsCommand;

/**
 * Unit tests for {@link AddStatsCommandParser}.
 */
public class AddStatsCommandParserTest {

    private final AddStatsCommandParser parser = new AddStatsCommandParser();

    @Test
    public void parse_allRequiredFieldsPresent_success() {
        Index idx = INDEX_FIRST_PERSON;

        String input = idx.getOneBased() + CPM_DESC_AMY + GD15_DESC_AMY + KDA_DESC_AMY;
        AddStatsCommand expected = new AddStatsCommand(idx, VALID_CPM_AMY, VALID_GD15_AMY, VALID_KDA_AMY);
        assertParseSuccess(parser, input, expected);

        // leading whitespace allowed
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + input, expected);

        // extra spaces between tokens
        String spaced = idx.getOneBased() + "   " + CPM_DESC_AMY + "   " + GD15_DESC_AMY + "   " + KDA_DESC_AMY + "  ";
        assertParseSuccess(parser, spaced, expected);
    }

    @Test
    public void parse_missingIndex_failure() {
        // no index preamble
        String input = CPM_DESC_AMY + GD15_DESC_AMY + KDA_DESC_AMY;
        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStatsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_failure() {
        assertParseFailure(parser, "0" + CPM_DESC_AMY + GD15_DESC_AMY + KDA_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStatsCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-1" + CPM_DESC_AMY + GD15_DESC_AMY + KDA_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStatsCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "abc" + CPM_DESC_AMY + GD15_DESC_AMY + KDA_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStatsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingRequiredPrefixes_failure() {
        // missing c/
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + GD15_DESC_AMY + KDA_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStatsCommand.MESSAGE_USAGE));

        // missing g/
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + CPM_DESC_AMY + KDA_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStatsCommand.MESSAGE_USAGE));

        // missing k/
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + CPM_DESC_AMY + GD15_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStatsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyValuesAfterPrefixes_failure() {
        // c/ <empty>
        assertParseFailure(parser,
                INDEX_FIRST_PERSON.getOneBased() + " " + CliSyntax.PREFIX_CPM + " "
                        + GD15_DESC_AMY + KDA_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStatsCommand.MESSAGE_EMPTY_FIELDS));

        // g/ <empty>
        assertParseFailure(parser,
                INDEX_FIRST_PERSON.getOneBased() + CPM_DESC_AMY
                        + " " + CliSyntax.PREFIX_GD15 + " "
                        + KDA_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStatsCommand.MESSAGE_EMPTY_FIELDS));

        // k/ <empty>
        assertParseFailure(parser,
                INDEX_FIRST_PERSON.getOneBased() + CPM_DESC_AMY + GD15_DESC_AMY
                        + " " + CliSyntax.PREFIX_KDA + " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStatsCommand.MESSAGE_EMPTY_FIELDS));
    }

    @Test
    public void parse_extraPreambleTokens_failure() {
        assertParseFailure(parser,
                "extra " + INDEX_FIRST_PERSON.getOneBased() + CPM_DESC_AMY + GD15_DESC_AMY + KDA_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStatsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_allRequiredFieldsPresentWithDifferentValues_success() {
        // sanity check with another set to ensure no accidental coupling to a single test case
        Index idx = INDEX_FIRST_PERSON;
        String input = idx.getOneBased() + CPM_DESC_BOB + GD15_DESC_BOB + " " + PREFIX_KDA + "0.5";
        AddStatsCommand expected = new AddStatsCommand(idx, "5.9", "-500", "0.5");
        assertParseSuccess(parser, input, expected);
    }
}
