package seedu.summoners.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.summoners.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.summoners.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_FIRST_TEAM;
import static seedu.summoners.testutil.TypicalTeams.getTypicalSummonersBookWithTeams;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.summoners.commons.core.index.Index;
import seedu.summoners.model.Model;
import seedu.summoners.model.ModelManager;
import seedu.summoners.model.UserPrefs;
import seedu.summoners.model.team.Team;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code WinCommand}.
 */
public class WinCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalSummonersBookWithTeams(), new UserPrefs());
    }

    @Test
    public void execute_validIndex_success() {
        Team firstTeam = model.getFilteredTeamList().get(0);
        WinCommand winCommand = new WinCommand(INDEX_FIRST_TEAM);

        int expectedWins = firstTeam.getWins() + 1;
        int expectedLosses = firstTeam.getLosses();

        String expectedMessage = String.format(WinCommand.MESSAGE_WIN_TEAM_SUCCESS,
                INDEX_FIRST_TEAM.getOneBased(), expectedWins, expectedLosses);

        Model expectedModel = new ModelManager(model.getSummonersBook(), new UserPrefs());
        expectedModel.setTeam(firstTeam, new Team(firstTeam.getId(), firstTeam.getPlayers(),
                expectedWins, expectedLosses));

        assertCommandSuccess(winCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        int outOfBoundsIndex = model.getFilteredTeamList().size() + 1;
        WinCommand winCommand = new WinCommand(seedu.summoners.commons.core.index.Index.fromOneBased(outOfBoundsIndex));
        assertCommandFailure(winCommand, model, seedu.summoners.logic.Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        WinCommand first = new WinCommand(INDEX_FIRST_TEAM);
        WinCommand second = new WinCommand(seedu.summoners.commons.core.index.Index.fromOneBased(2));

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
