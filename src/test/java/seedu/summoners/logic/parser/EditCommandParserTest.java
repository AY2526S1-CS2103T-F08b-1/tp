package seedu.summoners.logic.parser;

import static seedu.summoners.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.summoners.logic.commands.CommandTestUtil.CHAMPION_DESC_AMY;
import static seedu.summoners.logic.commands.CommandTestUtil.CHAMPION_DESC_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.INVALID_CHAMPION_DESC;
import static seedu.summoners.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.summoners.logic.commands.CommandTestUtil.INVALID_RANK_DESC;
import static seedu.summoners.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.summoners.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.summoners.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.summoners.logic.commands.CommandTestUtil.RANK_DESC_AMY;
import static seedu.summoners.logic.commands.CommandTestUtil.RANK_DESC_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.ROLE_DESC_AMY;
import static seedu.summoners.logic.commands.CommandTestUtil.ROLE_DESC_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.summoners.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_CHAMPION_AMY;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_RANK_AMY;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.summoners.logic.parser.CliSyntax.PREFIX_CHAMPION;
import static seedu.summoners.logic.parser.CliSyntax.PREFIX_RANK;
import static seedu.summoners.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.summoners.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.summoners.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.summoners.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_FIRST_PLAYER;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_SECOND_PLAYER;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_THIRD_PLAYER;

import org.junit.jupiter.api.Test;

import seedu.summoners.commons.core.index.Index;
import seedu.summoners.logic.Messages;
import seedu.summoners.logic.commands.EditCommand;
import seedu.summoners.logic.commands.EditCommand.EditPlayerDescriptor;
import seedu.summoners.model.player.Champion;
import seedu.summoners.model.player.Name;
import seedu.summoners.model.player.Rank;
import seedu.summoners.model.player.Role;
import seedu.summoners.model.tag.Tag;
import seedu.summoners.testutil.EditPlayerDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_ROLE_DESC, Role.MESSAGE_CONSTRAINTS); // invalid role
        assertParseFailure(parser, "1" + INVALID_RANK_DESC, Rank.MESSAGE_CONSTRAINTS); // invalid rank
        assertParseFailure(parser, "1" + INVALID_CHAMPION_DESC, Champion.MESSAGE_CONSTRAINTS); // invalid champ
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_ROLE_DESC + RANK_DESC_AMY, Role.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Player} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_RANK_DESC + VALID_CHAMPION_AMY
                        + VALID_ROLE_AMY, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PLAYER;
        String userInput = targetIndex.getOneBased() + ROLE_DESC_BOB + TAG_DESC_HUSBAND
                + RANK_DESC_AMY + CHAMPION_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditPlayerDescriptor descriptor = new EditPlayerDescriptorBuilder().withName(VALID_NAME_AMY)
                .withRole(VALID_ROLE_BOB).withRank(VALID_RANK_AMY).withChampion(VALID_CHAMPION_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PLAYER;
        String userInput = targetIndex.getOneBased() + ROLE_DESC_BOB + RANK_DESC_AMY;

        EditPlayerDescriptor descriptor = new EditPlayerDescriptorBuilder().withRole(VALID_ROLE_BOB)
                .withRank(VALID_RANK_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PLAYER;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPlayerDescriptor descriptor = new EditPlayerDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // role
        userInput = targetIndex.getOneBased() + ROLE_DESC_AMY;
        descriptor = new EditPlayerDescriptorBuilder().withRole(VALID_ROLE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // rank
        userInput = targetIndex.getOneBased() + RANK_DESC_AMY;
        descriptor = new EditPlayerDescriptorBuilder().withRank(VALID_RANK_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // champion
        userInput = targetIndex.getOneBased() + CHAMPION_DESC_AMY;
        descriptor = new EditPlayerDescriptorBuilder().withChampion(VALID_CHAMPION_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditPlayerDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PLAYER;
        String userInput = targetIndex.getOneBased() + INVALID_ROLE_DESC + ROLE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + ROLE_DESC_BOB + INVALID_ROLE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased() + ROLE_DESC_AMY + RANK_DESC_AMY + CHAMPION_DESC_AMY
                + TAG_DESC_FRIEND + ROLE_DESC_AMY + RANK_DESC_AMY + CHAMPION_DESC_AMY + TAG_DESC_FRIEND
                + ROLE_DESC_BOB + RANK_DESC_BOB + CHAMPION_DESC_BOB + TAG_DESC_HUSBAND;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE, PREFIX_RANK, PREFIX_CHAMPION));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_ROLE_DESC + INVALID_RANK_DESC + INVALID_CHAMPION_DESC
                + INVALID_ROLE_DESC + INVALID_RANK_DESC + INVALID_CHAMPION_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE, PREFIX_RANK, PREFIX_CHAMPION));
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PLAYER;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditPlayerDescriptor descriptor = new EditPlayerDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
