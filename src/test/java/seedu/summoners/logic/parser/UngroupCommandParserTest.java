package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TEAM;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UngroupCommand;

public class UngroupCommandParserTest {

    private UngroupCommandParser parser = new UngroupCommandParser();

    @Test
    public void parse_validIndex_returnsUngroupCommand() {
        assertParseSuccess(parser, "1", new UngroupCommand(INDEX_FIRST_TEAM));
    }

    @Test
    public void parse_validAll_returnsUngroupCommand() {
        assertParseSuccess(parser, "all", new UngroupCommand());
        assertParseSuccess(parser, "ALL", new UngroupCommand());
        assertParseSuccess(parser, "All", new UngroupCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Empty argument
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UngroupCommand.MESSAGE_USAGE));

        // Invalid index
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UngroupCommand.MESSAGE_USAGE));

        // Negative index
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UngroupCommand.MESSAGE_USAGE));

        // Zero index
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UngroupCommand.MESSAGE_USAGE));
    }
}
