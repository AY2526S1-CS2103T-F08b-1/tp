package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ExportCommandTest {

    @TempDir
    Path tempDir;

    @Test
    public void execute_players_writesFileAndReturnsMessage() throws Exception {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        Path out = tempDir.resolve("players.csv");

        ExportCommand cmd = new ExportCommand(ExportCommand.Target.PLAYERS, out);
        CommandResult result = cmd.execute(model);

        assertTrue(Files.exists(out));
        assertTrue(result.getFeedbackToUser().contains("Exported player data to"));
    }

    @Test
    public void equals_sameValues_true() {
        ExportCommand a = new ExportCommand(ExportCommand.Target.TEAMS, null);
        ExportCommand b = new ExportCommand(ExportCommand.Target.TEAMS, null);
        assertEquals(a, b);
    }
}

