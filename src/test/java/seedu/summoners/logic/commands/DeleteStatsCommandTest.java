package seedu.summoners.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.summoners.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.summoners.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.summoners.logic.commands.CommandTestUtil.showPlayerAtIndex;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_FIRST_PLAYER;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_SECOND_PLAYER;
import static seedu.summoners.testutil.TypicalPlayers.ALICE;
import static seedu.summoners.testutil.TypicalPlayers.getTypicalSummonersBook;
import static seedu.summoners.testutil.TypicalTeams.getTypicalSummonersBookWithTeams;

import org.junit.jupiter.api.Test;

import seedu.summoners.commons.core.index.Index;
import seedu.summoners.logic.Messages;
import seedu.summoners.model.Model;
import seedu.summoners.model.ModelManager;
import seedu.summoners.model.SummonersBook;
import seedu.summoners.model.UserPrefs;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.player.Stats;
import seedu.summoners.model.team.Team;
import seedu.summoners.testutil.TeamBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@link DeleteStatsCommand}.
 */
public class DeleteStatsCommandTest {

    private static final String AUG_CPM = "10.2";
    private static final String AUG_GD15 = "2400";
    private static final String AUG_KDA = "2.6";

    private final Model model = new ModelManager(getTypicalSummonersBook(), new UserPrefs());

    @Test
    public void execute_unfilteredListPlayerNotInAnyTeam_success() throws Exception {
        // Player not in any team (typical summoners book without teams)
        Player original = model.getFilteredPlayerList().get(INDEX_FIRST_PLAYER.getZeroBased());

        // Pre-augment stats so deletion has an effect
        Stats augmentedStats = original.getStats().addLatestStats(AUG_CPM, AUG_GD15, AUG_KDA);
        Player augmented = new Player(
                original.getId(), original.getName(), original.getRole(), original.getRank(),
                original.getChampion(), original.getTags(), original.getWins(), original.getLosses(),
                augmentedStats
        );
        model.setPlayer(original, augmented);

        // After delete, we should get back the original stats (pre-augmentation)
        Player expectedAfterDelete = new Player(
                original.getId(), original.getName(), original.getRole(), original.getRank(),
                original.getChampion(), original.getTags(), original.getWins(), original.getLosses(),
                original.getStats()
        );

        Model expectedModel = new ModelManager(new SummonersBook(model.getSummonersBook()), new UserPrefs());
        expectedModel.setPlayer(augmented, expectedAfterDelete);
        expectedModel.updateFilteredPlayerList(Model.PREDICATE_SHOW_ALL_PLAYERS);
        expectedModel.updateFilteredTeamList(Model.PREDICATE_SHOW_ALL_TEAMS);

        DeleteStatsCommand cmd = new DeleteStatsCommand(INDEX_FIRST_PLAYER);
        String expectedMessage = String.format(DeleteStatsCommand.MESSAGE_RECORD_SUCCESS,
                Messages.format(expectedAfterDelete));

        assertCommandSuccess(cmd, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unfilteredListPlayerInTeamUpdatesPlayerAndTeam_success() throws Exception {
        // Model with teams
        Model modelWithTeams = new ModelManager(getTypicalSummonersBookWithTeams(), new UserPrefs());

        // Use ALICE who is in a team in typical data
        Player aliceInModel = modelWithTeams.getFilteredPlayerList().stream()
                .filter(p -> p.isSamePlayer(ALICE))
                .findFirst()
                .orElseThrow(() -> new AssertionError("ALICE should exist in typical teams model"));

        Team teamContainingAlice = modelWithTeams.getFilteredTeamList().stream()
                .filter(t -> t.hasPlayer(aliceInModel))
                .findFirst()
                .orElseThrow(() -> new AssertionError("ALICE should be in a team in typical teams model"));

        // Pre-augment ALICE so deletion has an effect
        Stats aliceAugmentedStats = aliceInModel.getStats().addLatestStats(AUG_CPM, AUG_GD15, AUG_KDA);
        Player aliceAugmented = new Player(
                aliceInModel.getId(), aliceInModel.getName(), aliceInModel.getRole(), aliceInModel.getRank(),
                aliceInModel.getChampion(), aliceInModel.getTags(), aliceInModel.getWins(), aliceInModel.getLosses(),
                aliceAugmentedStats
        );
        modelWithTeams.setPlayer(aliceInModel, aliceAugmented);
        Team teamWithAugmentedAlice = new TeamBuilder(teamContainingAlice)
                .replacePlayer(aliceInModel, aliceAugmented)
                .build();
        modelWithTeams.setTeam(teamContainingAlice, teamWithAugmentedAlice);

        // Expected model equals the original typical model (i.e., after deletion we revert to original stats)
        Model expectedModel = new ModelManager(getTypicalSummonersBookWithTeams(), new UserPrefs());

        // ALICE is typically first in list
        DeleteStatsCommand cmd = new DeleteStatsCommand(Index.fromOneBased(1));

        Player expectedAlice = expectedModel.getFilteredPlayerList().get(0);
        String expectedMessage = String.format(DeleteStatsCommand.MESSAGE_RECORD_SUCCESS,
                Messages.format(expectedAlice));

        assertCommandSuccess(cmd, modelWithTeams, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        // Filter to only the first player
        showPlayerAtIndex(model, INDEX_FIRST_PLAYER);

        Player original = model.getFilteredPlayerList().get(INDEX_FIRST_PLAYER.getZeroBased());

        // Pre-augment stats so deletion has an effect
        Stats augmentedStats = original.getStats().addLatestStats(AUG_CPM, AUG_GD15, AUG_KDA);
        Player augmented = new Player(
                original.getId(), original.getName(), original.getRole(), original.getRank(),
                original.getChampion(), original.getTags(), original.getWins(), original.getLosses(),
                augmentedStats
        );
        model.setPlayer(original, augmented);

        // Expected model should reflect original stats after delete
        Player expectedAfterDelete = new Player(
                original.getId(), original.getName(), original.getRole(), original.getRank(),
                original.getChampion(), original.getTags(), original.getWins(), original.getLosses(),
                original.getStats()
        );

        Model expectedModel = new ModelManager(new SummonersBook(model.getSummonersBook()), new UserPrefs());
        expectedModel.setPlayer(augmented, expectedAfterDelete);
        expectedModel.updateFilteredPlayerList(Model.PREDICATE_SHOW_ALL_PLAYERS);
        expectedModel.updateFilteredTeamList(Model.PREDICATE_SHOW_ALL_TEAMS);

        DeleteStatsCommand cmd = new DeleteStatsCommand(INDEX_FIRST_PLAYER);
        String expectedMessage = String.format(DeleteStatsCommand.MESSAGE_RECORD_SUCCESS,
                Messages.format(expectedAfterDelete));

        assertCommandSuccess(cmd, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBound = Index.fromOneBased(model.getFilteredPlayerList().size() + 1);
        DeleteStatsCommand cmd = new DeleteStatsCommand(outOfBound);

        assertCommandFailure(cmd, model, Messages.MESSAGE_INVALID_PLAYER_DISPLAYED_INDEX);
    }

    /**
     * Invalid index against the filtered list but still within the size of the full summoners book.
     */
    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPlayerAtIndex(model, INDEX_FIRST_PLAYER);
        Index outOfBoundIndex = INDEX_SECOND_PLAYER;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getSummonersBook().getPlayerList().size());

        DeleteStatsCommand cmd = new DeleteStatsCommand(outOfBoundIndex);
        assertCommandFailure(cmd, model, Messages.MESSAGE_INVALID_PLAYER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteStatsCommand standard = new DeleteStatsCommand(INDEX_FIRST_PLAYER);

        // same values -> true
        DeleteStatsCommand sameValues = new DeleteStatsCommand(INDEX_FIRST_PLAYER);
        assertTrue(standard.equals(sameValues));

        // same object -> true
        assertTrue(standard.equals(standard));

        // null -> false
        assertFalse(standard.equals(null));

        // different type -> false
        assertFalse(standard.equals(new ClearCommand()));

        // different index -> false
        assertFalse(standard.equals(new DeleteStatsCommand(INDEX_SECOND_PLAYER)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        DeleteStatsCommand command = new DeleteStatsCommand(index);
        String expected = DeleteStatsCommand.class.getCanonicalName()
                + "{index=" + index + "}";
        assertEquals(expected, command.toString());
    }
}
