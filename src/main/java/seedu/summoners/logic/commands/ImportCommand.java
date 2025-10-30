package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.csv.CsvImporter;
import seedu.address.logic.csv.exceptions.InvalidCsvException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

/**
 * Imports player data from a CSV file into the application's player database.
 * <p>
 * Supported headers include:
 * <ul>
 *     <li>{@code Name,Role,Rank,Champion}</li>
 *     <li>{@code Name,Role,Rank,Champion,Wins,Losses}</li>
 * </ul>
 * Each imported player is added to the model unless a duplicate already exists.
 */
public class ImportCommand extends Command {
    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports players from a CSV.\n"
            + "Parameters: import players from FILEPATH\n"
            + "Example: import players from data/new_players.csv";

    private final Path path;

    /**
     * Constructs an {@code ImportCommand} with the specified file path.
     *
     * @param path the path to the CSV file to import; must not be {@code null}
     */
    public ImportCommand(Path path) {
        this.path = requireNonNull(path);
    }

    /**
     * Executes the import command by reading player data from the specified CSV file
     * and inserting valid entries into the model.
     * <p>
     * Provides a summary indicating how many players were imported, skipped as duplicates,
     * or rejected due to invalid data.
     *
     * @param model the model in which players are stored
     * @return a {@link CommandResult} containing the import summary message
     * @throws CommandException if the file is missing, invalid, or cannot be parsed
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            CsvImporter.Result r = CsvImporter.importPlayers(model, path);
            StringBuilder msg = new StringBuilder(
                    String.format("Imported %d players, skipped %d duplicates, %d invalid row(s).",
                            r.imported, r.duplicates, r.invalid));

            // Show sample invalid rows if any
            if (r.invalid > 0 && r.sampleErrors != null && !r.sampleErrors.isEmpty()) {
                msg.append("\nExamples of invalid rows:\n  - ");
                msg.append(String.join("\n  - ", r.sampleErrors));
                if (r.invalid > r.sampleErrors.size()) {
                    msg.append("\n  ... (showing up to ")
                            .append(CsvImporter.MAX_SAMPLE_ERRORS)
                            .append(" errors)");
                }
            }

            return new CommandResult(msg.toString());
        } catch (NoSuchFileException e) {
            throw new CommandException("Failed to import: file not found.");
        } catch (InvalidCsvException e) {
            throw new CommandException(e.getMessage());
        } catch (ParseException e) {
            throw new CommandException("Invalid value: " + e.getMessage());
        } catch (Exception e) {
            throw new CommandException("Failed to import: " + e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ImportCommand)) {
            return false;
        }
        ImportCommand o = (ImportCommand) other;
        return path.equals(o.path);
    }
}


