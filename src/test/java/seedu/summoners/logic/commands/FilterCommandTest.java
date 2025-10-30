package seedu.summoners.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.summoners.logic.Messages.MESSAGE_PLAYERS_LISTED_OVERVIEW;
import static seedu.summoners.logic.commands.CommandTestUtil.FILTER_AMY;
import static seedu.summoners.logic.commands.CommandTestUtil.FILTER_AMY_AND_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_CHAMPION_AMY;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_RANK_AMY;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.summoners.testutil.Assert.assertThrows;
import static seedu.summoners.testutil.TypicalPlayers.ALICE;
import static seedu.summoners.testutil.TypicalPlayers.BENSON;
import static seedu.summoners.testutil.TypicalPlayers.getTypicalSummonersBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.summoners.logic.commands.FilterCommand.FilterPlayerDescriptor;
import seedu.summoners.logic.commands.exceptions.CommandException;
import seedu.summoners.model.Model;
import seedu.summoners.model.ModelManager;
import seedu.summoners.model.UserPrefs;
import seedu.summoners.testutil.FilterPlayerDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalSummonersBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalSummonersBook(), new UserPrefs());

    @Test
    public void constructor_nullDescriptor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FilterCommand(null));
    }

    @Test
    public void equals() {
        final FilterCommand standardCommand = new FilterCommand(FILTER_AMY);

        // same values -> returns true
        FilterPlayerDescriptor copyDescriptor = new FilterPlayerDescriptor(FILTER_AMY);
        FilterCommand commandWithSameValues = new FilterCommand(copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        FilterPlayerDescriptor anotherDescriptor = new FilterPlayerDescriptorBuilder(FILTER_AMY)
                .withRanks("silver", "gold").build();
        // different ranks -> returns false
        assertFalse(standardCommand.equals(new FilterCommand(anotherDescriptor)));

        anotherDescriptor = new FilterPlayerDescriptorBuilder(FILTER_AMY)
                .withRoles("mid", "top").build();
        // different roles -> returns false
        assertFalse(standardCommand.equals(new FilterCommand(anotherDescriptor)));

        anotherDescriptor = new FilterPlayerDescriptorBuilder(FILTER_AMY)
                .withChampions("xayah", "rakan").build();
        // different champions -> returns false
        assertFalse(standardCommand.equals(new FilterCommand(anotherDescriptor)));
    }

    @Test
    public void execute_multipleKeywords_multiplePlayersFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PLAYERS_LISTED_OVERVIEW, 2);
        FilterCommand command = new FilterCommand(FILTER_AMY_AND_BOB);
        command.execute(expectedModel);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON), model.getFilteredPlayerList());
    }

    @Test
    public void descriptor_isAnyFieldFiltered_correctlyDetects() {
        FilterPlayerDescriptor empty = new FilterPlayerDescriptorBuilder()
                .withScoreThreshold(null)
                .build();
        assertFalse(empty.isAnyFieldFiltered());

        FilterPlayerDescriptor filled = new FilterPlayerDescriptorBuilder()
                .withChampions(VALID_CHAMPION_AMY).build();
        assertTrue(filled.isAnyFieldFiltered());

        filled = new FilterPlayerDescriptorBuilder()
                .withRanks(VALID_RANK_AMY).build();
        assertTrue(filled.isAnyFieldFiltered());

        filled = new FilterPlayerDescriptorBuilder()
                .withRoles(VALID_ROLE_AMY).build();
        assertTrue(filled.isAnyFieldFiltered());
    }

    @Test
    public void descriptor_isAnyFieldFiltered_withScoreThresholdzeroOrNull() {
        // scoreThreshold null
        FilterPlayerDescriptor descriptor = new FilterPlayerDescriptorBuilder()
                .withScoreThreshold(null)
                .build();
        assertFalse(descriptor.isAnyFieldFiltered());

        // scoreThreshold 0.0
        descriptor = new FilterPlayerDescriptorBuilder()
                .withScoreThreshold(0.0F)
                .build();
        assertFalse(descriptor.isAnyFieldFiltered());
    }

    @Test
    public void descriptor_isAnyFieldFiltered_withScoreThresholdpositive() {
        FilterPlayerDescriptor descriptor = new FilterPlayerDescriptorBuilder()
                .withScoreThreshold(1.5F)
                .build();
        assertTrue(descriptor.isAnyFieldFiltered());
    }

    @Test
    public void execute_filterByScore_only() throws CommandException {
        // Assuming some players have scores above 2.0
        FilterPlayerDescriptor descriptor = new FilterPlayerDescriptorBuilder()
                .withScoreThreshold(2.0F).build();
        FilterCommand command = new FilterCommand(descriptor);
        command.execute(expectedModel);

        // All filtered players should have score >= 2.0
        expectedModel.getFilteredPlayerList().forEach(player ->
                assertTrue(player.getStats().getValue() >= 2.0F));
    }

    @Test
    public void execute_filterByMultipleFields() throws CommandException {
        FilterPlayerDescriptor descriptor = new FilterPlayerDescriptorBuilder()
                .withRoles(VALID_ROLE_AMY)
                .withRanks(VALID_RANK_AMY)
                .withChampions(VALID_CHAMPION_AMY)
                .withScoreThreshold(1.0F)
                .build();
        FilterCommand command = new FilterCommand(descriptor);
        command.execute(expectedModel);

        expectedModel.getFilteredPlayerList().forEach(player -> {
            assertTrue(Arrays.asList(player.getRole()).contains(VALID_ROLE_AMY));
            assertTrue(Arrays.asList(player.getRank()).contains(VALID_RANK_AMY));
            assertTrue(Arrays.asList(player.getChampion()).contains(VALID_CHAMPION_AMY));
            assertTrue(player.getStats().getValue() >= 1.0F);
        });
    }

    @Test
    public void equals_sameObjectAndDifferentObjects() {
        FilterPlayerDescriptor descriptor1 = new FilterPlayerDescriptorBuilder()
                .withRoles(VALID_ROLE_AMY).build();
        FilterPlayerDescriptor descriptor2 = new FilterPlayerDescriptorBuilder()
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
    public void filterPlayerDescriptor_copyConstructor_createsEqualCopy() {
        FilterPlayerDescriptor original = new FilterPlayerDescriptorBuilder()
                .withRoles(VALID_ROLE_AMY)
                .withRanks(VALID_RANK_AMY)
                .withChampions(VALID_CHAMPION_AMY)
                .withScoreThreshold(2.0F)
                .build();

        FilterPlayerDescriptor copy = new FilterPlayerDescriptor(original);
        assertEquals(original, copy);
        assertNotSame(original, copy); // ensure defensive copy
    }

    @Test
    public void filterPlayerDescriptor_toString_containsAllFields() {
        FilterPlayerDescriptor descriptor = new FilterPlayerDescriptorBuilder()
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
        FilterPlayerDescriptor filterPlayerDescriptor = new FilterPlayerDescriptor();
        FilterCommand filterCommand = new FilterCommand(filterPlayerDescriptor);
        String expected = FilterCommand.class.getCanonicalName() + "{filterPlayerDescriptor="
                + filterPlayerDescriptor + "}";
        assertEquals(expected, filterCommand.toString());
    }
}
