package seedu.address.logic.csv;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.address.logic.csv.exceptions.InvalidCsvException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Champion;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Rank;
import seedu.address.model.person.Role;
import seedu.address.model.person.Stats;
import seedu.address.model.tag.Tag;

/**
 * CSV importer for players. Supports headers:
 * <ul>
 *   <li>{@code Name,Role,Rank,Champion}</li>
 *   <li>{@code Name,Role,Rank,Champion,Wins,Losses}</li>
 * </ul>
 * <p>WinRate% is not supported and such files are rejected.</p>
 */
public final class CsvImporter {

    /** Max number of individual row errors shown in the import summary. */
    public static final int MAX_SAMPLE_ERRORS = 5;

    /**
     * Result summary for an import operation.
     */
    public static final class Result {
        public final int imported;
        public final int duplicates;
        public final int invalid;
        public final List<String> sampleErrors;

        /**
         * Constructs a result with counts.
         *
         * @param imported   number of rows successfully imported
         * @param duplicates number of rows skipped as duplicates
         * @param invalid    number of rows that failed validation/parsing
         */
        Result(int imported, int duplicates, int invalid, List<String> sampleErrors) {
            this.imported = imported;
            this.duplicates = duplicates;
            this.invalid = invalid;
            this.sampleErrors = sampleErrors;
        }
    }

    private CsvImporter() {
        // utility class
    }

    /**
     * Imports players from the given CSV file path into the model.
     *
     * @param model the model to mutate
     * @param path  path to CSV file
     * @return a {@link Result} summary of the import
     * @throws IOException         when IO fails (e.g., file missing)
     * @throws InvalidCsvException when header/format is invalid
     * @throws ParseException      when values fail domain validation
     */
    public static Result importPlayers(Model model, Path path)
            throws IOException, InvalidCsvException, ParseException {
        requireNonNull(model);
        validateFileExists(path);

        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            HeaderType headerType = readAndValidateHeader(br);
            Result result = processDataRows(model, br, headerType);
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
            return result;
        }
    }

    private static void validateFileExists(Path path) throws NoSuchFileException {
        if (!Files.exists(path)) {
            throw new NoSuchFileException(path.toString());
        }
    }

    private static HeaderType readAndValidateHeader(BufferedReader br)
            throws IOException, InvalidCsvException {
        String header = br.readLine();
        if (header == null) {
            throw new InvalidCsvException("Empty CSV. Expected header: "
                    + "'Name,Role,Rank,Champion' or 'Name,Role,Rank,Champion,Wins,Losses'.");
        }

        HeaderType type = HeaderType.from(header);
        if (type == HeaderType.UNKNOWN) {
            throw new InvalidCsvException(buildInvalidHeaderMessage(header));
        }
        return type;
    }

    private static String buildInvalidHeaderMessage(String header) {
        String sanitized = Arrays.stream(header.split(",", -1))
                .map(String::trim)
                .reduce((a, b) -> a + "," + b)
                .orElse(header)
                .trim();
        int cols = sanitized.isEmpty() ? 0 : sanitized.split(",", -1).length;

        return "Invalid header (" + cols + " column(s)): '" + sanitized + "'.\n"
                + "Expected either:\n"
                + "  - Name,Role,Rank,Champion\n"
                + "  - Name,Role,Rank,Champion,Wins,Losses";
    }

    private static Result processDataRows(Model model, BufferedReader br, HeaderType headerType)
            throws IOException {
        int imported = 0;
        int duplicates = 0;
        int invalid = 0;
        List<String> sampleErrors = new ArrayList<>();

        int lineNo = 1;
        String line;
        while ((line = br.readLine()) != null) {
            lineNo++;
            if (line.isBlank()) {
                continue;
            }

            ImportOutcome outcome = processRow(model, line, lineNo, headerType);
            imported += outcome.imported;
            duplicates += outcome.duplicates;
            invalid += outcome.invalid;
            if (outcome.error != null && sampleErrors.size() < MAX_SAMPLE_ERRORS) {
                sampleErrors.add(outcome.error);
            }
        }

        return new Result(imported, duplicates, invalid, sampleErrors);
    }

    private static ImportOutcome processRow(Model model, String line, int lineNo, HeaderType headerType) {
        try {
            List<String> cols = parseCsvLine(line);
            PlayerRow row = PlayerRow.parse(cols, headerType);
            Person candidate = createPerson(row);
            return addPersonToModel(model, candidate);
        } catch (IllegalArgumentException iae) {
            return ImportOutcome.invalid("line " + lineNo + ": " + iae.getMessage());
        }
    }

    private static Person createPerson(PlayerRow row) {
        return new Person(
                new Name(row.name),
                new Role(row.role),
                new Rank(row.rank),
                new Champion(row.champion),
                Collections.<Tag>emptySet(),
                row.wins,
                row.losses,
                new Stats()
        );
    }

    private static ImportOutcome addPersonToModel(Model model, Person candidate) {
        if (model.hasPerson(candidate)) {
            return ImportOutcome.duplicate();
        }
        model.addPerson(candidate);
        return ImportOutcome.imported();
    }

    /**
     * Tracks the outcome of attempting to import a single row.
     */
    private static final class ImportOutcome {
        final int imported;
        final int duplicates;
        final int invalid;
        final String error;

        private ImportOutcome(int imported, int duplicates, int invalid, String error) {
            this.imported = imported;
            this.duplicates = duplicates;
            this.invalid = invalid;
            this.error = error;
        }

        static ImportOutcome imported() {
            return new ImportOutcome(1, 0, 0, null);
        }

        static ImportOutcome duplicate() {
            return new ImportOutcome(0, 1, 0, null);
        }

        static ImportOutcome invalid(String error) {
            return new ImportOutcome(0, 0, 1, error);
        }
    }

    /**
     * Supported CSV header formats.
     */
    private enum HeaderType {
        H4, // Name,Role,Rank,Champion
        H6, // Name,Role,Rank,Champion,Wins,Losses
        UNKNOWN;

        static HeaderType from(String header) {
            if (header == null || header.isBlank()) {
                return HeaderType.UNKNOWN;
            }

            if (header.charAt(0) == '\uFEFF') {
                header = header.substring(1);
            }

            List<String> h = Arrays.stream(header.split(",", -1))
                    .map(s -> s.trim().toLowerCase())
                    .toList();

            if (h.equals(List.of("name", "role", "rank", "champion"))) {
                return HeaderType.H4;
            }
            if (h.equals(List.of("name", "role", "rank", "champion", "wins", "losses"))) {
                return HeaderType.H6;
            }
            return HeaderType.UNKNOWN;
        }
    }

    private static final class PlayerRow {
        final String name;
        final String role;
        final String rank;
        final String champion;
        final int wins;
        final int losses;

        private PlayerRow(String name, String role, String rank, String champion, int wins, int losses) {
            this.name = name;
            this.role = role;
            this.rank = rank;
            this.champion = champion;
            this.wins = wins;
            this.losses = losses;
        }

        static PlayerRow parse(List<String> cols, HeaderType type) {
            switch (type) {
            case H4:
                if (cols.size() != 4) {
                    throw new IllegalArgumentException("bad cols");
                }
                return new PlayerRow(
                        cols.get(0).trim(),
                        cols.get(1).trim(),
                        cols.get(2).trim(),
                        cols.get(3).trim(),
                        0,
                        0
                );
            case H6:
                if (cols.size() != 6) {
                    throw new IllegalArgumentException("bad cols");
                }
                int wins = parseNonNegativeInt(cols.get(4).trim(), "Wins");
                int losses = parseNonNegativeInt(cols.get(5).trim(), "Losses");
                return new PlayerRow(
                        cols.get(0).trim(),
                        cols.get(1).trim(),
                        cols.get(2).trim(),
                        cols.get(3).trim(),
                        wins,
                        losses
                );

            default:
                throw new IllegalArgumentException("unknown header type");
            }
        }

        private static int parseNonNegativeInt(String raw, String colName) {
            try {
                int v = Integer.parseInt(raw);
                if (v < 0) {
                    throw new IllegalArgumentException(colName + " must be a non-negative integer");
                }
                return v;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(colName + " must be an integer");
            }
        }
    }

    /**
     * Minimal RFC4180 tokenizer supporting quotes and escaped quotes.
     *
     * @param line a single CSV line
     * @return a list of column values for the line
     */
    static List<String> parseCsvLine(String line) {
        ArrayList<String> out = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    sb.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                out.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }

        out.add(sb.toString());
        return out;
    }
}


