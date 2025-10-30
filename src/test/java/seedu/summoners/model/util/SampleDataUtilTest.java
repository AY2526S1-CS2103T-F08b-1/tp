package seedu.summoners.model.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.summoners.model.ReadOnlySummonersBook;
import seedu.summoners.model.player.Player;

public class SampleDataUtilTest {

    @Test
    public void getSamplePlayers_returnsNonEmptyArray() {
        Player[] samplePlayers = SampleDataUtil.getSamplePlayers();
        assertTrue(samplePlayers.length > 0);
    }

    @Test
    public void getSampleSummonersBook_returnsValidSummonersBook() {
        ReadOnlySummonersBook sampleAb = SampleDataUtil.getSampleSummonersBook();
        assertFalse(sampleAb.getPlayerList().isEmpty());
        assertFalse(sampleAb.getTeamList().isEmpty());
    }
}
