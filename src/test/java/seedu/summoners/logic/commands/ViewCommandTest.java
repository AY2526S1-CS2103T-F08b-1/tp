package seedu.summoners.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.summoners.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.summoners.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.summoners.logic.commands.CommandTestUtil.showPlayerAtIndex;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_FIRST_PLAYER;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_SECOND_PLAYER;
import static seedu.summoners.testutil.TypicalPlayers.getTypicalSummonersBook;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.summoners.commons.core.index.Index;
import seedu.summoners.logic.Messages;
import seedu.summoners.model.Model;
import seedu.summoners.model.ModelManager;
import seedu.summoners.model.UserPrefs;
import seedu.summoners.model.player.Player;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewCommand.
 */
public class ViewCommandTest {

    private Model model = new ModelManager(getTypicalSummonersBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Player playerToView = model.getFilteredPlayerList().get(INDEX_FIRST_PLAYER.getZeroBased());
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PLAYER);

        String expectedMessage = String.format(ViewCommand.MESSAGE_VIEW_PLAYER_SUCCESS,
                Messages.format(playerToView));

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, playerToView);

        assertCommandSuccess(viewCommand, model, expectedCommandResult, model);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPlayerList().size() + 1);
        ViewCommand viewCommand = new ViewCommand(outOfBoundIndex);

        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_PLAYER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPlayerAtIndex(model, INDEX_FIRST_PLAYER);

        Player playerToView = model.getFilteredPlayerList().get(INDEX_FIRST_PLAYER.getZeroBased());
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PLAYER);

        String expectedMessage = String.format(ViewCommand.MESSAGE_VIEW_PLAYER_SUCCESS,
                Messages.format(playerToView));

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, playerToView);

        assertCommandSuccess(viewCommand, model, expectedCommandResult, model);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPlayerAtIndex(model, INDEX_FIRST_PLAYER);

        Index outOfBoundIndex = INDEX_SECOND_PLAYER;
        // ensures that outOfBoundIndex is still in bounds of summoners book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getSummonersBook().getPlayerList().size());

        ViewCommand viewCommand = new ViewCommand(outOfBoundIndex);

        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_PLAYER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ViewCommand viewFirstCommand = new ViewCommand(INDEX_FIRST_PLAYER);
        ViewCommand viewSecondCommand = new ViewCommand(INDEX_SECOND_PLAYER);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewCommand viewFirstCommandCopy = new ViewCommand(INDEX_FIRST_PLAYER);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different player -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ViewCommand viewCommand = new ViewCommand(targetIndex);
        String expected = ViewCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, viewCommand.toString());
    }

    @Test
    public void execute_commandResult_hasCorrectFlags() throws Exception {
        Player playerToView = model.getFilteredPlayerList().get(INDEX_FIRST_PLAYER.getZeroBased());
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PLAYER);

        CommandResult result = viewCommand.execute(model);

        // Verify the command result has the correct flags set
        assertTrue(result.isShowPlayerDetail());
        assertFalse(result.isShowHelp());
        assertFalse(result.isExit());
        assertEquals(Optional.of(playerToView), result.getPlayerToShow());
    }
}
