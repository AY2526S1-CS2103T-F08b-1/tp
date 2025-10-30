package seedu.address.logic.csv;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalTeams;

public class CsvExporterTest {

    @TempDir
    Path tempDir;

    private Model populatedModel;

    @BeforeEach
    public void setUp() {
        AddressBook ab = TypicalTeams.getTypicalAddressBookWithTeams(); // has persons + teams
        populatedModel = new ModelManager(ab, new UserPrefs());
    }

    @Test
    public void exportPlayers_writesHeaderAndRows() throws Exception {
        Path out = tempDir.resolve("players.csv");
        CsvExporter.exportPlayers(populatedModel, out);
        assertTrue(Files.exists(out));

        String content = Files.readString(out);
        assertTrue(content.startsWith("Name,Role,Rank,Champion,Wins,Losses"));
        // At least one known name appears
        assertTrue(content.contains(TypicalPersons.ALICE.getName().toString()));
    }

    @Test
    public void exportTeams_writesHeaderAndRows() throws Exception {
        Path out = tempDir.resolve("teams.csv");
        CsvExporter.exportTeams(populatedModel, out);
        assertTrue(Files.exists(out));

        String content = Files.readString(out);
        assertTrue(content.startsWith("TeamId,Top,Jungle,Mid,Adc,Support,Wins,Losses,WinRate%"));
        // A team id should appear (any non-empty line after header)
        long nonHeaderLines = Files.readAllLines(out).stream().skip(1).count();
        assertTrue(nonHeaderLines > 0);
    }
}
