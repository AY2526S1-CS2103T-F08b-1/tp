package seedu.summoners.model.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_CHAMPION_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_RANK_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.summoners.testutil.Assert.assertThrows;
import static seedu.summoners.testutil.TypicalPlayers.ALICE;
import static seedu.summoners.testutil.TypicalPlayers.BOB;

import org.junit.jupiter.api.Test;

import seedu.summoners.testutil.PlayerBuilder;

public class PlayerTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Player player = new PlayerBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> player.getTags().remove(0));
    }

    @Test
    public void isSamePlayer() {
        // same object -> returns true
        assertTrue(ALICE.isSamePlayer(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePlayer(null));

        // same name, all other attributes different -> returns true
        Player editedAlice = new PlayerBuilder(ALICE).withRole(VALID_ROLE_BOB).withRank(VALID_RANK_BOB)
                .withChampion(VALID_CHAMPION_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePlayer(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PlayerBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePlayer(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Player editedBob = new PlayerBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSamePlayer(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PlayerBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePlayer(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Player aliceCopy = new PlayerBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different player -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Player editedAlice = new PlayerBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different role -> returns false
        editedAlice = new PlayerBuilder(ALICE).withRole(VALID_ROLE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different rank -> returns false
        editedAlice = new PlayerBuilder(ALICE).withRank(VALID_RANK_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different champion -> returns false
        editedAlice = new PlayerBuilder(ALICE).withChampion(VALID_CHAMPION_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PlayerBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Player.class.getCanonicalName() + "{name=" + ALICE.getName() + ", role=" + ALICE.getRole()
                + ", rank=" + ALICE.getRank() + ", champion=" + ALICE.getChampion() + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
