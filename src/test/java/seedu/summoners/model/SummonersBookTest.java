package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CHAMPION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTeams.TEAM_A;
import static seedu.address.testutil.TypicalTeams.TEAM_B;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.team.Team;
import seedu.address.model.team.exceptions.DuplicateTeamException;
import seedu.address.model.team.exceptions.TeamNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getTeamList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withChampion(VALID_CHAMPION_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons, Collections.emptyList());

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void resetData_withDuplicateTeams_throwsDuplicateTeamException() {
        // Use the new AddressBookStub constructor
        List<Team> newTeams = Arrays.asList(TEAM_A, TEAM_A);
        AddressBookStub newData = new AddressBookStub(Collections.emptyList(), newTeams);

        assertThrows(DuplicateTeamException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withChampion(VALID_CHAMPION_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void hasTeam_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasTeam(null));
    }

    @Test
    public void hasTeam_teamNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasTeam(TEAM_A));
    }

    @Test
    public void hasTeam_teamInAddressBook_returnsTrue() {
        addressBook.addTeam(TEAM_A);
        assertTrue(addressBook.hasTeam(TEAM_A));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void addTeam_duplicateTeam_throwsDuplicateTeamException() {
        addressBook.addTeam(TEAM_A);
        assertThrows(DuplicateTeamException.class, () -> addressBook.addTeam(TEAM_A));
    }

    @Test
    public void setTeam_replacesTeamInList() {
        addressBook.addTeam(TEAM_A);
        addressBook.setTeam(TEAM_A, TEAM_B);
        AddressBook expectedAddressBook = new AddressBook();
        expectedAddressBook.addTeam(TEAM_B);
        assertEquals(expectedAddressBook, addressBook);
    }

    @Test
    public void setTeam_targetTeamNotInList_throwsTeamNotFoundException() {
        assertThrows(TeamNotFoundException.class, () -> addressBook.setTeam(TEAM_A, TEAM_A));
    }

    @Test
    public void removeTeam_removesTeamFromList() {
        addressBook.addTeam(TEAM_A);
        addressBook.removeTeam(TEAM_A);
        AddressBook expectedAddressBook = new AddressBook();
        assertEquals(expectedAddressBook, addressBook);
    }

    @Test
    public void removeTeam_teamDoesNotExist_throwsTeamNotFoundException() {
        assertThrows(TeamNotFoundException.class, () -> addressBook.removeTeam(TEAM_A));
    }

    @Test
    public void getTeamList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getTeamList().remove(0));
    }

    @Test
    public void findPersonByName_personExists_returnsPerson() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.findPersonByName(ALICE.getName()).isPresent());
        assertEquals(ALICE, addressBook.findPersonByName(ALICE.getName()).get());
    }

    @Test
    public void findPersonByName_personDoesNotExist_returnsEmptyOptional() {
        assertFalse(addressBook.findPersonByName(ALICE.getName()).isPresent());
    }

    @Test
    public void findPersonByName_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.findPersonByName(null));
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(addressBook.equals(addressBook));

        // same values -> returns true
        AddressBook addressBookCopy = new AddressBook();
        assertTrue(addressBook.equals(addressBookCopy));

        // different types -> returns false
        assertFalse(addressBook.equals(5));

        // null -> returns false
        assertFalse(addressBook.equals(null));

        // different persons -> returns false
        AddressBook differentPersonsAddressBook = new AddressBook();
        differentPersonsAddressBook.addPerson(ALICE);
        assertFalse(addressBook.equals(differentPersonsAddressBook));

        // different teams -> returns false
        AddressBook differentTeamsAddressBook = new AddressBook();
        differentTeamsAddressBook.addTeam(TEAM_A);
        assertFalse(addressBook.equals(differentTeamsAddressBook));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{persons=" + addressBook.getPersonList()
                + ", teams=" + addressBook.getTeamList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Team> teams = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons, Collection<Team> teams) {
            this.persons.setAll(persons);
            this.teams.setAll(teams);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Team> getTeamList() {
            return teams;
        }
    }

}
