package seedu.address.logic.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.csv.exceptions.InvalidCsvException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class CsvImporterTest {

    @TempDir
    Path tempDir;

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
    }

    @Test
    public void importPlayers_basicHeader_success() throws Exception {
        Path csv = tempDir.resolve("players_basic.csv");
        Files.write(csv, List.of(
                "Name,Role,Rank,Champion",
                "Alice,Top,Gold,Ahri",
                "Bob,Mid,Platinum,Yasuo"
        ));
        CsvImporter.Result r = CsvImporter.importPlayers(model, csv);
        assertEquals(2, r.imported);
        assertEquals(0, r.duplicates);
        assertEquals(0, r.invalid);
        assertEquals(2, model.getAddressBook().getPersonList().size());
    }

    @Test
    public void importPlayers_extendedHeaderWithWl_success() throws Exception {
        Path csv = tempDir.resolve("players_wl.csv");
        Files.write(csv, List.of(
                "Name,Role,Rank,Champion,Wins,Losses",
                "Carol,Jungle,Diamond,Lee Sin,2,1",
                "Dan,Adc,Gold,Jinx,0,0"
        ));
        CsvImporter.Result r = CsvImporter.importPlayers(model, csv);
        assertEquals(2, r.imported);
        assertEquals(0, r.duplicates);
        assertEquals(0, r.invalid);
    }

    @Test
    public void importPlayers_extendedHeaderWithWrAvg_rejectedAsInvalid() throws Exception {
        Path csv = tempDir.resolve("players_wr_avg.csv");
        Files.write(csv, List.of(
                "Name,Role,Rank,Champion,Wins,Losses,WinRate%,AvgGrade",
                "Eve,Support,Silver,Thresh,3,2,60.0,7.5"
        ));
        assertThrows(InvalidCsvException.class, () -> CsvImporter.importPlayers(model, csv));
    }

    @Test
    public void importPlayers_invalidHeader_throwsInvalidCsvException() throws Exception {
        Path csv = tempDir.resolve("bad_header.csv");
        Files.write(csv, List.of(
                "Bad,Header",
                "X,Y"
        ));
        assertThrows(InvalidCsvException.class, () -> CsvImporter.importPlayers(model, csv));
    }

    @Test
    public void importPlayers_duplicateRows_countedAsDuplicates() throws Exception {
        Path csv = tempDir.resolve("dups.csv");
        Files.write(csv, List.of(
                "Name,Role,Rank,Champion",
                "Alice,Top,Gold,Ahri",
                "Alice,Top,Gold,Ahri" // duplicate by name
        ));
        CsvImporter.Result r = CsvImporter.importPlayers(model, csv);
        assertEquals(1, r.imported);
        assertEquals(1, r.duplicates);
        assertEquals(0, r.invalid);
    }

    @Test
    public void importPlayers_malformedRows_countedAsInvalid() throws Exception {
        Path csv = tempDir.resolve("bad_rows.csv");
        Files.write(csv, List.of(
                "Name,Role,Rank,Champion",
                "", // blank line -> ignored
                "Too,Few,Cols", // invalid
                "Frank,Top,BadRank,Ahri" // ParseException (invalid rank)
        ));
        CsvImporter.Result r = CsvImporter.importPlayers(model, csv);
        // invalid = 2 (Too few cols + bad rank)
        assertEquals(0, r.imported);
        assertEquals(0, r.duplicates);
        assertEquals(2, r.invalid);
    }
}

