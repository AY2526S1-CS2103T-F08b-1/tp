package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.GroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class GroupCommandParserTest {

    private final GroupCommandParser parser = new GroupCommandParser();

    @Test
    public void parse_emptyArgs_returnsGroupCommand() {
        assertParseSuccess(parser, "", new GroupCommand());
    }

    @Test
    public void parse_whitespaceArgs_returnsGroupCommand() {
        assertParseSuccess(parser, "   ", new GroupCommand());
    }

    @Test
    public void parse_nonEmptyArgs_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse("extra arguments"));
        assertTrue(exception.getMessage().contains("does not take any arguments"));
    }

    @Test
    public void parse_singleWordArg_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("invalid"));
    }
}
