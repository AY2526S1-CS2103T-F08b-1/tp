package seedu.address.model.team;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.team.exceptions.DuplicateChampionException;
import seedu.address.model.team.exceptions.DuplicateRoleException;
import seedu.address.model.team.exceptions.InvalidTeamSizeException;
import seedu.address.testutil.PersonBuilder;

public class TeamTest {
    private static final String DUMMY_ID = "";
    private static final int DUMMY_WINS = 0;
    private static final int DUMMY_LOSSES = 0;

    // Valid team with 5 persons: unique roles and unique champions
    private static final Person TOP_PERSON = new PersonBuilder().withName("Top Person")
            .withRole("top").withChampion("Garen").withRank("gold").build();
    private static final Person JUNGLE_PERSON = new PersonBuilder().withName("Jungle Person")
            .withRole("jungle").withChampion("Lee Sin").withRank("platinum").build();
    private static final Person MID_PERSON = new PersonBuilder().withName("Mid Person")
            .withRole("mid").withChampion("Ahri").withRank("diamond").build();
    private static final Person ADC_PERSON = new PersonBuilder().withName("ADC Person")
            .withRole("adc").withChampion("Jinx").withRank("gold").build();
    private static final Person SUPPORT_PERSON = new PersonBuilder().withName("Support Person")
            .withRole("support").withChampion("Thresh").withRank("platinum").build();
    private static final Person EXTRA_PERSON = new PersonBuilder().withName("Extra Person")
            .withRole("top").withChampion("Ahri").withRank("master").build();

    private static final List<Person> VALID_PERSONS = Arrays.asList(
            TOP_PERSON, JUNGLE_PERSON, MID_PERSON, ADC_PERSON, SUPPORT_PERSON);

    // Invalid: Only 4 persons
    private static final List<Person> INVALID_PERSONS_SIZE_FOUR = Arrays.asList(
            TOP_PERSON, JUNGLE_PERSON, MID_PERSON, ADC_PERSON);

    // Invalid: 6 persons
    private static final List<Person> INVALID_PERSONS_SIZE_SIX = Arrays.asList(
            TOP_PERSON, JUNGLE_PERSON, MID_PERSON, ADC_PERSON, SUPPORT_PERSON, EXTRA_PERSON);

    // Invalid: Duplicate role (top)
    private static final List<Person> INVALID_PERSONS_DUPLICATE_ROLE = Arrays.asList(
            TOP_PERSON, EXTRA_PERSON, MID_PERSON, ADC_PERSON, SUPPORT_PERSON);

    // Invalid: Duplicate champion (Ahri)
    private static final List<Person> INVALID_PERSONS_DUPLICATE_CHAMPIONS = Arrays.asList(
            EXTRA_PERSON, JUNGLE_PERSON, MID_PERSON, ADC_PERSON, SUPPORT_PERSON);

    @Test
    public void constructor_validPersons_success() {
        Team team = new Team(VALID_PERSONS);
        assertEquals(VALID_PERSONS, team.getPersons());
        assertNotNull(team.getId());
    }

    @Test
    public void constructor_validPersonsWithIdWithWinsWithLosses_success() {
        Team team = new Team(DUMMY_ID, VALID_PERSONS, DUMMY_WINS, DUMMY_LOSSES);
        assertEquals(VALID_PERSONS, team.getPersons());
        assertEquals(DUMMY_ID, team.getId());
        assertEquals(DUMMY_WINS, team.getWins());
        assertEquals(DUMMY_LOSSES, team.getLosses());
    }

    @Test
    public void constructor_nullPersons_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Team(null));
        assertThrows(NullPointerException.class, () -> new Team(DUMMY_ID, null, DUMMY_WINS, DUMMY_LOSSES));
    }

    @Test
    public void constructor_nullId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Team(null, VALID_PERSONS, DUMMY_WINS, DUMMY_LOSSES));
    }

    @Test
    public void constructor_fourPersons_throwsInvalidTeamSizeException() {
        InvalidTeamSizeException exception = assertThrows(
                InvalidTeamSizeException.class, () -> new Team(INVALID_PERSONS_SIZE_FOUR));
        assertTrue(exception.getMessage().contains("4"));
    }

    @Test
    public void constructor_sixPersons_throwsInvalidTeamSizeException() {
        InvalidTeamSizeException exception = assertThrows(
                InvalidTeamSizeException.class, () -> new Team(INVALID_PERSONS_SIZE_SIX));
        assertTrue(exception.getMessage().contains("6"));
    }

    @Test
    public void constructor_duplicateRole_throwsDuplicateRoleException() {
        DuplicateRoleException exception = assertThrows(
                DuplicateRoleException.class, () -> new Team(INVALID_PERSONS_DUPLICATE_ROLE));

        // Check that exception contains information about the conflicting persons
        assertNotNull(exception.getPerson1());
        assertNotNull(exception.getPerson2());
        assertEquals(exception.getPerson1().getRole(), exception.getPerson2().getRole());
        assertTrue(exception.getMessage().contains("Top"));
    }

    @Test
    public void constructor_duplicateChampion_throwsDuplicateChampionException() {
        DuplicateChampionException exception = assertThrows(
                DuplicateChampionException.class, () -> new Team(INVALID_PERSONS_DUPLICATE_CHAMPIONS));

        // Check that exception contains information about the conflicting persons
        assertNotNull(exception.getPerson1());
        assertNotNull(exception.getPerson2());
        assertEquals(exception.getPerson1().getChampion(), exception.getPerson2().getChampion());
        assertTrue(exception.getMessage().contains("Ahri"));
    }

    @Test
    public void hasPerson_existingPerson_returnsTrue() {
        Team team = new Team(VALID_PERSONS);
        assertTrue(team.hasPerson(TOP_PERSON));
        assertTrue(team.hasPerson(JUNGLE_PERSON));
        assertTrue(team.hasPerson(MID_PERSON));
        assertTrue(team.hasPerson(ADC_PERSON));
        assertTrue(team.hasPerson(SUPPORT_PERSON));
    }

    @Test
    public void hasPerson_nonExistingPerson_returnsFalse() {
        Team team = new Team(VALID_PERSONS);
        Person nonExistingPerson = new PersonBuilder().withName("Non Existing")
                .withRole("top").withChampion("Darius").withRank("bronze").build();
        assertFalse(team.hasPerson(nonExistingPerson));
    }

    @Test
    public void getPersons_modifyReturnedList_teamUnchanged() {
        Team team = new Team(VALID_PERSONS);
        List<Person> returnedList = team.getPersons();

        // Modify the returned list
        Person newPerson = new PersonBuilder().withName("New Person")
                .withRole("top").withChampion("Darius").build();
        returnedList.add(newPerson);

        // Original team should be unchanged
        assertEquals(5, team.getPersons().size());
        assertFalse(team.hasPerson(newPerson));
    }

    @Test
    public void equals_sameTeam_returnsTrue() {
        Team team = new Team(DUMMY_ID, VALID_PERSONS, DUMMY_WINS, DUMMY_LOSSES);
        Team otherTeam = new Team(DUMMY_ID, VALID_PERSONS, DUMMY_WINS, DUMMY_LOSSES);
        assertTrue(team.equals(otherTeam));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Team team = new Team(DUMMY_ID, VALID_PERSONS, DUMMY_WINS, DUMMY_LOSSES);
        assertTrue(team.equals(team));
    }

    @Test
    public void equals_null_returnsFalse() {
        Team team = new Team(DUMMY_ID, VALID_PERSONS, DUMMY_WINS, DUMMY_LOSSES);
        assertFalse(team.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Team team = new Team(DUMMY_ID, VALID_PERSONS, DUMMY_WINS, DUMMY_LOSSES);
        assertFalse(team.equals("string"));
    }

    @Test
    public void equals_differentPersons_returnsFalse() {
        List<Person> differentPersons = Arrays.asList(
                JUNGLE_PERSON, TOP_PERSON, MID_PERSON, ADC_PERSON, SUPPORT_PERSON); // Different order
        Team team = new Team(DUMMY_ID, VALID_PERSONS, DUMMY_WINS, DUMMY_LOSSES);
        Team otherTeam = new Team(DUMMY_ID, differentPersons, DUMMY_WINS, DUMMY_LOSSES);
        assertFalse(team.equals(otherTeam));
    }

    @Test
    public void hashCode_sameTeam_sameHashCode() {
        Team team = new Team(DUMMY_ID, VALID_PERSONS, DUMMY_WINS, DUMMY_LOSSES);
        Team otherTeam = new Team(DUMMY_ID, VALID_PERSONS, DUMMY_WINS, DUMMY_LOSSES);
        assertEquals(team.hashCode(), otherTeam.hashCode());
    }

    /*
    @Test
    public void toString_validTeam_correctFormat() {
        Team team = new Team(DUMMY_ID, VALID_PERSONS, DUMMY_WINS, DUMMY_LOSSES);
        String result = team.toString();

        assertTrue(result.contains("persons"));
    }
     */

    @Test
    public void teamSize_constantValue() {
        assertEquals(5, Team.TEAM_SIZE);
    }
}
