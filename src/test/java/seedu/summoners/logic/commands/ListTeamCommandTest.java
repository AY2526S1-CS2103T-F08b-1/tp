package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTeamAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TEAM;
import static seedu.address.testutil.TypicalTeams.getTypicalAddressBookWithTeams;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListTeamCommand.
 */
public class ListTeamCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookWithTeams(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
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

