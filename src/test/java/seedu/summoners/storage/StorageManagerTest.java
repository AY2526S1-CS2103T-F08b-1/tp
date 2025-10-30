package seedu.summoners.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.summoners.testutil.TypicalPlayers.getTypicalSummonersBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.summoners.commons.core.GuiSettings;
import seedu.summoners.model.ReadOnlySummonersBook;
import seedu.summoners.model.SummonersBook;
import seedu.summoners.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonSummonersBookStorage summonersBookStorage = new JsonSummonersBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(summonersBookStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void summonersBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonSummonersBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonSummonersBookStorageTest} class.
         */
        SummonersBook original = getTypicalSummonersBook();
        storageManager.saveSummonersBook(original);
        ReadOnlySummonersBook retrieved = storageManager.readSummonersBook().get();
        assertEquals(original, new SummonersBook(retrieved));
    }

    @Test
    public void getSummonersBookFilePath() {
        assertNotNull(storageManager.getSummonersBookFilePath());
    }

}
