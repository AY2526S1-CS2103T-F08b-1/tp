package seedu.summoners.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.summoners.commons.exceptions.DataLoadingException;
import seedu.summoners.model.ReadOnlySummonersBook;

/**
 * Represents a storage for {@link seedu.summoners.model.SummonersBook}.
 */
public interface SummonersBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getSummonersBookFilePath();

    /**
     * Returns SummonersBook data as a {@link ReadOnlySummonersBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlySummonersBook> readSummonersBook() throws DataLoadingException;

    /**
     * @see #getSummonersBookFilePath()
     */
    Optional<ReadOnlySummonersBook> readSummonersBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlySummonersBook} to the storage.
     * @param summonersBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveSummonersBook(ReadOnlySummonersBook summonersBook) throws IOException;

    /**
     * @see #saveSummonersBook(ReadOnlySummonersBook)
     */
    void saveSummonersBook(ReadOnlySummonersBook summonersBook, Path filePath) throws IOException;

}
