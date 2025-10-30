package seedu.summoners.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.summoners.commons.exceptions.DataLoadingException;
import seedu.summoners.model.ReadOnlySummonersBook;
import seedu.summoners.model.ReadOnlyUserPrefs;
import seedu.summoners.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends SummonersBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getSummonersBookFilePath();

    @Override
    Optional<ReadOnlySummonersBook> readSummonersBook() throws DataLoadingException;

    @Override
    void saveSummonersBook(ReadOnlySummonersBook summonersBook) throws IOException;

}
