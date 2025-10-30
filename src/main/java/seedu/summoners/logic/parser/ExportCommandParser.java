package seedu.address.logic.parser;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.ExportCommand.Target;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input into an {@link ExportCommand}.
 * <p>
 * Recognises the following valid patterns:
 * <ul>
 *   <li>{@code export players}</li>
 *   <li>{@code export teams}</li>
 *   <li>{@code export players to/data/players.csv}</li>
 *   <li>{@code export teams to/data/teams.csv}</li>
 * </ul>
 * If no {@code to/FILEPATH} is provided, a default path is used.
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Parses the given user input and constructs an {@link ExportCommand}.
     *
     * @param args full user input string (excluding the command word)
     * @return an {@link ExportCommand} configured with target type and optional output path
     * @throws ParseException if the input does not conform to the expected format
     */
    @Override
    public ExportCommand parse(String args) throws ParseException {
        String trimmed = args.trim();
        if (trimmed.isEmpty()) {
            throw new ParseException(ExportCommand.MESSAGE_USAGE);
        }

        // Example inputs: "players to/data/players.csv" or "teams"
        String[] parts = trimmed.split("\\s+");
        String first = parts[0].toLowerCase();

        Target target;
        if (first.equals("players")) {
            target = Target.PLAYERS;
        } else if (first.equals("teams")) {
            target = Target.TEAMS;
        } else {
            throw new ParseException(ExportCommand.MESSAGE_USAGE);
        }

        Optional<Path> out = Optional.empty();
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].startsWith("to/")) {
                out = Optional.of(Paths.get(parts[i].substring(3)));
            } else {
                throw new ParseException(ExportCommand.MESSAGE_USAGE);
            }
        }
        return new ExportCommand(target, out.orElse(null));
    }
}

