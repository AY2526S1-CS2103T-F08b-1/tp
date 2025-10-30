package seedu.address.logic.teammatcher;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.model.team.Team;

/**
 * A class responsible for matching unassigned persons into balanced teams.
 * Uses a role-based matching algorithm that considers person ranks and champions.
 *
 * This class follows the Single Responsibility Principle by focusing solely on team matching logic.
 */
public class TeamMatcher {

    private static final int TEAM_SIZE = 5;
    private static final Role[] REQUIRED_ROLES = {
        new Role("Top"),
        new Role("Jungle"),
        new Role("Mid"),
        new Role("Adc"),
        new Role("Support")
    };

    /**
     * Attempts to create balanced teams from a list of unassigned persons.
     *
     * Algorithm:
     * 1. Groups persons by role
     * 2. Sorts each role group by rank (highest to lowest)
     * 3. Iteratively forms teams by selecting one person per role
     * 4. Ensures no duplicate champions within each team
     *
     * @param unassignedPersons List of persons not currently in any team.
     * @return List of teams that can be formed.
     * @throws InsufficientPersonsException if there are not enough persons to form at least one complete team.
     */
    public List<Team> matchTeams(List<Person> unassignedPersons) throws InsufficientPersonsException {
        // Group by role
        Map<Role, List<Person>> personsByRole = groupByRole(unassignedPersons);

        // Validate we have at least one person per role
        validateMinimumPersons(personsByRole);

        // Create defensive copies and sort each role group by rank
        Map<Role, List<Person>> sortedPersonsByRole = createSortedCopy(personsByRole);

        // Form teams
        return formTeams(sortedPersonsByRole);
    }

    /**
     * Groups persons by their role.
     */
    private Map<Role, List<Person>> groupByRole(List<Person> persons) {
        return persons.stream()
                .collect(Collectors.groupingBy(Person::getRole));
    }

    /**
     * Validates that there is at least one person for each required role.
     *
     * @throws InsufficientPersonsException if any role is missing.
     */
    private void validateMinimumPersons(Map<Role, List<Person>> personsByRole)
            throws InsufficientPersonsException {
        for (Role role : REQUIRED_ROLES) {
            if (!personsByRole.containsKey(role) || personsByRole.get(role).isEmpty()) {
                throw new InsufficientPersonsException(
                        "Cannot form a team: No persons available for role " + role + ".");
            }
        }
    }

    /**
     * Creates a sorted defensive copy of the persons by role map.
     * Each role's person list is sorted by rank (highest to lowest).
     * Uses Rank's natural ordering (Comparable) in reverse.
     *
     * @param personsByRole Original map of persons grouped by role (not modified).
     * @return New map with sorted copies of person lists.
     */
    private Map<Role, List<Person>> createSortedCopy(Map<Role, List<Person>> personsByRole) {
        Comparator<Person> rankComparator = Comparator.comparing(Person::getRank).reversed();
        Map<Role, List<Person>> sortedCopy = new HashMap<>();

        for (Map.Entry<Role, List<Person>> entry : personsByRole.entrySet()) {
            List<Person> sortedPersons = new ArrayList<>(entry.getValue());
            sortedPersons.sort(rankComparator);
            sortedCopy.put(entry.getKey(), sortedPersons);
        }

        return sortedCopy;
    }

    /**
     * Forms as many complete teams as possible from the sorted role groups.
     * Ensures no duplicate champions within each team.
     * Modifies the input map by removing persons as they are assigned to teams.
     *
     * @param personsByRole Map of roles to lists of persons (will be modified).
     * @return List of teams formed from the persons.
     */
    private List<Team> formTeams(Map<Role, List<Person>> personsByRole) {
        List<Team> teams = new ArrayList<>();

        // Keep forming teams until we can't form any more complete teams
        while (canFormTeam(personsByRole)) {
            List<Person> teamMembers = new ArrayList<>();

            // Try to select one person from each role
            for (Role role : REQUIRED_ROLES) {
                List<Person> availablePersons = personsByRole.get(role);
                Optional<Person> selectedPerson = selectPersonWithoutChampionConflict(availablePersons, teamMembers);

                if (selectedPerson.isEmpty()) {
                    // Can't form a complete team without champion conflicts
                    // Put back the persons we've selected and stop
                    return teams;
                }

                teamMembers.add(selectedPerson.get());
                availablePersons.remove(selectedPerson.get());
            }

            // Successfully formed a team
            assert teamMembers.size() == TEAM_SIZE
                : "Team should have exactly " + TEAM_SIZE + " members before creation";
            teams.add(new Team(teamMembers));
        }

        return teams;
    }

    /**
     * Checks if we can potentially form at least one more complete team.
     */
    private boolean canFormTeam(Map<Role, List<Person>> personsByRole) {
        for (Role role : REQUIRED_ROLES) {
            if (!personsByRole.containsKey(role) || personsByRole.get(role).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Selects a person from the available list who doesn't have a champion conflict
     * with already selected team members.
     *
     * @param availablePersons List of persons to choose from.
     * @param selectedMembers Already selected team members.
     * @return The selected person, or Optional.empty() if no valid person can be found.
     */
    private Optional<Person> selectPersonWithoutChampionConflict(List<Person> availablePersons,
                                                                  List<Person> selectedMembers) {
        for (Person candidate : availablePersons) {
            if (!hasChampionConflict(candidate, selectedMembers)) {
                return Optional.of(candidate);
            }
        }
        return Optional.empty();
    }

    /**
     * Checks if a candidate has a champion conflict with any of the selected members.
     */
    private boolean hasChampionConflict(Person candidate, List<Person> selectedMembers) {
        return selectedMembers.stream()
                .anyMatch(member -> candidate.getChampion().equals(member.getChampion()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // All TeamMatcher instances are equal since they have no state
        return other instanceof TeamMatcher;
    }

    @Override
    public int hashCode() {
        // All instances return the same hash code since they're stateless
        return TeamMatcher.class.hashCode();
    }
}
