package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class RoleContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("mid");
        List<String> secondPredicateKeywordList = Arrays.asList("top", "mid");

        RoleContainsKeywordsPredicate firstPredicate = new RoleContainsKeywordsPredicate(firstPredicateKeywordList);
        RoleContainsKeywordsPredicate secondPredicate = new RoleContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RoleContainsKeywordsPredicate firstPredicateCopy = new RoleContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_roleContainsKeywords_returnsTrue() {
        // One keyword
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(Collections.singletonList("mid"));
        assertTrue(predicate.test(new PersonBuilder().withRole("mid").build()));

        // Multiple keywords
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("mid", "top"));
        assertTrue(predicate.test(new PersonBuilder().withRole("mid").build()));
        assertTrue(predicate.test(new PersonBuilder().withRole("top").build()));

        // Only one matching keyword
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("ADC", "jungle"));
        assertTrue(predicate.test(new PersonBuilder().withRole("ADC").build()));

        // Mixed-case keywords
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("SuPPorT"));
        assertTrue(predicate.test(new PersonBuilder().withRole("Support").build()));

        // Zero keywords
        predicate = new RoleContainsKeywordsPredicate(Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withRole("mid").build()));
    }

    @Test
    public void test_roleDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(Arrays.asList("mid"));
        assertFalse(predicate.test(new PersonBuilder().withRole("top").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("adc", "top");
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(keywords);

        String expected = RoleContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
