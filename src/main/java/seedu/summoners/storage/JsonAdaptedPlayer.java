package seedu.summoners.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.summoners.commons.exceptions.IllegalValueException;
import seedu.summoners.model.player.Champion;
import seedu.summoners.model.player.Name;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.player.Rank;
import seedu.summoners.model.player.Role;
import seedu.summoners.model.player.Stats;
import seedu.summoners.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Player}.
 */
class JsonAdaptedPlayer {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Player's %s field is missing!";

    private final String id;
    private final String name;
    private final String role;
    private final String rank;
    private final String champion;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final int wins;
    private final int losses;
    private final JsonAdaptedStats stats;

    /**
     * Constructs a {@code JsonAdaptedPlayer} with the given player details.
     */
    @JsonCreator
    public JsonAdaptedPlayer(@JsonProperty("id") String id,
                             @JsonProperty("name") String name,
                             @JsonProperty("role") String role,
                             @JsonProperty("rank") String rank,
                             @JsonProperty("champion") String champion,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("wins") int wins,
                             @JsonProperty("losses") int losses,
                             @JsonProperty("stats") JsonAdaptedStats stats) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.rank = rank;
        this.champion = champion;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.wins = wins;
        this.losses = losses;
        this.stats = stats;
    }

    /**
     * Converts a given {@code Player} into this class for Jackson use.
     */
    public JsonAdaptedPlayer(Player source) {
        id = source.getId();
        name = source.getName().fullName;
        role = source.getRole().toString();
        rank = source.getRank().toString();
        champion = source.getChampion().toString();
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        wins = source.getWins();
        losses = source.getLosses();
        stats = new JsonAdaptedStats(source.getStats());
    }

    /**
     * Converts this Jackson-friendly adapted player object into the model's {@code Player} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted player.
     */
    public Player toModelType() throws IllegalValueException {
        final List<Tag> playerTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            playerTags.add(tag.toModelType());
        }

        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "ID"));
        }
        final String modelId = id;

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (role == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName()));
        }
        if (!Role.isValidRole(role)) {
            throw new IllegalValueException(Role.MESSAGE_CONSTRAINTS);
        }
        final Role modelRole = new Role(role);

        if (rank == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Rank.class.getSimpleName()));
        }
        if (!Rank.isValidRank(rank)) {
            throw new IllegalValueException(Rank.MESSAGE_CONSTRAINTS);
        }
        final Rank modelRank = new Rank(rank);

        if (champion == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Champion.class.getSimpleName()));
        }
        if (!Champion.isValidChampion(champion)) {
            throw new IllegalValueException(Champion.MESSAGE_CONSTRAINTS);
        }
        final Champion modelChampion = new Champion(champion);

        final Set<Tag> modelTags = new HashSet<>(playerTags);

        Stats modelStats = stats != null ? stats.toModelType() : new Stats();

        return new Player(modelId, modelName, modelRole, modelRank, modelChampion, modelTags, wins, losses, modelStats);
    }

}
