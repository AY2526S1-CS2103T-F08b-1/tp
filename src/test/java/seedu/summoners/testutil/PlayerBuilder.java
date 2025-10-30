package seedu.summoners.testutil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import seedu.summoners.model.player.Champion;
import seedu.summoners.model.player.Name;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.player.Rank;
import seedu.summoners.model.player.Role;
import seedu.summoners.model.player.Stats;
import seedu.summoners.model.tag.Tag;
import seedu.summoners.model.util.SampleDataUtil;

/**
 * A utility class to help with building Player objects.
 */
public class PlayerBuilder {

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
     * Creates a {@code PlayerBuilder} with the default details.
     */
    public PlayerBuilder() {
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
     * Initializes the PlayerBuilder with the data of {@code playerToCopy}.
     */
    public PlayerBuilder(Player playerToCopy) {
        id = playerToCopy.getId();
        name = playerToCopy.getName();
        role = playerToCopy.getRole();
        rank = playerToCopy.getRank();
        champion = playerToCopy.getChampion();
        tags = new HashSet<>(playerToCopy.getTags());
        wins = playerToCopy.getWins();
        losses = playerToCopy.getLosses();
    }

    /**
     * Sets the {@code Name} of the {@code Player} that we are building.
     */
    public PlayerBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Player} that we are building.
     */
    public PlayerBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Player} that we are building.
     */
    public PlayerBuilder withRole(String role) {
        this.role = new Role(role);
        return this;
    }

    /**
     * Sets the {@code Rank} of the {@code Player} that we are building.
     */
    public PlayerBuilder withRank(String rank) {
        this.rank = new Rank(rank);
        return this;
    }

    /**
     * Sets the {@code Champion} of the {@code Player} that we are building.
     */
    public PlayerBuilder withChampion(String champion) {
        this.champion = new Champion(champion);
        return this;
    }

    /**
     * Sets the {@code Stats} of the {@code Player} that we are building.
     */
    public PlayerBuilder withStats(Stats stats) {
        this.stats = stats;
        return this;
    }

    public Player build() {
        return new Player(id, name, role, rank, champion, tags, wins, losses, stats);
    }

}
