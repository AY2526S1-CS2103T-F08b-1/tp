package seedu.summoners.model.team;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.exceptions.DuplicateChampionException;
import seedu.summoners.model.team.exceptions.DuplicateRoleException;
import seedu.summoners.model.team.exceptions.InvalidTeamSizeException;
import seedu.summoners.testutil.PlayerBuilder;

public class TeamTest {
    private static final String DUMMY_ID = "";
    private static final int DUMMY_WINS = 0;
    private static final int DUMMY_LOSSES = 0;

    // Valid team with 5 players: unique roles and unique champions
    private static final Player TOP_PLAYER = new PlayerBuilder().withName("Top Player")
            .withRole("top").withChampion("Garen").withRank("gold").build();
    private static final Player JUNGLE_PLAYER = new PlayerBuilder().withName("Jungle Player")
            .withRole("jungle").withChampion("Lee Sin").withRank("platinum").build();
    private static final Player MID_PLAYER = new PlayerBuilder().withName("Mid Player")
            .withRole("mid").withChampion("Ahri").withRank("diamond").build();
    private static final Player ADC_PLAYER = new PlayerBuilder().withName("ADC Player")
            .withRole("adc").withChampion("Jinx").withRank("gold").build();
    private static final Player SUPPORT_PLAYER = new PlayerBuilder().withName("Support Player")
            .withRole("support").withChampion("Thresh").withRank("platinum").build();
    private static final Player EXTRA_PLAYER = new PlayerBuilder().withName("Extra Player")
            .withRole("top").withChampion("Ahri").withRank("master").build();

    private static final List<Player> VALID_PLAYERS = Arrays.asList(
            TOP_PLAYER, JUNGLE_PLAYER, MID_PLAYER, ADC_PLAYER, SUPPORT_PLAYER);

    // Invalid: Only 4 players
    private static final List<Player> INVALID_PLAYERS_SIZE_FOUR = Arrays.asList(
            TOP_PLAYER, JUNGLE_PLAYER, MID_PLAYER, ADC_PLAYER);

    // Invalid: 6 players
    private static final List<Player> INVALID_PLAYERS_SIZE_SIX = Arrays.asList(
            TOP_PLAYER, JUNGLE_PLAYER, MID_PLAYER, ADC_PLAYER, SUPPORT_PLAYER, EXTRA_PLAYER);

    // Invalid: Duplicate role (top)
    private static final List<Player> INVALID_PLAYERS_DUPLICATE_ROLE = Arrays.asList(
            TOP_PLAYER, EXTRA_PLAYER, MID_PLAYER, ADC_PLAYER, SUPPORT_PLAYER);

    // Invalid: Duplicate champion (Ahri)
    private static final List<Player> INVALID_PLAYERS_DUPLICATE_CHAMPIONS = Arrays.asList(
            EXTRA_PLAYER, JUNGLE_PLAYER, MID_PLAYER, ADC_PLAYER, SUPPORT_PLAYER);

    @Test
    public void constructor_validPlayers_success() {
        Team team = new Team(VALID_PLAYERS);
        assertEquals(VALID_PLAYERS, team.getPlayers());
        assertNotNull(team.getId());
    }

    @Test
    public void constructor_validPlayersWithIdWithWinsWithLosses_success() {
        Team team = new Team(DUMMY_ID, VALID_PLAYERS, DUMMY_WINS, DUMMY_LOSSES);
        assertEquals(VALID_PLAYERS, team.getPlayers());
        assertEquals(DUMMY_ID, team.getId());
        assertEquals(DUMMY_WINS, team.getWins());
        assertEquals(DUMMY_LOSSES, team.getLosses());
    }

    @Test
    public void constructor_nullPlayers_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Team(null));
        assertThrows(NullPointerException.class, () -> new Team(DUMMY_ID, null, DUMMY_WINS, DUMMY_LOSSES));
    }

    @Test
    public void constructor_nullId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Team(null, VALID_PLAYERS, DUMMY_WINS, DUMMY_LOSSES));
    }

    @Test
    public void constructor_fourPlayers_throwsInvalidTeamSizeException() {
        InvalidTeamSizeException exception = assertThrows(
                InvalidTeamSizeException.class, () -> new Team(INVALID_PLAYERS_SIZE_FOUR));
        assertTrue(exception.getMessage().contains("4"));
    }

    @Test
    public void constructor_sixPlayers_throwsInvalidTeamSizeException() {
        InvalidTeamSizeException exception = assertThrows(
                InvalidTeamSizeException.class, () -> new Team(INVALID_PLAYERS_SIZE_SIX));
        assertTrue(exception.getMessage().contains("6"));
    }

    @Test
    public void constructor_duplicateRole_throwsDuplicateRoleException() {
        DuplicateRoleException exception = assertThrows(
                DuplicateRoleException.class, () -> new Team(INVALID_PLAYERS_DUPLICATE_ROLE));

        // Check that exception contains information about the conflicting players
        assertNotNull(exception.getPlayer1());
        assertNotNull(exception.getPlayer2());
        assertEquals(exception.getPlayer1().getRole(), exception.getPlayer2().getRole());
        assertTrue(exception.getMessage().contains("Top"));
    }

    @Test
    public void constructor_duplicateChampion_throwsDuplicateChampionException() {
        DuplicateChampionException exception = assertThrows(
                DuplicateChampionException.class, () -> new Team(INVALID_PLAYERS_DUPLICATE_CHAMPIONS));

        // Check that exception contains information about the conflicting players
        assertNotNull(exception.getPlayer1());
        assertNotNull(exception.getPlayer2());
        assertEquals(exception.getPlayer1().getChampion(), exception.getPlayer2().getChampion());
        assertTrue(exception.getMessage().contains("Ahri"));
    }

    @Test
    public void hasPlayer_existingPlayer_returnsTrue() {
        Team team = new Team(VALID_PLAYERS);
        assertTrue(team.hasPlayer(TOP_PLAYER));
        assertTrue(team.hasPlayer(JUNGLE_PLAYER));
        assertTrue(team.hasPlayer(MID_PLAYER));
        assertTrue(team.hasPlayer(ADC_PLAYER));
        assertTrue(team.hasPlayer(SUPPORT_PLAYER));
    }

    @Test
    public void hasPlayer_nonExistingPlayer_returnsFalse() {
        Team team = new Team(VALID_PLAYERS);
        Player nonExistingPlayer = new PlayerBuilder().withName("Non Existing")
                .withRole("top").withChampion("Darius").withRank("bronze").build();
        assertFalse(team.hasPlayer(nonExistingPlayer));
    }

    @Test
    public void getPlayers_modifyReturnedList_teamUnchanged() {
        Team team = new Team(VALID_PLAYERS);
        List<Player> returnedList = team.getPlayers();

        // Modify the returned list
        Player newPlayer = new PlayerBuilder().withName("New Player")
                .withRole("top").withChampion("Darius").build();
        returnedList.add(newPlayer);

        // Original team should be unchanged
        assertEquals(5, team.getPlayers().size());
        assertFalse(team.hasPlayer(newPlayer));
    }

    @Test
    public void equals_sameTeam_returnsTrue() {
        Team team = new Team(DUMMY_ID, VALID_PLAYERS, DUMMY_WINS, DUMMY_LOSSES);
        Team otherTeam = new Team(DUMMY_ID, VALID_PLAYERS, DUMMY_WINS, DUMMY_LOSSES);
        assertTrue(team.equals(otherTeam));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Team team = new Team(DUMMY_ID, VALID_PLAYERS, DUMMY_WINS, DUMMY_LOSSES);
        assertTrue(team.equals(team));
    }

    @Test
    public void equals_null_returnsFalse() {
        Team team = new Team(DUMMY_ID, VALID_PLAYERS, DUMMY_WINS, DUMMY_LOSSES);
        assertFalse(team.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Team team = new Team(DUMMY_ID, VALID_PLAYERS, DUMMY_WINS, DUMMY_LOSSES);
        assertFalse(team.equals("string"));
    }

    @Test
    public void equals_differentPlayers_returnsFalse() {
        List<Player> differentPlayers = Arrays.asList(
                JUNGLE_PLAYER, TOP_PLAYER, MID_PLAYER, ADC_PLAYER, SUPPORT_PLAYER); // Different order
        Team team = new Team(DUMMY_ID, VALID_PLAYERS, DUMMY_WINS, DUMMY_LOSSES);
        Team otherTeam = new Team(DUMMY_ID, differentPlayers, DUMMY_WINS, DUMMY_LOSSES);
        assertFalse(team.equals(otherTeam));
    }

    @Test
    public void hashCode_sameTeam_sameHashCode() {
        Team team = new Team(DUMMY_ID, VALID_PLAYERS, DUMMY_WINS, DUMMY_LOSSES);
        Team otherTeam = new Team(DUMMY_ID, VALID_PLAYERS, DUMMY_WINS, DUMMY_LOSSES);
        assertEquals(team.hashCode(), otherTeam.hashCode());
    }

    /*
    @Test
    public void toString_validTeam_correctFormat() {
        Team team = new Team(DUMMY_ID, VALID_PLAYERS, DUMMY_WINS, DUMMY_LOSSES);
        String result = team.toString();

        assertTrue(result.contains("players"));
    }
     */

    @Test
    public void teamSize_constantValue() {
        assertEquals(5, Team.TEAM_SIZE);
    }
}
