package seedu.summoners.logic.parser;

import static seedu.summoners.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.summoners.commons.core.index.Index;
import seedu.summoners.logic.commands.ViewTeamCommand;
import seedu.summoners.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@link seedu.summoners.logic.commands.ViewTeamCommand} object.
 * <p>
 * Expected format: {@code viewteam INDEX}, where {@code INDEX} refers to the team position
 * in the currently displayed team list.
 */
public class ViewTeamCommandParser implements Parser<ViewTeamCommand> {
    @Override
    public ViewTeamCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewTeamCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewTeamCommand.MESSAGE_USAGE), pe);
        }
    }
}

