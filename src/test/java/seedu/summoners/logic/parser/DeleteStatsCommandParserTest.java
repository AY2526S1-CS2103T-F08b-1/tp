package seedu.summoners.logic.parser;

import static seedu.summoners.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.summoners.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.summoners.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.summoners.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_FIRST_PLAYER;

import org.junit.jupiter.api.Test;

import seedu.summoners.commons.core.index.Index;
import seedu.summoners.logic.commands.DeleteStatsCommand;

/**
 * Unit tests for {@link DeleteStatsCommandParser}.
 */
public class DeleteStatsCommandParserTest {

    private final DeleteStatsCommandParser parser = new DeleteStatsCommandParser();

    @Test
    public void parse_validIndex_success() {
        Index target = INDEX_FIRST_PLAYER;

        // canonical
        assertParseSuccess(parser, String.valueOf(target.getOneBased()),
                new DeleteStatsCommand(target));

        // leading whitespace tolerated
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + target.getOneBased(),
                new DeleteStatsCommand(target));

        // trailing spaces tolerated
        assertParseSuccess(parser, target.getOneBased() + "   ",
                new DeleteStatsCommand(target));
    }

    @Test
    public void parse_missingIndex_failure() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStatsCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStatsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_failure() {
        // zero / negative
        assertParseFailure(parser, "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStatsCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStatsCommand.MESSAGE_USAGE));

        // non-numeric
        assertParseFailure(parser, "abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStatsCommand.MESSAGE_USAGE));

        // mixed tokens in preamble (parser expects index only)
        assertParseFailure(parser, "1 extra",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStatsCommand.MESSAGE_USAGE));
    }
}
