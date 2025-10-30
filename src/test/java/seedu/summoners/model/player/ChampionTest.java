package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ChampionTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Champion(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Champion(invalidName));
    }

    @Test
    public void isValidChampion() {
        // null name
        assertThrows(NullPointerException.class, () -> Champion.isValidChampion(null));

        // invalid name
        assertFalse(Champion.isValidChampion("")); // empty string
        assertFalse(Champion.isValidChampion(" ")); // spaces only
        assertFalse(Champion.isValidChampion("peter")); // wrong name

        // valid name
        assertTrue(Champion.isValidChampion("sion")); // alphanumeric name
        assertTrue(Champion.isValidChampion("kha'zix")); // name contains special characters

    }

    @Test
    public void equals() {
        Champion champion = new Champion("azir");

        // same values -> returns true
        assertTrue(champion.equals(new Champion("azir")));

        // same object -> returns true
        assertTrue(champion.equals(champion));

        // null -> returns false
        assertFalse(champion.equals(null));

        // different types -> returns false
        assertFalse(champion.equals(5.0f));

        // different values -> returns false
        assertFalse(champion.equals(new Champion("yasuo")));
    }
}
