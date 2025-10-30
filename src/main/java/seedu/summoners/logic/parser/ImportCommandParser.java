package seedu.address.logic.parser;

import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input into an {@link ImportCommand}.
 * <p>
 * Recognises commands of the form:
 * <ul>
 *   <li>{@code import players from data/players.csv}</li>
 * </ul>
 * Extracts the file path after the keyword {@code from} and constructs
 * an {@link ImportCommand} with that path.
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    /**
     * Parses the given user input and returns an {@link ImportCommand}.
     * <p>
     * Expected format: {@code players from FILEPATH}.
     *
     * @param args full user input string (excluding the command word)
     * @return an {@link ImportCommand} that imports players from the specified file path
     * @throws ParseException if the input does not match the required format
     */
    @Override
    public ImportCommand parse(String args) throws ParseException {
        String trimmed = args.trim();
        // Expected input example: "players from data/players.csv"
        String[] parts = trimmed.split("\\s+");
        if (parts.length < 3 || !parts[0].equalsIgnoreCase("players") || !parts[1].equalsIgnoreCase("from")) {
            throw new ParseException(ImportCommand.MESSAGE_USAGE);
        }
        Path path = Paths.get(trimmed.substring(trimmed.toLowerCase().indexOf("from") + 5));
        return new ImportCommand(path);
    }
}

