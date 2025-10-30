package seedu.summoners.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.summoners.commons.exceptions.IllegalValueException;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.Team;

/**
 * Jackson-friendly version of {@link Team}.
 */
public class JsonAdaptedTeam {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Team's %s field is missing!";

    private final String teamId;
    private final List<String> playerIds = new ArrayList<>();
    private final int wins;
    private final int losses;

    /**
     * Constructs a {@code JsonAdaptedTeam} with the given team details.
     */
    @JsonCreator
    public JsonAdaptedTeam(@JsonProperty("teamId") String teamId,
                           @JsonProperty("playerIds") List<String> playerIds,
                           @JsonProperty("wins") int wins,
                           @JsonProperty("losses") int losses) {
        this.teamId = teamId;
        if (playerIds != null) {
            this.playerIds.addAll(playerIds);
        }
        this.wins = wins;
        this.losses = losses;
    }

    /**
     * Converts a given {@code Team} into this class for Jackson use.
     */
    public JsonAdaptedTeam(Team source) {
        this.teamId = source.getId();
        this.playerIds.addAll(source.getPlayers().stream()
                .map(Player::getId)
                .collect(Collectors.toList()));
        this.wins = source.getWins();
        this.losses = source.getLosses();
    }

    /**
     * Converts this Jackson-friendly adapted team object into the model's {@code Team} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted team.
     */
    public Team toModelType(List<Player> allPlayers) throws IllegalValueException {
        if (teamId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "teamId"));
        }

        final List<Player> teamPlayers = new ArrayList<>();
        for (String playerId : playerIds) {
            Player player = allPlayers.stream()
                    .filter(p -> p.getId().equals(playerId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalValueException("Invalid Player ID in Team: " + playerId));
            teamPlayers.add(player);
        }
        return new Team(teamId, teamPlayers, wins, losses);
    }
}
