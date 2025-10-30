package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CHAMPION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.CHAMPION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CHAMPION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RANK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.RANK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.RANK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CHAMPION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CHAMPION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RANK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RANK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FilterCommand.FilterPersonDescriptor;
import seedu.address.model.person.Champion;
import seedu.address.model.person.Rank;
import seedu.address.model.person.Role;
import seedu.address.testutil.FilterPersonDescriptorBuilder;

public class FilterCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE);

    private static final String MESSAGE_NOT_FILTERED =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_NOT_FILTERED);

    private final FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no field specified
        assertParseFailure(parser, "", MESSAGE_NOT_FILTERED);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_NOT_FILTERED);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "i/gold", MESSAGE_NOT_FILTERED);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_ROLE_DESC, Role.MESSAGE_CONSTRAINTS); // invalid role
        assertParseFailure(parser, INVALID_RANK_DESC, Rank.MESSAGE_CONSTRAINTS); // invalid rank
        assertParseFailure(parser, INVALID_CHAMPION_DESC, Champion.MESSAGE_CONSTRAINTS); // invalid champ

        // invalid role followed by valid rank
        assertParseFailure(parser, INVALID_ROLE_DESC + RANK_DESC_AMY, Role.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, INVALID_RANK_DESC + VALID_CHAMPION_AMY
                + VALID_ROLE_AMY, Rank.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = ROLE_DESC_BOB + RANK_DESC_AMY + CHAMPION_DESC_AMY;
        FilterPersonDescriptor descriptor = new FilterPersonDescriptorBuilder()
                .withRoles(VALID_ROLE_BOB).withRanks(VALID_RANK_AMY).withChampions(VALID_CHAMPION_AMY)
                .build();
        FilterCommand expectedCommand = new FilterCommand(descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = ROLE_DESC_BOB + RANK_DESC_AMY;

        FilterPersonDescriptor descriptor = new FilterPersonDescriptorBuilder().withRoles(VALID_ROLE_BOB)
                .withRanks(VALID_RANK_AMY).build();
        FilterCommand expectedCommand = new FilterCommand(descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // role
        String userInput = ROLE_DESC_AMY;
        FilterPersonDescriptor descriptor = new FilterPersonDescriptorBuilder().withRoles(VALID_ROLE_AMY).build();
        FilterCommand expectedCommand = new FilterCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // rank
        userInput = RANK_DESC_AMY;
        descriptor = new FilterPersonDescriptorBuilder().withRanks(VALID_RANK_AMY).build();
        expectedCommand = new FilterCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // champion
        userInput = CHAMPION_DESC_AMY;
        descriptor = new FilterPersonDescriptorBuilder().withChampions(VALID_CHAMPION_AMY).build();
        expectedCommand = new FilterCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_multipleRepeatedFields_success() {
        String userInput = ROLE_DESC_AMY + ROLE_DESC_BOB
                + RANK_DESC_AMY + RANK_DESC_BOB
                + CHAMPION_DESC_AMY + CHAMPION_DESC_BOB;
        FilterPersonDescriptor descriptor = new FilterPersonDescriptorBuilder()
                .withRoles(VALID_ROLE_AMY, VALID_ROLE_BOB)
                .withRanks(VALID_RANK_AMY, VALID_RANK_BOB)
                .withChampions(VALID_CHAMPION_AMY, VALID_CHAMPION_BOB).build();
        FilterCommand expectedCommand = new FilterCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_scoreFieldSpecified_success() {
        // single valid score
        String userInput = " s/5.5";
        FilterPersonDescriptor descriptor = new FilterPersonDescriptorBuilder().withScoreThreshold(5.5f).build();
        FilterCommand expectedCommand = new FilterCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // score with whitespace
        userInput = " s/ 3.2 ";
        descriptor = new FilterPersonDescriptorBuilder().withScoreThreshold(3.2f).build();
        expectedCommand = new FilterCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_scoreWithOtherFields_success() {
        String userInput = ROLE_DESC_AMY + RANK_DESC_AMY + CHAMPION_DESC_AMY + " s/7.7";
        FilterPersonDescriptor descriptor = new FilterPersonDescriptorBuilder()
                .withRoles(VALID_ROLE_AMY)
                .withRanks(VALID_RANK_AMY)
                .withChampions(VALID_CHAMPION_AMY)
                .withScoreThreshold(7.7f)
                .build();
        FilterCommand expectedCommand = new FilterCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
