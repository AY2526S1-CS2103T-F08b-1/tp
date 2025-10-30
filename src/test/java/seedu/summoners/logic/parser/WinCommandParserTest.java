package seedu.summoners.logic.parser;

import static seedu.summoners.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.summoners.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.summoners.commons.core.index.Index;
import seedu.summoners.logic.commands.WinCommand;

/**
 * Tests for {@code WinCommandParser}.
 */
public class WinCommandParserTest {

    private final WinCommandParser parser = new WinCommandParser();

    @Test
    public void parse_validArgs_returnsWinCommand() {
        assertParseSuccess(parser, "1", new WinCommand(Index.fromOneBased(1)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(seedu.summoners.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT, WinCommand.MESSAGE_USAGE));
    }
}
