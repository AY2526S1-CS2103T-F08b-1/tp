package seedu.summoners.model.team;

import static seedu.summoners.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import seedu.summoners.commons.util.ToStringBuilder;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.exceptions.DuplicateChampionException;
import seedu.summoners.model.team.exceptions.DuplicateRoleException;
import seedu.summoners.model.team.exceptions.InvalidTeamSizeException;

/**
 * Represents a Team in the summoners book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 * A team must have exactly 5 players with unique roles (Top, Jungle, Mid, ADC, Support).
 */
public class Team {

    public static final int TEAM_SIZE = 5;
    public static final String MESSAGE_CONSTRAINTS =
            "A team must have exactly 5 players with unique roles and unique champions.";
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
    private final List<Player> players;

    // Stat fields
    private final int wins;
    private final int losses;

    /**
     * Constructor for creating a new Team with a randomly generated unique ID.
     *
     * @param players List of 5 players for the team.
     */
    public Team(List<Player> players) {
        this(UUID.randomUUID().toString(), players, 0, 0);
    }

    /**
     * Constructor for creating a Team with an explicit ID.
     * This is used for deserialization from JSON to preserve the original ID.
     *
     * @param id      Unique identifier for the team.
     * @param players List of 5 players for the team.
     */
    public Team(String id, List<Player> players, int wins, int losses) {
        requireAllNonNull(id, players);
        validateTeamComposition(players);
        this.id = id;
        this.players = new ArrayList<>(players);
        this.wins = wins;
        this.losses = losses;
    }

    /**
     * Returns the numeric index of a player's role based on a fixed lane order
     * (Top → Jungle → Mid → Adc → Support).
     * <p>
     * Used to sort team members consistently in {@link #toDisplayString()}.
     * Roles not found in {@link #ROLE_ORDER} are assigned a high index (999)
     * so they appear last in the sorted order.
     *
     * @param p The player whose role index to retrieve.
     * @return An integer representing the role's position in the fixed order.
     */
    private static int roleIndex(seedu.summoners.model.player.Player p) {
        String roleStr = p.getRole().toString();
        return ROLE_ORDER.getOrDefault(roleStr.toLowerCase(), 999);
    }


    /**
     * Validates that the team has exactly 5 players with unique roles and unique champions.
     *
     * @param players List of players to validate.
     * @throws InvalidTeamSizeException   if team does not have exactly 5 players.
     * @throws DuplicateRoleException     if team has duplicate roles.
     * @throws DuplicateChampionException if team has duplicate champions.
     */
    private void validateTeamComposition(List<Player> players) {
        // Check team size
        if (players.size() != TEAM_SIZE) {
            throw new InvalidTeamSizeException(players.size());
        }

        // Pairwise conflict check for all players
        for (int i = 0; i < players.size(); i++) {
            for (int j = i + 1; j < players.size(); j++) {
                checkConflict(players.get(i), players.get(j));
            }
        }
    }

    /**
     * Checks if two players have a conflict for team composition.
     * A conflict occurs when two players have the same role or the same champion.
     *
     * @param firstPlayer  First player to check.
     * @param secondPlayer Second player to check.
     * @throws DuplicateRoleException     if both players have the same role.
     * @throws DuplicateChampionException if both players have the same champion.
     */
    private void checkConflict(Player firstPlayer, Player secondPlayer) {
        // Check for duplicate role
        if (firstPlayer.getRole().equals(secondPlayer.getRole())) {
            throw new DuplicateRoleException(firstPlayer, secondPlayer);
        }

        // Check for duplicate champion
        if (firstPlayer.getChampion().equals(secondPlayer.getChampion())) {
            throw new DuplicateChampionException(firstPlayer, secondPlayer);
        }
    }

    public String getId() {
        return id;
    }

    /**
     * Returns an immutable list of players.
     */
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
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
        return players.stream()
                .sorted(java.util.Comparator.comparingInt(Team::roleIndex))
                .map(player -> String.format("%s (%s)", player.getName(), player.getRole()))
                .collect(java.util.stream.Collectors.joining(", "));
    }


    /**
     * Returns true if this team contains the specified player.
     */
    public boolean hasPlayer(Player player) {
        return players.contains(player);
    }

    /**
     * Returns true if both teams have the same players.
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
        return players.equals(otherTeam.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(players);
    }

    @Override
    public String toString() {
        String playersString = players.stream()
                .map(Player::toString)
                .collect(java.util.stream.Collectors.joining(", "));
        return new ToStringBuilder(this.getClass().getSimpleName())
                .add("players", playersString)
                .toString();
    }
}
