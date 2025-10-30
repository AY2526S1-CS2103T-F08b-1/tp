package seedu.summoners.logic.parser;

import static seedu.summoners.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.summoners.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.summoners.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_FIRST_PLAYER;

import org.junit.jupiter.api.Test;

import seedu.summoners.logic.commands.ViewCommand;

public class ViewCommandParserTest {

    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_validArgs_returnsViewCommand() {
        assertParseSuccess(parser, "1", new ViewCommand(INDEX_FIRST_PLAYER));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }
}
