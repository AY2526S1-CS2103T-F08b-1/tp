package seedu.summoners.logic.commands;

import static seedu.summoners.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.summoners.logic.commands.CommandTestUtil.showPlayerAtIndex;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_FIRST_PLAYER;
import static seedu.summoners.testutil.TypicalPlayers.getTypicalSummonersBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.summoners.model.Model;
import seedu.summoners.model.ModelManager;
import seedu.summoners.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalSummonersBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getSummonersBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPlayerAtIndex(model, INDEX_FIRST_PLAYER);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
