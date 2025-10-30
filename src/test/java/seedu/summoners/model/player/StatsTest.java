package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

public class StatsTest {

    private static final double EPS = 1e-6;

    // Helpers

    /** Mirrors Stats.calculateScore(float,int,float) for deterministic checks. */
    private static double expectedScore(float cpm, int gd15, float kda) {
        double kdaNorm = Math.min(kda / 3.0, 1.0);
        double csNorm = Math.min(cpm / 10.0, 1.0);
        double gdNorm = 1.0 / (1.0 + Math.exp(-gd15 / 500.0));
        double s = 10.0 * (0.45 * kdaNorm + 0.35 * csNorm + 0.20 * gdNorm);
        return Math.max(0.0, Math.min(s, 10.0));
    }

    /** Mirrors rounding in calculateAverageScore(): round to 1dp (float). */
    private static float round1(double v) {
        return (float) (Math.round(v * 10.0) / 10.0);
    }

    // Defaults & Basic Behavior

    @Test
    void defaultConstructor_initialState() {
        Stats stats = new Stats();
        assertEquals(0.0F, stats.getValue(), EPS);
        assertTrue(stats.getCsPerMinute().isEmpty());
        assertTrue(stats.getGoldDiffAt15().isEmpty());
        assertTrue(stats.getKdaScores().isEmpty());
        assertTrue(stats.getScores().isEmpty());
    }

    // Validation

    @Test
    void isValidStats_boundaries_valid() {
        assertTrue(Stats.isValidStats("0", "0", "0"));
        assertTrue(Stats.isValidStats("40", "10000", "200"));
        assertTrue(Stats.isValidStats("10.5", "-10000", "2.0"));
    }

    @Test
    void isValidStats_invalid() {
        // Non-numeric
        assertFalse(Stats.isValidStats("x", "0", "1"));
        assertFalse(Stats.isValidStats("1", "y", "1"));
        assertFalse(Stats.isValidStats("1", "0", "z"));

        // Out of range
        assertFalse(Stats.isValidStats("-0.1", "0", "0")); // cpm < 0
        assertFalse(Stats.isValidStats("40.1", "0", "0")); // cpm > 40
        assertFalse(Stats.isValidStats("0", "-10001", "0")); // gd15 < -10000
        assertFalse(Stats.isValidStats("0", "10001", "0")); // gd15 > 10000
        assertFalse(Stats.isValidStats("0", "0", "-0.01")); // kda < 0
        assertFalse(Stats.isValidStats("0", "0", "200.01")); // kda > 200
    }

    // Add & Average

    @Test
    void addLatestStats_appendsAndRecomputesAverage() {
        Stats s0 = new Stats();

        // First record
        Stats s1 = s0.addLatestStats("7.0", "1000", "2.2");
        assertNotEquals(s0, s1); // immutability-by-return-new
        assertEquals(List.of(7.0F), s1.getCsPerMinute());
        assertEquals(List.of(1000), s1.getGoldDiffAt15());
        assertEquals(List.of(2.2F), s1.getKdaScores());
        assertEquals(1, s1.getScores().size());

        double e1 = expectedScore(7.0F, 1000, 2.2F);
        assertEquals(round1(e1), s1.getValue(), EPS);

        // Second record
        Stats s2 = s1.addLatestStats("4.0", "-200", "0.7");
        assertNotEquals(s1, s2);
        assertEquals(2, s2.getCsPerMinute().size());
        double e2 = expectedScore(4.0F, -200, 0.7F);
        float avgRounded = round1((e1 + e2) / 2.0);
        assertEquals(avgRounded, s2.getValue(), EPS);
    }

    @Test
    void addLatestStats_invalid_throws() {
        Stats s0 = new Stats();
        assertThrows(IllegalArgumentException.class, () -> s0.addLatestStats("-1", "0", "1"));
        assertThrows(IllegalArgumentException.class, () -> s0.addLatestStats("0", "20000", "1"));
        assertThrows(IllegalArgumentException.class, () -> s0.addLatestStats("0", "0", "999"));
    }

    // Delete latest

    @Test
    void deleteLatestStats_onEmpty_returnsEqual() {
        Stats s0 = new Stats();
        Stats s1 = s0.deleteLatestStats();
        assertEquals(s0, s1); // equals compares lists + value
        assertEquals(0.0F, s1.getValue(), EPS);
    }

    @Test
    void deleteLatestStats_removesLast() {
        Stats s = new Stats()
                .addLatestStats("7.0", "1000", "2.2")
                .addLatestStats("9.0", "1200", "2.0");

        assertEquals(2, s.getScores().size());

        Stats sAfter = s.deleteLatestStats(); // removes 2nd record
        assertEquals(1, sAfter.getScores().size());

        double e1 = expectedScore(7.0F, 1000, 2.2F);
        assertEquals(round1(e1), sAfter.getValue(), EPS);
    }

    // Getters (defensive copies)

    @Test
    void getters_returnDefensiveCopies() {
        Stats s = new Stats().addLatestStats("5.0", "300", "1.2");

        int csSize = s.getCsPerMinute().size();
        int gdSize = s.getGoldDiffAt15().size();
        int kdSize = s.getKdaScores().size();
        int scSize = s.getScores().size();

        // Mutate returned lists
        List<Float> cs = s.getCsPerMinute();
        cs.add(9999F);
        List<Integer> gd = s.getGoldDiffAt15();
        gd.add(9999);
        List<Float> kd = s.getKdaScores();
        kd.add(9999F);
        List<Double> sc = s.getScores();
        sc.add(9999.0);

        // Internal sizes unchanged
        assertEquals(csSize, s.getCsPerMinute().size());
        assertEquals(gdSize, s.getGoldDiffAt15().size());
        assertEquals(kdSize, s.getKdaScores().size());
        assertEquals(scSize, s.getScores().size());
    }

    // equals/hashCode & toString

    @Test
    void equals_basic() {
        Stats a = new Stats()
                .addLatestStats("7.0", "1000", "2.2")
                .addLatestStats("4.0", "-200", "0.7");

        Stats b = new Stats()
                .addLatestStats("7.0", "1000", "2.2")
                .addLatestStats("4.0", "-200", "0.7");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());

        Stats c = b.deleteLatestStats();
        assertNotEquals(a, c);
    }

    @Test
    void toString_matchesValueString() {
        Stats s = new Stats()
                .addLatestStats("7.0", "1000", "2.2");
        assertEquals(Float.toString(s.getValue()), s.toString());
    }
}
