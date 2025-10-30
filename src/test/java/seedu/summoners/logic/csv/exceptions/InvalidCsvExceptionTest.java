package seedu.address.logic.csv.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class InvalidCsvExceptionTest {

    @Test
    public void message_constructor_setsMessage() {
        InvalidCsvException ex = new InvalidCsvException("bad csv");
        assertEquals("bad csv", ex.getMessage());
    }

    @Test
    public void noArg_constructor_hasNullMessage() {
        InvalidCsvException ex = new InvalidCsvException();
        // getMessage() may be null; just ensure no crash
        assertNull(ex.getMessage());
    }
}

