package seedu.address.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Team> PREDICATE_SHOW_ALL_TEAMS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a team with the same identity as {@code team} exists in the address book.
     */
    boolean hasTeam(Team team);

    /**
     * Returns true if the given person is currently in any team.
     */
    boolean isPersonInAnyTeam(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Deletes the given team.
     * The team must exist in the address book.
     */
    void deleteTeam(Team target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Returns an {@code Optional<Person>} containing the person with the given {@code Name}, if present.
     *
     * @param name The name of the person to find.
     * @return An {@code Optional<Person>} containing the matching person, or an empty {@code Optional} if not found.
     */
    Optional<Person> findPersonByName(Name name);

    /**
     * Adds the given team.
     * {@code team} must not already exist in the address book.
     */
    void addTeam(Team team);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Replaces the given team {@code target} with {@code editedTeam}.
     * {@code target} must exist in the address book.
     * The team identity of {@code editedTeam} must not be the same as another existing team in the address book.
     */
    void setTeam(Team target, Team editedTeam);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered team list */
    ObservableList<Team> getFilteredTeamList();

    /** Returns an unmodifiable view of the unassigned person list */
    ObservableList<Person> getUnassignedPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered team list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTeamList(Predicate<Team> predicate);
}
