package seedu.summoners.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_CHAMPION_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.summoners.testutil.Assert.assertThrows;
import static seedu.summoners.testutil.TypicalPlayers.ALICE;
import static seedu.summoners.testutil.TypicalPlayers.getTypicalSummonersBook;
import static seedu.summoners.testutil.TypicalTeams.TEAM_A;
import static seedu.summoners.testutil.TypicalTeams.TEAM_B;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.player.exceptions.DuplicatePlayerException;
import seedu.summoners.model.team.Team;
import seedu.summoners.model.team.exceptions.DuplicateTeamException;
import seedu.summoners.model.team.exceptions.TeamNotFoundException;
import seedu.summoners.testutil.PlayerBuilder;

public class SummonersBookTest {

    private final SummonersBook summonersBook = new SummonersBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), summonersBook.getPlayerList());
        assertEquals(Collections.emptyList(), summonersBook.getTeamList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> summonersBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlySummonersBook_replacesData() {
        SummonersBook newData = getTypicalSummonersBook();
        summonersBook.resetData(newData);
        assertEquals(newData, summonersBook);
    }

    @Test
    public void resetData_withDuplicatePlayers_throwsDuplicatePlayerException() {
        // Two players with the same identity fields
        Player editedAlice = new PlayerBuilder(ALICE).withChampion(VALID_CHAMPION_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Player> newPlayers = Arrays.asList(ALICE, editedAlice);
        SummonersBookStub newData = new SummonersBookStub(newPlayers, Collections.emptyList());

        assertThrows(DuplicatePlayerException.class, () -> summonersBook.resetData(newData));
    }

    @Test
    public void resetData_withDuplicateTeams_throwsDuplicateTeamException() {
        // Use the new SummonersBookStub constructor
        List<Team> newTeams = Arrays.asList(TEAM_A, TEAM_A);
        SummonersBookStub newData = new SummonersBookStub(Collections.emptyList(), newTeams);

        assertThrows(DuplicateTeamException.class, () -> summonersBook.resetData(newData));
    }

    @Test
    public void hasPlayer_nullPlayer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> summonersBook.hasPlayer(null));
    }

    @Test
    public void hasPlayer_playerNotInSummonersBook_returnsFalse() {
        assertFalse(summonersBook.hasPlayer(ALICE));
    }

    @Test
    public void hasPlayer_playerInSummonersBook_returnsTrue() {
        summonersBook.addPlayer(ALICE);
        assertTrue(summonersBook.hasPlayer(ALICE));
    }

    @Test
    public void hasPlayer_playerWithSameIdentityFieldsInSummonersBook_returnsTrue() {
        summonersBook.addPlayer(ALICE);
        Player editedAlice = new PlayerBuilder(ALICE).withChampion(VALID_CHAMPION_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(summonersBook.hasPlayer(editedAlice));
    }

    @Test
    public void hasTeam_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> summonersBook.hasTeam(null));
    }

    @Test
    public void hasTeam_teamNotInSummonersBook_returnsFalse() {
        assertFalse(summonersBook.hasTeam(TEAM_A));
    }

    @Test
    public void hasTeam_teamInSummonersBook_returnsTrue() {
        summonersBook.addTeam(TEAM_A);
        assertTrue(summonersBook.hasTeam(TEAM_A));
    }

    @Test
    public void getPlayerList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> summonersBook.getPlayerList().remove(0));
    }

    @Test
    public void addTeam_duplicateTeam_throwsDuplicateTeamException() {
        summonersBook.addTeam(TEAM_A);
        assertThrows(DuplicateTeamException.class, () -> summonersBook.addTeam(TEAM_A));
    }

    @Test
    public void setTeam_replacesTeamInList() {
        summonersBook.addTeam(TEAM_A);
        summonersBook.setTeam(TEAM_A, TEAM_B);
        SummonersBook expectedSummonersBook = new SummonersBook();
        expectedSummonersBook.addTeam(TEAM_B);
        assertEquals(expectedSummonersBook, summonersBook);
    }

    @Test
    public void setTeam_targetTeamNotInList_throwsTeamNotFoundException() {
        assertThrows(TeamNotFoundException.class, () -> summonersBook.setTeam(TEAM_A, TEAM_A));
    }

    @Test
    public void removeTeam_removesTeamFromList() {
        summonersBook.addTeam(TEAM_A);
        summonersBook.removeTeam(TEAM_A);
        SummonersBook expectedSummonersBook = new SummonersBook();
        assertEquals(expectedSummonersBook, summonersBook);
    }

    @Test
    public void removeTeam_teamDoesNotExist_throwsTeamNotFoundException() {
        assertThrows(TeamNotFoundException.class, () -> summonersBook.removeTeam(TEAM_A));
    }

    @Test
    public void getTeamList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> summonersBook.getTeamList().remove(0));
    }

    @Test
    public void findPlayerByName_playerExists_returnsPlayer() {
        summonersBook.addPlayer(ALICE);
        assertTrue(summonersBook.findPlayerByName(ALICE.getName()).isPresent());
        assertEquals(ALICE, summonersBook.findPlayerByName(ALICE.getName()).get());
    }

    @Test
    public void findPlayerByName_playerDoesNotExist_returnsEmptyOptional() {
        assertFalse(summonersBook.findPlayerByName(ALICE.getName()).isPresent());
    }

    @Test
    public void findPlayerByName_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> summonersBook.findPlayerByName(null));
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(summonersBook.equals(summonersBook));

        // same values -> returns true
        SummonersBook summonersBookCopy = new SummonersBook();
        assertTrue(summonersBook.equals(summonersBookCopy));

        // different types -> returns false
        assertFalse(summonersBook.equals(5));

        // null -> returns false
        assertFalse(summonersBook.equals(null));

        // different players -> returns false
        SummonersBook differentPlayersSummonersBook = new SummonersBook();
        differentPlayersSummonersBook.addPlayer(ALICE);
        assertFalse(summonersBook.equals(differentPlayersSummonersBook));

        // different teams -> returns false
        SummonersBook differentTeamsSummonersBook = new SummonersBook();
        differentTeamsSummonersBook.addTeam(TEAM_A);
        assertFalse(summonersBook.equals(differentTeamsSummonersBook));
    }

    @Test
    public void toStringMethod() {
        String expected = SummonersBook.class.getCanonicalName() + "{players=" + summonersBook.getPlayerList()
                + ", teams=" + summonersBook.getTeamList() + "}";
        assertEquals(expected, summonersBook.toString());
    }

    /**
     * A stub ReadOnlySummonersBook whose players list can violate interface constraints.
     */
    private static class SummonersBookStub implements ReadOnlySummonersBook {
        private final ObservableList<Player> players = FXCollections.observableArrayList();
        private final ObservableList<Team> teams = FXCollections.observableArrayList();

        SummonersBookStub(Collection<Player> players, Collection<Team> teams) {
            this.players.setAll(players);
            this.teams.setAll(teams);
        }

        @Override
        public ObservableList<Player> getPlayerList() {
            return players;
        }

        @Override
        public ObservableList<Team> getTeamList() {
            return teams;
        }
    }

}
