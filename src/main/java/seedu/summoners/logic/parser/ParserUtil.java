package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Champion;
import seedu.address.model.person.Name;
import seedu.address.model.person.Rank;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String indices} of space-separated indices into a {@code List<Index>}.
     * Leading and trailing whitespace will be trimmed.
     *
     * @throws ParseException if any of the specified indices are invalid (not a positive integer).
     */
    public static List<Index> parseIndices(String indices) throws ParseException {
        final String trimmedIndices = indices.trim();
        final String[] indexStrings = trimmedIndices.split("\\s+");

        final List<Index> result = new ArrayList<>();
        for (String indexStr : indexStrings) {
            result.add(parseIndex(indexStr));
        }
        return result;
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String champion} into a {@code Champion}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code champion} is invalid.
     */
    public static Champion parseChampion(String champion) throws ParseException {
        requireNonNull(champion);
        String trimmedChampion = champion.trim();
        if (!Champion.isValidChampion(trimmedChampion)) {
            throw new ParseException(Champion.MESSAGE_CONSTRAINTS);
        }
        return new Champion(trimmedChampion);
    }

    /**
     * Parses {@code Collection<String> champions} into a {@code Set<Champion>}.
     */
    public static Set<Champion> parseChampions(Collection<String> champions) throws ParseException {
        requireNonNull(champions);
        final Set<Champion> championSet = new HashSet<>();
        for (String championName : champions) {
            championSet.add(parseChampion(championName));
        }
        return championSet;
    }

    /**
     * Parses a {@code String rank} into a {@code Rank}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code rank} is invalid.
     */
    public static Rank parseRank(String rank) throws ParseException {
        requireNonNull(rank);
        String trimmedRank = rank.trim();
        if (!Rank.isValidRank(trimmedRank)) {
            throw new ParseException(Rank.MESSAGE_CONSTRAINTS);
        }
        return new Rank(trimmedRank);
    }

    /**
     * Parses {@code Collection<String> ranks} into a {@code Set<Rank>}.
     */
    public static Set<Rank> parseRanks(Collection<String> ranks) throws ParseException {
        requireNonNull(ranks);
        final Set<Rank> rankSet = new HashSet<>();
        for (String rankName : ranks) {
            rankSet.add(parseRank(rankName));
        }
        return rankSet;
    }

    /**
     * Parses a {@code String role} into a {@code Role}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code role} is invalid.
     */
    public static Role parseRole(String role) throws ParseException {
        requireNonNull(role);
        String trimmedRole = role.trim();
        if (!Role.isValidRole(trimmedRole)) {
            throw new ParseException(Role.MESSAGE_CONSTRAINTS);
        }
        return new Role(trimmedRole);
    }

    /**
     * Parses {@code Collection<String> roles} into a {@code Set<Role>}.
     */
    public static Set<Role> parseRoles(Collection<String> roles) throws ParseException {
        requireNonNull(roles);
        final Set<Role> roleSet = new HashSet<>();
        for (String roleName : roles) {
            roleSet.add(parseRole(roleName));
        }
        return roleSet;
    }

    /**
     * Parses a {@code String score} into a {@code Float}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code score} is invalid (not a float or negative).
     */
    public static Float parseScore(String score) throws ParseException {
        requireNonNull(score);
        String trimmedScore = score.trim();
        if (trimmedScore.isEmpty()) {
            throw new ParseException("Score cannot be empty.");
        }

        try {
            float parsedScore = Float.parseFloat(trimmedScore);
            if (parsedScore <= 0.0f) {
                throw new ParseException("Score must be a positive number.");
            }
            if (parsedScore > 10.0f) {
                throw new ParseException("Score must be at most 10.");
            }
            return parsedScore;
        } catch (NumberFormatException e) {
            throw new ParseException("Score must be a valid number: " + trimmedScore);
        }
    }
}
