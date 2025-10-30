package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.FILTER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.FILTER_AMY_AND_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CHAMPION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RANK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand.FilterPersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.FilterPersonDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullDescriptor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FilterCommand(null));
    }

    @Test
    public void equals() {
        final FilterCommand standardCommand = new FilterCommand(FILTER_AMY);

        // same values -> returns true
        FilterPersonDescriptor copyDescriptor = new FilterPersonDescriptor(FILTER_AMY);
        FilterCommand commandWithSameValues = new FilterCommand(copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        FilterPersonDescriptor anotherDescriptor = new FilterPersonDescriptorBuilder(FILTER_AMY)
                .withRanks("silver", "gold").build();
        // different ranks -> returns false
        assertFalse(standardCommand.equals(new FilterCommand(anotherDescriptor)));

        anotherDescriptor = new FilterPersonDescriptorBuilder(FILTER_AMY)
                .withRoles("mid", "top").build();
        // different roles -> returns false
        assertFalse(standardCommand.equals(new FilterCommand(anotherDescriptor)));

        anotherDescriptor = new FilterPersonDescriptorBuilder(FILTER_AMY)
                .withChampions("xayah", "rakan").build();
        // different champions -> returns false
        assertFalse(standardCommand.equals(new FilterCommand(anotherDescriptor)));
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FilterCommand command = new FilterCommand(FILTER_AMY_AND_BOB);
        command.execute(expectedModel);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON), model.getFilteredPersonList());
    }

    @Test
    public void descriptor_isAnyFieldFiltered_correctlyDetects() {
        FilterPersonDescriptor empty = new FilterPersonDescriptorBuilder()
                .withScoreThreshold(null)
                .build();
        assertFalse(empty.isAnyFieldFiltered());

        FilterPersonDescriptor filled = new FilterPersonDescriptorBuilder()
                .withChampions(VALID_CHAMPION_AMY).build();
        assertTrue(filled.isAnyFieldFiltered());

        filled = new FilterPersonDescriptorBuilder()
                .withRanks(VALID_RANK_AMY).build();
        assertTrue(filled.isAnyFieldFiltered());

        filled = new FilterPersonDescriptorBuilder()
                .withRoles(VALID_ROLE_AMY).build();
        assertTrue(filled.isAnyFieldFiltered());
    }

    @Test
    public void descriptor_isAnyFieldFiltered_withScoreThresholdzeroOrNull() {
        // scoreThreshold null
        FilterPersonDescriptor descriptor = new FilterPersonDescriptorBuilder()
                .withScoreThreshold(null)
                .build();
        assertFalse(descriptor.isAnyFieldFiltered());

        // scoreThreshold 0.0
        descriptor = new FilterPersonDescriptorBuilder()
                .withScoreThreshold(0.0F)
                .build();
        assertFalse(descriptor.isAnyFieldFiltered());
    }

    @Test
    public void descriptor_isAnyFieldFiltered_withScoreThresholdpositive() {
        FilterPersonDescriptor descriptor = new FilterPersonDescriptorBuilder()
                .withScoreThreshold(1.5F)
                .build();
        assertTrue(descriptor.isAnyFieldFiltered());
    }

    @Test
    public void execute_filterByScore_only() throws CommandException {
        // Assuming some persons have scores above 2.0
        FilterPersonDescriptor descriptor = new FilterPersonDescriptorBuilder()
                .withScoreThreshold(2.0F).build();
        FilterCommand command = new FilterCommand(descriptor);
        command.execute(expectedModel);

        // All filtered persons should have score >= 2.0
        expectedModel.getFilteredPersonList().forEach(person ->
                assertTrue(person.getStats().getValue() >= 2.0F));
    }

    @Test
    public void execute_filterByMultipleFields() throws CommandException {
        FilterPersonDescriptor descriptor = new FilterPersonDescriptorBuilder()
                .withRoles(VALID_ROLE_AMY)
                .withRanks(VALID_RANK_AMY)
                .withChampions(VALID_CHAMPION_AMY)
                .withScoreThreshold(1.0F)
                .build();
        FilterCommand command = new FilterCommand(descriptor);
        command.execute(expectedModel);

        expectedModel.getFilteredPersonList().forEach(person -> {
            assertTrue(Arrays.asList(person.getRole()).contains(VALID_ROLE_AMY));
            assertTrue(Arrays.asList(person.getRank()).contains(VALID_RANK_AMY));
            assertTrue(Arrays.asList(person.getChampion()).contains(VALID_CHAMPION_AMY));
            assertTrue(person.getStats().getValue() >= 1.0F);
        });
    }

    @Test
    public void equals_sameObjectAndDifferentObjects() {
        FilterPersonDescriptor descriptor1 = new FilterPersonDescriptorBuilder()
                .withRoles(VALID_ROLE_AMY).build();
        FilterPersonDescriptor descriptor2 = new FilterPersonDescriptorBuilder()
                .withRoles(VALID_ROLE_BOB).build();

        FilterCommand command1 = new FilterCommand(descriptor1);
        FilterCommand command2 = new FilterCommand(descriptor1);
        FilterCommand command3 = new FilterCommand(descriptor2);

        // same object
        assertTrue(command1.equals(command1));
        // same values
        assertTrue(command1.equals(command2));
        // different values
        assertFalse(command1.equals(command3));
        // null
        assertFalse(command1.equals(null));
        // different type
        assertFalse(command1.equals("some string"));
    }

    @Test
    public void filterPersonDescriptor_copyConstructor_createsEqualCopy() {
        FilterPersonDescriptor original = new FilterPersonDescriptorBuilder()
                .withRoles(VALID_ROLE_AMY)
                .withRanks(VALID_RANK_AMY)
                .withChampions(VALID_CHAMPION_AMY)
                .withScoreThreshold(2.0F)
                .build();

        FilterPersonDescriptor copy = new FilterPersonDescriptor(original);
        assertEquals(original, copy);
        assertNotSame(original, copy); // ensure defensive copy
    }

    @Test
    public void filterPersonDescriptor_toString_containsAllFields() {
        FilterPersonDescriptor descriptor = new FilterPersonDescriptorBuilder()
                .withRoles(VALID_ROLE_AMY)
                .withRanks(VALID_RANK_AMY)
                .withChampions(VALID_CHAMPION_AMY)
                .withScoreThreshold(3.5F)
                .build();

        String str = descriptor.toString();
        assertTrue(str.contains(VALID_ROLE_AMY));
        assertTrue(str.contains(VALID_RANK_AMY));
        assertTrue(str.contains(VALID_CHAMPION_AMY));
        assertTrue(str.contains("3.5"));
    }

    @Test
    public void toStringMethod() {
        FilterPersonDescriptor filterPersonDescriptor = new FilterPersonDescriptor();
        FilterCommand filterCommand = new FilterCommand(filterPersonDescriptor);
        String expected = FilterCommand.class.getCanonicalName() + "{filterPersonDescriptor="
                + filterPersonDescriptor + "}";
        assertEquals(expected, filterCommand.toString());
    }
}
