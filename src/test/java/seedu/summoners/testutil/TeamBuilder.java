package seedu.summoners.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.Team;

/**
 * A utility class to help with building Team objects.
 */
public class TeamBuilder {

    public static final String DUMMY_ID = "";

    public static final int DEFAULT_WINS = 0;
    public static final int DEFAULT_LOSSES = 0;

    private String id;
    private List<Player> players;
    private int wins;
    private int losses;

    /**
     * Creates a {@code TeamBuilder} with the default details.
     */
    public TeamBuilder() {
        id = UUID.randomUUID().toString();
        players = new ArrayList<>();
        wins = DEFAULT_WINS;
        losses = DEFAULT_LOSSES;
    }

    /**
     * Initializes the TeamBuilder with the data of {@code teamToCopy}.
     */
    public TeamBuilder(Team teamToCopy) {
        id = teamToCopy.getId();
        players = new ArrayList<>(teamToCopy.getPlayers());
        wins = teamToCopy.getWins();
        losses = teamToCopy.getLosses();
    }

    /**
     * Sets the {@code players} of the {@code Team} that we are building.
     */
    public TeamBuilder withPlayers(Player... players) {
        this.players = Arrays.asList(players);
        return this;
    }

    /**
     * Replaces the target {@code Player} in the team with the {@code editedPlayer}.
     * If the target player does not exist in the team, the team remains unchanged.
     */
    public TeamBuilder replacePlayer(Player target, Player editedPlayer) {
        List<Player> updatedPlayers = new ArrayList<>(players);
        int index = updatedPlayers.indexOf(target);
        if (index != -1) {
            updatedPlayers.set(index, editedPlayer);
        }
        this.players = updatedPlayers;
        return this;
    }

    public Team build() {
        return new Team(id, players, wins, losses);
    }
}
