package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.teammatcher.TeamMatcher;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.testutil.PersonBuilder;

public class GroupCommandTest {

    @Test
    public void execute_sufficientPersons_success() throws Exception {
        // Create 5 unassigned persons
        Person top = new PersonBuilder().withName("Top1").withRole("top")
                .withRank("Gold").withChampion("Garen").build();
        Person jungle = new PersonBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Lee Sin").build();
        Person mid = new PersonBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();
        Person adc = new PersonBuilder().withName("Adc1").withRole("adc")
                .withRank("Gold").withChampion("Jinx").build();
        Person support = new PersonBuilder().withName("Support1").withRole("support")
                .withRank("Gold").withChampion("Leona").build();

        List<Person> unassignedPersons = Arrays.asList(top, jungle, mid, adc, support);
        ModelStubWithUnassignedPersons modelStub = new ModelStubWithUnassignedPersons(unassignedPersons);

        GroupCommand command = new GroupCommand();
        CommandResult result = command.execute(modelStub);

        // Verify team was added
        assertEquals(1, modelStub.teamsAdded.size());
        assertTrue(result.getFeedbackToUser().contains("Successfully created 1 team"));
    }

    @Test
    public void execute_noUnassignedPersons_throwsCommandException() {
        ModelStubWithUnassignedPersons modelStub = new ModelStubWithUnassignedPersons(new ArrayList<>());

        GroupCommand command = new GroupCommand();

        CommandException exception = assertThrows(CommandException.class, () -> command.execute(modelStub));
        assertTrue(exception.getMessage().contains("No unassigned persons"));
    }

    @Test
    public void execute_insufficientPersons_throwsCommandException() {
        // Only 3 persons - not enough for a full team
        Person top = new PersonBuilder().withName("Top1").withRole("top")
                .withRank("Gold").withChampion("Garen").build();
        Person jungle = new PersonBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Lee Sin").build();
        Person mid = new PersonBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();

        List<Person> unassignedPersons = Arrays.asList(top, jungle, mid);
        ModelStubWithUnassignedPersons modelStub = new ModelStubWithUnassignedPersons(unassignedPersons);

        GroupCommand command = new GroupCommand();

        assertThrows(CommandException.class, () -> command.execute(modelStub));
    }

    @Test
    public void execute_multipleTeams_success() throws Exception {
        // Create 10 unassigned persons (2 per role)
        Person top1 = new PersonBuilder().withName("Top1").withRole("top")
                .withRank("Gold").withChampion("Garen").build();
        Person top2 = new PersonBuilder().withName("Top2").withRole("top")
                .withRank("Silver").withChampion("Darius").build();

        Person jungle1 = new PersonBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Lee Sin").build();
        Person jungle2 = new PersonBuilder().withName("Jungle2").withRole("jungle")
                .withRank("Silver").withChampion("Jarvan IV").build();

        Person mid1 = new PersonBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();
        Person mid2 = new PersonBuilder().withName("Mid2").withRole("mid")
                .withRank("Silver").withChampion("Zed").build();

        Person adc1 = new PersonBuilder().withName("Adc1").withRole("adc")
                .withRank("Gold").withChampion("Jinx").build();
        Person adc2 = new PersonBuilder().withName("Adc2").withRole("adc")
                .withRank("Silver").withChampion("Ashe").build();

        Person support1 = new PersonBuilder().withName("Support1").withRole("support")
                .withRank("Gold").withChampion("Leona").build();
        Person support2 = new PersonBuilder().withName("Support2").withRole("support")
                .withRank("Silver").withChampion("Thresh").build();

        List<Person> unassignedPersons = Arrays.asList(
                top1, top2, jungle1, jungle2, mid1, mid2, adc1, adc2, support1, support2
        );
        ModelStubWithUnassignedPersons modelStub = new ModelStubWithUnassignedPersons(unassignedPersons);

        GroupCommand command = new GroupCommand();
        CommandResult result = command.execute(modelStub);

        // Verify 2 teams were added
        assertEquals(2, modelStub.teamsAdded.size());
        assertTrue(result.getFeedbackToUser().contains("Successfully created 2 team"));
    }

    @Test
    public void constructor_withTeamMatcher_success() {
        TeamMatcher teamMatcher = new TeamMatcher();
        GroupCommand command = new GroupCommand(teamMatcher);

        // Verify the command was created successfully
        assertEquals(new GroupCommand(), command);
    }

    @Test
    public void constructor_nullTeamMatcher_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GroupCommand(null));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        GroupCommand command = new GroupCommand();
        assertTrue(command.equals(command));
    }

    @Test
    public void equals_null_returnsFalse() {
        GroupCommand command = new GroupCommand();
        assertFalse(command.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        GroupCommand command = new GroupCommand();
        assertFalse(command.equals("not a command"));
    }

    @Test
    public void equals_differentCommand_returnsFalse() {
        GroupCommand command = new GroupCommand();
        DeleteCommand deleteCommand = new DeleteCommand(null);
        assertFalse(command.equals(deleteCommand));
    }

    @Test
    public void equals_sameTeamMatcher_returnsTrue() {
        TeamMatcher teamMatcher = new TeamMatcher();
        GroupCommand command1 = new GroupCommand(teamMatcher);
        GroupCommand command2 = new GroupCommand(teamMatcher);

        assertTrue(command1.equals(command2));
    }

    @Test
    public void equals_differentTeamMatcherInstances_returnsTrue() {
        // Since TeamMatcher is stateless, different instances should be equal
        TeamMatcher teamMatcher1 = new TeamMatcher();
        TeamMatcher teamMatcher2 = new TeamMatcher();
        GroupCommand command1 = new GroupCommand(teamMatcher1);
        GroupCommand command2 = new GroupCommand(teamMatcher2);

        assertTrue(command1.equals(command2));
    }

    @Test
    public void hashCode_sameTeamMatcher_returnsSameHashCode() {
        TeamMatcher teamMatcher = new TeamMatcher();
        GroupCommand command1 = new GroupCommand(teamMatcher);
        GroupCommand command2 = new GroupCommand(teamMatcher);

        assertEquals(command1.hashCode(), command2.hashCode());
    }

    @Test
    public void hashCode_differentTeamMatcherInstances_returnsSameHashCode() {
        // Since TeamMatcher instances are equal (stateless), hashCodes should match
        TeamMatcher teamMatcher1 = new TeamMatcher();
        TeamMatcher teamMatcher2 = new TeamMatcher();
        GroupCommand command1 = new GroupCommand(teamMatcher1);
        GroupCommand command2 = new GroupCommand(teamMatcher2);

        assertEquals(command1.hashCode(), command2.hashCode());
    }

    @Test
    public void hashCode_consistentAcrossMultipleCalls_returnsTrue() {
        GroupCommand command = new GroupCommand();
        int firstHashCode = command.hashCode();
        int secondHashCode = command.hashCode();

        assertEquals(firstHashCode, secondHashCode);
    }

    @Test
    public void execute_noTeamsFormed_throwsCommandException() {
        // Create persons but use a TeamMatcher that returns empty teams list
        Person top = new PersonBuilder().withName("Top1").withRole("top")
                .withRank("Gold").withChampion("Garen").build();
        Person jungle = new PersonBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Lee Sin").build();
        Person mid = new PersonBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();
        Person adc = new PersonBuilder().withName("Adc1").withRole("adc")
                .withRank("Gold").withChampion("Jinx").build();
        Person support = new PersonBuilder().withName("Support1").withRole("support")
                .withRank("Gold").withChampion("Leona").build();

        List<Person> unassignedPersons = Arrays.asList(top, jungle, mid, adc, support);
        ModelStubWithUnassignedPersons modelStub = new ModelStubWithUnassignedPersons(unassignedPersons);

        // Use a TeamMatcher that returns no teams
        TeamMatcherStub teamMatcherStub = new TeamMatcherStub(new ArrayList<>());
        GroupCommand command = new GroupCommand(teamMatcherStub);

        CommandException exception = assertThrows(CommandException.class, () -> command.execute(modelStub));
        assertEquals(GroupCommand.MESSAGE_NO_TEAMS_FORMED, exception.getMessage());
    }

    @Test
    public void toStringMethod() {
        TeamMatcher teamMatcher = new TeamMatcher();
        GroupCommand groupCommand = new GroupCommand(teamMatcher);
        String expected = GroupCommand.class.getCanonicalName() + "{teamMatcher=" + teamMatcher + "}";
        assertEquals(expected, groupCommand.toString());
    }

    /**
     * A TeamMatcher stub that returns a predefined list of teams.
     */
    private class TeamMatcherStub extends TeamMatcher {
        private final List<Team> teamsToReturn;

        TeamMatcherStub(List<Team> teamsToReturn) {
            this.teamsToReturn = teamsToReturn;
        }

        @Override
        public List<Team> matchTeams(List<Person> unassignedPersons) {
            return teamsToReturn;
        }
    }

    /**
     * A default model stub that has all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTeam(Team team) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTeam(Team team) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isPersonInAnyTeam(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTeam(Team target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTeam(Team target, Team editedTeam) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Team> getFilteredTeamList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getUnassignedPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTeamList(Predicate<Team> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Person> findPersonByName(Name name) {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that always provides unassigned persons and tracks teams.
     */
    private class ModelStubWithUnassignedPersons extends ModelStub {
        final ArrayList<Team> teamsAdded = new ArrayList<>();
        private final List<Person> unassignedPersons;

        ModelStubWithUnassignedPersons(List<Person> unassignedPersons) {
            requireNonNull(unassignedPersons);
            this.unassignedPersons = new ArrayList<>(unassignedPersons);
        }

        @Override
        public ObservableList<Person> getUnassignedPersonList() {
            return FXCollections.observableArrayList(unassignedPersons);
        }

        @Override
        public void addTeam(Team team) {
            requireNonNull(team);
            teamsAdded.add(team);
        }
    }
}
