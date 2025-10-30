package seedu.summoners.model.team;

import static java.util.Objects.requireNonNull;
import static seedu.summoners.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.exceptions.DuplicateTeamException;
import seedu.summoners.model.team.exceptions.PlayerAlreadyInTeamException;
import seedu.summoners.model.team.exceptions.TeamNotFoundException;

/**
 * A list of teams that enforces uniqueness between its elements and does not allow nulls.
 * A team is considered unique by comparing using {@code Team#isSameTeam(Team)}. As such, adding and updating of
 * teams uses Team#isSameTeam(Team) for equality so as to ensure that the team being added or updated is
 * unique in terms of identity in the UniqueTeamList. However, the removal of a team uses Team#equals(Object) so
 * as to ensure that the team with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Team#isSameTeam(Team)
 */
public class UniqueTeamList implements Iterable<Team> {

    private final ObservableList<Team> internalList = FXCollections.observableArrayList();
    private final ObservableList<Team> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent team as the given argument.
     */
    public boolean contains(Team toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameTeam);
    }

    /**
     * Adds a team to the list.
     * The team must not already exist in the list.
     * All players in the team must not be in any other existing team.
     *
     * @throws PlayerAlreadyInTeamException if any player in the team is already in another team.
     */
    public void add(Team toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTeamException();
        }

        // Validate that no player in the team is already in another team
        for (Player player : toAdd.getPlayers()) {
            if (isPlayerInAnyTeam(player)) {
                throw new PlayerAlreadyInTeamException(player);
            }
        }

        internalList.add(toAdd);
    }

    /**
     * Replaces the team {@code target} in the list with {@code editedTeam}.
     * {@code target} must exist in the list.
     * The team identity of {@code editedTeam} must not be the same as another existing team in the list.
     */
    public void setTeam(Team target, Team editedTeam) {
        requireAllNonNull(target, editedTeam);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TeamNotFoundException();
        }

        if (!target.isSameTeam(editedTeam) && contains(editedTeam)) {
            throw new DuplicateTeamException();
        }

        internalList.set(index, editedTeam);
    }

    /**
     * Removes the equivalent team from the list.
     * The team must exist in the list.
     */
    public void remove(Team toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TeamNotFoundException();
        }
    }

    public void setTeams(UniqueTeamList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code teams}.
     * {@code teams} must not contain duplicate teams.
     * Players must not appear in multiple teams.
     *
     * @throws PlayerAlreadyInTeamException if any player appears in multiple teams.
     */
    public void setTeams(List<Team> teams) {
        requireAllNonNull(teams);
        if (!areTeamsUnique(teams)) {
            throw new DuplicateTeamException();
        }
        if (!arePlayersUniqueAcrossTeams(teams)) {
            throw new PlayerAlreadyInTeamException();
        }

        internalList.setAll(teams);
    }

    /**
     * Returns the team containing the given player, or null if the player is not in any team.
     *
     * @param player The player to search for.
     * @return The team containing the player, or null if not found.
     */
    public Team getTeamContainingPlayer(Player player) {
        requireNonNull(player);
        for (Team team : internalList) {
            if (team.hasPlayer(player)) {
                return team;
            }
        }
        return null;
    }

    /**
     * Returns true if the given player is currently in any team.
     *
     * @param player The player to check.
     * @return True if the player is in a team, false otherwise.
     */
    public boolean isPlayerInAnyTeam(Player player) {
        return getTeamContainingPlayer(player) != null;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Team> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Team> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueTeamList)) {
            return false;
        }

        UniqueTeamList otherUniqueTeamList = (UniqueTeamList) other;
        return internalList.equals(otherUniqueTeamList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code teams} contains only unique teams.
     */
    private boolean areTeamsUnique(List<Team> teams) {
        for (int i = 0; i < teams.size() - 1; i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                if (teams.get(i).isSameTeam(teams.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns true if no player appears in multiple teams across {@code teams}.
     */
    private boolean arePlayersUniqueAcrossTeams(List<Team> teams) {
        Set<String> seenPlayerIds = new HashSet<>();
        for (Team team : teams) {
            for (Player player : team.getPlayers()) {
                String playerId = player.getId().toString();
                if (seenPlayerIds.contains(playerId)) {
                    return false;
                }
                seenPlayerIds.add(playerId);
            }
        }
        return true;
    }
}
