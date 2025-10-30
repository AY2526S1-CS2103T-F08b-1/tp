package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TEAMS;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Champion;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Rank;
import seedu.address.model.person.Role;
import seedu.address.model.person.Stats;
import seedu.address.model.tag.Tag;
import seedu.address.model.team.Team;

/**
 * Deletes the latest performance statistics entry from the specified {@link Person}.
 * <p>
 * The target {@code Person} is identified by its index in the currently displayed person list.
 * If that person belongs to a team currently shown in the team list, the corresponding
 * {@link Team} entry is also updated to reference the edited {@code Person}.
 * </p>
 */
public class DeleteStatsCommand extends Command {

    /** Primary command word for deleting the latest stats of a person. */
    public static final String COMMAND_WORD = "deleteStats";

    /**
     * Usage string describing parameters and an example invocation.
     * <pre>
     * deleteStats INDEX
     * e.g. deleteStats 1
     * </pre>
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete the latest performance stats of"
            + " the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    /** Success message format used in the {@link CommandResult}. */
    public static final String MESSAGE_RECORD_SUCCESS = "Latest stats deleted: %1$s";

    private final Index index;

    /**
     * Creates a {@code DeleteStatsCommand} targeting the person at the given {@code index}.
     *
     * @param index Index of the person in the filtered person list. Must be non-null.
     */
    public DeleteStatsCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    /**
     * Executes the command: removes the most recent statistics item from the selected person and,
     * if applicable, updates any team that contains that person to reference the edited instance.
     * <ul>
     *   <li>Validates the index against the current filtered person list.</li>
     *   <li>Builds an edited {@link Person} whose {@link Stats} has the latest entry removed.</li>
     *   <li>Updates the person (and the containing team, if any) in the {@link Model}.</li>
     *   <li>Refreshes both person and team filtered lists.</li>
     * </ul>
     *
     * @param model The model in which updates are applied. Must be non-null.
     * @return A {@link CommandResult} summarizing the operation.
     * @throws CommandException If the index is invalid (out of bounds).
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Team> lastShownTeamList = model.getFilteredTeamList();

        if (index.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownPersonList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit);

        Optional<Team> teamToEditOptional = lastShownTeamList.stream()
                .filter(team -> team.hasPerson(personToEdit))
                .findFirst();

        if (teamToEditOptional.isPresent()) {
            Team teamToEdit = teamToEditOptional.get();
            Team editedTeam = createEditedTeam(teamToEdit, personToEdit, editedPerson);

            // Apply the updates only after successful validation.
            model.setPerson(personToEdit, editedPerson);
            model.setTeam(teamToEdit, editedTeam);
        } else {
            model.setPerson(personToEdit, editedPerson);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredTeamList(PREDICATE_SHOW_ALL_TEAMS);
        return new CommandResult(String.format(MESSAGE_RECORD_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Produces a new {@link Person} based on {@code personToEdit} whose {@link Stats}
     * has the latest (most recently added) entry removed.
     * <p>
     * Identity (ID), name, role, rank, champion, tags, and win/loss record are preserved.
     * </p>
     *
     * @param personToEdit The original person to update. Must be non-null.
     * @return A new {@code Person} with updated {@code Stats}.
     */
    private static Person createEditedPerson(Person personToEdit) {
        assert personToEdit != null;

        String id = personToEdit.getId();
        Name updatedName = personToEdit.getName();
        Rank updatedRank = personToEdit.getRank();
        Role updatedRole = personToEdit.getRole();
        Champion updatedChampion = personToEdit.getChampion();
        Set<Tag> updatedTags = personToEdit.getTags();
        int wins = personToEdit.getWins();
        int losses = personToEdit.getLosses();

        Stats updatedStats = personToEdit.getStats().deleteLatestStats();

        // Preserve id from the original person
        return new Person(id, updatedName, updatedRole, updatedRank, updatedChampion, updatedTags,
                wins, losses, updatedStats);
    }

    /**
     * Produces a new {@link Team} identical to {@code teamToEdit} except that
     * {@code personToEdit} is replaced by {@code editedPerson} in the team roster.
     * Team identity (ID) and win–loss record are preserved.
     *
     * @param teamToEdit   The team currently containing {@code personToEdit}. Must contain that person.
     * @param personToEdit The existing person entry to replace.
     * @param editedPerson The new person entry to insert.
     * @return A new {@code Team} reflecting the person update.
     * @throws AssertionError If {@code teamToEdit} does not contain {@code personToEdit}.
     */
    private static Team createEditedTeam(Team teamToEdit, Person personToEdit, Person editedPerson) {
        assert teamToEdit.hasPerson(personToEdit);

        String id = teamToEdit.getId();
        List<Person> updatedPersonList = new ArrayList<>(teamToEdit.getPersons());
        int personIndex = updatedPersonList.indexOf(personToEdit);
        updatedPersonList.set(personIndex, editedPerson);
        int wins = teamToEdit.getWins();
        int losses = teamToEdit.getLosses();

        return new Team(id, updatedPersonList, wins, losses);
    }

    /**
     * Two {@code DeleteStatsCommand}s are equal if they target the same index.
     *
     * @param other The other object to compare.
     * @return {@code true} if both commands have the same target index; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteStatsCommand)) {
            return false;
        }

        DeleteStatsCommand otherDeleteStatsCommand = (DeleteStatsCommand) other;
        return index.equals(otherDeleteStatsCommand.index);
    }

    /**
     * Returns a string representation suitable for logging and debugging.
     *
     * @return String containing the command class and target index.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}
