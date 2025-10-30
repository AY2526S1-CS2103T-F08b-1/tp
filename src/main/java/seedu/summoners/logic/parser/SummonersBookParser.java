package seedu.summoners.logic.parser;

import static seedu.summoners.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.summoners.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.summoners.commons.core.LogsCenter;
import seedu.summoners.logic.commands.AddCommand;
import seedu.summoners.logic.commands.AddStatsCommand;
import seedu.summoners.logic.commands.ClearCommand;
import seedu.summoners.logic.commands.Command;
import seedu.summoners.logic.commands.DeleteCommand;
import seedu.summoners.logic.commands.DeleteStatsCommand;
import seedu.summoners.logic.commands.EditCommand;
import seedu.summoners.logic.commands.ExitCommand;
import seedu.summoners.logic.commands.ExportCommand;
import seedu.summoners.logic.commands.FilterCommand;
import seedu.summoners.logic.commands.FindCommand;
import seedu.summoners.logic.commands.GroupCommand;
import seedu.summoners.logic.commands.HelpCommand;
import seedu.summoners.logic.commands.ImportCommand;
import seedu.summoners.logic.commands.ListCommand;
import seedu.summoners.logic.commands.ListTeamCommand;
import seedu.summoners.logic.commands.LoseCommand;
import seedu.summoners.logic.commands.MakeGroupCommand;
import seedu.summoners.logic.commands.UngroupCommand;
import seedu.summoners.logic.commands.ViewCommand;
import seedu.summoners.logic.commands.ViewTeamCommand;
import seedu.summoners.logic.commands.WinCommand;
import seedu.summoners.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class SummonersBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(SummonersBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListTeamCommand.COMMAND_WORD:
            return new ListTeamCommand();

        case MakeGroupCommand.COMMAND_WORD:
            return new MakeGroupCommandParser().parse(arguments);

        case GroupCommand.COMMAND_WORD:
            return new GroupCommandParser().parse(arguments);

        case UngroupCommand.COMMAND_WORD:
            return new UngroupCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case FilterCommand.COMMAND_WORD:
            return new FilterCommandParser().parse(arguments);

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);

        case ImportCommand.COMMAND_WORD:
            return new ImportCommandParser().parse(arguments);

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        case ViewTeamCommand.COMMAND_WORD:
            return new ViewTeamCommandParser().parse(arguments);

        case WinCommand.COMMAND_WORD:
            return new WinCommandParser().parse(arguments);

        case LoseCommand.COMMAND_WORD:
            return new LoseCommandParser().parse(arguments);

        case AddStatsCommand.COMMAND_WORD:
            return new AddStatsCommandParser().parse(arguments);

        case DeleteStatsCommand.COMMAND_WORD:
            return new DeleteStatsCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
