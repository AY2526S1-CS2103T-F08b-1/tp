package seedu.summoners.model.team;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.summoners.testutil.Assert.assertThrows;
import static seedu.summoners.testutil.TypicalPlayers.ALICE;
import static seedu.summoners.testutil.TypicalPlayers.BENSON;
import static seedu.summoners.testutil.TypicalPlayers.CARL;
import static seedu.summoners.testutil.TypicalPlayers.DANIEL;
import static seedu.summoners.testutil.TypicalPlayers.ELLE;
import static seedu.summoners.testutil.TypicalPlayers.FIONA;
import static seedu.summoners.testutil.TypicalPlayers.GEORGE;
import static seedu.summoners.testutil.TypicalPlayers.HOON;
import static seedu.summoners.testutil.TypicalPlayers.IDA;
import static seedu.summoners.testutil.TypicalPlayers.JAMES;
import static seedu.summoners.testutil.TypicalTeams.TEAM_A;
import static seedu.summoners.testutil.TypicalTeams.TEAM_B;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.summoners.model.team.exceptions.DuplicateTeamException;
import seedu.summoners.model.team.exceptions.PlayerAlreadyInTeamException;
import seedu.summoners.model.team.exceptions.TeamNotFoundException;
import seedu.summoners.testutil.TeamBuilder;

public class UniqueTeamListTest {

    private final UniqueTeamList uniqueTeamList = new UniqueTeamList();

    @Test
    public void contains_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTeamList.contains(null));
    }

    @Test
    public void contains_teamNotInList_returnsFalse() {
        assertFalse(uniqueTeamList.contains(TEAM_A));
    }

    @Test
    public void contains_teamInList_returnsTrue() {
        uniqueTeamList.add(TEAM_A);
        assertTrue(uniqueTeamList.contains(TEAM_A));
    }

    @Test
    public void contains_teamWithSameIdentityFieldsInList_returnsTrue() {
        uniqueTeamList.add(TEAM_A);
        Team editedTeamA = new TeamBuilder(TEAM_A).build(); // Same ID
        assertTrue(uniqueTeamList.contains(editedTeamA));
    }

    @Test
    public void add_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTeamList.add(null));
    }

    @Test
    public void add_duplicateTeam_throwsDuplicateTeamException() {
        uniqueTeamList.add(TEAM_A);
        assertThrows(DuplicateTeamException.class, () -> uniqueTeamList.add(TEAM_A));
    }

    @Test
    public void remove_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTeamList.remove(null));
    }

    @Test
    public void remove_teamDoesNotExist_throwsTeamNotFoundException() {
        assertThrows(TeamNotFoundException.class, () -> uniqueTeamList.remove(TEAM_A));
    }

    @Test
    public void remove_existingTeam_removesTeam() {
        uniqueTeamList.add(TEAM_A);
        uniqueTeamList.remove(TEAM_A);
        UniqueTeamList expectedUniqueTeamList = new UniqueTeamList();
        assertEquals(expectedUniqueTeamList, uniqueTeamList);
    }

    @Test
    public void setTeam_nullTargetTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTeamList.setTeam(null, TEAM_A));
    }

    @Test
    public void setTeam_nullEditedTeam_throwsNullPointerException() {
        uniqueTeamList.add(TEAM_A);
        assertThrows(NullPointerException.class, () -> uniqueTeamList.setTeam(TEAM_A, null));
    }

    @Test
    public void setTeam_targetTeamNotInList_throwsTeamNotFoundException() {
        assertThrows(TeamNotFoundException.class, () -> uniqueTeamList.setTeam(TEAM_A, TEAM_A));
    }

    @Test
    public void setTeam_editedTeamHasDifferentIdentity_success() {
        uniqueTeamList.add(TEAM_A);
        uniqueTeamList.setTeam(TEAM_A, TEAM_B);
        UniqueTeamList expectedUniqueTeamList = new UniqueTeamList();
        expectedUniqueTeamList.add(TEAM_B);
        assertEquals(expectedUniqueTeamList, uniqueTeamList);
    }

    @Test
    public void setTeams_listWithDuplicateTeams_throwsDuplicateTeamException() {
        List<Team> listWithDuplicateTeams = Arrays.asList(TEAM_A, TEAM_A);
        assertThrows(DuplicateTeamException.class, () -> uniqueTeamList.setTeams(listWithDuplicateTeams));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueTeamList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void isPlayerInAnyTeam_nullPlayer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTeamList.isPlayerInAnyTeam(null));
    }

    @Test
    public void isPlayerInAnyTeam_playerNotInAnyTeam_returnsFalse() {
        uniqueTeamList.add(TEAM_A);
        assertFalse(uniqueTeamList.isPlayerInAnyTeam(FIONA));
    }

    @Test
    public void isPlayerInAnyTeam_playerInTeam_returnsTrue() {
        Team team = new Team(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE));
        uniqueTeamList.add(team);
        assertTrue(uniqueTeamList.isPlayerInAnyTeam(ALICE));
        assertTrue(uniqueTeamList.isPlayerInAnyTeam(BENSON));
    }

    @Test
    public void getTeamContainingPlayer_nullPlayer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTeamList.getTeamContainingPlayer(null));
    }

    @Test
    public void getTeamContainingPlayer_playerNotInAnyTeam_returnsNull() {
        uniqueTeamList.add(TEAM_A);
        assertNull(uniqueTeamList.getTeamContainingPlayer(FIONA));
    }

    @Test
    public void getTeamContainingPlayer_playerInTeam_returnsTeam() {
        Team team = new Team(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE));
        uniqueTeamList.add(team);
        assertEquals(team, uniqueTeamList.getTeamContainingPlayer(ALICE));
        assertEquals(team, uniqueTeamList.getTeamContainingPlayer(BENSON));
    }

    @Test
    public void add_playerAlreadyInAnotherTeam_throwsPlayerAlreadyInTeamException() {
        // Add first team: ALICE (mid), BENSON (top), CARL (jungle), DANIEL (adc), ELLE (support)
        Team team1 = new Team(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE));
        uniqueTeamList.add(team1);

        // Try to add second team that also contains ALICE
        // Team2: ALICE (mid), GEORGE (top), IDA (jungle), JAMES (adc), HOON (support)
        Team team2 = new Team(Arrays.asList(ALICE, GEORGE, IDA, JAMES, HOON));
        assertThrows(PlayerAlreadyInTeamException.class, () -> uniqueTeamList.add(team2));
    }

    @Test
    public void setTeams_playersReusedAcrossTeams_throwsPlayerAlreadyInTeamException() {
        // Create two teams where ALICE appears in both
        Team team1 = new Team(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE));
        Team team2 = new Team(Arrays.asList(ALICE, GEORGE, IDA, JAMES, HOON));

        List<Team> teamsWithReusedPlayer = Arrays.asList(team1, team2);
        assertThrows(PlayerAlreadyInTeamException.class, () -> uniqueTeamList.setTeams(teamsWithReusedPlayer));
    }

    @Test
    public void setTeams_playersUniqueAcrossTeams_success() {
        // TEAM_A and TEAM_B have completely different players
        List<Team> teamsWithUniquePlayers = Arrays.asList(TEAM_A, TEAM_B);
        uniqueTeamList.setTeams(teamsWithUniquePlayers);

        UniqueTeamList expectedUniqueTeamList = new UniqueTeamList();
        expectedUniqueTeamList.add(TEAM_A);
        expectedUniqueTeamList.add(TEAM_B);
        assertEquals(expectedUniqueTeamList, uniqueTeamList);
    }
}
