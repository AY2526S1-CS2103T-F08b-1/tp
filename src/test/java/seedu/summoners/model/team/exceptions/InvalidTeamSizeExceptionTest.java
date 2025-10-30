package seedu.address.model.team.exceptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class InvalidTeamSizeExceptionTest {

    @Test
    public void constructor_noArgs_success() {
        InvalidTeamSizeException exception = new InvalidTeamSizeException();
        assertTrue(exception.getMessage().contains("5 persons"));
    }

    @Test
    public void constructor_withSize_success() {
        InvalidTeamSizeException exception = new InvalidTeamSizeException(3);
        assertTrue(exception.getMessage().contains("3"));
    }
}
