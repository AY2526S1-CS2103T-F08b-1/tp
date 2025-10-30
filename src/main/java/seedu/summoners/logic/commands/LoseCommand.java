package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * Records a loss for a team and all its persons.
 */
public class LoseCommand extends Command {

    public static final String COMMAND_WORD = "lose";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Records a loss for the team and its persons identified by the index used in the displayed team list.\n"
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
        List<Person> originalPersons = teamToLose.getPersons();
        List<Person> updatedPersons = new ArrayList<>();

        // Update each person in the team
        for (Person person : originalPersons) {
            Person updatedPerson = createPersonWithNewLoss(person);
            updatedPersons.add(updatedPerson);
        }

        // Create the updated team with the new persons and stats
        Team updatedTeam = createTeamWithNewLoss(teamToLose, updatedPersons);

        // Apply all updates to the model
        for (int i = 0; i < originalPersons.size(); i++) {
            Person originalPerson = originalPersons.get(i);
            Person updatedPerson = updatedPersons.get(i);
            model.setPerson(originalPerson, updatedPerson);
        }
        model.setTeam(teamToLose, updatedTeam);

        return new CommandResult(String.format(MESSAGE_LOSE_TEAM_SUCCESS, targetIndex.getOneBased(),
                updatedTeam.getWins(), updatedTeam.getLosses()));
    }

    /**
     * Creates and returns a {@code Person} with an incremented loss count.
     */
    private Person createPersonWithNewLoss(Person personToEdit) {
        assert personToEdit != null;

        return new Person(
                personToEdit.getId(),
                personToEdit.getName(),
                personToEdit.getRole(),
                personToEdit.getRank(),
                personToEdit.getChampion(),
                personToEdit.getTags(),
                personToEdit.getWins(),
                personToEdit.getLosses() + 1);
    }

    /**
     * Creates and returns a {@code Team} with an incremented loss count and updated player list.
     */
    private Team createTeamWithNewLoss(Team teamToEdit, List<Person> updatedPersons) {
        requireAllNonNull(teamToEdit, updatedPersons);
        return new Team(
                teamToEdit.getId(),
                updatedPersons,
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
