package seedu.summoners.model.team.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.summoners.model.player.Player;
import seedu.summoners.testutil.PlayerBuilder;

public class DuplicateRoleExceptionTest {

    @Test
    public void constructor_validPlayers_success() {
        Player player1 = new PlayerBuilder().withName("Alice")
                .withRole("mid").withChampion("Ahri").build();
        Player player2 = new PlayerBuilder().withName("Bob")
                .withRole("mid").withChampion("Zed").build();

        DuplicateRoleException exception = new DuplicateRoleException(player1, player2);

        assertEquals(player1, exception.getPlayer1());
        assertEquals(player2, exception.getPlayer2());
        assertTrue(exception.getMessage().contains("Mid"));
    }
}
