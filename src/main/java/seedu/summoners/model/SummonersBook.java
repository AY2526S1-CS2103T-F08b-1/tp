package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.team.Team;
import seedu.address.model.team.UniqueTeamList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueTeamList teams;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        teams = new UniqueTeamList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the team list with {@code teams}.
     * {@code teams} must not contain duplicate teams.
     */
    public void setTeams(List<Team> teams) {
        this.teams.setTeams(teams);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setTeams(newData.getTeamList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Returns true if a team with the same identity as {@code team} exists in the address book.
     */
    public boolean hasTeam(Team team) {
        requireNonNull(team);
        return teams.contains(team);
    }

    /**
     * Returns true if the given person is currently in any team.
     */
    public boolean isPersonInAnyTeam(Person person) {
        requireNonNull(person);
        return teams.isPersonInAnyTeam(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Adds a team to the address book.
     * The team must not already exist in the address book.
     */
    public void addTeam(Team t) {
        teams.add(t);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);
        persons.setPerson(target, editedPerson);
    }

    /**
     * Returns an {@code Optional<Person>} containing the person with the given {@code Name}, if present
     * in the address book.
     *
     * @param name The name of the person to find.
     * @return An {@code Optional<Person>} containing the matching person, or an empty {@code Optional} if not found.
     */
    public Optional<Person> findPersonByName(Name name) {
        requireNonNull(name);
        return persons.asUnmodifiableObservableList()
                .stream()
                .filter(p -> p.getName().equals(name))
                .findFirst();
    }

    /**
     * Replaces the given team {@code target} in the list with {@code editedTeam}.
     * {@code target} must exist in the address book.
     * The team identity of {@code editedTeam} must not be the same as another existing team in the address book.
     */
    public void setTeam(Team target, Team editedTeam) {
        requireNonNull(editedTeam);
        teams.setTeam(target, editedTeam);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeTeam(Team key) {
        teams.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .add("teams", teams)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Team> getTeamList() {
        return teams.asUnmodifiableObservableList();
    }

    /**
     * Returns an observable list of persons who are not currently in any team.
     * @return Observable list of unassigned persons.
     */
    public ObservableList<Person> getUnassignedPersonList() {
        return persons.asUnmodifiableObservableList()
                .filtered(person -> !teams.isPersonInAnyTeam(person));
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons)
                && teams.equals(otherAddressBook.teams);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(persons, teams);
    }
}
