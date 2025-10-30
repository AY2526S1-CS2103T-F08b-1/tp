package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RankTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Rank(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Rank(invalidName));
    }

    @Test
    public void isValidRank() {
        // invalid name
        assertFalse(Rank.isValidRank("")); // empty string
        assertFalse(Rank.isValidRank(" ")); // spaces only
        assertFalse(Rank.isValidRank("peter")); // wrong name

        // valid name
        assertTrue(Rank.isValidRank("challenger")); // alphanumeric name

    }

    @Test
    public void equals() {
        Rank rank = new Rank("gold");

        // same values -> returns true
        assertTrue(rank.equals(new Rank("gold")));

        // same object -> returns true
        assertTrue(rank.equals(rank));

        // null -> returns false
        assertFalse(rank.equals(null));

        // different types -> returns false
        assertFalse(rank.equals(5.0f));

        // different values -> returns false
        assertFalse(rank.equals(new Rank("silver")));
    }

    @Test
    public void compareTo() {
        Rank iron = new Rank("iron");
        Rank bronze = new Rank("bronze");
        Rank silver = new Rank("silver");
        Rank gold = new Rank("gold");
        Rank platinum = new Rank("platinum");
        Rank emerald = new Rank("emerald");
        Rank diamond = new Rank("diamond");
        Rank master = new Rank("master");
        Rank grandmaster = new Rank("grandmaster");
        Rank challenger = new Rank("challenger");

        // Test ascending order
        assertTrue(iron.compareTo(bronze) < 0);
        assertTrue(bronze.compareTo(silver) < 0);
        assertTrue(silver.compareTo(gold) < 0);
        assertTrue(gold.compareTo(platinum) < 0);
        assertTrue(platinum.compareTo(emerald) < 0);
        assertTrue(emerald.compareTo(diamond) < 0);
        assertTrue(diamond.compareTo(master) < 0);
        assertTrue(master.compareTo(grandmaster) < 0);
        assertTrue(grandmaster.compareTo(challenger) < 0);

        // Test descending order
        assertTrue(challenger.compareTo(grandmaster) > 0);
        assertTrue(grandmaster.compareTo(master) > 0);
        assertTrue(master.compareTo(diamond) > 0);

        // Test equality
        assertEquals(0, gold.compareTo(new Rank("gold")));
        assertEquals(0, challenger.compareTo(new Rank("challenger")));
    }
}
