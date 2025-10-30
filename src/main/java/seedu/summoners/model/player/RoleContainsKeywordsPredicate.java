package seedu.summoners.model.player;

import java.util.List;
import java.util.function.Predicate;

import seedu.summoners.commons.util.StringUtil;
import seedu.summoners.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Player}'s {@code Role} matches any of the keywords given.
 */
public class RoleContainsKeywordsPredicate implements Predicate<Player> {
    private final List<String> keywords;

    public RoleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Player player) {
        if (keywords.isEmpty()) {
            return true;
        }

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(player.getRole().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RoleContainsKeywordsPredicate)) {
            return false;
        }

        RoleContainsKeywordsPredicate otherRoleContainsKeywordsPredicate = (RoleContainsKeywordsPredicate) other;
        return keywords.equals(otherRoleContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
