package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MakeGroupCommand;

public class MakeGroupCommandParserTest {

    private final MakeGroupCommandParser parser = new MakeGroupCommandParser();

    @Test
    public void parse_validArgs_returnsMakeGroupCommand() {
        List<Index> expectedIndices = Arrays.asList(
                INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON,
                INDEX_THIRD_PERSON,
                INDEX_FOURTH_PERSON,
                INDEX_FIFTH_PERSON
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
