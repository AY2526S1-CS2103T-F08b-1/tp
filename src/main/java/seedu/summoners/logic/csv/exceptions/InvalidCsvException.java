package seedu.address.logic.csv.exceptions;

/**
 * Signals that a CSV file used for import is invalid or malformed.
 * <p>
 * Typically thrown when the header or column structure does not match expected formats.
 */
public class InvalidCsvException extends Exception {

    /**
     * Constructs an {@code InvalidCsvException} with the specified detail message.
     *
     * @param msg the detail message
     */
    public InvalidCsvException(String msg) {
        super(msg);
    }

    /**
     * Constructs an {@code InvalidCsvException} with no detail message.
     * <p>
     * This no-argument constructor is optional; it is useful if you want to throw
     * a generic invalid CSV error without a specific message.
     */
    public InvalidCsvException() {
        super();
    }
}
