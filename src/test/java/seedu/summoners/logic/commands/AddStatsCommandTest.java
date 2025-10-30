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
 * Contains integration tests (interaction with the Model) and unit tests for {@link AddStatsCommand}.
 */
public class AddStatsCommandTest {

    private static final String CPM = "10.2";
    private static final String GD15 = "2400";
    private static final String KDA = "2.6";

    private Model model = new ModelManager(getTypicalSummonersBook(), new UserPrefs());

    @Test
    public void execute_unfilteredListPlayerNotInAnyTeam_success() throws Exception {
        // Use the typical summoners book without teams to ensure the player is not in any team
        Player playerToEdit = model.getFilteredPlayerList().get(INDEX_FIRST_PLAYER.getZeroBased());

        // Build expected edited player (preserving identity & attributes, updating Stats only)
        Stats updatedStats = playerToEdit.getStats().addLatestStats(CPM, GD15, KDA);
        Player editedPlayer = new Player(playerToEdit.getId(),
                playerToEdit.getName(),
                playerToEdit.getRole(),
                playerToEdit.getRank(),
                playerToEdit.getChampion(),
                playerToEdit.getTags(),
                playerToEdit.getWins(),
                playerToEdit.getLosses(),
                updatedStats);

        AddStatsCommand cmd = new AddStatsCommand(INDEX_FIRST_PLAYER, CPM, GD15, KDA);
        String expectedMessage = String.format(AddStatsCommand.MESSAGE_RECORD_SUCCESS, Messages.format(editedPlayer));

        Model expectedModel = new ModelManager(new SummonersBook(model.getSummonersBook()), new UserPrefs());
        expectedModel.setPlayer(playerToEdit, editedPlayer);
        expectedModel.updateFilteredPlayerList(Model.PREDICATE_SHOW_ALL_PLAYERS);
        expectedModel.updateFilteredTeamList(Model.PREDICATE_SHOW_ALL_TEAMS);

        assertCommandSuccess(cmd, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unfilteredListPlayerInTeamUpdatesPlayerAndTeam_success() throws Exception {
        // Model that already has teams with players assigned
        Model modelWithTeams = new ModelManager(getTypicalSummonersBookWithTeams(), new UserPrefs());

        // Pick a player that is known to be in a team; use ALICE as in other tests
        Player playerToEdit = ALICE;
        Stats updatedStats = playerToEdit.getStats().addLatestStats("7.0", "1000", "2.2");
        Player editedPlayer = new Player(playerToEdit.getId(),
                playerToEdit.getName(),
                playerToEdit.getRole(),
                playerToEdit.getRank(),
                playerToEdit.getChampion(),
                playerToEdit.getTags(),
                playerToEdit.getWins(),
                playerToEdit.getLosses(),
                updatedStats);

        // Find the team containing that player in the model
        Team originalTeam = modelWithTeams.getFilteredTeamList()
                .stream()
                .filter(t -> t.hasPlayer(playerToEdit))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected ALICE to be in a team in typical teams data"));

        Team expectedTeam = new TeamBuilder(originalTeam)
                .replacePlayer(playerToEdit, editedPlayer)
                .build();

        // Command uses the player index in filtered player list — ALICE is typically first
        AddStatsCommand cmd = new AddStatsCommand(Index.fromOneBased(1), "7.0", "1000", "2.2");
        String expectedMessage = String.format(AddStatsCommand.MESSAGE_RECORD_SUCCESS, Messages.format(editedPlayer));

        Model expectedModel = new ModelManager(getTypicalSummonersBookWithTeams(), new UserPrefs());
        expectedModel.setPlayer(playerToEdit, editedPlayer);
        expectedModel.setTeam(originalTeam, expectedTeam);
        expectedModel.updateFilteredPlayerList(Model.PREDICATE_SHOW_ALL_PLAYERS);
        expectedModel.updateFilteredTeamList(Model.PREDICATE_SHOW_ALL_TEAMS);

        assertCommandSuccess(cmd, modelWithTeams, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        // Filter the list to show only the first player
        showPlayerAtIndex(model, INDEX_FIRST_PLAYER);

        Player playerInFilteredList = model.getFilteredPlayerList().get(INDEX_FIRST_PLAYER.getZeroBased());
        Stats updatedStats = playerInFilteredList.getStats().addLatestStats(CPM, GD15, KDA);
        Player editedPlayer = new Player(playerInFilteredList.getId(),
                playerInFilteredList.getName(),
                playerInFilteredList.getRole(),
                playerInFilteredList.getRank(),
                playerInFilteredList.getChampion(),
                playerInFilteredList.getTags(),
                playerInFilteredList.getWins(),
                playerInFilteredList.getLosses(),
                updatedStats);

        AddStatsCommand cmd = new AddStatsCommand(INDEX_FIRST_PLAYER, CPM, GD15, KDA);
        String expectedMessage = String.format(AddStatsCommand.MESSAGE_RECORD_SUCCESS, Messages.format(editedPlayer));

        Model expectedModel = new ModelManager(new SummonersBook(model.getSummonersBook()), new UserPrefs());
        expectedModel.setPlayer(model.getFilteredPlayerList().get(0), editedPlayer);
        expectedModel.updateFilteredPlayerList(Model.PREDICATE_SHOW_ALL_PLAYERS);
        expectedModel.updateFilteredTeamList(Model.PREDICATE_SHOW_ALL_TEAMS);

        assertCommandSuccess(cmd, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBound = Index.fromOneBased(model.getFilteredPlayerList().size() + 1);
        AddStatsCommand cmd = new AddStatsCommand(outOfBound, CPM, GD15, KDA);

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

        AddStatsCommand cmd = new AddStatsCommand(outOfBoundIndex, CPM, GD15, KDA);
        assertCommandFailure(cmd, model, Messages.MESSAGE_INVALID_PLAYER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        AddStatsCommand standard = new AddStatsCommand(INDEX_FIRST_PLAYER, "7.0", "1000", "2.2");

        // same values -> true
        AddStatsCommand sameValues = new AddStatsCommand(INDEX_FIRST_PLAYER, "7.0", "1000", "2.2");
        assertTrue(standard.equals(sameValues));

        // same object -> true
        assertTrue(standard.equals(standard));

        // null -> false
        assertFalse(standard.equals(null));

        // different type -> false
        assertFalse(standard.equals(new ClearCommand()));

        // different index -> false
        assertFalse(standard.equals(new AddStatsCommand(INDEX_SECOND_PLAYER, "7.0", "1000", "2.2")));

        // different params -> false
        assertFalse(standard.equals(new AddStatsCommand(INDEX_FIRST_PLAYER, "10.2", "2400", "2.6")));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        AddStatsCommand command = new AddStatsCommand(index, CPM, GD15, KDA);
        String expected = AddStatsCommand.class.getCanonicalName()
                + "{index=" + index
                + ", cpm=" + CPM
                + ", gd15=" + GD15
                + ", kda=" + KDA
                + "}";
        assertEquals(expected, command.toString());
    }
}
