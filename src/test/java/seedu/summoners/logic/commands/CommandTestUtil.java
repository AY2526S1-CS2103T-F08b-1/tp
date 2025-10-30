package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHAMPION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CPM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GD15;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KDA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.FilterPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_RANK_AMY = "Gold";
    public static final String VALID_RANK_BOB = "Silver";
    public static final String VALID_ROLE_AMY = "Mid";
    public static final String VALID_ROLE_BOB = "Top";
    public static final String VALID_CHAMPION_AMY = "Ahri";
    public static final String VALID_CHAMPION_BOB = "Garen";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_CPM_AMY = "9.9";
    public static final String VALID_CPM_BOB = "5.9";
    public static final String VALID_GD15_AMY = "2000";
    public static final String VALID_GD15_BOB = "-500";
    public static final String VALID_KDA_AMY = "3.5";
    public static final String VALID_KDA_BOB = "0.5";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String RANK_DESC_AMY = " " + PREFIX_RANK + VALID_RANK_AMY;
    public static final String RANK_DESC_BOB = " " + PREFIX_RANK + VALID_RANK_BOB;
    public static final String ROLE_DESC_AMY = " " + PREFIX_ROLE + VALID_ROLE_AMY;
    public static final String ROLE_DESC_BOB = " " + PREFIX_ROLE + VALID_ROLE_BOB;
    public static final String CHAMPION_DESC_AMY = " " + PREFIX_CHAMPION + VALID_CHAMPION_AMY;
    public static final String CHAMPION_DESC_BOB = " " + PREFIX_CHAMPION + VALID_CHAMPION_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String CPM_DESC_AMY = " " + PREFIX_CPM + VALID_CPM_AMY;
    public static final String CPM_DESC_BOB = " " + PREFIX_CPM + VALID_CPM_BOB;
    public static final String GD15_DESC_AMY = " " + PREFIX_GD15 + VALID_GD15_AMY;
    public static final String GD15_DESC_BOB = " " + PREFIX_GD15 + VALID_GD15_BOB;
    public static final String KDA_DESC_AMY = " " + PREFIX_KDA + VALID_KDA_AMY;
    public static final String KDA_DESC_BOB = " " + PREFIX_KDA + VALID_KDA_BOB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_ROLE_DESC = " " + PREFIX_ROLE + "Sky"; // 'Sky' is not a valid role
    public static final String INVALID_RANK_DESC = " " + PREFIX_RANK + "Wood"; // 'Wood' is not a valid rank
    public static final String INVALID_CHAMPION_DESC = " " + PREFIX_CHAMPION + "Aniga"; // 'Aniga' is not a champion
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_CPM_DESC = " " + PREFIX_CPM + "2.2.2";
    public static final String INVALID_GD15_DESC = " " + PREFIX_GD15 + "5K";
    public static final String INVALID_KDA_DESC = " " + PREFIX_KDA + "-2";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withRole(VALID_ROLE_AMY).withRank(VALID_RANK_AMY).withChampion(VALID_CHAMPION_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withRole(VALID_ROLE_AMY).withRank(VALID_RANK_AMY).withChampion(VALID_CHAMPION_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    public static final FilterCommand.FilterPersonDescriptor FILTER_AMY;
    public static final FilterCommand.FilterPersonDescriptor FILTER_AMY_AND_BOB;

    static {
        FILTER_AMY = new FilterPersonDescriptorBuilder()
                .withRoles(VALID_ROLE_AMY).withRanks(VALID_RANK_AMY)
                .withChampions(VALID_CHAMPION_AMY).build();
        FILTER_AMY_AND_BOB = new FilterPersonDescriptorBuilder()
                .withRoles(VALID_ROLE_AMY, VALID_ROLE_BOB)
                .withRanks(VALID_RANK_AMY, VALID_RANK_BOB)
                .withChampions(VALID_CHAMPION_AMY, VALID_CHAMPION_BOB).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered team list to show only the team at the given {@code targetIndex}.
     */
    public static void showTeamAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTeamList().size());

        Team targetTeam = model.getFilteredTeamList().get(targetIndex.getZeroBased());
        model.updateFilteredTeamList(t -> t.equals(targetTeam));

        assertEquals(1, model.getFilteredTeamList().size());
    }
}
