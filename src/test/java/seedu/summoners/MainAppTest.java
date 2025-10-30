package seedu.summoners;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.summoners.model.Model;
import seedu.summoners.model.ReadOnlyUserPrefs;
import seedu.summoners.model.UserPrefs;
import seedu.summoners.storage.JsonSummonersBookStorage;
import seedu.summoners.storage.JsonUserPrefsStorage;
import seedu.summoners.storage.Storage;
import seedu.summoners.storage.StorageManager;

public class MainAppTest {

    private static final Path TEST_DATA_FOLDER = Path.of("src", "test", "data", "JsonSummonersBookStorageTest");

    @TempDir
    public Path testFolder;

    /**
     * Helper method to create a Model using the specified test data file.
     *
     * @param testDataFileName Name of the test data file in JsonSummonersBookStorageTest folder.
     * @return The initialized Model.
     */
    private Model createModelFromTestData(String testDataFileName) {
        Path summonersBookPath = TEST_DATA_FOLDER.resolve(testDataFileName);
        Path userPrefsPath = testFolder.resolve("userprefs.json");

        Storage storage = new StorageManager(
                new JsonSummonersBookStorage(summonersBookPath),
                new JsonUserPrefsStorage(userPrefsPath)
        );

        ReadOnlyUserPrefs userPrefs = new UserPrefs();
        TestableMainApp mainApp = new TestableMainApp();
        return mainApp.initModelManager(storage, userPrefs);
    }

    /**
     * Helper method to verify that a model has an empty summoners book.
     */
    private void assertEmptySummonersBook(Model model) {
        assertNotNull(model);
        assertEquals(0, model.getSummonersBook().getPlayerList().size());
        assertEquals(0, model.getSummonersBook().getTeamList().size());
    }

    /**
     * Helper method to verify that a model has sample data loaded.
     */
    private void assertSampleDataLoaded(Model model) {
        assertNotNull(model);
        assertTrue(model.getSummonersBook().getPlayerList().size() > 0);
    }

    @Test
    public void initModelManager_missingFile_loadsSampleData() {
        Model model = createModelFromTestData("nonexistent.json");
        assertSampleDataLoaded(model);
    }

    @Test
    public void initModelManager_invalidJsonFormat_loadsEmptySummonersBook() {
        assertEmptySummonersBook(createModelFromTestData("notJsonFormatSummonersBook.json"));
    }

    @Test
    public void initModelManager_invalidPlayerData_loadsEmptySummonersBook() {
        assertEmptySummonersBook(createModelFromTestData("invalidPlayerSummonersBook.json"));
    }

    @Test
    public void initModelManager_playerInMultipleTeams_loadsEmptySummonersBook() {
        assertEmptySummonersBook(createModelFromTestData("playerInMultipleTeamsSummonersBook.json"));
    }

    @Test
    public void initModelManager_teamWithDuplicateRoles_loadsEmptySummonersBook() {
        assertEmptySummonersBook(createModelFromTestData("teamWithDuplicateRolesSummonersBook.json"));
    }

    @Test
    public void initModelManager_teamWithDuplicateChampions_loadsEmptySummonersBook() {
        assertEmptySummonersBook(createModelFromTestData("teamWithDuplicateChampionsSummonersBook.json"));
    }

    @Test
    public void initModelManager_teamWithInvalidSize_loadsEmptySummonersBook() {
        assertEmptySummonersBook(createModelFromTestData("teamWithInvalidSizeSummonersBook.json"));
    }

    @Test
    public void initModelManager_invalidRank_loadsEmptySummonersBook() {
        assertEmptySummonersBook(createModelFromTestData("invalidRankSummonersBook.json"));
    }

    @Test
    public void initModelManager_invalidRole_loadsEmptySummonersBook() {
        assertEmptySummonersBook(createModelFromTestData("invalidRoleSummonersBook.json"));
    }

    @Test
    public void initModelManager_invalidChampion_loadsEmptySummonersBook() {
        assertEmptySummonersBook(createModelFromTestData("invalidChampionSummonersBook.json"));
    }

    @Test
    public void initModelManager_missingName_loadsEmptySummonersBook() {
        assertEmptySummonersBook(createModelFromTestData("missingNameSummonersBook.json"));
    }

    @Test
    public void initModelManager_invalidStatsType_loadsEmptySummonersBook() {
        assertEmptySummonersBook(createModelFromTestData("invalidStatsTypeSummonersBook.json"));
    }

    @Test
    public void initModelManager_invalidTag_loadsEmptySummonersBook() {
        assertEmptySummonersBook(createModelFromTestData("invalidTagSummonersBook.json"));
    }

    @Test
    public void initModelManager_dataLoadingException_catchesAndLoadsEmpty() {
        // Explicitly tests the DataLoadingException catch block (MainApp.java:87-90)
        assertEmptySummonersBook(createModelFromTestData("notJsonFormatSummonersBook.json"));
    }

    @Test
    public void initModelManager_runtimeException_catchesAndLoadsEmpty() {
        // Explicitly tests the Exception catch block (MainApp.java:91-95)
        assertEmptySummonersBook(createModelFromTestData("playerInMultipleTeamsSummonersBook.json"));
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
