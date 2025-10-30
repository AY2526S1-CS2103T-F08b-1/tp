package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ImportCommandTest {

    @TempDir
    Path tempDir;

    @Test
    public void execute_validFile_importsAndReturnsSummary() throws Exception {
        Path csv = tempDir.resolve("players.csv");
        Files.write(csv, List.of(
                "Name,Role,Rank,Champion",
                "Alice,Top,Gold,Ahri"
        ));

        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        ImportCommand cmd = new ImportCommand(csv);
        CommandResult result = cmd.execute(model);

        assertTrue(result.getFeedbackToUser().contains("Imported 1 players"));
        assertTrue(model.getAddressBook().getPersonList().size() >= 1);
    }

    @Test
    public void execute_players_success() throws Exception {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        Path out = tempDir.resolve("players.csv");
        CommandResult r = new ExportCommand(ExportCommand.Target.PLAYERS, out).execute(model);
        assertTrue(Files.exists(out));
        assertTrue(r.getFeedbackToUser().contains("Exported player data"));
    }

    @Test
    public void execute_teams_success() throws Exception {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        Path out = tempDir.resolve("teams.csv");
        CommandResult r = new ExportCommand(ExportCommand.Target.TEAMS, out).execute(model);
        assertTrue(Files.exists(out));
        assertTrue(r.getFeedbackToUser().contains("Exported team data"));
    }

    @Test
    public void execute_ioError_wrappedAsCommandException() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        // Pass a directory as "file" -> Files.write will throw, exercising catch block
        Command cmd = new ExportCommand(ExportCommand.Target.PLAYERS, tempDir);
        assertThrows(CommandException.class, () -> cmd.execute(model));
    }

}
