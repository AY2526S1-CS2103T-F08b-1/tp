package seedu.summoners.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.summoners.commons.core.GuiSettings;
import seedu.summoners.commons.core.LogsCenter;
import seedu.summoners.logic.commands.Command;
import seedu.summoners.logic.commands.CommandResult;
import seedu.summoners.logic.commands.exceptions.CommandException;
import seedu.summoners.logic.parser.SummonersBookParser;
import seedu.summoners.logic.parser.exceptions.ParseException;
import seedu.summoners.model.Model;
import seedu.summoners.model.ReadOnlySummonersBook;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.Team;
import seedu.summoners.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final SummonersBookParser summonersBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        summonersBookParser = new SummonersBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = summonersBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveSummonersBook(model.getSummonersBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlySummonersBook getSummonersBook() {
        return model.getSummonersBook();
    }

    @Override
    public ObservableList<Player> getFilteredPlayerList() {
        return model.getFilteredPlayerList();
    }

    @Override
    public ObservableList<Team> getFilteredTeamList() {
        return model.getFilteredTeamList();
    }

    @Override
    public Path getSummonersBookFilePath() {
        return model.getSummonersBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
