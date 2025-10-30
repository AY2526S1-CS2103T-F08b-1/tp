package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_returnsNonEmptyArray() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        assertTrue(samplePersons.length > 0);
    }

    @Test
    public void getSampleAddressBook_returnsValidAddressBook() {
        ReadOnlyAddressBook sampleAb = SampleDataUtil.getSampleAddressBook();
        assertFalse(sampleAb.getPersonList().isEmpty());
        assertFalse(sampleAb.getTeamList().isEmpty());
    }
}
