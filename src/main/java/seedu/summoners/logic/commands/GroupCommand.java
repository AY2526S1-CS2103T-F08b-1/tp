package seedu.summoners.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.summoners.commons.util.ToStringBuilder;
import seedu.summoners.logic.commands.exceptions.CommandException;
import seedu.summoners.logic.teammatcher.InsufficientPlayersException;
import seedu.summoners.logic.teammatcher.TeamMatcher;
import seedu.summoners.model.Model;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.Team;

/**
 * Automatically creates balanced teams from unassigned players.
 * Uses a role-based matching algorithm that considers player ranks and champions.
 */
public class GroupCommand extends Command {

    public static final String COMMAND_WORD = "group";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Automatically creates balanced teams from all unassigned persons.\n"
            + "The algorithm groups players by role, sorts by rank, and ensures no duplicate champions per team.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Successfully created %1$d team(s):\n%2$s\n\n"
            + "%3$d player(s) remain unassigned.";
    public static final String MESSAGE_NO_TEAMS_FORMED = "No teams could be formed. "
            + "Ensure there is at least one unassigned player for each role (Top, Jungle, Mid, ADC, Support).";

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

        // Get all unassigned players
        List<Player> unassignedPlayers = model.getUnassignedPlayerList();

        if (unassignedPlayers.isEmpty()) {
            throw new CommandException("No unassigned players available to form teams.");
        }

        // Use TeamMatcher to form teams
        List<Team> teams;
        try {
            teams = teamMatcher.matchTeams(unassignedPlayers);
        } catch (InsufficientPlayersException e) {
            throw new CommandException(e.getMessage());
        }

        if (teams.isEmpty()) {
            throw new CommandException(MESSAGE_NO_TEAMS_FORMED);
        }

        // Get the number of existing teams before adding
        int existingTeamCount = model.getFilteredTeamList().size();

        // Add all teams to the model
        for (Team team : teams) {
            model.addTeam(team);
        }

        // Get the actual count of remaining unassigned players from the model
        int remainingPlayers = model.getUnassignedPlayerList().size();

        // Format the success message
        String teamsFormatted = formatTeams(teams, existingTeamCount);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                teams.size(), teamsFormatted, remainingPlayers));
    }

    /**
     * Formats the list of teams for display.
     * Each team is displayed on a separate line with its number and members.
     *
     * @param teams List of teams to format.
     * @param startIndex Number of teams that existed before these were added.
     * @return Formatted string representation of teams.
     */
    private String formatTeams(List<Team> teams, int startIndex) {
        assert !teams.isEmpty() : "formatTeams should only be called with non-empty teams list";
        return IntStream.range(0, teams.size())
                .mapToObj(i -> String.format("Team %d: %s", startIndex + i + 1, teams.get(i).toDisplayString()))
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
