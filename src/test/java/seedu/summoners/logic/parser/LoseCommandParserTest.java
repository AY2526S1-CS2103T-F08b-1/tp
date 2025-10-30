package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LoseCommand;

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
                String.format(seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT, LoseCommand.MESSAGE_USAGE));
    }
}
