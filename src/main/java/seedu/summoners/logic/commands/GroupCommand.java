package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.teammatcher.InsufficientPersonsException;
import seedu.address.logic.teammatcher.TeamMatcher;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * Automatically creates balanced teams from unassigned persons.
 * Uses a role-based matching algorithm that considers person ranks and champions.
 */
public class GroupCommand extends Command {

    public static final String COMMAND_WORD = "group";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Automatically creates balanced teams from all unassigned persons.\n"
            + "The algorithm groups persons by role, sorts by rank, and ensures no duplicate champions per team.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Successfully created %1$d team(s):\n%2$s\n\n"
            + "%3$d person(s) remain unassigned.";
    public static final String MESSAGE_NO_TEAMS_FORMED = "No teams could be formed. "
            + "Ensure there is at least one unassigned person for each role (Top, Jungle, Mid, ADC, Support).";

    private final TeamMatcher teamMatcher;

    /**
     * Creates a GroupCommand with the default TeamMatcher.
     */
    public GroupCommand() {
        this(new TeamMatcher());
    }

    /**
     * Creates a GroupCommand with a specified TeamMatcher.
     * This constructor supports Dependency Inversion Principle by allowing injection of the matching strategy.
     *
     * @param teamMatcher The team matcher to use for forming teams.
     */
    public GroupCommand(TeamMatcher teamMatcher) {
        requireNonNull(teamMatcher);
        this.teamMatcher = teamMatcher;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Get all unassigned persons
        List<Person> unassignedPersons = model.getUnassignedPersonList();

        if (unassignedPersons.isEmpty()) {
            throw new CommandException("No unassigned persons available to form teams.");
        }

        // Use TeamMatcher to form teams
        List<Team> teams;
        try {
            teams = teamMatcher.matchTeams(unassignedPersons);
        } catch (InsufficientPersonsException e) {
            throw new CommandException(e.getMessage());
        }

        if (teams.isEmpty()) {
            throw new CommandException(MESSAGE_NO_TEAMS_FORMED);
        }

        // Add all teams to the model
        for (Team team : teams) {
            model.addTeam(team);
        }

        // Get the actual count of remaining unassigned persons from the model
        int remainingPersons = model.getUnassignedPersonList().size();

        // Format the success message
        String teamsFormatted = formatTeams(teams);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                teams.size(), teamsFormatted, remainingPersons));
    }

    /**
     * Formats the list of teams for display.
     * Each team is displayed on a separate line with its number and members.
     *
     * @param teams List of teams to format.
     * @return Formatted string representation of teams.
     */
    private String formatTeams(List<Team> teams) {
        assert !teams.isEmpty() : "formatTeams should only be called with non-empty teams list";
        return IntStream.range(0, teams.size())
                .mapToObj(i -> String.format("Team %d: %s", i + 1, teams.get(i).toDisplayString()))
                .collect(Collectors.joining("\n"));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GroupCommand)) {
            return false;
        }

        GroupCommand otherCommand = (GroupCommand) other;
        return teamMatcher.equals(otherCommand.teamMatcher);
    }

    @Override
    public int hashCode() {
        return teamMatcher.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("teamMatcher", teamMatcher)
                .toString();
    }
}
