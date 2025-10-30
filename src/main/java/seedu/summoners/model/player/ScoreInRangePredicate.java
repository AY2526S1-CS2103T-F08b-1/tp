package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code score} is greater than or equal to the given threshold.
 */
public class ScoreInRangePredicate implements Predicate<Person> {

    private final Float threshold;

    public ScoreInRangePredicate(float threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean test(Person person) {
        return person.getStats().value >= threshold;
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
