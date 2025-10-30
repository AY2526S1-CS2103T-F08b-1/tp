package seedu.summoners.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.summoners.testutil.Assert.assertThrows;
import static seedu.summoners.testutil.TypicalTeams.TEAM_A;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.summoners.commons.exceptions.IllegalValueException;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.Team;
import seedu.summoners.testutil.TypicalPlayers;
import seedu.summoners.testutil.TypicalTeams;

public class JsonAdaptedTeamTest {
    private static final List<Player> ALL_PLAYERS = TypicalPlayers.getTypicalPlayers();
    private static final List<Team> ALL_TEAMS = TypicalTeams.getTypicalTeams();

    private static final String DUMMY_ID = "";
    private static final int DUMMY_WINS = 0;
    private static final int DUMMY_LOSSES = 0;

    // Only invalid as it does not match any of the team's player's IDs
    private static final String INVALID_PLAYER_ID = "invalid-id-123";

    @Test
    public void toModelType_validTeamDetails_returnsTeam() throws Exception {
        JsonAdaptedTeam team = new JsonAdaptedTeam(TEAM_A);
        assertEquals(TEAM_A, team.toModelType(ALL_PLAYERS));
    }

    @Test
    public void toModelType_invalidPlayerId_throwsIllegalValueException() {
        List<String> invalidPlayerIds = TEAM_A.getPlayers().stream()
                .map(Player::getId)
                .collect(Collectors.toList());
        invalidPlayerIds.add(INVALID_PLAYER_ID);

        JsonAdaptedTeam team = new JsonAdaptedTeam(DUMMY_ID, invalidPlayerIds, DUMMY_WINS, DUMMY_LOSSES);
        String expectedMessage = "Invalid Player ID in Team: " + INVALID_PLAYER_ID;
        assertThrows(IllegalValueException.class, expectedMessage, () -> team.toModelType(ALL_PLAYERS));
    }
}
