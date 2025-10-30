package seedu.summoners.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.summoners.commons.core.GuiSettings;
import seedu.summoners.logic.commands.CommandResult;
import seedu.summoners.logic.commands.exceptions.CommandException;
import seedu.summoners.logic.parser.exceptions.ParseException;
import seedu.summoners.model.ReadOnlySummonersBook;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.Team;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the SummonersBook.
     *
     * @see seedu.summoners.model.Model#getSummonersBook()
     */
    ReadOnlySummonersBook getSummonersBook();

    /** Returns an unmodifiable view of the filtered list of players */
    ObservableList<Player> getFilteredPlayerList();

    /** Returns an unmodifiable view of the filtered list of teams */
    ObservableList<Team> getFilteredTeamList();

    /**
     * Returns the user prefs' summoners book file path.
     */
    Path getSummonersBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
