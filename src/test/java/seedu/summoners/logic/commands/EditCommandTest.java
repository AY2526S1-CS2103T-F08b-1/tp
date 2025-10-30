package seedu.summoners.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.summoners.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.summoners.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.summoners.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.summoners.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.summoners.logic.commands.CommandTestUtil.showPlayerAtIndex;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_FIRST_PLAYER;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_SECOND_PLAYER;
import static seedu.summoners.testutil.TypicalPlayers.ALICE;
import static seedu.summoners.testutil.TypicalPlayers.BENSON;
import static seedu.summoners.testutil.TypicalPlayers.getTypicalSummonersBook;
import static seedu.summoners.testutil.TypicalTeams.getTypicalSummonersBookWithTeams;

import org.junit.jupiter.api.Test;

import seedu.summoners.commons.core.index.Index;
import seedu.summoners.logic.Messages;
import seedu.summoners.logic.commands.EditCommand.EditPlayerDescriptor;
import seedu.summoners.model.SummonersBook;
import seedu.summoners.model.Model;
import seedu.summoners.model.ModelManager;
import seedu.summoners.model.UserPrefs;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.Team;
import seedu.summoners.testutil.EditPlayerDescriptorBuilder;
import seedu.summoners.testutil.PlayerBuilder;
import seedu.summoners.testutil.TeamBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalSummonersBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Player editedPlayer = new PlayerBuilder().build();
        EditPlayerDescriptor descriptor = new EditPlayerDescriptorBuilder(editedPlayer).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PLAYER, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PLAYER_SUCCESS, Messages.format(editedPlayer));

        Model expectedModel = new ModelManager(new SummonersBook(model.getSummonersBook()), new UserPrefs());
        expectedModel.setPlayer(model.getFilteredPlayerList().get(0), editedPlayer);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPlayer = Index.fromOneBased(model.getFilteredPlayerList().size());
        Player lastPlayer = model.getFilteredPlayerList().get(indexLastPlayer.getZeroBased());

        PlayerBuilder playerInList = new PlayerBuilder(lastPlayer);
        Player editedPlayer = playerInList.withName(VALID_NAME_BOB).withRole(VALID_ROLE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPlayerDescriptor descriptor = new EditPlayerDescriptorBuilder().withName(VALID_NAME_BOB)
                .withRole(VALID_ROLE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastPlayer, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PLAYER_SUCCESS, Messages.format(editedPlayer));

        Model expectedModel = new ModelManager(new SummonersBook(model.getSummonersBook()), new UserPrefs());
        expectedModel.setPlayer(lastPlayer, editedPlayer);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PLAYER, new EditPlayerDescriptor());
        Player editedPlayer = model.getFilteredPlayerList().get(INDEX_FIRST_PLAYER.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PLAYER_SUCCESS, Messages.format(editedPlayer));

        Model expectedModel = new ModelManager(new SummonersBook(model.getSummonersBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPlayerAtIndex(model, INDEX_FIRST_PLAYER);

        Player playerInFilteredList = model.getFilteredPlayerList().get(INDEX_FIRST_PLAYER.getZeroBased());
        Player editedPlayer = new PlayerBuilder(playerInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PLAYER,
                new EditPlayerDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PLAYER_SUCCESS, Messages.format(editedPlayer));

        Model expectedModel = new ModelManager(new SummonersBook(model.getSummonersBook()), new UserPrefs());
        expectedModel.setPlayer(model.getFilteredPlayerList().get(0), editedPlayer);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePlayerUnfilteredList_failure() {
        Player firstPlayer = model.getFilteredPlayerList().get(INDEX_FIRST_PLAYER.getZeroBased());
        EditPlayerDescriptor descriptor = new EditPlayerDescriptorBuilder(firstPlayer).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PLAYER, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PLAYER);
    }

    @Test
    public void execute_duplicatePlayerFilteredList_failure() {
        showPlayerAtIndex(model, INDEX_FIRST_PLAYER);

        // edit player in filtered list into a duplicate in summoners book
        Player playerInList = model.getSummonersBook().getPlayerList().get(INDEX_SECOND_PLAYER.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PLAYER,
                new EditPlayerDescriptorBuilder(playerInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PLAYER);
    }

    @Test
    public void execute_invalidPlayerIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPlayerList().size() + 1);
        EditPlayerDescriptor descriptor = new EditPlayerDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PLAYER_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of summoners book
     */
    @Test
    public void execute_invalidPlayerIndexFilteredList_failure() {
        showPlayerAtIndex(model, INDEX_FIRST_PLAYER);
        Index outOfBoundIndex = INDEX_SECOND_PLAYER;
        // ensures that outOfBoundIndex is still in bounds of summoners book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getSummonersBook().getPlayerList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPlayerDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PLAYER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_editPlayerInTeam_updatesTeamAndPlayerSuccessfully() {
        Model modelWithTeams = new ModelManager(getTypicalSummonersBookWithTeams(), new UserPrefs());

        Player playerToEdit = ALICE;
        Player editedPlayer = new PlayerBuilder(playerToEdit)
                .withName("Alice Edited")
                .withRank("Diamond")
                .build();

        EditPlayerDescriptor descriptor = new EditPlayerDescriptorBuilder(editedPlayer).build();
        EditCommand editCommand = new EditCommand(Index.fromOneBased(1), descriptor);

        Team originalTeam = modelWithTeams.getFilteredTeamList().get(0);
        Team expectedTeam = new TeamBuilder(originalTeam)
                .replacePlayer(playerToEdit, editedPlayer)
                .build();

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PLAYER_SUCCESS,
                Messages.format(editedPlayer));

        Model expectedModel = new ModelManager(getTypicalSummonersBookWithTeams(), new UserPrefs());
        expectedModel.setPlayer(playerToEdit, editedPlayer);
        expectedModel.setTeam(originalTeam, expectedTeam);
        expectedModel.updateFilteredPlayerList(Model.PREDICATE_SHOW_ALL_PLAYERS);

        assertCommandSuccess(editCommand, modelWithTeams, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editPlayerInTeamWithDuplicateRole_throwsCommandException() {
        Model modelWithTeams = new ModelManager(getTypicalSummonersBookWithTeams(), new UserPrefs());

        Player playerToEdit = ALICE;
        Player editedPlayer = new PlayerBuilder(playerToEdit)
                .withRole(BENSON.getRole().toString())
                .build();

        EditPlayerDescriptor descriptor = new EditPlayerDescriptorBuilder(editedPlayer).build();
        EditCommand editCommand = new EditCommand(Index.fromOneBased(1), descriptor);
        String expectedMessage = "Operation would result in duplicate roles in the team. "
                + ALICE.getName() + " and " + BENSON.getName() + " both have the role: "
                + editedPlayer.getRole();

        assertCommandFailure(editCommand, modelWithTeams, expectedMessage);
    }

    @Test
    public void execute_editPlayerInTeamWithDuplicateChampion_throwsCommandException() {
        Model modelWithTeams = new ModelManager(getTypicalSummonersBookWithTeams(), new UserPrefs());

        Player playerToEdit = ALICE;
        Player editedPlayer = new PlayerBuilder(playerToEdit)
                .withChampion(BENSON.getChampion().toString())
                .build();

        EditPlayerDescriptor descriptor = new EditPlayerDescriptorBuilder(editedPlayer).build();
        EditCommand editCommand = new EditCommand(Index.fromOneBased(1), descriptor);
        String expectedMessage = "Operation would result in duplicate champions in the team. "
                + ALICE.getName() + " and " + BENSON.getName() + " both play: "
                + editedPlayer.getChampion();

        assertCommandFailure(editCommand, modelWithTeams, expectedMessage);
    }

    @Test
    public void execute_editPlayerNotInAnyTeam_onlyUpdatesPlayer() {
        Model model = new ModelManager(getTypicalSummonersBookWithTeams(), new UserPrefs());
        Player nonTeamPlayer = new PlayerBuilder().withName("Teamless Player").withRole("mid").withChampion("Ahri")
                .build();
        model.addPlayer(nonTeamPlayer);

        Player editedPlayer = new PlayerBuilder(nonTeamPlayer).withRank("Diamond").build();
        EditPlayerDescriptor descriptor = new EditPlayerDescriptorBuilder(editedPlayer).build();
        EditCommand editCommand = new EditCommand(Index.fromOneBased(model.getFilteredPlayerList().size()), descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PLAYER_SUCCESS, Messages.format(editedPlayer));
        Model expectedModel = new ModelManager(model.getSummonersBook(), new UserPrefs());
        expectedModel.setPlayer(nonTeamPlayer, editedPlayer);
        expectedModel.updateFilteredPlayerList(Model.PREDICATE_SHOW_ALL_PLAYERS);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PLAYER, DESC_AMY);

        // same values -> returns true
        EditPlayerDescriptor copyDescriptor = new EditPlayerDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PLAYER, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PLAYER, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PLAYER, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditPlayerDescriptor editPlayerDescriptor = new EditPlayerDescriptor();
        EditCommand editCommand = new EditCommand(index, editPlayerDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editPlayerDescriptor="
                + editPlayerDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
