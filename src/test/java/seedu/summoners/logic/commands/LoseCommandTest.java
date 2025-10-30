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
 * Contains integration tests (interaction with the Model) and unit tests for {@code LoseCommand}.
 */
public class LoseCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalSummonersBookWithTeams(), new UserPrefs());
    }

    @Test
    public void execute_validIndex_success() {
        Team firstTeam = model.getFilteredTeamList().get(0);
        LoseCommand loseCommand = new LoseCommand(INDEX_FIRST_TEAM);

        int expectedWins = firstTeam.getWins();
        int expectedLosses = firstTeam.getLosses() + 1;

        String expectedMessage = String.format(LoseCommand.MESSAGE_LOSE_TEAM_SUCCESS,
                INDEX_FIRST_TEAM.getOneBased(), expectedWins, expectedLosses);

        Model expectedModel = new ModelManager(model.getSummonersBook(), new UserPrefs());
        expectedModel.setTeam(firstTeam, new Team(firstTeam.getId(), firstTeam.getPlayers(),
                expectedWins, expectedLosses));

        assertCommandSuccess(loseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        int outOfBoundsIndex = model.getFilteredTeamList().size() + 1;
        LoseCommand loseCommand = new LoseCommand(seedu.summoners.commons.core.index.Index
                .fromOneBased(outOfBoundsIndex));
        assertCommandFailure(loseCommand, model, seedu.summoners.logic.Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        LoseCommand first = new LoseCommand(INDEX_FIRST_TEAM);
        LoseCommand second = new LoseCommand(seedu.summoners.commons.core.index.Index.fromOneBased(2));

        assertTrue(first.equals(first));
        assertTrue(first.equals(new LoseCommand(INDEX_FIRST_TEAM)));
        assertTrue(!first.equals(1));
        assertTrue(!first.equals(null));
        assertTrue(!first.equals(second));
    }

    @Test
    public void toString_loseCommand() {
        Index targetIndex = INDEX_FIRST_TEAM;
        LoseCommand loseCommand = new LoseCommand(targetIndex);
        String expected = LoseCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, loseCommand.toString());
    }
}
