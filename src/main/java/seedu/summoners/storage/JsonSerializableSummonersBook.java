package seedu.summoners.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.summoners.commons.exceptions.IllegalValueException;
import seedu.summoners.model.ReadOnlySummonersBook;
import seedu.summoners.model.SummonersBook;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.Team;

/**
 * An Immutable SummonersBook that is serializable to JSON format.
 */
@JsonRootName(value = "summonersbook")
class JsonSerializableSummonersBook {

    public static final String MESSAGE_DUPLICATE_PLAYER = "Players list contains duplicate player(s).";
    public static final String MESSAGE_DUPLICATE_TEAM = "Teams list contains duplicate team(s).";

    private final List<JsonAdaptedPlayer> players = new ArrayList<>();
    private final List<JsonAdaptedTeam> teams = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableSummonersBook} with the given players.
     */
    @JsonCreator
    public JsonSerializableSummonersBook(@JsonProperty("players") List<JsonAdaptedPlayer> players,
                                       @JsonProperty("teams") List<JsonAdaptedTeam> teams) {
        if (players != null) {
            this.players.addAll(players);
        }
        if (teams != null) {
            this.teams.addAll(teams);
        }
    }

    /**
     * Converts a given {@code ReadOnlySummonersBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableSummonersBook}.
     */
    public JsonSerializableSummonersBook(ReadOnlySummonersBook source) {
        players.addAll(source.getPlayerList().stream().map(JsonAdaptedPlayer::new).collect(Collectors.toList()));
        teams.addAll(source.getTeamList().stream().map(JsonAdaptedTeam::new).collect(Collectors.toList()));
    }

    /**
     * Converts this summoners book into the model's {@code SummonersBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public SummonersBook toModelType() throws IllegalValueException {
        SummonersBook summonersBook = new SummonersBook();

        List<Player> playerList = new ArrayList<>();
        for (JsonAdaptedPlayer jsonAdaptedPlayer : players) {
            Player player = jsonAdaptedPlayer.toModelType();
            if (summonersBook.hasPlayer(player)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PLAYER);
            }
            summonersBook.addPlayer(player);
            playerList.add(player);
        }

        for (JsonAdaptedTeam jsonAdaptedTeam : teams) {
            Team team = jsonAdaptedTeam.toModelType(playerList);
            if (summonersBook.hasTeam(team)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TEAM);
            }
            summonersBook.addTeam(team);
        }

        return summonersBook;
    }
}
