package seedu.summoners.logic.commands;

import static seedu.summoners.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.summoners.testutil.TypicalPlayers.getTypicalSummonersBook;

import org.junit.jupiter.api.Test;

import seedu.summoners.model.SummonersBook;
import seedu.summoners.model.Model;
import seedu.summoners.model.ModelManager;
import seedu.summoners.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptySummonersBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptySummonersBook_success() {
        Model model = new ModelManager(getTypicalSummonersBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalSummonersBook(), new UserPrefs());
        expectedModel.setSummonersBook(new SummonersBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
