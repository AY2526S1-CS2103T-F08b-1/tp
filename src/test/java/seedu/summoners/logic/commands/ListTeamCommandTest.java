package seedu.summoners.logic.commands;

import static seedu.summoners.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.summoners.logic.commands.CommandTestUtil.showTeamAtIndex;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_FIRST_TEAM;
import static seedu.summoners.testutil.TypicalTeams.getTypicalSummonersBookWithTeams;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.summoners.model.Model;
import seedu.summoners.model.ModelManager;
import seedu.summoners.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListTeamCommand.
 */
public class ListTeamCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalSummonersBookWithTeams(), new UserPrefs());
        expectedModel = new ModelManager(model.getSummonersBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListTeamCommand(), model,
                ListTeamCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showTeamAtIndex(model, INDEX_FIRST_TEAM);
        assertCommandSuccess(new ListTeamCommand(), model,
                ListTeamCommand.MESSAGE_SUCCESS, expectedModel);
    }
}

