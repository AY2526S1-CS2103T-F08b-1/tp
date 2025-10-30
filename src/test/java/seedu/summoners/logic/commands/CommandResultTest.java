package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalTeams.TEAM_A;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true)));

        // different showPersonDetail value -> returns false
        Person person = ALICE;
        CommandResult personDetailResult = new CommandResult("feedback", person);
        assertFalse(commandResult.equals(personDetailResult));

        // different showTeamStats value -> returns false
        Team team = TEAM_A;
        CommandResult teamStatsResult = CommandResult.showTeamStats("feedback", team);
        assertFalse(commandResult.equals(teamStatsResult));

        // same person detail values -> returns true
        CommandResult personDetailResult1 = new CommandResult("message", person);
        CommandResult personDetailResult2 = new CommandResult("message", person);
        assertTrue(personDetailResult1.equals(personDetailResult2));

        // different person -> returns false
        CommandResult personDetailResultNull = new CommandResult("message", (Person) null);
        assertFalse(personDetailResult1.equals(personDetailResultNull));

        // same team stats values -> returns true
        CommandResult teamStatsResult1 = CommandResult.showTeamStats("team msg", team);
        CommandResult teamStatsResult2 = CommandResult.showTeamStats("team msg", team);
        assertTrue(teamStatsResult1.equals(teamStatsResult2));

        // different team -> returns false
        CommandResult teamStatsResultDiff = CommandResult.showTeamStats("different msg", team);
        assertFalse(teamStatsResult1.equals(teamStatsResultDiff));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true).hashCode());

        // different showPersonDetail value -> returns different hashcode
        Person person = ALICE;
        CommandResult personDetailResult = new CommandResult("feedback", person);
        assertNotEquals(commandResult.hashCode(), personDetailResult.hashCode());

        // different showTeamStats value -> returns different hashcode
        Team team = TEAM_A;
        CommandResult teamStatsResult = CommandResult.showTeamStats("feedback", team);
        assertNotEquals(commandResult.hashCode(), teamStatsResult.hashCode());

        // same person detail values -> returns same hashcode
        CommandResult personDetailResult1 = new CommandResult("message", person);
        CommandResult personDetailResult2 = new CommandResult("message", person);
        assertEquals(personDetailResult1.hashCode(), personDetailResult2.hashCode());

        // same team stats values -> returns same hashcode
        CommandResult teamStatsResult1 = CommandResult.showTeamStats("team msg", team);
        CommandResult teamStatsResult2 = CommandResult.showTeamStats("team msg", team);
        assertEquals(teamStatsResult1.hashCode(), teamStatsResult2.hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName()
                + "{feedbackToUser=" + commandResult.getFeedbackToUser()
                + ", showHelp=" + commandResult.isShowHelp()
                + ", exit=" + commandResult.isExit()
                + ", showPersonDetail=" + commandResult.isShowPersonDetail()
                + ", personToShow=" + commandResult.getPersonToShow().orElse(null)
                + ", showTeamStats=" + commandResult.isShowTeamStats()
                + ", teamToShow=" + commandResult.getTeamToShow().orElse(null) + "}";
        assertEquals(expected, commandResult.toString());
    }

    @Test
    public void constructor_singleParameter_success() {
        CommandResult result = new CommandResult("feedback");

        assertEquals("feedback", result.getFeedbackToUser());
        assertFalse(result.isShowHelp());
        assertFalse(result.isExit());
        assertFalse(result.isShowPersonDetail());
        assertFalse(result.getPersonToShow().isPresent());
        assertFalse(result.isShowTeamStats());
        assertFalse(result.getTeamToShow().isPresent());
    }

    @Test
    public void constructor_threeParameters_success() {
        CommandResult helpResult = new CommandResult("help", true, false);
        CommandResult exitResult = new CommandResult("exit", false, true);

        // test help result
        assertEquals("help", helpResult.getFeedbackToUser());
        assertTrue(helpResult.isShowHelp());
        assertFalse(helpResult.isExit());
        assertFalse(helpResult.isShowPersonDetail());
        assertFalse(helpResult.isShowTeamStats());

        // test exit result
        assertEquals("exit", exitResult.getFeedbackToUser());
        assertFalse(exitResult.isShowHelp());
        assertTrue(exitResult.isExit());
        assertFalse(exitResult.isShowPersonDetail());
        assertFalse(exitResult.isShowTeamStats());
    }

    @Test
    public void constructor_personDetail_success() {
        Person person = ALICE;
        CommandResult result = new CommandResult("Viewing person", person);

        assertEquals("Viewing person", result.getFeedbackToUser());
        assertTrue(result.isShowPersonDetail());
        assertTrue(result.getPersonToShow().isPresent());
        assertEquals(person, result.getPersonToShow().get());
        assertFalse(result.isShowHelp());
        assertFalse(result.isExit());
        assertFalse(result.isShowTeamStats());
    }

    @Test
    public void getters_allFields_success() {
        Person person = ALICE;
        Team team = TEAM_A;

        // test with person detail
        CommandResult personResult = new CommandResult("message", person);
        assertEquals("message", personResult.getFeedbackToUser());
        assertTrue(personResult.isShowPersonDetail());
        assertEquals(person, personResult.getPersonToShow().get());

        // test with team stats
        CommandResult teamResult = CommandResult.showTeamStats("team message", team);
        assertEquals("team message", teamResult.getFeedbackToUser());
        assertTrue(teamResult.isShowTeamStats());
        assertEquals(team, teamResult.getTeamToShow().get());

        // test with help
        CommandResult helpResult = new CommandResult("help", true, false);
        assertTrue(helpResult.isShowHelp());

        // test with exit
        CommandResult exitResult = new CommandResult("exit", false, true);
        assertTrue(exitResult.isExit());
    }

    @Test
    public void equals_personDetail_success() {
        Person person = ALICE;
        CommandResult result1 = new CommandResult("message", person);
        CommandResult result2 = new CommandResult("message", person);

        // same values -> returns true
        assertEquals(result1, result2);

        // different person -> returns false
        CommandResult result3 = new CommandResult("message", (Person) null);
        assertNotEquals(result1, result3);

        // different message -> returns false
        CommandResult result4 = new CommandResult("different", person);
        assertNotEquals(result1, result4);
    }

    @Test
    public void hashCode_personDetail_success() {
        Person person = ALICE;
        CommandResult result1 = new CommandResult("message", person);
        CommandResult result2 = new CommandResult("message", person);

        // same values -> same hashcode
        assertEquals(result1.hashCode(), result2.hashCode());

        // different person -> different hashcode
        CommandResult result3 = new CommandResult("message", (Person) null);
        assertNotEquals(result1.hashCode(), result3.hashCode());
    }

    @Test
    public void showTeamStats_success() {
        Team team = TEAM_A;
        CommandResult result = CommandResult.showTeamStats("Viewing team stats", team);

        // verify showTeamStats flag is set
        assertTrue(result.isShowTeamStats());

        // verify team is present
        assertTrue(result.getTeamToShow().isPresent());
        assertEquals(team, result.getTeamToShow().get());

        // verify other flags are not set
        assertFalse(result.isShowHelp());
        assertFalse(result.isExit());
        assertFalse(result.isShowPersonDetail());

        // verify feedback message
        assertEquals("Viewing team stats", result.getFeedbackToUser());
    }

    @Test
    public void showTeamStats_equality() {
        Team team = TEAM_A;
        CommandResult result1 = CommandResult.showTeamStats("message", team);
        CommandResult result2 = CommandResult.showTeamStats("message", team);

        // same values -> returns true
        assertEquals(result1, result2);

        // same values -> returns same hashcode
        assertEquals(result1.hashCode(), result2.hashCode());

        // different message -> returns false
        CommandResult result3 = CommandResult.showTeamStats("different", team);
        assertNotEquals(result1, result3);
    }
}
