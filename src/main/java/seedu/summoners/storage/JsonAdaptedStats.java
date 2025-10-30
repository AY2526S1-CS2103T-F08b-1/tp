package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Stats;

/**
 * Jackson-friendly version of {@link Stats}.
 */
public class JsonAdaptedStats {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Stats' %s field is missing!";

    /** Historical list of CS per minute values recorded. */
    private final List<Float> csPerMinute = new ArrayList<>();

    /** Historical list of gold difference at 15-minute values recorded. */
    private final List<Integer> goldDiffAt15 = new ArrayList<>();

    /** Historical list of KDA scores recorded. */
    private final List<Float> kdaScores = new ArrayList<>();

    /** List of individual calculated performance scores. */
    private final List<Double> scores = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedStats} with the given stat details.
     */
    @JsonCreator
    public JsonAdaptedStats(@JsonProperty("csPerMinute") List<Float> csPerMinute,
                            @JsonProperty("goldDiffAt15") List<Integer> goldDiffAt15,
                            @JsonProperty("kdaScores") List<Float> kdaScores,
                            @JsonProperty("scores") List<Double> scores) {
        if (csPerMinute != null) {
            this.csPerMinute.addAll(csPerMinute);
        }
        if (goldDiffAt15 != null) {
            this.goldDiffAt15.addAll(goldDiffAt15);
        }
        if (kdaScores != null) {
            this.kdaScores.addAll(kdaScores);
        }
        if (scores != null) {
            this.scores.addAll(scores);
        }
    }

    /**
     * Converts a given {@code Stats} into this class for Jackson use.
     */
    public JsonAdaptedStats(Stats source) {
        this.csPerMinute.addAll(source.getCsPerMinute());
        this.goldDiffAt15.addAll(source.getGoldDiffAt15());
        this.kdaScores.addAll(source.getKdaScores());
        this.scores.addAll(source.getScores());
    }

    /**
     * Converts this Jackson-friendly adapted stats object into the model's {@code Stats} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted stats.
     */
    public Stats toModelType() throws IllegalValueException {
        // You can validate the fields if you want stricter data control
        if (csPerMinute == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "csPerMinute"));
        }
        if (goldDiffAt15 == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "goldDiffAt15"));
        }
        if (kdaScores == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "kdaScores"));
        }
        if (scores == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "scores"));
        }

        // Construct Stats model object directly
        return new Stats(
                new ArrayList<>(csPerMinute),
                new ArrayList<>(goldDiffAt15),
                new ArrayList<>(kdaScores),
                new ArrayList<>(scores)
        );
    }
}
