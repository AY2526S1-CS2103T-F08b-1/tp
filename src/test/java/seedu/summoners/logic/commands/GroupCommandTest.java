package seedu.summoners.logic.commands;

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
import seedu.summoners.commons.core.GuiSettings;
import seedu.summoners.logic.commands.exceptions.CommandException;
import seedu.summoners.logic.teammatcher.TeamMatcher;
import seedu.summoners.model.Model;
import seedu.summoners.model.ReadOnlySummonersBook;
import seedu.summoners.model.ReadOnlyUserPrefs;
import seedu.summoners.model.player.Name;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.Team;
import seedu.summoners.testutil.PlayerBuilder;

public class GroupCommandTest {

    @Test
    public void execute_sufficientPlayers_success() throws Exception {
        // Create 5 unassigned players
        Player top = new PlayerBuilder().withName("Top1").withRole("top")
                .withRank("Gold").withChampion("Garen").build();
        Player jungle = new PlayerBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Lee Sin").build();
        Player mid = new PlayerBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();
        Player adc = new PlayerBuilder().withName("Adc1").withRole("adc")
                .withRank("Gold").withChampion("Jinx").build();
        Player support = new PlayerBuilder().withName("Support1").withRole("support")
                .withRank("Gold").withChampion("Leona").build();

        List<Player> unassignedPlayers = Arrays.asList(top, jungle, mid, adc, support);
        ModelStubWithUnassignedPlayers modelStub = new ModelStubWithUnassignedPlayers(unassignedPlayers);

        GroupCommand command = new GroupCommand();
        CommandResult result = command.execute(modelStub);

        // Verify team was added
        assertEquals(1, modelStub.teamsAdded.size());
        assertTrue(result.getFeedbackToUser().contains("Successfully created 1 team"));
    }

    @Test
    public void execute_noUnassignedPlayers_throwsCommandException() {
        ModelStubWithUnassignedPlayers modelStub = new ModelStubWithUnassignedPlayers(new ArrayList<>());

        GroupCommand command = new GroupCommand();

        CommandException exception = assertThrows(CommandException.class, () -> command.execute(modelStub));
        assertTrue(exception.getMessage().contains("No unassigned players"));
    }

    @Test
    public void execute_insufficientPlayers_throwsCommandException() {
        // Only 3 players - not enough for a full team
        Player top = new PlayerBuilder().withName("Top1").withRole("top")
                .withRank("Gold").withChampion("Garen").build();
        Player jungle = new PlayerBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Lee Sin").build();
        Player mid = new PlayerBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();

        List<Player> unassignedPlayers = Arrays.asList(top, jungle, mid);
        ModelStubWithUnassignedPlayers modelStub = new ModelStubWithUnassignedPlayers(unassignedPlayers);

        GroupCommand command = new GroupCommand();

        assertThrows(CommandException.class, () -> command.execute(modelStub));
    }

    @Test
    public void execute_multipleTeams_success() throws Exception {
        // Create 10 unassigned players (2 per role)
        Player top1 = new PlayerBuilder().withName("Top1").withRole("top")
                .withRank("Gold").withChampion("Garen").build();
        Player top2 = new PlayerBuilder().withName("Top2").withRole("top")
                .withRank("Silver").withChampion("Darius").build();

        Player jungle1 = new PlayerBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Lee Sin").build();
        Player jungle2 = new PlayerBuilder().withName("Jungle2").withRole("jungle")
                .withRank("Silver").withChampion("Jarvan IV").build();

        Player mid1 = new PlayerBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();
        Player mid2 = new PlayerBuilder().withName("Mid2").withRole("mid")
                .withRank("Silver").withChampion("Zed").build();

        Player adc1 = new PlayerBuilder().withName("Adc1").withRole("adc")
                .withRank("Gold").withChampion("Jinx").build();
        Player adc2 = new PlayerBuilder().withName("Adc2").withRole("adc")
                .withRank("Silver").withChampion("Ashe").build();

        Player support1 = new PlayerBuilder().withName("Support1").withRole("support")
                .withRank("Gold").withChampion("Leona").build();
        Player support2 = new PlayerBuilder().withName("Support2").withRole("support")
                .withRank("Silver").withChampion("Thresh").build();

        List<Player> unassignedPlayers = Arrays.asList(
                top1, top2, jungle1, jungle2, mid1, mid2, adc1, adc2, support1, support2
        );
        ModelStubWithUnassignedPlayers modelStub = new ModelStubWithUnassignedPlayers(unassignedPlayers);

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
        // Create players but use a TeamMatcher that returns empty teams list
        Player top = new PlayerBuilder().withName("Top1").withRole("top")
                .withRank("Gold").withChampion("Garen").build();
        Player jungle = new PlayerBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Lee Sin").build();
        Player mid = new PlayerBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();
        Player adc = new PlayerBuilder().withName("Adc1").withRole("adc")
                .withRank("Gold").withChampion("Jinx").build();
        Player support = new PlayerBuilder().withName("Support1").withRole("support")
                .withRank("Gold").withChampion("Leona").build();

        List<Player> unassignedPlayers = Arrays.asList(top, jungle, mid, adc, support);
        ModelStubWithUnassignedPlayers modelStub = new ModelStubWithUnassignedPlayers(unassignedPlayers);

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
        public List<Team> matchTeams(List<Player> unassignedPlayers) {
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
        public Path getSummonersBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSummonersBookFilePath(Path summonersBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPlayer(Player player) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTeam(Team team) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSummonersBook(ReadOnlySummonersBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlySummonersBook getSummonersBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPlayer(Player player) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTeam(Team team) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isPlayerInAnyTeam(Player player) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePlayer(Player target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTeam(Team target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPlayer(Player target, Player editedPlayer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTeam(Team target, Team editedTeam) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Player> getFilteredPlayerList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Team> getFilteredTeamList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Player> getUnassignedPlayerList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPlayerList(Predicate<Player> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTeamList(Predicate<Team> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Player> findPlayerByName(Name name) {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that always provides unassigned players and tracks teams.
     */
    private class ModelStubWithUnassignedPlayers extends ModelStub {
        final ArrayList<Team> teamsAdded = new ArrayList<>();
        private final List<Player> unassignedPlayers;

        ModelStubWithUnassignedPlayers(List<Player> unassignedPlayers) {
            requireNonNull(unassignedPlayers);
            this.unassignedPlayers = new ArrayList<>(unassignedPlayers);
        }

        @Override
        public ObservableList<Player> getUnassignedPlayerList() {
            return FXCollections.observableArrayList(unassignedPlayers);
        }

        @Override
        public ObservableList<Team> getFilteredTeamList() {
            return FXCollections.observableArrayList(this.teamsAdded);
        }

        @Override
        public void addTeam(Team team) {
            requireNonNull(team);
            teamsAdded.add(team);
        }
    }
}
