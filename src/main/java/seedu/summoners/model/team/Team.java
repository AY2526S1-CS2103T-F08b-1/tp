package seedu.address.model.team;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.team.exceptions.DuplicateChampionException;
import seedu.address.model.team.exceptions.DuplicateRoleException;
import seedu.address.model.team.exceptions.InvalidTeamSizeException;

/**
 * Represents a Team in the summoners book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 * A team must have exactly 5 persons with unique roles (Top, Jungle, Mid, ADC, Support).
 */
public class Team {

    public static final int TEAM_SIZE = 5;
    public static final String MESSAGE_CONSTRAINTS =
            "A team must have exactly 5 persons with unique roles and unique champions.";
    private static final java.util.Map<String, Integer> ROLE_ORDER = new java.util.LinkedHashMap<>();

    static {
        ROLE_ORDER.put("top", 0);
        ROLE_ORDER.put("jungle", 1);
        ROLE_ORDER.put("mid", 2);
        ROLE_ORDER.put("adc", 3);
        ROLE_ORDER.put("support", 4);
    }

    // Identity fields
    private final String id;

    // Data fields
    private final List<Person> persons;

    // Stat fields
    private final int wins;
    private final int losses;

    /**
     * Constructor for creating a new Team with a randomly generated unique ID.
     *
     * @param persons List of 5 persons for the team.
     */
    public Team(List<Person> persons) {
        this(UUID.randomUUID().toString(), persons, 0, 0);
    }

    /**
     * Constructor for creating a Team with an explicit ID.
     * This is used for deserialization from JSON to preserve the original ID.
     *
     * @param id      Unique identifier for the team.
     * @param persons List of 5 persons for the team.
     */
    public Team(String id, List<Person> persons, int wins, int losses) {
        requireAllNonNull(id, persons);
        validateTeamComposition(persons);
        this.id = id;
        this.persons = new ArrayList<>(persons);
        this.wins = wins;
        this.losses = losses;
    }

    /**
     * Returns the numeric index of a person's role based on a fixed lane order
     * (Top → Jungle → Mid → Adc → Support).
     * <p>
     * Used to sort team members consistently in {@link #toDisplayString()}.
     * Roles not found in {@link #ROLE_ORDER} are assigned a high index (999)
     * so they appear last in the sorted order.
     *
     * @param p The person whose role index to retrieve.
     * @return An integer representing the role's position in the fixed order.
     */
    private static int roleIndex(seedu.address.model.person.Person p) {
        String roleStr = p.getRole().toString();
        return ROLE_ORDER.getOrDefault(roleStr.toLowerCase(), 999);
    }


    /**
     * Validates that the team has exactly 5 persons with unique roles and unique champions.
     *
     * @param persons List of persons to validate.
     * @throws InvalidTeamSizeException   if team does not have exactly 5 persons.
     * @throws DuplicateRoleException     if team has duplicate roles.
     * @throws DuplicateChampionException if team has duplicate champions.
     */
    private void validateTeamComposition(List<Person> persons) {
        // Check team size
        if (persons.size() != TEAM_SIZE) {
            throw new InvalidTeamSizeException(persons.size());
        }

        // Pairwise conflict check for all persons
        for (int i = 0; i < persons.size(); i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                checkConflict(persons.get(i), persons.get(j));
            }
        }
    }

    /**
     * Checks if two persons have a conflict for team composition.
     * A conflict occurs when two persons have the same role or the same champion.
     *
     * @param firstPerson  First person to check.
     * @param secondPerson Second person to check.
     * @throws DuplicateRoleException     if both persons have the same role.
     * @throws DuplicateChampionException if both persons have the same champion.
     */
    private void checkConflict(Person firstPerson, Person secondPerson) {
        // Check for duplicate role
        if (firstPerson.getRole().equals(secondPerson.getRole())) {
            throw new DuplicateRoleException(firstPerson, secondPerson);
        }

        // Check for duplicate champion
        if (firstPerson.getChampion().equals(secondPerson.getChampion())) {
            throw new DuplicateChampionException(firstPerson, secondPerson);
        }
    }

    public String getId() {
        return id;
    }

    /**
     * Returns an immutable list of persons.
     */
    public List<Person> getPersons() {
        return new ArrayList<>(persons);
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    /**
     * Returns a formatted string representation of the team for display purposes.
     * Shows team members in the format: Name1 (Role1), Name2 (Role2), ...
     */
    public String toDisplayString() {
        return persons.stream()
                .sorted(java.util.Comparator.comparingInt(Team::roleIndex))
                .map(person -> String.format("%s (%s)", person.getName(), person.getRole()))
                .collect(java.util.stream.Collectors.joining(", "));
    }


    /**
     * Returns true if this team contains the specified person.
     */
    public boolean hasPerson(Person person) {
        return persons.contains(person);
    }

    /**
     * Returns true if both teams have the same persons.
     * This defines a weaker notion of equality between two teams.
     * <p>
     * It is provided for potential future extensions where partial team comparison may be required.
     * Currently, it behaves identically to {@link #equals(Object)}
     */
    public boolean isSameTeam(Team otherTeam) {
        return this.equals(otherTeam);
    }

    /**
     * Returns true if both teams have the same data fields.
     * This defines a stronger notion of equality between two teams.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Team)) {
            return false;
        }

        Team otherTeam = (Team) other;
        return persons.equals(otherTeam.persons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons);
    }

    @Override
    public String toString() {
        String personsString = persons.stream()
                .map(Person::toString)
                .collect(java.util.stream.Collectors.joining(", "));
        return new ToStringBuilder(this.getClass().getSimpleName())
                .add("persons", personsString)
                .toString();
    }
}
