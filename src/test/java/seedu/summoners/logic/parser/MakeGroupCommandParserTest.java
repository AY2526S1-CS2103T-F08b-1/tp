package seedu.summoners.logic.parser;

import static seedu.summoners.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.summoners.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.summoners.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_FIFTH_PLAYER;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_FIRST_PLAYER;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_FOURTH_PLAYER;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_SECOND_PLAYER;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_THIRD_PLAYER;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.summoners.commons.core.index.Index;
import seedu.summoners.logic.commands.MakeGroupCommand;

public class MakeGroupCommandParserTest {

    private final MakeGroupCommandParser parser = new MakeGroupCommandParser();

    @Test
    public void parse_validArgs_returnsMakeGroupCommand() {
        List<Index> expectedIndices = Arrays.asList(
                INDEX_FIRST_PLAYER,
                INDEX_SECOND_PLAYER,
                INDEX_THIRD_PLAYER,
                INDEX_FOURTH_PLAYER,
                INDEX_FIFTH_PLAYER
        );
        MakeGroupCommand expectedCommand = new MakeGroupCommand(expectedIndices);

        // Standard valid input
        assertParseSuccess(parser, "1 2 3 4 5", expectedCommand);

        // Valid input with multiple spaces between indices
        assertParseSuccess(parser, "1   2   3   4   5", expectedCommand);

        // Valid input with leading and trailing whitespace
        assertParseSuccess(parser, "  1 2 3 4 5  ", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedErrorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MakeGroupCommand.MESSAGE_USAGE);

        // No indices provided
        assertParseFailure(parser, "", expectedErrorMessage);
        assertParseFailure(parser, "   ", expectedErrorMessage);

        // Non-integer value
        assertParseFailure(parser, "1 2 a 4 5", expectedErrorMessage);

        // Zero index
        assertParseFailure(parser, "1 2 0 4 5", expectedErrorMessage);

        // Negative index
        assertParseFailure(parser, "1 2 -3 4 5", expectedErrorMessage);
    }
}
