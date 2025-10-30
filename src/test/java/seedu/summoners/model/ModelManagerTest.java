package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalTeams.TEAM_A;
import static seedu.address.testutil.TypicalTeams.TEAM_B;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void deletePerson_deletesPerson_success() {
        modelManager.addPerson(ALICE);
        modelManager.deletePerson(ALICE);
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void setPerson_replacesPerson_success() {
        modelManager.addPerson(ALICE);
        modelManager.setPerson(ALICE, BENSON);
        assertFalse(modelManager.hasPerson(ALICE));
        assertTrue(modelManager.hasPerson(BENSON));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void hasTeam_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasTeam(null));
    }

    @Test
    public void hasTeam_teamNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasTeam(TEAM_A));
    }

    @Test
    public void hasTeam_teamInAddressBook_returnsTrue() {
        modelManager.addTeam(TEAM_A);
        assertTrue(modelManager.hasTeam(TEAM_A));
    }

    @Test
    public void deleteTeam_deletesTeam_success() {
        modelManager.addTeam(TEAM_A);
        modelManager.deleteTeam(TEAM_A);
        assertFalse(modelManager.hasTeam(TEAM_A));
    }

    @Test
    public void setTeam_replacesTeam_success() {
        modelManager.addTeam(TEAM_A);
        modelManager.setTeam(TEAM_A, TEAM_B);
        assertFalse(modelManager.hasTeam(TEAM_A));
        assertTrue(modelManager.hasTeam(TEAM_B));
    }

    @Test
    public void getFilteredTeamList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredTeamList().remove(0));
    }

    @Test
    public void getUnassignedPersonList_noTeams_returnsAllPersons() {
        modelManager.addPerson(ALICE);
        modelManager.addPerson(BENSON);

        assertEquals(2, modelManager.getUnassignedPersonList().size());
        assertTrue(modelManager.getUnassignedPersonList().contains(ALICE));
        assertTrue(modelManager.getUnassignedPersonList().contains(BENSON));
    }

    @Test
    public void getUnassignedPersonList_someInTeam_returnsOnlyUnassigned() {
        // Add all persons for TEAM_A (ALICE, BENSON, CARL, DANIEL, ELLE)
        modelManager.addPerson(ALICE);
        modelManager.addPerson(BENSON);

        // Add person not in any team
        Person george = new PersonBuilder().withName("George Best").build();
        modelManager.addPerson(george);

        // Add team containing ALICE and BENSON (but not george)
        modelManager.addTeam(TEAM_A);

        // Only george should be unassigned
        assertEquals(1, modelManager.getUnassignedPersonList().size());
        assertTrue(modelManager.getUnassignedPersonList().contains(george));
        assertFalse(modelManager.getUnassignedPersonList().contains(ALICE));
        assertFalse(modelManager.getUnassignedPersonList().contains(BENSON));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON)
                .withTeam(TEAM_A).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different filteredTeamList -> returns false
        modelManager.updateFilteredTeamList(team -> false);
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredTeamList(Model.PREDICATE_SHOW_ALL_TEAMS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
