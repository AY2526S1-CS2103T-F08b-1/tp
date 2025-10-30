package seedu.summoners.testutil;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.summoners.model.SummonersBook;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.Team;

/**
 * A utility class containing a list of {@code Team} objects to be used in tests.
 */
public class TypicalTeams {
    public static final Team TEAM_A = new TeamBuilder().withPlayers(
            ALICE, BENSON, CARL, DANIEL, ELLE).build();

    public static final Team TEAM_B = new TeamBuilder().withPlayers(
            FIONA, GEORGE, HOON, IDA, JAMES).build();

    private static final List<Player> TEAM_A_ROSTER = new ArrayList<>(Arrays.asList(
            ALICE, BENSON, CARL, DANIEL, ELLE));

    private static final List<Player> TEAM_B_ROSTER = new ArrayList<>(Arrays.asList(
            FIONA, GEORGE, HOON, IDA, JAMES));


    private TypicalTeams() {} // prevents instantiation

    /**
     * Returns an {@code SummonersBook} with all the typical players and a typical team.
     * This is useful for integration tests.
     */
    public static SummonersBook getTypicalSummonersBookWithTeams() {
        SummonersBook ab = new SummonersBook();

        // Add the players to the summoners book first
        for (Player player : TEAM_A_ROSTER) {
            ab.addPlayer(player);
        }
        for (Player player : TEAM_B_ROSTER) {
            ab.addPlayer(player);
        }
        // Then add the teams
        ab.addTeam(TEAM_A);
        ab.addTeam(TEAM_B);
        return ab;
    }

    /**
     * Returns a list of typical teams.
     */
    public static List<Team> getTypicalTeams() {
        return new ArrayList<>(Arrays.asList(TEAM_A, TEAM_B));
    }
}
