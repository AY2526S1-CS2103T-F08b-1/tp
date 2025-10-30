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
 * Records a win for a team and all its persons.
 */
public class WinCommand extends Command {

    public static final String COMMAND_WORD = "win";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Records a win for the team and its persons identified by the index used in the displayed team list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_WIN_TEAM_SUCCESS = "Team %1$d has won a match! "
            + "Their stats have been updated to W:%2$d-L:%3$d.";

    private final Index targetIndex;

    public WinCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Team> lastShownList = model.getFilteredTeamList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }

        Team teamToWin = lastShownList.get(targetIndex.getZeroBased());
        List<Person> originalPersons = teamToWin.getPersons();
        List<Person> updatedPersons = new ArrayList<>();

        // Update each person in the team
        for (Person person : originalPersons) {
            Person updatedPerson = createPersonWithNewWin(person);
            updatedPersons.add(updatedPerson);
        }

        // Create the updated team with the new persons and stats
        Team updatedTeam = createTeamWithNewWin(teamToWin, updatedPersons);

        // Apply all updates to the model
        for (int i = 0; i < originalPersons.size(); i++) {
            Person originalPerson = originalPersons.get(i);
            Person updatedPerson = updatedPersons.get(i);
            model.setPerson(originalPerson, updatedPerson);
        }
        model.setTeam(teamToWin, updatedTeam);

        return new CommandResult(String.format(MESSAGE_WIN_TEAM_SUCCESS, targetIndex.getOneBased(),
                updatedTeam.getWins(), updatedTeam.getLosses()));
    }

    /**
     * Creates and returns a {@code Person} with an incremented win count.
     */
    private Person createPersonWithNewWin(Person personToEdit) {
        assert personToEdit != null;

        return new Person(
                personToEdit.getId(),
                personToEdit.getName(),
                personToEdit.getRole(),
                personToEdit.getRank(),
                personToEdit.getChampion(),
                personToEdit.getTags(),
                personToEdit.getWins() + 1,
                personToEdit.getLosses());
    }

    /**
     * Creates and returns a {@code Team} with an incremented win count and updated person list.
     */
    private Team createTeamWithNewWin(Team teamToEdit, List<Person> updatedPersons) {
        requireAllNonNull(teamToEdit, updatedPersons);
        return new Team(
                teamToEdit.getId(),
                updatedPersons,
                teamToEdit.getWins() + 1,
                teamToEdit.getLosses());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WinCommand)) {
            return false;
        }

        WinCommand otherWinCommand = (WinCommand) other;
        return targetIndex.equals(otherWinCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
