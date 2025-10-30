package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's rank in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRank(String)}
 * Implements Comparable to allow natural ordering from lowest (Iron) to highest (Challenger).
 */
public class Rank implements Comparable<Rank> {

    private enum RankName {
        IRON("iron", 0),
        BRONZE("bronze", 1),
        SILVER("silver", 2),
        GOLD("gold", 3),
        PLATINUM("platinum", 4),
        EMERALD("emerald", 5),
        DIAMOND("diamond", 6),
        MASTER("master", 7),
        GRANDMASTER("grandmaster", 8),
        CHALLENGER("challenger", 9);

        private final String name;
        private final int order;

        RankName(String name, int order) {
            this.name = name;
            this.order = order;
        }

        public String getName() {
            return name;
        }

        public int getOrder() {
            return order;
        }
    }

    public static final String MESSAGE_CONSTRAINTS =
            "Rank must be one of the following: iron, bronze, silver, gold, platinum, "
                    + "emerald, diamond, master, grandmaster, challenger.";

    public final String value;

    /**
     * Constructs a {@code Rank }.
     *
     * @param rank A valid rank.
     */
    public Rank(String rank) {
        requireNonNull(rank);
        checkArgument(isValidRank(rank), MESSAGE_CONSTRAINTS);
        this.value = rank.substring(0, 1).toUpperCase() + rank.substring(1).toLowerCase();
    }

    /**
     * Returns true if a given string is a valid RankName.
     */
    public static boolean isValidRank(String test) {
        for (RankName r : RankName.values()) {
            if (r.getName().equalsIgnoreCase(test)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Rank)) {
            return false;
        }

        Rank otherRank = (Rank) other;
        return value.equals(otherRank.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Compares this rank with another rank for order.
     * Returns a negative integer, zero, or a positive integer as this rank is
     * less than, equal to, or greater than the specified rank.
     *
     * @param other The rank to be compared.
     * @return A negative integer, zero, or a positive integer as this rank is
     *         less than, equal to, or greater than the specified rank.
     */
    @Override
    public int compareTo(Rank other) {
        return Integer.compare(getRankOrder(), other.getRankOrder());
    }

    /**
     * Returns the ordinal value of this rank (0 for Iron, 9 for Challenger).
     * Used for comparison purposes.
     */
    private int getRankOrder() {
        for (RankName rankName : RankName.values()) {
            if (rankName.getName().equalsIgnoreCase(this.value)) {
                return rankName.getOrder();
            }
        }
        throw new AssertionError("Valid rank should always be found in RankName enum");
    }

}
