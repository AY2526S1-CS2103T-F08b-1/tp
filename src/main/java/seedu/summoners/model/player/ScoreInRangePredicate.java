package seedu.summoners.model.player;

import java.util.function.Predicate;

import seedu.summoners.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Player}'s {@code score} is greater than or equal to the given threshold.
 */
public class ScoreInRangePredicate implements Predicate<Player> {

    private final Float threshold;

    public ScoreInRangePredicate(float threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean test(Player player) {
        return player.getStats().value >= threshold;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ScoreInRangePredicate)) {
            return false;
        }

        ScoreInRangePredicate otherPredicate = (ScoreInRangePredicate) other;
        return Double.compare(threshold, otherPredicate.threshold) == 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("threshold", threshold)
                .toString();
    }
}
