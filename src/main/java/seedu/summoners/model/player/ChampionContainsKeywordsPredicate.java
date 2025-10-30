package seedu.summoners.model.player;

import java.util.List;
import java.util.function.Predicate;

import seedu.summoners.commons.util.StringUtil;
import seedu.summoners.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Player}'s {@code Champion} matches any of the keywords given.
 */
public class ChampionContainsKeywordsPredicate implements Predicate<Player> {
    private final List<String> keywords;

    public ChampionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Player player) {
        if (keywords.isEmpty()) {
            return true;
        }

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(player.getChampion().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ChampionContainsKeywordsPredicate)) {
            return false;
        }

        ChampionContainsKeywordsPredicate otherChampionContainsKeywordsPredicate =
                (ChampionContainsKeywordsPredicate) other;
        return keywords.equals(otherChampionContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
