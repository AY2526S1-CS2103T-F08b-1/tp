package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidName));
    }

    @Test
    public void isValidRole() {
        // invalid name
        assertFalse(Role.isValidRole("")); // empty string
        assertFalse(Role.isValidRole(" ")); // spaces only
        assertFalse(Role.isValidRole("peter")); // wrong name

        // valid name
        assertTrue(Role.isValidRole("top")); // alphanumeric name

    }

    @Test
    public void equals() {
        Role role = new Role("top");

        // same values -> returns true
        assertTrue(role.equals(new Role("top")));

        // same object -> returns true
        assertTrue(role.equals(role));

        // null -> returns false
        assertFalse(role.equals(null));

        // different types -> returns false
        assertFalse(role.equals(5.0f));

        // different values -> returns false
        assertFalse(role.equals(new Role("mid")));
    }
}
