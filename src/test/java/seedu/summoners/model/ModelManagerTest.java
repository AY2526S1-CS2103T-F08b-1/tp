package seedu.summoners.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.summoners.model.Model.PREDICATE_SHOW_ALL_PLAYERS;
import static seedu.summoners.testutil.Assert.assertThrows;
import static seedu.summoners.testutil.TypicalPlayers.ALICE;
import static seedu.summoners.testutil.TypicalPlayers.BENSON;
import static seedu.summoners.testutil.TypicalTeams.TEAM_A;
import static seedu.summoners.testutil.TypicalTeams.TEAM_B;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.summoners.commons.core.GuiSettings;
import seedu.summoners.model.player.NameContainsKeywordsPredicate;
import seedu.summoners.model.player.Player;
import seedu.summoners.testutil.SummonersBookBuilder;
import seedu.summoners.testutil.PlayerBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new SummonersBook(), new SummonersBook(modelManager.getSummonersBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setSummonersBookFilePath(Paths.get("summoners/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setSummonersBookFilePath(Paths.get("new/summoners/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setSummonersBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setSummonersBookFilePath(null));
    }

    @Test
    public void setSummonersBookFilePath_validPath_setsSummonersBookFilePath() {
        Path path = Paths.get("summoners/book/file/path");
        modelManager.setSummonersBookFilePath(path);
        assertEquals(path, modelManager.getSummonersBookFilePath());
    }

    @Test
    public void hasPlayer_nullPlayer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPlayer(null));
    }

    @Test
    public void hasPlayer_playerNotInSummonersBook_returnsFalse() {
        assertFalse(modelManager.hasPlayer(ALICE));
    }

    @Test
    public void hasPlayer_playerInSummonersBook_returnsTrue() {
        modelManager.addPlayer(ALICE);
        assertTrue(modelManager.hasPlayer(ALICE));
    }

    @Test
    public void deletePlayer_deletesPlayer_success() {
        modelManager.addPlayer(ALICE);
        modelManager.deletePlayer(ALICE);
        assertFalse(modelManager.hasPlayer(ALICE));
    }

    @Test
    public void setPlayer_replacesPlayer_success() {
        modelManager.addPlayer(ALICE);
        modelManager.setPlayer(ALICE, BENSON);
        assertFalse(modelManager.hasPlayer(ALICE));
        assertTrue(modelManager.hasPlayer(BENSON));
    }

    @Test
    public void getFilteredPlayerList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPlayerList().remove(0));
    }

    @Test
    public void hasTeam_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasTeam(null));
    }

    @Test
    public void hasTeam_teamNotInSummonersBook_returnsFalse() {
        assertFalse(modelManager.hasTeam(TEAM_A));
    }

    @Test
    public void hasTeam_teamInSummonersBook_returnsTrue() {
        modelManager.addTeam(TEAM_A);
        assertTrue(modelManager.hasTeam(TEAM_A));
    }

    @Test
    public void deleteTeam_deletesTeam_success() {
        modelManager.addTeam(TEAM_A);
        modelManager.deleteTeam(TEAM_A);
        assertFalse(modelManager.hasTeam(TEAM_A));
    }

    @Test
    public void setTeam_replacesTeam_success() {
        modelManager.addTeam(TEAM_A);
        modelManager.setTeam(TEAM_A, TEAM_B);
        assertFalse(modelManager.hasTeam(TEAM_A));
        assertTrue(modelManager.hasTeam(TEAM_B));
    }

    @Test
    public void getFilteredTeamList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredTeamList().remove(0));
    }

    @Test
    public void getUnassignedPlayerList_noTeams_returnsAllPlayers() {
        modelManager.addPlayer(ALICE);
        modelManager.addPlayer(BENSON);

        assertEquals(2, modelManager.getUnassignedPlayerList().size());
        assertTrue(modelManager.getUnassignedPlayerList().contains(ALICE));
        assertTrue(modelManager.getUnassignedPlayerList().contains(BENSON));
    }

    @Test
    public void getUnassignedPlayerList_someInTeam_returnsOnlyUnassigned() {
        // Add all players for TEAM_A (ALICE, BENSON, CARL, DANIEL, ELLE)
        modelManager.addPlayer(ALICE);
        modelManager.addPlayer(BENSON);

        // Add player not in any team
        Player george = new PlayerBuilder().withName("George Best").build();
        modelManager.addPlayer(george);

        // Add team containing ALICE and BENSON (but not george)
        modelManager.addTeam(TEAM_A);

        // Only george should be unassigned
        assertEquals(1, modelManager.getUnassignedPlayerList().size());
        assertTrue(modelManager.getUnassignedPlayerList().contains(george));
        assertFalse(modelManager.getUnassignedPlayerList().contains(ALICE));
        assertFalse(modelManager.getUnassignedPlayerList().contains(BENSON));
    }

    @Test
    public void equals() {
        SummonersBook summonersBook = new SummonersBookBuilder().withPlayer(ALICE).withPlayer(BENSON)
                .withTeam(TEAM_A).build();
        SummonersBook differentSummonersBook = new SummonersBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(summonersBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(summonersBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different summonersBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentSummonersBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPlayerList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(summonersBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPlayerList(PREDICATE_SHOW_ALL_PLAYERS);

        // different filteredTeamList -> returns false
        modelManager.updateFilteredTeamList(team -> false);
        assertFalse(modelManager.equals(new ModelManager(summonersBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredTeamList(Model.PREDICATE_SHOW_ALL_TEAMS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setSummonersBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(summonersBook, differentUserPrefs)));
    }
}
