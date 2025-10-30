package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Team> filteredTeams;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredTeams = new FilteredList<>(this.addressBook.getTeamList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    //=========== Person-level Operations ====================================================================

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public boolean isPersonInAnyTeam(Person person) {
        requireNonNull(person);
        return addressBook.isPersonInAnyTeam(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    /**
     * Returns an {@code Optional<Person>} containing the person with the given {@code Name}, if present.
     * Delegates the search to the underlying {@code AddressBook}.
     *
     * @param name The name of the person to find.
     * @return An {@code Optional<Person>} containing the matching person, or an empty {@code Optional} if not found.
     */
    @Override
    public Optional<Person> findPersonByName(Name name) {
        requireNonNull(name);
        return addressBook.findPersonByName(name);
    }

    //=========== Team-level Operations ======================================================================

    @Override
    public boolean hasTeam(Team team) {
        requireNonNull(team);
        return addressBook.hasTeam(team);
    }

    @Override
    public void deleteTeam(Team target) {
        addressBook.removeTeam(target);
    }

    @Override
    public void addTeam(Team team) {
        addressBook.addTeam(team);
        updateFilteredTeamList(PREDICATE_SHOW_ALL_TEAMS);
    }

    @Override
    public void setTeam(Team target, Team editedTeam) {
        requireAllNonNull(target, editedTeam);

        addressBook.setTeam(target, editedTeam);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Filtered Team List Accessors ===============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Team> getFilteredTeamList() {
        return filteredTeams;
    }

    @Override
    public void updateFilteredTeamList(Predicate<Team> predicate) {
        requireNonNull(predicate);
        filteredTeams.setPredicate(predicate);
    }

    //=========== Unassigned Person List Accessors ===============================================================

    /**
     * Returns an unmodifiable view of the list of unassigned {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getUnassignedPersonList() {
        return addressBook.getUnassignedPersonList();
    }

    //=========== Equals ======================================================================================
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredTeams.equals(otherModelManager.filteredTeams);
    }
}
