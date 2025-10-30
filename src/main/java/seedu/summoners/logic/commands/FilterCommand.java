package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHAMPION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCORE;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Champion;
import seedu.address.model.person.ChampionContainsKeywordsPredicate;
import seedu.address.model.person.Rank;
import seedu.address.model.person.RankContainsKeywordsPredicate;
import seedu.address.model.person.Role;
import seedu.address.model.person.RoleContainsKeywordsPredicate;
import seedu.address.model.person.ScoreInRangePredicate;

/**
 * Filters the list of persons in the address book based on the specified criteria:
 * ranks, roles, champions, and/or score threshold. Only persons matching all
 * provided criteria will be included in the filtered list.
 *
 * <p>At least one filter criterion must be specified. If no criteria are provided,
 * a {@code ParseException} is thrown when parsing the command.
 *
 * <p>The filtered list is updated in the model, and the command returns a summary
 * message indicating the number of persons found.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filter out players based on their "
            + "rank, role and champion (case-insensitive)"
            + "and displays them as a list with index numbers.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_RANK + "RANK]... "
            + "[" + PREFIX_ROLE + "ROLE]... "
            + "[" + PREFIX_CHAMPION + "CHAMPION]... "
            + "[" + PREFIX_SCORE + "SCORE] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_RANK + "Diamond "
            + PREFIX_CHAMPION + "Yasuo"
            + PREFIX_SCORE + "2.4";

    public static final String MESSAGE_NOT_FILTERED = "At least one field to filter must be provided.";

    private final RankContainsKeywordsPredicate rankPredicate;
    private final RoleContainsKeywordsPredicate rolePredicate;
    private final ChampionContainsKeywordsPredicate championPredicate;
    private final ScoreInRangePredicate scorePredicate;

    private final FilterPersonDescriptor filterPersonDescriptor;

    /**
     * @param filterPersonDescriptor details to edit the person with
     */
    public FilterCommand(FilterPersonDescriptor filterPersonDescriptor) {
        requireNonNull(filterPersonDescriptor);
        this.filterPersonDescriptor = new FilterPersonDescriptor(filterPersonDescriptor);
        this.rankPredicate = new RankContainsKeywordsPredicate(List.of(filterPersonDescriptor.getRanks()));
        this.rolePredicate = new RoleContainsKeywordsPredicate(List.of(filterPersonDescriptor.getRoles()));
        this.championPredicate = new ChampionContainsKeywordsPredicate(List.of(filterPersonDescriptor.getChampions()));
        this.scorePredicate = new ScoreInRangePredicate(filterPersonDescriptor.getScoreThreshold());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(
                rankPredicate.and(rolePredicate).and(championPredicate).and(scorePredicate)
        );
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherFilterCommand = (FilterCommand) other;
        return filterPersonDescriptor.equals(otherFilterCommand.filterPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("filterPersonDescriptor", filterPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class FilterPersonDescriptor {
        private static final float DEFAULT_SCORE_THRESHOLD = 0.0F;

        private Set<Role> roles;
        private Set<Rank> ranks;
        private Set<Champion> champions;
        private Float scoreThreshold = DEFAULT_SCORE_THRESHOLD;

        public FilterPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public FilterPersonDescriptor(FilterPersonDescriptor toCopy) {
            setRoles(toCopy.roles);
            setRanks(toCopy.ranks);
            setChampions(toCopy.champions);
            setScoreThreshold(toCopy.scoreThreshold);
        }

        /**
         * Returns true if at least one field is filtered.
         */
        public boolean isAnyFieldFiltered() {
            boolean x = CollectionUtil.isAnyNonNull(roles, ranks, champions);
            boolean y = scoreThreshold != null && scoreThreshold > DEFAULT_SCORE_THRESHOLD;
            return x || y;
        }

        /**
         * Sets {@code roles} to this object's {@code roles}.
         * A defensive copy of {@code roles} is used internally.
         */
        public void setRoles(Set<Role> roles) {
            this.roles = (roles != null) ? new HashSet<>(roles) : null;
        }

        /**
         * Returns an array of role names as Strings.
         * Returns an empty array if {@code roles} is null or empty.
         */
        public String[] getRoles() {
            if (roles == null) {
                return new String[0];
            }
            return roles.stream()
                    .map(Role::toString) // or Role::getName if you have that method
                    .toArray(String[]::new);
        }

        /**
         * Sets {@code ranks} to this object's {@code ranks}.
         * A defensive copy of {@code ranks} is used internally.
         */
        public void setRanks(Set<Rank> ranks) {
            this.ranks = (ranks != null) ? new HashSet<>(ranks) : null;
        }

        /**
         * Returns an array of rank names as Strings.
         * Returns an empty array if {@code ranks} is null or empty.
         */
        public String[] getRanks() {
            if (ranks == null) {
                return new String[0];
            }
            return ranks.stream()
                    .map(Rank::toString) // or Role::getName if you have that method
                    .toArray(String[]::new);
        }

        /**
         * Sets {@code champions} to this object's {@code champions}.
         * A defensive copy of {@code champions} is used internally.
         */
        public void setChampions(Set<Champion> champions) {
            this.champions = (champions != null) ? new HashSet<>(champions) : null;
        }

        /**
         * Returns an array of champion names as Strings.
         * Returns an empty array if {@code champions} is null or empty.
         */
        public String[] getChampions() {
            if (champions == null) {
                return new String[0];
            }
            return champions.stream()
                    .map(Champion::toString) // or Champion::getName if you have that method
                    .toArray(String[]::new);
        }

        public void setScoreThreshold(Float scoreThreshold) {
            this.scoreThreshold = scoreThreshold;
        }

        public Float getScoreThreshold() {
            return scoreThreshold;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof FilterPersonDescriptor)) {
                return false;
            }

            FilterPersonDescriptor otherFilterPersonDescriptor = (FilterPersonDescriptor) other;
            return Objects.equals(roles, otherFilterPersonDescriptor.roles)
                    && Objects.equals(ranks, otherFilterPersonDescriptor.ranks)
                    && Objects.equals(champions, otherFilterPersonDescriptor.champions)
                    && Objects.equals(scoreThreshold, otherFilterPersonDescriptor.scoreThreshold);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("roles", Arrays.toString(getRoles()))
                    .add("ranks", Arrays.toString(getRanks()))
                    .add("champions", Arrays.toString(getChampions()))
                    .add("scoreThreshold", scoreThreshold != null ? scoreThreshold.toString() : "0.0")
                    .toString();
        }
    }
}
