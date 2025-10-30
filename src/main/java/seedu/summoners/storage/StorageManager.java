package seedu.summoners.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.summoners.commons.core.LogsCenter;
import seedu.summoners.commons.exceptions.DataLoadingException;
import seedu.summoners.model.ReadOnlySummonersBook;
import seedu.summoners.model.ReadOnlyUserPrefs;
import seedu.summoners.model.UserPrefs;

/**
 * Manages storage of SummonersBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private SummonersBookStorage summonersBookStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code SummonersBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(SummonersBookStorage summonersBookStorage, UserPrefsStorage userPrefsStorage) {
        this.summonersBookStorage = summonersBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ SummonersBook methods ==============================

    @Override
    public Path getSummonersBookFilePath() {
        return summonersBookStorage.getSummonersBookFilePath();
    }

    @Override
    public Optional<ReadOnlySummonersBook> readSummonersBook() throws DataLoadingException {
        return readSummonersBook(summonersBookStorage.getSummonersBookFilePath());
    }

    @Override
    public Optional<ReadOnlySummonersBook> readSummonersBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return summonersBookStorage.readSummonersBook(filePath);
    }

    @Override
    public void saveSummonersBook(ReadOnlySummonersBook summonersBook) throws IOException {
        saveSummonersBook(summonersBook, summonersBookStorage.getSummonersBookFilePath());
    }

    @Override
    public void saveSummonersBook(ReadOnlySummonersBook summonersBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        summonersBookStorage.saveSummonersBook(summonersBook, filePath);
    }

}
