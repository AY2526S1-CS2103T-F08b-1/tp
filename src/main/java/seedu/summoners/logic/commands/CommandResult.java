package seedu.address.logic.commands;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** Person detail window should be shown to the user. */
    private final boolean showPersonDetail;

    /** The person whose details should be shown. */
    private final Person personToShow;

    /** Team stats window should be shown to the user. */
    private final boolean showTeamStats;

    /** The team whose stats should be shown. */
    private final Team teamToShow;

    /**
     * Constructs a {@code CommandResult} with all fields.
     *
     * @param feedbackToUser Feedback message to display to the user.
     * @param showHelp Whether to show the help window.
     * @param exit Whether the application should exit.
     * @param showPersonDetail Whether to show the person detail window.
     * @param personToShow The person whose details should be shown, or null.
     * @param showTeamStats Whether to show the team stats window.
     * @param teamToShow The team whose stats should be shown, or null.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit,
                         boolean showPersonDetail, Person personToShow,
                         boolean showTeamStats, Team teamToShow) {
        this.feedbackToUser = Objects.requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.showPersonDetail = showPersonDetail;
        this.personToShow = personToShow;
        this.showTeamStats = showTeamStats;
        this.teamToShow = teamToShow;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields (without person detail or team stats).
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, false, null, false, null);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, null, false, null);
    }

    /**
     * Constructs a {@code CommandResult} for showing person details.
     */
    public CommandResult(String feedbackToUser, Person personToShow) {
        this(feedbackToUser, false, false, true, personToShow, false, null);
    }

    /**
     * Factory method to create a result that opens the Person detail window.
     *
     * @param message feedback line for the result display
     * @param person person to show
     * @return a {@code CommandResult} configured to show the Person detail window
     */
    public static CommandResult showPersonDetail(String message, Person person) {
        return new CommandResult(message, false, false, true, person, false, null);
    }

    /**
     * Factory method to create a result that opens the Team stats window.
     *
     * @param message feedback line for the result display
     * @param team team to show
     * @return a {@code CommandResult} configured to show the Team stats window
     */
    public static CommandResult showTeamStats(String message, Team team) {
        return new CommandResult(message, false, false, false, null, true, team);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isShowPersonDetail() {
        return showPersonDetail;
    }

    public Optional<Person> getPersonToShow() {
        return Optional.ofNullable(personToShow);
    }

    public boolean isShowTeamStats() {
        return showTeamStats;
    }

    public Optional<Team> getTeamToShow() {
        return Optional.ofNullable(teamToShow);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && showPersonDetail == otherCommandResult.showPersonDetail
                && Objects.equals(personToShow, otherCommandResult.personToShow)
                && showTeamStats == otherCommandResult.showTeamStats
                && Objects.equals(teamToShow, otherCommandResult.teamToShow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, showPersonDetail, personToShow,
                showTeamStats, teamToShow);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("showPersonDetail", showPersonDetail)
                .add("personToShow", personToShow)
                .add("showTeamStats", showTeamStats)
                .add("teamToShow", teamToShow)
                .toString();
    }
}
