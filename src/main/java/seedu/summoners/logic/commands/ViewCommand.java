package seedu.summoners.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.summoners.commons.core.index.Index;
import seedu.summoners.commons.util.ToStringBuilder;
import seedu.summoners.logic.Messages;
import seedu.summoners.logic.commands.exceptions.CommandException;
import seedu.summoners.model.Model;
import seedu.summoners.model.player.Player;

/**
 * Displays detailed information about a player identified by their index in the summoners book.
 * Opens a modal window showing the player's complete profile including performance statistics
 * visualized in a graph.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays detailed information about the player identified by the index number "
            + "used in the displayed player list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_PLAYER_SUCCESS = "Viewing Player: %1$s";

    private final Index targetIndex;

    /**
     * Creates a ViewCommand to view the player at the specified {@code Index}.
     *
     * @param targetIndex Index of the player in the filtered player list to view.
     */
    public ViewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Player> lastShownList = model.getFilteredPlayerList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PLAYER_DISPLAYED_INDEX);
        }

        Player playerToView = lastShownList.get(targetIndex.getZeroBased());

        return CommandResult.showPlayerDetail(
                String.format(MESSAGE_VIEW_PLAYER_SUCCESS, Messages.format(playerToView)),
                playerToView);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand)) {
            return false;
        }

        ViewCommand otherViewCommand = (ViewCommand) other;
        return targetIndex.equals(otherViewCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
