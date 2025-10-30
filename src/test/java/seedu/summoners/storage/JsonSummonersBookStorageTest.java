package seedu.summoners.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.summoners.testutil.Assert.assertThrows;
import static seedu.summoners.testutil.TypicalPlayers.ALICE;
import static seedu.summoners.testutil.TypicalPlayers.IDA;
import static seedu.summoners.testutil.TypicalPlayers.JAMES;
import static seedu.summoners.testutil.TypicalPlayers.getTypicalSummonersBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.summoners.commons.exceptions.DataLoadingException;
import seedu.summoners.model.ReadOnlySummonersBook;
import seedu.summoners.model.SummonersBook;

public class JsonSummonersBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSummonersBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readSummonersBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readSummonersBook(null));
    }

    private java.util.Optional<ReadOnlySummonersBook> readSummonersBook(String filePath) throws Exception {
        return new JsonSummonersBookStorage(Paths.get(filePath))
                .readSummonersBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readSummonersBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readSummonersBook("notJsonFormatSummonersBook.json"));
    }

    @Test
    public void readSummonersBook_invalidPlayerSummonersBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readSummonersBook("invalidPlayerSummonersBook.json"));
    }

    @Test
    public void readSummonersBook_invalidAndValidPlayerSummonersBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readSummonersBook("invalidAndValidPlayerSummonersBook.json"));
    }

    @Test
    public void readAndSaveSummonersBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempSummonersBook.json");
        SummonersBook original = getTypicalSummonersBook();
        JsonSummonersBookStorage jsonSummonersBookStorage = new JsonSummonersBookStorage(filePath);

        // Save in new file and read back
        jsonSummonersBookStorage.saveSummonersBook(original, filePath);
        ReadOnlySummonersBook readBack = jsonSummonersBookStorage.readSummonersBook(filePath).get();
        assertEquals(original, new SummonersBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPlayer(IDA);
        original.removePlayer(ALICE);
        jsonSummonersBookStorage.saveSummonersBook(original, filePath);
        readBack = jsonSummonersBookStorage.readSummonersBook(filePath).get();
        assertEquals(original, new SummonersBook(readBack));

        // Save and read without specifying file path
        original.addPlayer(JAMES);
        jsonSummonersBookStorage.saveSummonersBook(original); // file path not specified
        readBack = jsonSummonersBookStorage.readSummonersBook().get(); // file path not specified
        assertEquals(original, new SummonersBook(readBack));
    }

    @Test
    public void saveSummonersBook_nullSummonersBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveSummonersBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code summonersBook} at the specified {@code filePath}.
     */
    private void saveSummonersBook(ReadOnlySummonersBook summonersBook, String filePath) {
        try {
            new JsonSummonersBookStorage(Paths.get(filePath))
                    .saveSummonersBook(summonersBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveSummonersBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveSummonersBook(new SummonersBook(), null));
    }

    @Test
    public void readSummonersBook_playerInMultipleTeams_throwsException() {
        // PlayerAlreadyInTeamException is a runtime exception that should be caught
        assertThrows(Exception.class, () -> readSummonersBook("playerInMultipleTeamsSummonersBook.json"));
    }

    @Test
    public void readSummonersBook_teamWithDuplicateRoles_throwsException() {
        // DuplicateRoleException is a runtime exception that should be caught
        assertThrows(Exception.class, () -> readSummonersBook("teamWithDuplicateRolesSummonersBook.json"));
    }

    @Test
    public void readSummonersBook_teamWithDuplicateChampions_throwsException() {
        // DuplicateChampionException is a runtime exception that should be caught
        assertThrows(Exception.class, () -> readSummonersBook("teamWithDuplicateChampionsSummonersBook.json"));
    }

    @Test
    public void readSummonersBook_teamWithInvalidSize_throwsException() {
        // InvalidTeamSizeException is a runtime exception that should be caught
        assertThrows(Exception.class, () -> readSummonersBook("teamWithInvalidSizeSummonersBook.json"));
    }

    @Test
    public void readSummonersBook_invalidRank_throwsDataLoadingException() {
        // Invalid rank should throw IllegalValueException -> DataLoadingException
        assertThrows(DataLoadingException.class, () -> readSummonersBook("invalidRankSummonersBook.json"));
    }

    @Test
    public void readSummonersBook_invalidRole_throwsDataLoadingException() {
        // Invalid role should throw IllegalValueException -> DataLoadingException
        assertThrows(DataLoadingException.class, () -> readSummonersBook("invalidRoleSummonersBook.json"));
    }

    @Test
    public void readSummonersBook_invalidChampion_throwsDataLoadingException() {
        // Empty champion should throw IllegalValueException -> DataLoadingException
        assertThrows(DataLoadingException.class, () -> readSummonersBook("invalidChampionSummonersBook.json"));
    }

    @Test
    public void readSummonersBook_missingName_throwsDataLoadingException() {
        // Missing name field should throw IllegalValueException -> DataLoadingException
        assertThrows(DataLoadingException.class, () -> readSummonersBook("missingNameSummonersBook.json"));
    }

    @Test
    public void readSummonersBook_invalidStatsType_throwsDataLoadingException() {
        // String in numeric stats field should throw DataLoadingException during JSON parsing
        assertThrows(DataLoadingException.class, () -> readSummonersBook("invalidStatsTypeSummonersBook.json"));
    }

    @Test
    public void readSummonersBook_invalidTag_throwsDataLoadingException() {
        // Tag with special characters should throw IllegalValueException -> DataLoadingException
        assertThrows(DataLoadingException.class, () -> readSummonersBook("invalidTagSummonersBook.json"));
    }
}
