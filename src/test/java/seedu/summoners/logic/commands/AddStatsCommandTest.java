package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTeams.getTypicalAddressBookWithTeams;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Stats;
import seedu.address.model.team.Team;
import seedu.address.testutil.TeamBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@link AddStatsCommand}.
 */
public class AddStatsCommandTest {

    private static final String CPM = "10.2";
    private static final String GD15 = "2400";
    private static final String KDA = "2.6";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unfilteredListPersonNotInAnyTeam_success() throws Exception {
        // Use the typical address book without teams to ensure the person is not in any team
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // Build expected edited person (preserving identity & attributes, updating Stats only)
        Stats updatedStats = personToEdit.getStats().addLatestStats(CPM, GD15, KDA);
        Person editedPerson = new Person(personToEdit.getId(),
                personToEdit.getName(),
                personToEdit.getRole(),
                personToEdit.getRank(),
                personToEdit.getChampion(),
                personToEdit.getTags(),
                personToEdit.getWins(),
                personToEdit.getLosses(),
                updatedStats);

        AddStatsCommand cmd = new AddStatsCommand(INDEX_FIRST_PERSON, CPM, GD15, KDA);
        String expectedMessage = String.format(AddStatsCommand.MESSAGE_RECORD_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.updateFilteredTeamList(Model.PREDICATE_SHOW_ALL_TEAMS);

        assertCommandSuccess(cmd, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unfilteredListPersonInTeamUpdatesPersonAndTeam_success() throws Exception {
        // Model that already has teams with persons assigned
        Model modelWithTeams = new ModelManager(getTypicalAddressBookWithTeams(), new UserPrefs());

        // Pick a person that is known to be in a team; use ALICE as in other tests
        Person personToEdit = ALICE;
        Stats updatedStats = personToEdit.getStats().addLatestStats("7.0", "1000", "2.2");
        Person editedPerson = new Person(personToEdit.getId(),
                personToEdit.getName(),
                personToEdit.getRole(),
                personToEdit.getRank(),
                personToEdit.getChampion(),
                personToEdit.getTags(),
                personToEdit.getWins(),
                personToEdit.getLosses(),
                updatedStats);

        // Find the team containing that person in the model
        Team originalTeam = modelWithTeams.getFilteredTeamList()
                .stream()
                .filter(t -> t.hasPerson(personToEdit))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected ALICE to be in a team in typical teams data"));

        Team expectedTeam = new TeamBuilder(originalTeam)
                .replacePerson(personToEdit, editedPerson)
                .build();

        // Command uses the person index in filtered person list — ALICE is typically first
        AddStatsCommand cmd = new AddStatsCommand(Index.fromOneBased(1), "7.0", "1000", "2.2");
        String expectedMessage = String.format(AddStatsCommand.MESSAGE_RECORD_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(getTypicalAddressBookWithTeams(), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);
        expectedModel.setTeam(originalTeam, expectedTeam);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.updateFilteredTeamList(Model.PREDICATE_SHOW_ALL_TEAMS);

        assertCommandSuccess(cmd, modelWithTeams, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        // Filter the list to show only the first person
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Stats updatedStats = personInFilteredList.getStats().addLatestStats(CPM, GD15, KDA);
        Person editedPerson = new Person(personInFilteredList.getId(),
                personInFilteredList.getName(),
                personInFilteredList.getRole(),
                personInFilteredList.getRank(),
                personInFilteredList.getChampion(),
                personInFilteredList.getTags(),
                personInFilteredList.getWins(),
                personInFilteredList.getLosses(),
                updatedStats);

        AddStatsCommand cmd = new AddStatsCommand(INDEX_FIRST_PERSON, CPM, GD15, KDA);
        String expectedMessage = String.format(AddStatsCommand.MESSAGE_RECORD_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.updateFilteredTeamList(Model.PREDICATE_SHOW_ALL_TEAMS);

        assertCommandSuccess(cmd, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBound = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddStatsCommand cmd = new AddStatsCommand(outOfBound, CPM, GD15, KDA);

        assertCommandFailure(cmd, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Invalid index against the filtered list but still within the size of the full address book.
     */
    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AddStatsCommand cmd = new AddStatsCommand(outOfBoundIndex, CPM, GD15, KDA);
        assertCommandFailure(cmd, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        AddStatsCommand standard = new AddStatsCommand(INDEX_FIRST_PERSON, "7.0", "1000", "2.2");

        // same values -> true
        AddStatsCommand sameValues = new AddStatsCommand(INDEX_FIRST_PERSON, "7.0", "1000", "2.2");
        assertTrue(standard.equals(sameValues));

        // same object -> true
        assertTrue(standard.equals(standard));

        // null -> false
        assertFalse(standard.equals(null));

        // different type -> false
        assertFalse(standard.equals(new ClearCommand()));

        // different index -> false
        assertFalse(standard.equals(new AddStatsCommand(INDEX_SECOND_PERSON, "7.0", "1000", "2.2")));

        // different params -> false
        assertFalse(standard.equals(new AddStatsCommand(INDEX_FIRST_PERSON, "10.2", "2400", "2.6")));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        AddStatsCommand command = new AddStatsCommand(index, CPM, GD15, KDA);
        String expected = AddStatsCommand.class.getCanonicalName()
                + "{index=" + index
                + ", cpm=" + CPM
                + ", gd15=" + GD15
                + ", kda=" + KDA
                + "}";
        assertEquals(expected, command.toString());
    }
}
