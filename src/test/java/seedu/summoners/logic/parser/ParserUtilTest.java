package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Champion;
import seedu.address.model.person.Name;
import seedu.address.model.person.Rank;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_ROLE = "Sky";
    private static final String INVALID_RANK = "Wood";
    private static final String INVALID_CHAMPION = "Aniga";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_ROLE_1 = "Mid";
    private static final String VALID_ROLE_2 = "top";
    private static final String VALID_RANK_1 = "Gold";
    private static final String VALID_RANK_2 = "diamond";
    private static final String VALID_CHAMPION_1 = "Ahri";
    private static final String VALID_CHAMPION_2 = "Garen";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseRole_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRole((String) null));
    }

    @Test
    public void parseRole_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRole(INVALID_ROLE));
    }

    @Test
    public void parseRole_validValueWithoutWhitespace_returnsRole() throws Exception {
        Role expectedRole = new Role(VALID_ROLE_1);
        assertEquals(expectedRole, ParserUtil.parseRole(VALID_ROLE_1));
    }

    @Test
    public void parseRole_validValueWithWhitespace_returnsTrimmedRole() throws Exception {
        String roleWithWhitespace = WHITESPACE + VALID_ROLE_1 + WHITESPACE;
        Role expectedRole = new Role(VALID_ROLE_1);
        assertEquals(expectedRole, ParserUtil.parseRole(roleWithWhitespace));
    }

    @Test
    public void parseRoles_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRoles(null));
    }

    @Test
    public void parseRoles_collectionWithInvalidRoles_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRoles(
                Arrays.asList(VALID_ROLE_1, INVALID_ROLE)));
    }

    @Test
    public void parseRoles_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseRoles(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseRoles_collectionWithValidRoles_returnsRoleset() throws Exception {
        Set<Role> actualRoleset = ParserUtil.parseRoles(
                Arrays.asList(VALID_ROLE_1, VALID_ROLE_2));
        Set<Role> expectedRoleset = new HashSet<Role>(
                Arrays.asList(new Role(VALID_ROLE_1), new Role(VALID_ROLE_2)));

        assertEquals(expectedRoleset, actualRoleset);
    }

    @Test
    public void parseRank_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRank((String) null));
    }

    @Test
    public void parseRank_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRank(INVALID_RANK));
    }

    @Test
    public void parseRank_validValueWithoutWhitespace_returnsRank() throws Exception {
        Rank expectedRank = new Rank(VALID_RANK_1);
        assertEquals(expectedRank, ParserUtil.parseRank(VALID_RANK_1));
    }

    @Test
    public void parseRank_validValueWithWhitespace_returnsTrimmedRank() throws Exception {
        String rankWithWhitespace = WHITESPACE + VALID_RANK_1 + WHITESPACE;
        Rank expectedRank = new Rank(VALID_RANK_1);
        assertEquals(expectedRank, ParserUtil.parseRank(rankWithWhitespace));
    }

    @Test
    public void parseRanks_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRanks(null));
    }

    @Test
    public void parseRanks_collectionWithInvalidRanks_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRanks(
                Arrays.asList(VALID_RANK_1, INVALID_RANK)));
    }

    @Test
    public void parseRanks_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseRanks(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseRanks_collectionWithValidRanks_returnsRankset() throws Exception {
        Set<Rank> actualRankset = ParserUtil.parseRanks(
                Arrays.asList(VALID_RANK_1, VALID_RANK_2));
        Set<Rank> expectedRankset = new HashSet<Rank>(
                Arrays.asList(new Rank(VALID_RANK_1), new Rank(VALID_RANK_2)));

        assertEquals(expectedRankset, actualRankset);
    }

    @Test
    public void parseChampion_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseChampion((String) null));
    }

    @Test
    public void parseChampion_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseChampion(INVALID_CHAMPION));
    }

    @Test
    public void parseChampion_validValueWithoutWhitespace_returnsChampion() throws Exception {
        Champion expectedEmail = new Champion(VALID_CHAMPION_1);
        assertEquals(expectedEmail, ParserUtil.parseChampion(VALID_CHAMPION_1));
    }

    @Test
    public void parseChampion_validValueWithWhitespace_returnsTrimmedChampion() throws Exception {
        String championWithWhitespace = WHITESPACE + VALID_CHAMPION_1 + WHITESPACE;
        Champion expectedChampion = new Champion(VALID_CHAMPION_1);
        assertEquals(expectedChampion, ParserUtil.parseChampion(championWithWhitespace));
    }

    @Test
    public void parseChampions_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseChampions(null));
    }

    @Test
    public void parseChampions_collectionWithInvalidChampions_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseChampions(
                Arrays.asList(VALID_CHAMPION_1, INVALID_CHAMPION)));
    }

    @Test
    public void parseChampions_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseChampions(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseChampions_collectionWithValidChampions_returnsChampionset() throws Exception {
        Set<Champion> actualChampionset = ParserUtil.parseChampions(
                Arrays.asList(VALID_CHAMPION_1, VALID_CHAMPION_2));
        Set<Champion> expectedChampionset = new HashSet<Champion>(
                Arrays.asList(new Champion(VALID_CHAMPION_1), new Champion(VALID_CHAMPION_2)));

        assertEquals(expectedChampionset, actualChampionset);
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseScore_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseScore(null));
    }

    @Test
    public void parseScore_emptyOrWhitespace_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseScore(""));
        assertThrows(ParseException.class, () -> ParserUtil.parseScore("   "));
    }

    @Test
    public void parseScore_validScores_success() throws Exception {
        assertEquals(0.1f, ParserUtil.parseScore("0.1"));
        assertEquals(1f, ParserUtil.parseScore("1"));
        assertEquals(5.5f, ParserUtil.parseScore("5.5"));
        assertEquals(10f, ParserUtil.parseScore("10"));
    }

    @Test
    public void parseScore_validScoresWithWhitespace_success() throws Exception {
        assertEquals(3.5f, ParserUtil.parseScore("   3.5  "));
        assertEquals(10f, ParserUtil.parseScore("\t10\n"));
    }

    @Test
    public void parseScore_invalidScores_throwsParseException() {
        // Zero is not allowed
        assertThrows(ParseException.class, () -> ParserUtil.parseScore("0"));
        assertThrows(ParseException.class, () -> ParserUtil.parseScore("0.0"));

        // Negative numbers are not allowed
        assertThrows(ParseException.class, () -> ParserUtil.parseScore("-0.1"));
        assertThrows(ParseException.class, () -> ParserUtil.parseScore("-5"));

        // Numbers above 10 are not allowed
        assertThrows(ParseException.class, () -> ParserUtil.parseScore("10.1"));
        assertThrows(ParseException.class, () -> ParserUtil.parseScore("11"));
        assertThrows(ParseException.class, () -> ParserUtil.parseScore("100"));

        // Non-numeric strings are not allowed
        assertThrows(ParseException.class, () -> ParserUtil.parseScore("abc"));
        assertThrows(ParseException.class, () -> ParserUtil.parseScore("5a"));
        assertThrows(ParseException.class, () -> ParserUtil.parseScore("!@#"));
    }
}
