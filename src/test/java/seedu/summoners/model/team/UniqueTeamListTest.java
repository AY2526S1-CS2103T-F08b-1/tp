package seedu.address.model.team;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.JAMES;
import static seedu.address.testutil.TypicalTeams.TEAM_A;
import static seedu.address.testutil.TypicalTeams.TEAM_B;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.team.exceptions.DuplicateTeamException;
import seedu.address.model.team.exceptions.PersonAlreadyInTeamException;
import seedu.address.model.team.exceptions.TeamNotFoundException;
import seedu.address.testutil.TeamBuilder;

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
    public void isPersonInAnyTeam_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTeamList.isPersonInAnyTeam(null));
    }

    @Test
    public void isPersonInAnyTeam_personNotInAnyTeam_returnsFalse() {
        uniqueTeamList.add(TEAM_A);
        assertFalse(uniqueTeamList.isPersonInAnyTeam(FIONA));
    }

    @Test
    public void isPersonInAnyTeam_personInTeam_returnsTrue() {
        Team team = new Team(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE));
        uniqueTeamList.add(team);
        assertTrue(uniqueTeamList.isPersonInAnyTeam(ALICE));
        assertTrue(uniqueTeamList.isPersonInAnyTeam(BENSON));
    }

    @Test
    public void getTeamContainingPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTeamList.getTeamContainingPerson(null));
    }

    @Test
    public void getTeamContainingPerson_personNotInAnyTeam_returnsNull() {
        uniqueTeamList.add(TEAM_A);
        assertNull(uniqueTeamList.getTeamContainingPerson(FIONA));
    }

    @Test
    public void getTeamContainingPerson_personInTeam_returnsTeam() {
        Team team = new Team(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE));
        uniqueTeamList.add(team);
        assertEquals(team, uniqueTeamList.getTeamContainingPerson(ALICE));
        assertEquals(team, uniqueTeamList.getTeamContainingPerson(BENSON));
    }

    @Test
    public void add_personAlreadyInAnotherTeam_throwsPersonAlreadyInTeamException() {
        // Add first team: ALICE (mid), BENSON (top), CARL (jungle), DANIEL (adc), ELLE (support)
        Team team1 = new Team(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE));
        uniqueTeamList.add(team1);

        // Try to add second team that also contains ALICE
        // Team2: ALICE (mid), GEORGE (top), IDA (jungle), JAMES (adc), HOON (support)
        Team team2 = new Team(Arrays.asList(ALICE, GEORGE, IDA, JAMES, HOON));
        assertThrows(PersonAlreadyInTeamException.class, () -> uniqueTeamList.add(team2));
    }

    @Test
    public void setTeams_personsReusedAcrossTeams_throwsPersonAlreadyInTeamException() {
        // Create two teams where ALICE appears in both
        Team team1 = new Team(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE));
        Team team2 = new Team(Arrays.asList(ALICE, GEORGE, IDA, JAMES, HOON));

        List<Team> teamsWithReusedPerson = Arrays.asList(team1, team2);
        assertThrows(PersonAlreadyInTeamException.class, () -> uniqueTeamList.setTeams(teamsWithReusedPerson));
    }

    @Test
    public void setTeams_personsUniqueAcrossTeams_success() {
        // TEAM_A and TEAM_B have completely different persons
        List<Team> teamsWithUniquePersons = Arrays.asList(TEAM_A, TEAM_B);
        uniqueTeamList.setTeams(teamsWithUniquePersons);

        UniqueTeamList expectedUniqueTeamList = new UniqueTeamList();
        expectedUniqueTeamList.add(TEAM_A);
        expectedUniqueTeamList.add(TEAM_B);
        assertEquals(expectedUniqueTeamList, uniqueTeamList);
    }
}
