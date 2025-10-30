package seedu.summoners.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.summoners.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.summoners.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.summoners.logic.commands.CommandTestUtil.showPlayerAtIndex;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_FIRST_PLAYER;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_SECOND_PLAYER;
import static seedu.summoners.testutil.TypicalPlayers.ALICE;
import static seedu.summoners.testutil.TypicalPlayers.BENSON;
import static seedu.summoners.testutil.TypicalPlayers.CARL;
import static seedu.summoners.testutil.TypicalPlayers.DANIEL;
import static seedu.summoners.testutil.TypicalPlayers.ELLE;
import static seedu.summoners.testutil.TypicalPlayers.getTypicalSummonersBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.summoners.commons.core.index.Index;
import seedu.summoners.logic.Messages;
import seedu.summoners.model.Model;
import seedu.summoners.model.ModelManager;
import seedu.summoners.model.UserPrefs;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.Team;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalSummonersBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Player playerToDelete = model.getFilteredPlayerList().get(INDEX_FIRST_PLAYER.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PLAYER);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PLAYER_SUCCESS,
                Messages.format(playerToDelete));

        ModelManager expectedModel = new ModelManager(model.getSummonersBook(), new UserPrefs());
        expectedModel.deletePlayer(playerToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPlayerList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PLAYER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPlayerAtIndex(model, INDEX_FIRST_PLAYER);

        Player playerToDelete = model.getFilteredPlayerList().get(INDEX_FIRST_PLAYER.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PLAYER);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PLAYER_SUCCESS,
                Messages.format(playerToDelete));

        Model expectedModel = new ModelManager(model.getSummonersBook(), new UserPrefs());
        expectedModel.deletePlayer(playerToDelete);
        showNoPlayer(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPlayerAtIndex(model, INDEX_FIRST_PLAYER);

        Index outOfBoundIndex = INDEX_SECOND_PLAYER;
        // ensures that outOfBoundIndex is still in bounds of summoners book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getSummonersBook().getPlayerList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PLAYER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_playerInTeam_throwsCommandException() {
        Model modelWithTeam = new ModelManager(getTypicalSummonersBook(), new UserPrefs());
        Team team = new Team(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE));
        modelWithTeam.addTeam(team);

        // ALICE is at index 0 in the typical summoners book
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PLAYER);

        assertCommandFailure(deleteCommand, modelWithTeam, Messages.MESSAGE_PLAYER_IN_TEAM);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PLAYER);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PLAYER);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PLAYER);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different player -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPlayer(Model model) {
        model.updateFilteredPlayerList(p -> false);

        assertTrue(model.getFilteredPlayerList().isEmpty());
    }
}
