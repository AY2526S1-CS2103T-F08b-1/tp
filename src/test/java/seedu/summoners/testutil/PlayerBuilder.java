package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.person.Champion;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Rank;
import seedu.address.model.person.Role;
import seedu.address.model.person.Stats;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_ROLE = "mid";
    public static final String DEFAULT_RANK = "gold";
    public static final String DEFAULT_CHAMPION = "Ahri";
    public static final int DEFAULT_WINS = 0;
    public static final int DEFAULT_LOSSES = 0;

    private String id;
    private Name name;
    private Role role;
    private Rank rank;
    private Champion champion;
    private Set<Tag> tags;
    private int wins;
    private int losses;
    private Stats stats;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        id = UUID.randomUUID().toString();
        name = new Name(DEFAULT_NAME);
        role = new Role(DEFAULT_ROLE);
        rank = new Rank(DEFAULT_RANK);
        champion = new Champion(DEFAULT_CHAMPION);
        tags = new HashSet<>();
        wins = DEFAULT_WINS;
        losses = DEFAULT_LOSSES;
        stats = new Stats();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        id = personToCopy.getId();
        name = personToCopy.getName();
        role = personToCopy.getRole();
        rank = personToCopy.getRank();
        champion = personToCopy.getChampion();
        tags = new HashSet<>(personToCopy.getTags());
        wins = personToCopy.getWins();
        losses = personToCopy.getLosses();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Person} that we are building.
     */
    public PersonBuilder withRole(String role) {
        this.role = new Role(role);
        return this;
    }

    /**
     * Sets the {@code Rank} of the {@code Person} that we are building.
     */
    public PersonBuilder withRank(String rank) {
        this.rank = new Rank(rank);
        return this;
    }

    /**
     * Sets the {@code Champion} of the {@code Person} that we are building.
     */
    public PersonBuilder withChampion(String champion) {
        this.champion = new Champion(champion);
        return this;
    }

    /**
     * Sets the {@code Stats} of the {@code Person} that we are building.
     */
    public PersonBuilder withStats(Stats stats) {
        this.stats = stats;
        return this;
    }

    public Person build() {
        return new Person(id, name, role, rank, champion, tags, wins, losses, stats);
    }

}
