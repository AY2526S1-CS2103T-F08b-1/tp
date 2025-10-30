package seedu.summoners.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.summoners.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.summoners.commons.exceptions.IllegalValueException;
import seedu.summoners.commons.util.JsonUtil;
import seedu.summoners.model.SummonersBook;
import seedu.summoners.testutil.TypicalPlayers;

public class JsonSerializableSummonersBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableSummonersBookTest");
    private static final Path TYPICAL_PLAYERS_FILE = TEST_DATA_FOLDER.resolve("typicalPlayersSummonersBook.json");
    private static final Path INVALID_PLAYER_FILE = TEST_DATA_FOLDER.resolve("invalidPlayerSummonersBook.json");
    private static final Path DUPLICATE_PLAYER_FILE = TEST_DATA_FOLDER.resolve("duplicatePlayerSummonersBook.json");
    private static final Path DUPLICATE_TEAM_FILE = TEST_DATA_FOLDER.resolve("duplicateTeamSummonersBook.json");

    @Test
    public void toModelType_typicalPlayersFile_success() throws Exception {
        JsonSerializableSummonersBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PLAYERS_FILE,
                JsonSerializableSummonersBook.class).get();
        SummonersBook summonersBookFromFile = dataFromFile.toModelType();
        SummonersBook typicalPlayersSummonersBook = TypicalPlayers.getTypicalSummonersBook();
        assertEquals(summonersBookFromFile, typicalPlayersSummonersBook);
    }

    @Test
    public void toModelType_invalidPlayerFile_throwsIllegalValueException() throws Exception {
        JsonSerializableSummonersBook dataFromFile = JsonUtil.readJsonFile(INVALID_PLAYER_FILE,
                JsonSerializableSummonersBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePlayers_throwsIllegalValueException() throws Exception {
        JsonSerializableSummonersBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PLAYER_FILE,
                JsonSerializableSummonersBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableSummonersBook.MESSAGE_DUPLICATE_PLAYER,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateTeams_throwsIllegalValueException() throws Exception {
        JsonSerializableSummonersBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TEAM_FILE,
                JsonSerializableSummonersBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableSummonersBook.MESSAGE_DUPLICATE_TEAM,
                dataFromFile::toModelType);
    }
}
