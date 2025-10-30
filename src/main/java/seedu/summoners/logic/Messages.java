package seedu.summoners.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.summoners.logic.parser.Prefix;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.Team;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PLAYER_DISPLAYED_INDEX = "The player index provided is invalid";
    public static final String MESSAGE_INVALID_TEAM_DISPLAYED_INDEX = "The team index provided is invalid";
    public static final String MESSAGE_PLAYERS_LISTED_OVERVIEW = "%1$d players listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_PLAYER_IN_TEAM =
                "This player is currently in a team.";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code player} for display to the user.
     */
    public static String format(Player player) {
        final StringBuilder builder = new StringBuilder();
        builder.append(player.getName())
                .append("; Rank: ")
                .append(player.getRank())
                .append("; Role: ")
                .append(player.getRole())
                .append("; Champion: ")
                .append(player.getChampion())
                .append("; Tags: ");
        player.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code team} for display to the user.
     */
    public static String format(Team team) {
        return team.toDisplayString();
    }

}
