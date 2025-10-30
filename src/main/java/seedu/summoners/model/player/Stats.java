package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

/**
 * Represents a person's performance statistics in the address book.
 * <p>
 * A {@code Stats} object stores cumulative information about a person's
 * game metrics across matches — specifically:
 * <ul>
 *     <li>CS per minute (creep score efficiency)</li>
 *     <li>Gold difference at 15 minutes</li>
 *     <li>KDA (Kill/Death/Assist ratio)</li>
 *     <li>Calculated performance scores based on these values</li>
 * </ul>
 * <p>
 * The class is immutable in its exposed API. Each update returns a new
 * {@code Stats} object containing extended data arrays and a recalculated
 * average score.
 */
public class Stats {
    /**
     * Constraints message shown when invalid stat values are provided.
     */
    public static final String MESSAGE_CONSTRAINTS =
            "CS per minute must be an integer between 0 and 40; "
                    + "Gold difference at 15m must be a decimal between -10,000 and 10,000; "
                    + "KDA must be a decimal between 0.0 and 200.0";

    /** The average score. */
    public final float value;

    /** Historical list of CS per minute values recorded. */
    public final ArrayList<Float> csPerMinute;

    /** Historical list of gold difference at 15-minute values recorded. */
    public final ArrayList<Integer> goldDiffAt15;

    /** Historical list of KDA scores recorded. */
    public final ArrayList<Float> kdaScores;

    /** List of individual calculated performance scores. */
    public final ArrayList<Double> scores;

    /**
     * Constructs an empty {@code Stats} object with no recorded games.
     * Initializes empty lists and sets the average value to "0.0".
     */
    public Stats() {
        this.csPerMinute = new ArrayList<>();
        this.goldDiffAt15 = new ArrayList<>();
        this.kdaScores = new ArrayList<>();
        this.scores = new ArrayList<>();
        this.value = calculateAverageScore();
    }

    /**
     * Constructor used for updating stats immutably.
     */
    public Stats(ArrayList<Float> csPerMinute,
                 ArrayList<Integer> goldDiffAt15,
                 ArrayList<Float> kdaScores,
                 ArrayList<Double> scores) {
        this.csPerMinute = csPerMinute;
        this.goldDiffAt15 = goldDiffAt15;
        this.kdaScores = kdaScores;
        this.scores = scores;
        this.value = calculateAverageScore();
    }

    /**
     * Returns a new {@code Stats} instance with the given CPM, GD15, and KDA values appended.
     * Performs validation and recalculates the average performance score.
     *
     * @param cpm CS per minute as a string
     * @param gd15 Gold difference at 15 minutes as a string
     * @param kda KDA as a string
     * @return a new {@code Stats} object with updated lists and average
     * @throws IllegalArgumentException if any of the values are invalid
     */
    public Stats addLatestStats(String cpm, String gd15, String kda) {
        requireAllNonNull(cpm, gd15, kda);
        checkArgument(isValidStats(cpm, gd15, kda), MESSAGE_CONSTRAINTS);
        // Already checked conversion
        float floatCpm = Float.parseFloat(cpm);
        int intGd15 = Integer.parseInt(gd15);
        float floatKda = Float.parseFloat(kda);
        double newScore = calculateScore(floatCpm, intGd15, floatKda);

        var cs = new ArrayList<>(this.csPerMinute);
        var gd = new ArrayList<>(this.goldDiffAt15);
        var kd = new ArrayList<>(this.kdaScores);
        var sc = new ArrayList<>(this.scores);

        cs.add(floatCpm);
        gd.add(intGd15);
        kd.add(floatKda);
        sc.add(newScore);

        return new Stats(cs, gd, kd, sc);
    }

    /**
     * @return a new {@code Stats} object with lists where the last element is removed
     */
    public Stats deleteLatestStats() {
        var cs = new ArrayList<>(this.csPerMinute);
        var gd = new ArrayList<>(this.goldDiffAt15);
        var kd = new ArrayList<>(this.kdaScores);
        var sc = new ArrayList<>(this.scores);

        if (!cs.isEmpty()) {
            cs.remove(cs.size() - 1);
            gd.remove(gd.size() - 1);
            kd.remove(kd.size() - 1);
            sc.remove(sc.size() - 1);
        }

        return new Stats(cs, gd, kd, sc);
    }

    /**
     * Returns the current average performance score as a string formatted to one decimal place.
     */
    public float getValue() {
        return this.value;
    }

    public ArrayList<Float> getCsPerMinute() {
        return new ArrayList<>(csPerMinute);
    }

    public ArrayList<Integer> getGoldDiffAt15() {
        return new ArrayList<>(goldDiffAt15);
    }

    public ArrayList<Float> getKdaScores() {
        return new ArrayList<>(kdaScores);
    }

    public ArrayList<Double> getScores() {
        return new ArrayList<>(scores);
    }

    /**
     * Validates whether the given values fall within the allowed constraints.
     *
     * @param cpm  CS per minute
     * @param gd15 Gold difference at 15 minutes
     * @param kda  KDA
     * @return true if all values are within valid ranges; false otherwise
     */
    public static boolean isValidStats(String cpm, String gd15, String kda) {
        float x;
        int y;
        float z;
        try {
            x = Float.parseFloat(cpm);
            y = Integer.parseInt(gd15);
            z = Float.parseFloat(kda);
        } catch (NumberFormatException e) {
            return false;
        }

        // Check cpm constraint
        if (x < 0 || x > 40) {
            return false;
        }

        // Check gd15 constraint
        if (y < -10000 || y > 10000) {
            return false;
        }

        // Check kda constraint
        if (z < 0 || z > 200) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return String.format("%.1f", value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Stats)) {
            return false;
        }

        Stats otherStats = (Stats) other;
        return csPerMinute.equals(otherStats.csPerMinute)
                && goldDiffAt15.equals(otherStats.goldDiffAt15)
                && kdaScores.equals(otherStats.kdaScores)
                && Float.compare(value, otherStats.value) == 0;
    }

    @Override
    public int hashCode() {
        return Float.hashCode(value);
    }

    /**
     * Calculates a single-game performance score (0–10) based on the provided stats.
     * The score uses normalized KDA, CS/min, and gold difference values with
     * logistic scaling for smoother distribution.
     */
    private double calculateScore(float cpm, int gd15, float kda) {
        // Normalize each metric
        double kdaNorm = Math.min(kda / 3.0, 1.0);
        double csNorm = Math.min(cpm / 10.0, 1.0);

        // Logistic scaling for gold difference
        double gdNorm = 1.0 / (1.0 + Math.exp(-gd15 / 500.0));

        // Weighted combination
        double score = 10.0 * (0.45 * kdaNorm + 0.35 * csNorm + 0.20 * gdNorm);

        // Keep score within bounds [0,10]
        return Math.max(0.0, Math.min(score, 10.0));
    }

    /**
     * Computes the average of all recorded scores and returns it formatted to one decimal place.
     */
    private float calculateAverageScore() {
        if (scores.isEmpty()) {
            return 0.0F;
        }
        double total = scores.stream()
                .reduce(0.0, Double::sum);

        double avg = total / scores.size();
        return (float) (Math.round(avg * 10.0) / 10.0);
    }
}
