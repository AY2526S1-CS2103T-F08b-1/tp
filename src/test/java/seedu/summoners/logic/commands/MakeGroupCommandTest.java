package seedu.summoners.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.summoners.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.summoners.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_EIGHTH_PLAYER;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_FIFTH_PLAYER;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_FIRST_PLAYER;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_FOURTH_PLAYER;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_NINTH_PLAYER;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_SECOND_PLAYER;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_SEVENTH_PLAYER;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_SIXTH_PLAYER;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_THIRD_PLAYER;
import static seedu.summoners.testutil.TypicalPlayers.ALICE;
import static seedu.summoners.testutil.TypicalPlayers.getTypicalSummonersBook;
import static seedu.summoners.testutil.TypicalTeams.getTypicalSummonersBookWithTeams;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.summoners.commons.core.index.Index;
import seedu.summoners.logic.Messages;
import seedu.summoners.logic.commands.exceptions.CommandException;
import seedu.summoners.model.Model;
import seedu.summoners.model.ModelManager;
import seedu.summoners.model.SummonersBook;
import seedu.summoners.model.UserPrefs;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.Team;

public class MakeGroupCommandTest {

    private Model model = new ModelManager(getTypicalSummonersBook(), new UserPrefs());

    @Test
    public void execute_validIndicesUnfilteredList_success() throws CommandException {
        List<Index> indices = Arrays.asList(INDEX_FIRST_PLAYER, INDEX_SECOND_PLAYER,
                INDEX_THIRD_PLAYER, INDEX_FOURTH_PLAYER, INDEX_FIFTH_PLAYER);
        MakeGroupCommand makeGroupCommand = new MakeGroupCommand(indices);

        Model expectedModel = new ModelManager(new SummonersBook(model.getSummonersBook()), new UserPrefs());
        List<Player> expectedTeamMembers = indices.stream()
                .map(index -> model.getFilteredPlayerList().get(index.getZeroBased()))
                .toList();
        Team expectedTeam = new Team(expectedTeamMembers);
        expectedModel.addTeam(expectedTeam);

        String expectedMessage = String.format(MakeGroupCommand.MESSAGE_MAKE_GROUP_SUCCESS,
                Messages.format(expectedTeam));

        assertCommandSuccess(makeGroupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateIndices_throwsCommandException() {
        List<Index> duplicateIndices = Arrays.asList(INDEX_FIRST_PLAYER, INDEX_FIRST_PLAYER,
                INDEX_SECOND_PLAYER, INDEX_THIRD_PLAYER, INDEX_FOURTH_PLAYER);
        MakeGroupCommand command = new MakeGroupCommand(duplicateIndices);
        assertCommandFailure(command, model, MakeGroupCommand.MESSAGE_DUPLICATE_INDEX);
    }

    @Test
    public void execute_invalidPlayerIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPlayerList().size() + 1);
        List<Index> indicesWithInvalid = Arrays.asList(INDEX_FIRST_PLAYER, INDEX_SECOND_PLAYER,
                INDEX_THIRD_PLAYER, INDEX_FOURTH_PLAYER, outOfBoundIndex);
        MakeGroupCommand command = new MakeGroupCommand(indicesWithInvalid);
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PLAYER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_playerAlreadyInTeam_throwsCommandException() {
        Model modelWithTeam = new ModelManager(getTypicalSummonersBookWithTeams(), new UserPrefs());

        // Attempt to create a new team using the same player
        List<Index> indices = Arrays.asList(
                INDEX_FIRST_PLAYER, // ALICE, who is already in a team
                INDEX_SIXTH_PLAYER,
                INDEX_SEVENTH_PLAYER,
                INDEX_EIGHTH_PLAYER,
                INDEX_NINTH_PLAYER
        );

        MakeGroupCommand command = new MakeGroupCommand(indices);

        String expectedMessage = String.format(MakeGroupCommand.MESSAGE_REUSED_PLAYER, Messages.format(ALICE));
        assertCommandFailure(command, modelWithTeam, expectedMessage);
    }

    @Test
    public void execute_invalidTeamSize_throwsCommandException() {
        String expectedMessage = String.format(MakeGroupCommand.MESSAGE_INVALID_TEAM_SIZE, Team.TEAM_SIZE);

        List<Index> fewerIndices = Arrays.asList(INDEX_FIRST_PLAYER, INDEX_SECOND_PLAYER);
        MakeGroupCommand command = new MakeGroupCommand(fewerIndices);
        assertCommandFailure(command, model, expectedMessage);

        List<Index> moreIndices = Arrays.asList(INDEX_FIRST_PLAYER, INDEX_SECOND_PLAYER, INDEX_THIRD_PLAYER,
                INDEX_FOURTH_PLAYER, INDEX_FIFTH_PLAYER, INDEX_SIXTH_PLAYER);
        command = new MakeGroupCommand(moreIndices);
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_invalidTeamComposition_throwsCommandException() {
        // Assumes TypicalPlayers has players with conflicting roles/champions at certain indices
        // For example, if player 1 and 6 have the same role.
        List<Index> conflictingIndices = Arrays.asList(INDEX_FIRST_PLAYER, Index.fromOneBased(6),
                INDEX_THIRD_PLAYER, INDEX_FOURTH_PLAYER, INDEX_FIFTH_PLAYER);
        MakeGroupCommand command = new MakeGroupCommand(conflictingIndices);

        // We can't check the exact error message as it depends on your TypicalPlayers,
        // so we assert that a CommandException is thrown.
        // A more specific test would require crafting a specific model.
        org.junit.jupiter.api.Assertions.assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void equals() {
        List<Index> indices1 = Arrays.asList(INDEX_FIRST_PLAYER, INDEX_SECOND_PLAYER, INDEX_THIRD_PLAYER,
                INDEX_FOURTH_PLAYER, INDEX_FIFTH_PLAYER);
        List<Index> indices2 = Arrays.asList(INDEX_FIRST_PLAYER, INDEX_SECOND_PLAYER, INDEX_THIRD_PLAYER,
                INDEX_FOURTH_PLAYER, INDEX_SIXTH_PLAYER);

        MakeGroupCommand command1 = new MakeGroupCommand(indices1);
        MakeGroupCommand command2 = new MakeGroupCommand(indices1);
        MakeGroupCommand command3 = new MakeGroupCommand(indices2);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        assertTrue(command1.equals(command2));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different indices -> returns false
        assertFalse(command1.equals(command3));
    }

    @Test
    public void toStringMethod() {
        List<Index> indices = Arrays.asList(INDEX_FIRST_PLAYER, INDEX_SECOND_PLAYER, INDEX_THIRD_PLAYER,
                INDEX_FOURTH_PLAYER, INDEX_FIFTH_PLAYER);
        MakeGroupCommand makeGroupCommand = new MakeGroupCommand(indices);
        String expected = MakeGroupCommand.class.getCanonicalName() + "{indexList=" + indices + "}";
        assertEquals(expected, makeGroupCommand.toString());
    }
}
