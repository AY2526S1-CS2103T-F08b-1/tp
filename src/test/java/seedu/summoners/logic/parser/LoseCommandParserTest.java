package seedu.summoners.logic.parser;

import static seedu.summoners.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.summoners.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.summoners.commons.core.index.Index;
import seedu.summoners.logic.commands.LoseCommand;

/**
 * Tests for {@code LoseCommandParser}.
 */
public class LoseCommandParserTest {

    private final LoseCommandParser parser = new LoseCommandParser();

    @Test
    public void parse_validArgs_returnsLoseCommand() {
        assertParseSuccess(parser, "1", new LoseCommand(Index.fromOneBased(1)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(seedu.summoners.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT, LoseCommand.MESSAGE_USAGE));
    }
}
