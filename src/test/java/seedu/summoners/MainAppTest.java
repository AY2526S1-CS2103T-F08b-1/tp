package seedu.address;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.Model;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;

public class MainAppTest {

    private static final Path TEST_DATA_FOLDER = Path.of("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    /**
     * Helper method to create a Model using the specified test data file.
     *
     * @param testDataFileName Name of the test data file in JsonAddressBookStorageTest folder.
     * @return The initialized Model.
     */
    private Model createModelFromTestData(String testDataFileName) {
        Path addressBookPath = TEST_DATA_FOLDER.resolve(testDataFileName);
        Path userPrefsPath = testFolder.resolve("userprefs.json");

        Storage storage = new StorageManager(
                new JsonAddressBookStorage(addressBookPath),
                new JsonUserPrefsStorage(userPrefsPath)
        );

        ReadOnlyUserPrefs userPrefs = new UserPrefs();
        TestableMainApp mainApp = new TestableMainApp();
        return mainApp.initModelManager(storage, userPrefs);
    }

    /**
     * Helper method to verify that a model has an empty address book.
     */
    private void assertEmptyAddressBook(Model model) {
        assertNotNull(model);
        assertEquals(0, model.getAddressBook().getPersonList().size());
        assertEquals(0, model.getAddressBook().getTeamList().size());
    }

    /**
     * Helper method to verify that a model has sample data loaded.
     */
    private void assertSampleDataLoaded(Model model) {
        assertNotNull(model);
        assertTrue(model.getAddressBook().getPersonList().size() > 0);
    }

    @Test
    public void initModelManager_missingFile_loadsSampleData() {
        Model model = createModelFromTestData("nonexistent.json");
        assertSampleDataLoaded(model);
    }

    @Test
    public void initModelManager_invalidJsonFormat_loadsEmptyAddressBook() {
        assertEmptyAddressBook(createModelFromTestData("notJsonFormatAddressBook.json"));
    }

    @Test
    public void initModelManager_invalidPersonData_loadsEmptyAddressBook() {
        assertEmptyAddressBook(createModelFromTestData("invalidPersonAddressBook.json"));
    }

    @Test
    public void initModelManager_personInMultipleTeams_loadsEmptyAddressBook() {
        assertEmptyAddressBook(createModelFromTestData("personInMultipleTeamsAddressBook.json"));
    }

    @Test
    public void initModelManager_teamWithDuplicateRoles_loadsEmptyAddressBook() {
        assertEmptyAddressBook(createModelFromTestData("teamWithDuplicateRolesAddressBook.json"));
    }

    @Test
    public void initModelManager_teamWithDuplicateChampions_loadsEmptyAddressBook() {
        assertEmptyAddressBook(createModelFromTestData("teamWithDuplicateChampionsAddressBook.json"));
    }

    @Test
    public void initModelManager_teamWithInvalidSize_loadsEmptyAddressBook() {
        assertEmptyAddressBook(createModelFromTestData("teamWithInvalidSizeAddressBook.json"));
    }

    @Test
    public void initModelManager_invalidRank_loadsEmptyAddressBook() {
        assertEmptyAddressBook(createModelFromTestData("invalidRankAddressBook.json"));
    }

    @Test
    public void initModelManager_invalidRole_loadsEmptyAddressBook() {
        assertEmptyAddressBook(createModelFromTestData("invalidRoleAddressBook.json"));
    }

    @Test
    public void initModelManager_invalidChampion_loadsEmptyAddressBook() {
        assertEmptyAddressBook(createModelFromTestData("invalidChampionAddressBook.json"));
    }

    @Test
    public void initModelManager_missingName_loadsEmptyAddressBook() {
        assertEmptyAddressBook(createModelFromTestData("missingNameAddressBook.json"));
    }

    @Test
    public void initModelManager_invalidStatsType_loadsEmptyAddressBook() {
        assertEmptyAddressBook(createModelFromTestData("invalidStatsTypeAddressBook.json"));
    }

    @Test
    public void initModelManager_invalidTag_loadsEmptyAddressBook() {
        assertEmptyAddressBook(createModelFromTestData("invalidTagAddressBook.json"));
    }

    @Test
    public void initModelManager_dataLoadingException_catchesAndLoadsEmpty() {
        // Explicitly tests the DataLoadingException catch block (MainApp.java:87-90)
        assertEmptyAddressBook(createModelFromTestData("notJsonFormatAddressBook.json"));
    }

    @Test
    public void initModelManager_runtimeException_catchesAndLoadsEmpty() {
        // Explicitly tests the Exception catch block (MainApp.java:91-95)
        assertEmptyAddressBook(createModelFromTestData("personInMultipleTeamsAddressBook.json"));
    }

    /**
     * A testable version of MainApp that exposes initModelManager for testing.
     */
    private static class TestableMainApp extends MainApp {
        @Override
        public Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
            return super.initModelManager(storage, userPrefs);
        }
    }
}
