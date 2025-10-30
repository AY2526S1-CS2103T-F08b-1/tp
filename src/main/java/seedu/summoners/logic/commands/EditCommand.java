package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHAMPION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TEAMS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Champion;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Rank;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;
import seedu.address.model.team.Team;
import seedu.address.model.team.exceptions.DuplicateChampionException;
import seedu.address.model.team.exceptions.DuplicateRoleException;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_RANK + "RANK] "
            + "[" + PREFIX_ROLE + "ROLE] "
            + "[" + PREFIX_CHAMPION + "CHAMPION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_RANK + "Diamond "
            + PREFIX_CHAMPION + "Yasuo";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Team> lastShownTeamList = model.getFilteredTeamList();

        if (index.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownPersonList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        Optional<Team> teamToEditOptional = lastShownTeamList.stream()
                .filter(team -> team.hasPerson(personToEdit))
                .findFirst();

        if (teamToEditOptional.isPresent()) {
            Team teamToEdit = teamToEditOptional.get();

            // Tries to create an updated team that includes the edited person.
            // This step validates that no duplicate roles or champions exist.
            // If validation fails, an exception is thrown and no model updates occur.
            try {
                Team editedTeam = createEditedTeam(teamToEdit, personToEdit, editedPerson);

                // Apply the updates only after successful validation.
                model.setPerson(personToEdit, editedPerson);
                model.setTeam(teamToEdit, editedTeam);
            } catch (DuplicateRoleException | DuplicateChampionException e) {
                throw new CommandException(e.getMessage());
            }
        } else {
            model.setPerson(personToEdit, editedPerson);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredTeamList(PREDICATE_SHOW_ALL_TEAMS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        String id = personToEdit.getId();
        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Rank updatedRank = editPersonDescriptor.getRank().orElse(personToEdit.getRank());
        Role updatedRole = editPersonDescriptor.getRole().orElse(personToEdit.getRole());
        Champion updatedChampion = editPersonDescriptor.getChampion().orElse(personToEdit.getChampion());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        int wins = personToEdit.getWins();
        int losses = personToEdit.getLosses();

        // Preserve id from the original person
        return new Person(id, updatedName, updatedRole, updatedRank, updatedChampion, updatedTags, wins, losses);
    }

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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Role role;
        private Rank rank;
        private Champion champion;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setRole(toCopy.role);
            setRank(toCopy.rank);
            setChampion(toCopy.champion);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, role, rank, champion, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public Optional<Role> getRole() {
            return Optional.ofNullable(role);
        }

        public void setRank(Rank rank) {
            this.rank = rank;
        }

        public Optional<Rank> getRank() {
            return Optional.ofNullable(rank);
        }

        public void setChampion(Champion champion) {
            this.champion = champion;
        }

        public Optional<Champion> getChampion() {
            return Optional.ofNullable(champion);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(role, otherEditPersonDescriptor.role)
                    && Objects.equals(rank, otherEditPersonDescriptor.rank)
                    && Objects.equals(champion, otherEditPersonDescriptor.champion)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("role", role)
                    .add("rank", rank)
                    .add("champion", champion)
                    .add("tags", tags)
                    .toString();
        }
    }
}
