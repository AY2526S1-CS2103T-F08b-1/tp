package seedu.summoners.logic.commands;

import java.util.Objects;
import java.util.Optional;

import seedu.summoners.commons.util.ToStringBuilder;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.Team;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** Player detail window should be shown to the user. */
    private final boolean showPlayerDetail;

    /** The player whose details should be shown. */
    private final Player playerToShow;

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
     * @param showPlayerDetail Whether to show the player detail window.
     * @param playerToShow The player whose details should be shown, or null.
     * @param showTeamStats Whether to show the team stats window.
     * @param teamToShow The team whose stats should be shown, or null.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit,
                         boolean showPlayerDetail, Player playerToShow,
                         boolean showTeamStats, Team teamToShow) {
        this.feedbackToUser = Objects.requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.showPlayerDetail = showPlayerDetail;
        this.playerToShow = playerToShow;
        this.showTeamStats = showTeamStats;
        this.teamToShow = teamToShow;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields (without player detail or team stats).
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
     * Constructs a {@code CommandResult} for showing player details.
     */
    public CommandResult(String feedbackToUser, Player playerToShow) {
        this(feedbackToUser, false, false, true, playerToShow, false, null);
    }

    /**
     * Factory method to create a result that opens the Player detail window.
     *
     * @param message feedback line for the result display
     * @param player player to show
     * @return a {@code CommandResult} configured to show the Player detail window
     */
    public static CommandResult showPlayerDetail(String message, Player player) {
        return new CommandResult(message, false, false, true, player, false, null);
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

    public boolean isShowPlayerDetail() {
        return showPlayerDetail;
    }

    public Optional<Player> getPlayerToShow() {
        return Optional.ofNullable(playerToShow);
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
                && showPlayerDetail == otherCommandResult.showPlayerDetail
                && Objects.equals(playerToShow, otherCommandResult.playerToShow)
                && showTeamStats == otherCommandResult.showTeamStats
                && Objects.equals(teamToShow, otherCommandResult.teamToShow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, showPlayerDetail, playerToShow,
                showTeamStats, teamToShow);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("showPlayerDetail", showPlayerDetail)
                .add("playerToShow", playerToShow)
                .add("showTeamStats", showTeamStats)
                .add("teamToShow", teamToShow)
                .toString();
    }
}
