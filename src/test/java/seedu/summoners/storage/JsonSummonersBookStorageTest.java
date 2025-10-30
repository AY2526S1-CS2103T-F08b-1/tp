package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.JAMES;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

public class JsonAddressBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws Exception {
        return new JsonAddressBookStorage(Paths.get(filePath)).readAddressBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidPersonAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidAndValidPersonAddressBook.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        AddressBook original = getTypicalAddressBook();
        JsonAddressBookStorage jsonAddressBookStorage = new JsonAddressBookStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        ReadOnlyAddressBook readBack = jsonAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(IDA);
        original.removePerson(ALICE);
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        readBack = jsonAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        // Save and read without specifying file path
        original.addPerson(JAMES);
        jsonAddressBookStorage.saveAddressBook(original); // file path not specified
        readBack = jsonAddressBookStorage.readAddressBook().get(); // file path not specified
        assertEquals(original, new AddressBook(readBack));
    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) {
        try {
            new JsonAddressBookStorage(Paths.get(filePath))
                    .saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new AddressBook(), null));
    }

    @Test
    public void readAddressBook_personInMultipleTeams_throwsException() {
        // PersonAlreadyInTeamException is a runtime exception that should be caught
        assertThrows(Exception.class, () -> readAddressBook("personInMultipleTeamsAddressBook.json"));
    }

    @Test
    public void readAddressBook_teamWithDuplicateRoles_throwsException() {
        // DuplicateRoleException is a runtime exception that should be caught
        assertThrows(Exception.class, () -> readAddressBook("teamWithDuplicateRolesAddressBook.json"));
    }

    @Test
    public void readAddressBook_teamWithDuplicateChampions_throwsException() {
        // DuplicateChampionException is a runtime exception that should be caught
        assertThrows(Exception.class, () -> readAddressBook("teamWithDuplicateChampionsAddressBook.json"));
    }

    @Test
    public void readAddressBook_teamWithInvalidSize_throwsException() {
        // InvalidTeamSizeException is a runtime exception that should be caught
        assertThrows(Exception.class, () -> readAddressBook("teamWithInvalidSizeAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidRank_throwsDataLoadingException() {
        // Invalid rank should throw IllegalValueException -> DataLoadingException
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidRankAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidRole_throwsDataLoadingException() {
        // Invalid role should throw IllegalValueException -> DataLoadingException
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidRoleAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidChampion_throwsDataLoadingException() {
        // Empty champion should throw IllegalValueException -> DataLoadingException
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidChampionAddressBook.json"));
    }

    @Test
    public void readAddressBook_missingName_throwsDataLoadingException() {
        // Missing name field should throw IllegalValueException -> DataLoadingException
        assertThrows(DataLoadingException.class, () -> readAddressBook("missingNameAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidStatsType_throwsDataLoadingException() {
        // String in numeric stats field should throw DataLoadingException during JSON parsing
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidStatsTypeAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidTag_throwsDataLoadingException() {
        // Tag with special characters should throw IllegalValueException -> DataLoadingException
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidTagAddressBook.json"));
    }
}
