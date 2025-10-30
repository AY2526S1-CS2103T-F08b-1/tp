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
 * Contains integration tests (interaction with the Model) and unit tests for {@link DeleteStatsCommand}.
 */
public class DeleteStatsCommandTest {

    private static final String AUG_CPM = "10.2";
    private static final String AUG_GD15 = "2400";
    private static final String AUG_KDA = "2.6";

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unfilteredListPersonNotInAnyTeam_success() throws Exception {
        // Person not in any team (typical address book without teams)
        Person original = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // Pre-augment stats so deletion has an effect
        Stats augmentedStats = original.getStats().addLatestStats(AUG_CPM, AUG_GD15, AUG_KDA);
        Person augmented = new Person(
                original.getId(), original.getName(), original.getRole(), original.getRank(),
                original.getChampion(), original.getTags(), original.getWins(), original.getLosses(),
                augmentedStats
        );
        model.setPerson(original, augmented);

        // After delete, we should get back the original stats (pre-augmentation)
        Person expectedAfterDelete = new Person(
                original.getId(), original.getName(), original.getRole(), original.getRank(),
                original.getChampion(), original.getTags(), original.getWins(), original.getLosses(),
                original.getStats()
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(augmented, expectedAfterDelete);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.updateFilteredTeamList(Model.PREDICATE_SHOW_ALL_TEAMS);

        DeleteStatsCommand cmd = new DeleteStatsCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(DeleteStatsCommand.MESSAGE_RECORD_SUCCESS,
                Messages.format(expectedAfterDelete));

        assertCommandSuccess(cmd, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unfilteredListPersonInTeamUpdatesPersonAndTeam_success() throws Exception {
        // Model with teams
        Model modelWithTeams = new ModelManager(getTypicalAddressBookWithTeams(), new UserPrefs());

        // Use ALICE who is in a team in typical data
        Person aliceInModel = modelWithTeams.getFilteredPersonList().stream()
                .filter(p -> p.isSamePerson(ALICE))
                .findFirst()
                .orElseThrow(() -> new AssertionError("ALICE should exist in typical teams model"));

        Team teamContainingAlice = modelWithTeams.getFilteredTeamList().stream()
                .filter(t -> t.hasPerson(aliceInModel))
                .findFirst()
                .orElseThrow(() -> new AssertionError("ALICE should be in a team in typical teams model"));

        // Pre-augment ALICE so deletion has an effect
        Stats aliceAugmentedStats = aliceInModel.getStats().addLatestStats(AUG_CPM, AUG_GD15, AUG_KDA);
        Person aliceAugmented = new Person(
                aliceInModel.getId(), aliceInModel.getName(), aliceInModel.getRole(), aliceInModel.getRank(),
                aliceInModel.getChampion(), aliceInModel.getTags(), aliceInModel.getWins(), aliceInModel.getLosses(),
                aliceAugmentedStats
        );
        modelWithTeams.setPerson(aliceInModel, aliceAugmented);
        Team teamWithAugmentedAlice = new TeamBuilder(teamContainingAlice)
                .replacePerson(aliceInModel, aliceAugmented)
                .build();
        modelWithTeams.setTeam(teamContainingAlice, teamWithAugmentedAlice);

        // Expected model equals the original typical model (i.e., after deletion we revert to original stats)
        Model expectedModel = new ModelManager(getTypicalAddressBookWithTeams(), new UserPrefs());

        // ALICE is typically first in list
        DeleteStatsCommand cmd = new DeleteStatsCommand(Index.fromOneBased(1));

        Person expectedAlice = expectedModel.getFilteredPersonList().get(0);
        String expectedMessage = String.format(DeleteStatsCommand.MESSAGE_RECORD_SUCCESS,
                Messages.format(expectedAlice));

        assertCommandSuccess(cmd, modelWithTeams, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        // Filter to only the first person
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person original = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // Pre-augment stats so deletion has an effect
        Stats augmentedStats = original.getStats().addLatestStats(AUG_CPM, AUG_GD15, AUG_KDA);
        Person augmented = new Person(
                original.getId(), original.getName(), original.getRole(), original.getRank(),
                original.getChampion(), original.getTags(), original.getWins(), original.getLosses(),
                augmentedStats
        );
        model.setPerson(original, augmented);

        // Expected model should reflect original stats after delete
        Person expectedAfterDelete = new Person(
                original.getId(), original.getName(), original.getRole(), original.getRank(),
                original.getChampion(), original.getTags(), original.getWins(), original.getLosses(),
                original.getStats()
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(augmented, expectedAfterDelete);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.updateFilteredTeamList(Model.PREDICATE_SHOW_ALL_TEAMS);

        DeleteStatsCommand cmd = new DeleteStatsCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(DeleteStatsCommand.MESSAGE_RECORD_SUCCESS,
                Messages.format(expectedAfterDelete));

        assertCommandSuccess(cmd, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBound = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteStatsCommand cmd = new DeleteStatsCommand(outOfBound);

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

        DeleteStatsCommand cmd = new DeleteStatsCommand(outOfBoundIndex);
        assertCommandFailure(cmd, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteStatsCommand standard = new DeleteStatsCommand(INDEX_FIRST_PERSON);

        // same values -> true
        DeleteStatsCommand sameValues = new DeleteStatsCommand(INDEX_FIRST_PERSON);
        assertTrue(standard.equals(sameValues));

        // same object -> true
        assertTrue(standard.equals(standard));

        // null -> false
        assertFalse(standard.equals(null));

        // different type -> false
        assertFalse(standard.equals(new ClearCommand()));

        // different index -> false
        assertFalse(standard.equals(new DeleteStatsCommand(INDEX_SECOND_PERSON)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        DeleteStatsCommand command = new DeleteStatsCommand(index);
        String expected = DeleteStatsCommand.class.getCanonicalName()
                + "{index=" + index + "}";
        assertEquals(expected, command.toString());
    }
}
