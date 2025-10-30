package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TEAM;
import static seedu.address.testutil.TypicalTeams.getTypicalAddressBookWithTeams;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.team.Team;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code WinCommand}.
 */
public class WinCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookWithTeams(), new UserPrefs());
    }

    @Test
    public void execute_validIndex_success() {
        Team firstTeam = model.getFilteredTeamList().get(0);
        WinCommand winCommand = new WinCommand(INDEX_FIRST_TEAM);

        int expectedWins = firstTeam.getWins() + 1;
        int expectedLosses = firstTeam.getLosses();

        String expectedMessage = String.format(WinCommand.MESSAGE_WIN_TEAM_SUCCESS,
                INDEX_FIRST_TEAM.getOneBased(), expectedWins, expectedLosses);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setTeam(firstTeam, new Team(firstTeam.getId(), firstTeam.getPersons(),
                expectedWins, expectedLosses));

        assertCommandSuccess(winCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        int outOfBoundsIndex = model.getFilteredTeamList().size() + 1;
        WinCommand winCommand = new WinCommand(seedu.address.commons.core.index.Index.fromOneBased(outOfBoundsIndex));
        assertCommandFailure(winCommand, model, seedu.address.logic.Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        WinCommand first = new WinCommand(INDEX_FIRST_TEAM);
        WinCommand second = new WinCommand(seedu.address.commons.core.index.Index.fromOneBased(2));

        // same object -> true
        assertTrue(first.equals(first));

        // same values -> true
        WinCommand firstCopy = new WinCommand(INDEX_FIRST_TEAM);
        assertTrue(first.equals(firstCopy));

        // different types -> false
        assertTrue(!first.equals(1));

        // null -> false
        assertTrue(!first.equals(null));

        // different index -> false
        assertTrue(!first.equals(second));
    }

    @Test
    public void toString_winCommand() {
        Index targetIndex = INDEX_FIRST_TEAM;
        WinCommand winCommand = new WinCommand(targetIndex);
        String expected = WinCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, winCommand.toString());
    }
}
