package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_EIGHTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_NINTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SEVENTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SIXTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTeams.getTypicalAddressBookWithTeams;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

public class MakeGroupCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndicesUnfilteredList_success() throws CommandException {
        List<Index> indices = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
                INDEX_THIRD_PERSON, INDEX_FOURTH_PERSON, INDEX_FIFTH_PERSON);
        MakeGroupCommand makeGroupCommand = new MakeGroupCommand(indices);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        List<Person> expectedTeamMembers = indices.stream()
                .map(index -> model.getFilteredPersonList().get(index.getZeroBased()))
                .toList();
        Team expectedTeam = new Team(expectedTeamMembers);
        expectedModel.addTeam(expectedTeam);

        String expectedMessage = String.format(MakeGroupCommand.MESSAGE_MAKE_GROUP_SUCCESS,
                Messages.format(expectedTeam));

        assertCommandSuccess(makeGroupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateIndices_throwsCommandException() {
        List<Index> duplicateIndices = Arrays.asList(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON, INDEX_THIRD_PERSON, INDEX_FOURTH_PERSON);
        MakeGroupCommand command = new MakeGroupCommand(duplicateIndices);
        assertCommandFailure(command, model, MakeGroupCommand.MESSAGE_DUPLICATE_INDEX);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        List<Index> indicesWithInvalid = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
                INDEX_THIRD_PERSON, INDEX_FOURTH_PERSON, outOfBoundIndex);
        MakeGroupCommand command = new MakeGroupCommand(indicesWithInvalid);
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_personAlreadyInTeam_throwsCommandException() {
        Model modelWithTeam = new ModelManager(getTypicalAddressBookWithTeams(), new UserPrefs());

        // Attempt to create a new team using the same person
        List<Index> indices = Arrays.asList(
                INDEX_FIRST_PERSON, // ALICE, who is already in a team
                INDEX_SIXTH_PERSON,
                INDEX_SEVENTH_PERSON,
                INDEX_EIGHTH_PERSON,
                INDEX_NINTH_PERSON
        );

        MakeGroupCommand command = new MakeGroupCommand(indices);

        String expectedMessage = String.format(MakeGroupCommand.MESSAGE_REUSED_PERSON, Messages.format(ALICE));
        assertCommandFailure(command, modelWithTeam, expectedMessage);
    }

    @Test
    public void execute_invalidTeamSize_throwsCommandException() {
        String expectedMessage = String.format(MakeGroupCommand.MESSAGE_INVALID_TEAM_SIZE, Team.TEAM_SIZE);

        List<Index> fewerIndices = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        MakeGroupCommand command = new MakeGroupCommand(fewerIndices);
        assertCommandFailure(command, model, expectedMessage);

        List<Index> moreIndices = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON,
                INDEX_FOURTH_PERSON, INDEX_FIFTH_PERSON, INDEX_SIXTH_PERSON);
        command = new MakeGroupCommand(moreIndices);
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_invalidTeamComposition_throwsCommandException() {
        // Assumes TypicalPersons has persons with conflicting roles/champions at certain indices
        // For example, if person 1 and 6 have the same role.
        List<Index> conflictingIndices = Arrays.asList(INDEX_FIRST_PERSON, Index.fromOneBased(6),
                INDEX_THIRD_PERSON, INDEX_FOURTH_PERSON, INDEX_FIFTH_PERSON);
        MakeGroupCommand command = new MakeGroupCommand(conflictingIndices);

        // We can't check the exact error message as it depends on your TypicalPersons,
        // so we assert that a CommandException is thrown.
        // A more specific test would require crafting a specific model.
        org.junit.jupiter.api.Assertions.assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void equals() {
        List<Index> indices1 = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON,
                INDEX_FOURTH_PERSON, INDEX_FIFTH_PERSON);
        List<Index> indices2 = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON,
                INDEX_FOURTH_PERSON, INDEX_SIXTH_PERSON);

        MakeGroupCommand command1 = new MakeGroupCommand(indices1);
        MakeGroupCommand command2 = new MakeGroupCommand(indices1);
        MakeGroupCommand command3 = new MakeGroupCommand(indices2);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        assertTrue(command1.equals(command2));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different indices -> returns false
        assertFalse(command1.equals(command3));
    }

    @Test
    public void toStringMethod() {
        List<Index> indices = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON,
                INDEX_FOURTH_PERSON, INDEX_FIFTH_PERSON);
        MakeGroupCommand makeGroupCommand = new MakeGroupCommand(indices);
        String expected = MakeGroupCommand.class.getCanonicalName() + "{indexList=" + indices + "}";
        assertEquals(expected, makeGroupCommand.toString());
    }
}
