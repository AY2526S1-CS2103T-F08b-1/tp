package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Champion} matches any of the keywords given.
 */
public class ChampionContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public ChampionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        if (keywords.isEmpty()) {
            return true;
        }

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getChampion().value, keyword));
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
