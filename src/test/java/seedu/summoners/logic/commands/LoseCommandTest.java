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
 * Contains integration tests (interaction with the Model) and unit tests for {@code LoseCommand}.
 */
public class LoseCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookWithTeams(), new UserPrefs());
    }

    @Test
    public void execute_validIndex_success() {
        Team firstTeam = model.getFilteredTeamList().get(0);
        LoseCommand loseCommand = new LoseCommand(INDEX_FIRST_TEAM);

        int expectedWins = firstTeam.getWins();
        int expectedLosses = firstTeam.getLosses() + 1;

        String expectedMessage = String.format(LoseCommand.MESSAGE_LOSE_TEAM_SUCCESS,
                INDEX_FIRST_TEAM.getOneBased(), expectedWins, expectedLosses);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setTeam(firstTeam, new Team(firstTeam.getId(), firstTeam.getPersons(),
                expectedWins, expectedLosses));

        assertCommandSuccess(loseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        int outOfBoundsIndex = model.getFilteredTeamList().size() + 1;
        LoseCommand loseCommand = new LoseCommand(seedu.address.commons.core.index.Index
                .fromOneBased(outOfBoundsIndex));
        assertCommandFailure(loseCommand, model, seedu.address.logic.Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        LoseCommand first = new LoseCommand(INDEX_FIRST_TEAM);
        LoseCommand second = new LoseCommand(seedu.address.commons.core.index.Index.fromOneBased(2));

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
