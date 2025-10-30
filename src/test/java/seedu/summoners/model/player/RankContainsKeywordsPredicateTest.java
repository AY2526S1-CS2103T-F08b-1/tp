package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class RankContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("diamond");
        List<String> secondPredicateKeywordList = Arrays.asList("diamond", "challenger");

        RankContainsKeywordsPredicate firstPredicate = new RankContainsKeywordsPredicate(firstPredicateKeywordList);
        RankContainsKeywordsPredicate secondPredicate = new RankContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RankContainsKeywordsPredicate firstPredicateCopy = new RankContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_rankContainsKeywords_returnsTrue() {
        // One keyword
        RankContainsKeywordsPredicate predicate = new RankContainsKeywordsPredicate(Collections.singletonList("gold"));
        assertTrue(predicate.test(new PersonBuilder().withRank("gold").build()));

        // Multiple keywords
        predicate = new RankContainsKeywordsPredicate(Arrays.asList("silver", "gold"));
        assertTrue(predicate.test(new PersonBuilder().withRank("silver").build()));
        assertTrue(predicate.test(new PersonBuilder().withRank("gold").build()));

        // Only one matching keyword
        predicate = new RankContainsKeywordsPredicate(Arrays.asList("diamond", "challenger"));
        assertTrue(predicate.test(new PersonBuilder().withRank("challenger").build()));

        // Mixed-case keywords
        predicate = new RankContainsKeywordsPredicate(Arrays.asList("gRanDmaSTer"));
        assertTrue(predicate.test(new PersonBuilder().withRank("Grandmaster").build()));

        // Zero keywords
        predicate = new RankContainsKeywordsPredicate(Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withRank("gold").build()));
    }

    @Test
    public void test_rankDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        RankContainsKeywordsPredicate predicate = new RankContainsKeywordsPredicate(Arrays.asList("Challenger"));
        assertFalse(predicate.test(new PersonBuilder().withRank("diamond").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("gold", "silver");
        RankContainsKeywordsPredicate predicate = new RankContainsKeywordsPredicate(keywords);

        String expected = RankContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
