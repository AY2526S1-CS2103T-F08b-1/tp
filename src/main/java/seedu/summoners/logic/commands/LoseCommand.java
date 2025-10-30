package seedu.summoners.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.summoners.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.summoners.commons.core.index.Index;
import seedu.summoners.commons.util.ToStringBuilder;
import seedu.summoners.logic.Messages;
import seedu.summoners.logic.commands.exceptions.CommandException;
import seedu.summoners.model.Model;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.Team;

/**
 * Records a loss for a team and all its players.
 */
public class LoseCommand extends Command {

    public static final String COMMAND_WORD = "lose";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Records a loss for the team and its players identified by the index used in the displayed team list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_LOSE_TEAM_SUCCESS = "Team %1$d has lost a match. "
            + "Their stats have been updated to W:%2$d-L:%3$d.";

    private final Index targetIndex;

    public LoseCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Team> lastShownList = model.getFilteredTeamList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }

        Team teamToLose = lastShownList.get(targetIndex.getZeroBased());
        List<Player> originalPlayers = teamToLose.getPlayers();
        List<Player> updatedPlayers = new ArrayList<>();

        // Update each player in the team
        for (Player player : originalPlayers) {
            Player updatedPlayer = createPlayerWithNewLoss(player);
            updatedPlayers.add(updatedPlayer);
        }

        // Create the updated team with the new players and stats
        Team updatedTeam = createTeamWithNewLoss(teamToLose, updatedPlayers);

        // Apply all updates to the model
        for (int i = 0; i < originalPlayers.size(); i++) {
            Player originalPlayer = originalPlayers.get(i);
            Player updatedPlayer = updatedPlayers.get(i);
            model.setPlayer(originalPlayer, updatedPlayer);
        }
        model.setTeam(teamToLose, updatedTeam);

        return new CommandResult(String.format(MESSAGE_LOSE_TEAM_SUCCESS, targetIndex.getOneBased(),
                updatedTeam.getWins(), updatedTeam.getLosses()));
    }

    /**
     * Creates and returns a {@code Player} with an incremented loss count.
     */
    private Player createPlayerWithNewLoss(Player playerToEdit) {
        assert playerToEdit != null;

        return new Player(
                playerToEdit.getId(),
                playerToEdit.getName(),
                playerToEdit.getRole(),
                playerToEdit.getRank(),
                playerToEdit.getChampion(),
                playerToEdit.getTags(),
                playerToEdit.getWins(),
                playerToEdit.getLosses() + 1);
    }

    /**
     * Creates and returns a {@code Team} with an incremented loss count and updated player list.
     */
    private Team createTeamWithNewLoss(Team teamToEdit, List<Player> updatedPlayers) {
        requireAllNonNull(teamToEdit, updatedPlayers);
        return new Team(
                teamToEdit.getId(),
                updatedPlayers,
                teamToEdit.getWins(),
                teamToEdit.getLosses() + 1);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LoseCommand)) {
            return false;
        }
        LoseCommand otherLoseCommand = (LoseCommand) other;
        return targetIndex.equals(otherLoseCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
