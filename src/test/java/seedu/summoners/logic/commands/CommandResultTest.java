package seedu.summoners.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.summoners.testutil.TypicalPlayers.ALICE;
import static seedu.summoners.testutil.TypicalTeams.TEAM_A;

import org.junit.jupiter.api.Test;

import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.Team;

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

        // different showPlayerDetail value -> returns false
        Player player = ALICE;
        CommandResult playerDetailResult = new CommandResult("feedback", player);
        assertFalse(commandResult.equals(playerDetailResult));

        // different showTeamStats value -> returns false
        Team team = TEAM_A;
        CommandResult teamStatsResult = CommandResult.showTeamStats("feedback", team);
        assertFalse(commandResult.equals(teamStatsResult));

        // same player detail values -> returns true
        CommandResult playerDetailResult1 = new CommandResult("message", player);
        CommandResult playerDetailResult2 = new CommandResult("message", player);
        assertTrue(playerDetailResult1.equals(playerDetailResult2));

        // different player -> returns false
        CommandResult playerDetailResultNull = new CommandResult("message", (Player) null);
        assertFalse(playerDetailResult1.equals(playerDetailResultNull));

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

        // different showPlayerDetail value -> returns different hashcode
        Player player = ALICE;
        CommandResult playerDetailResult = new CommandResult("feedback", player);
        assertNotEquals(commandResult.hashCode(), playerDetailResult.hashCode());

        // different showTeamStats value -> returns different hashcode
        Team team = TEAM_A;
        CommandResult teamStatsResult = CommandResult.showTeamStats("feedback", team);
        assertNotEquals(commandResult.hashCode(), teamStatsResult.hashCode());

        // same player detail values -> returns same hashcode
        CommandResult playerDetailResult1 = new CommandResult("message", player);
        CommandResult playerDetailResult2 = new CommandResult("message", player);
        assertEquals(playerDetailResult1.hashCode(), playerDetailResult2.hashCode());

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
                + ", showPlayerDetail=" + commandResult.isShowPlayerDetail()
                + ", playerToShow=" + commandResult.getPlayerToShow().orElse(null)
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
        assertFalse(result.isShowPlayerDetail());
        assertFalse(result.getPlayerToShow().isPresent());
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
        assertFalse(helpResult.isShowPlayerDetail());
        assertFalse(helpResult.isShowTeamStats());

        // test exit result
        assertEquals("exit", exitResult.getFeedbackToUser());
        assertFalse(exitResult.isShowHelp());
        assertTrue(exitResult.isExit());
        assertFalse(exitResult.isShowPlayerDetail());
        assertFalse(exitResult.isShowTeamStats());
    }

    @Test
    public void constructor_playerDetail_success() {
        Player player = ALICE;
        CommandResult result = new CommandResult("Viewing player", player);

        assertEquals("Viewing player", result.getFeedbackToUser());
        assertTrue(result.isShowPlayerDetail());
        assertTrue(result.getPlayerToShow().isPresent());
        assertEquals(player, result.getPlayerToShow().get());
        assertFalse(result.isShowHelp());
        assertFalse(result.isExit());
        assertFalse(result.isShowTeamStats());
    }

    @Test
    public void getters_allFields_success() {
        Player player = ALICE;
        Team team = TEAM_A;

        // test with player detail
        CommandResult playerResult = new CommandResult("message", player);
        assertEquals("message", playerResult.getFeedbackToUser());
        assertTrue(playerResult.isShowPlayerDetail());
        assertEquals(player, playerResult.getPlayerToShow().get());

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
    public void equals_playerDetail_success() {
        Player player = ALICE;
        CommandResult result1 = new CommandResult("message", player);
        CommandResult result2 = new CommandResult("message", player);

        // same values -> returns true
        assertEquals(result1, result2);

        // different player -> returns false
        CommandResult result3 = new CommandResult("message", (Player) null);
        assertNotEquals(result1, result3);

        // different message -> returns false
        CommandResult result4 = new CommandResult("different", player);
        assertNotEquals(result1, result4);
    }

    @Test
    public void hashCode_playerDetail_success() {
        Player player = ALICE;
        CommandResult result1 = new CommandResult("message", player);
        CommandResult result2 = new CommandResult("message", player);

        // same values -> same hashcode
        assertEquals(result1.hashCode(), result2.hashCode());

        // different player -> different hashcode
        CommandResult result3 = new CommandResult("message", (Player) null);
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
        assertFalse(result.isShowPlayerDetail());

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
