package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTeams.TEAM_A;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalTeams;

public class JsonAdaptedTeamTest {
    private static final List<Person> ALL_PERSONS = TypicalPersons.getTypicalPersons();
    private static final List<Team> ALL_TEAMS = TypicalTeams.getTypicalTeams();

    private static final String DUMMY_ID = "";
    private static final int DUMMY_WINS = 0;
    private static final int DUMMY_LOSSES = 0;

    // Only invalid as it does not match any of the team's person's IDs
    private static final String INVALID_PERSON_ID = "invalid-id-123";

    @Test
    public void toModelType_validTeamDetails_returnsTeam() throws Exception {
        JsonAdaptedTeam team = new JsonAdaptedTeam(TEAM_A);
        assertEquals(TEAM_A, team.toModelType(ALL_PERSONS));
    }

    @Test
    public void toModelType_invalidPersonId_throwsIllegalValueException() {
        List<String> invalidPersonIds = TEAM_A.getPersons().stream()
                .map(Person::getId)
                .collect(Collectors.toList());
        invalidPersonIds.add(INVALID_PERSON_ID);

        JsonAdaptedTeam team = new JsonAdaptedTeam(DUMMY_ID, invalidPersonIds, DUMMY_WINS, DUMMY_LOSSES);
        String expectedMessage = "Invalid Person ID in Team: " + INVALID_PERSON_ID;
        assertThrows(IllegalValueException.class, expectedMessage, () -> team.toModelType(ALL_PERSONS));
    }
}
