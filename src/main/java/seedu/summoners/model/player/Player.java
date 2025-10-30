package seedu.summoners.model.player;

import static seedu.summoners.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import seedu.summoners.commons.util.ToStringBuilder;
import seedu.summoners.model.tag.Tag;

/**
 * Represents a Player in the summoners book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Player {

    // Identity fields
    private final String id;
    private final Name name;

    // Data fields
    private final Role role;
    private final Rank rank;
    private final Champion champion;
    private final Set<Tag> tags = new HashSet<>();

    // Stat fields
    private final Stats stats;
    private final int wins;
    private final int losses;

    /**
     * Constructor for creating a new Player with specified role, rank, and champion.
     * Generates a random UUID for the player.
     *
     * @param name     Name of the player.
     * @param role     Role of the player.
     * @param rank     Rank of the player.
     * @param champion Champion of the player.
     * @param tags     Set of tags associated with the player.
     */
    public Player(Name name, Role role, Rank rank, Champion champion, Set<Tag> tags) {
        this(UUID.randomUUID().toString(), name, role, rank, champion, tags, 0, 0);
    }

    /**
     * Constructor for creating a new Player with specified role, rank, and champion.
     * Generates a random UUID for the player.
     *
     * @param name     Name of the player.
     * @param role     Role of the player.
     * @param rank     Rank of the player.
     * @param champion Champion of the player.
     * @param tags     Set of tags associated with the player.
     * @param stats    Performance stats of the player.
     */
    public Player(Name name, Role role, Rank rank, Champion champion, Set<Tag> tags,
                  int wins, int losses, Stats stats) {
        this(UUID.randomUUID().toString(), name, role, rank, champion, tags, wins, losses, stats);
    }

    /**
     * Constructor for creating a Player with an explicit ID.
     * This is used for deserialization from JSON to preserve the original ID.
     *
     * @param id       Unique identifier for the player.
     * @param name     Name of the player.
     * @param role     Role of the player.
     * @param rank     Rank of the player.
     * @param champion Champion of the player.
     * @param tags     Set of tags associated with the player.
     */
    public Player(String id, Name name, Role role, Rank rank, Champion champion, Set<Tag> tags, int wins, int losses) {
        requireAllNonNull(id, name, role, rank, champion, tags);
        this.id = id;
        this.name = name;
        this.role = role;
        this.rank = rank;
        this.champion = champion;
        this.tags.addAll(tags);
        this.wins = wins;
        this.losses = losses;
        this.stats = new Stats();
    }

    /**
     * Constructor for creating a Player with an explicit ID.
     * This is used for deserialization from JSON to preserve the original ID.
     *
     * @param id       Unique identifier for the player.
     * @param name     Name of the player.
     * @param role     Role of the player.
     * @param rank     Rank of the player.
     * @param champion Champion of the player.
     * @param tags     Set of tags associated with the player.
     * @param stats    Performance stats of the player.
     */
    public Player(String id, Name name, Role role, Rank rank, Champion champion, Set<Tag> tags,
                  int wins, int losses, Stats stats) {
        requireAllNonNull(id, name, role, rank, champion, tags);
        this.id = id;
        this.name = name;
        this.role = role;
        this.rank = rank;
        this.champion = champion;
        this.tags.addAll(tags);
        this.wins = wins;
        this.losses = losses;
        this.stats = stats;
    }

    public String getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public Rank getRank() {
        return rank;
    }

    public Champion getChampion() {
        return champion;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public Stats getStats() {
        return this.stats;
    }

    /**
     * Returns true if both players have the same name.
     * This defines a weaker notion of equality between two players.
     */
    public boolean isSamePlayer(Player otherPlayer) {
        if (otherPlayer == this) {
            return true;
        }

        return otherPlayer != null
                && otherPlayer.getName().equals(getName());
    }

    /**
     * Returns true if both players have the same identity and data fields.
     * This defines a stronger notion of equality between two players.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Player)) {
            return false;
        }

        Player otherPlayer = (Player) other;
        return name.equals(otherPlayer.name)
                && role.equals(otherPlayer.role)
                && rank.equals(otherPlayer.rank)
                && champion.equals(otherPlayer.champion)
                && tags.equals(otherPlayer.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, role, rank, champion, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("role", role)
                .add("rank", rank)
                .add("champion", champion)
                .add("tags", tags)
                .toString();
    }

}
