package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class ChampionContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("sion");
        List<String> secondPredicateKeywordList = Arrays.asList("ahri", "azir");

        ChampionContainsKeywordsPredicate firstPredicate =
                new ChampionContainsKeywordsPredicate(firstPredicateKeywordList);
        ChampionContainsKeywordsPredicate secondPredicate =
                new ChampionContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ChampionContainsKeywordsPredicate firstPredicateCopy =
                new ChampionContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_championContainsKeywords_returnsTrue() {
        // One keyword
        ChampionContainsKeywordsPredicate predicate =
                new ChampionContainsKeywordsPredicate(Collections.singletonList("lux"));
        assertTrue(predicate.test(new PersonBuilder().withChampion("lux").build()));

        // Multiple keywords
        predicate = new ChampionContainsKeywordsPredicate(Arrays.asList("zac", "zed"));
        assertTrue(predicate.test(new PersonBuilder().withChampion("zac").build()));
        assertTrue(predicate.test(new PersonBuilder().withChampion("zed").build()));

        // Only one matching keyword
        predicate = new ChampionContainsKeywordsPredicate(Arrays.asList("corki", "orianna"));
        assertTrue(predicate.test(new PersonBuilder().withChampion("orianna").build()));

        // Mixed-case keywords
        predicate = new ChampionContainsKeywordsPredicate(Arrays.asList("gArEN"));
        assertTrue(predicate.test(new PersonBuilder().withChampion("garen").build()));

        // Zero keywords
        predicate = new ChampionContainsKeywordsPredicate(Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withChampion("poppy").build()));
    }

    @Test
    public void test_championDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        ChampionContainsKeywordsPredicate predicate = new ChampionContainsKeywordsPredicate(Arrays.asList("trundle"));
        assertFalse(predicate.test(new PersonBuilder().withChampion("neeko").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("qiyana", "sivir");
        ChampionContainsKeywordsPredicate predicate = new ChampionContainsKeywordsPredicate(keywords);

        String expected = ChampionContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
