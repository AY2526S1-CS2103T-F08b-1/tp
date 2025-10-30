package seedu.summoners.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_FIRST_TEAM;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_SECOND_TEAM;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.summoners.commons.core.GuiSettings;
import seedu.summoners.commons.core.index.Index;
import seedu.summoners.logic.Messages;
import seedu.summoners.logic.commands.exceptions.CommandException;
import seedu.summoners.model.Model;
import seedu.summoners.model.ReadOnlySummonersBook;
import seedu.summoners.model.ReadOnlyUserPrefs;
import seedu.summoners.model.player.Name;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.Team;
import seedu.summoners.testutil.PlayerBuilder;

public class UngroupCommandTest {

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ModelStubWithTeams modelStub = new ModelStubWithTeams();

        // Create 2 teams
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

        Team team1 = new Team(Arrays.asList(top, jungle, mid, adc, support));

        Player top2 = new PlayerBuilder().withName("Top2").withRole("top")
                .withRank("Silver").withChampion("Darius").build();
        Player jungle2 = new PlayerBuilder().withName("Jungle2").withRole("jungle")
                .withRank("Silver").withChampion("Jarvan IV").build();
        Player mid2 = new PlayerBuilder().withName("Mid2").withRole("mid")
                .withRank("Silver").withChampion("Zed").build();
        Player adc2 = new PlayerBuilder().withName("Adc2").withRole("adc")
                .withRank("Silver").withChampion("Ashe").build();
        Player support2 = new PlayerBuilder().withName("Support2").withRole("support")
                .withRank("Silver").withChampion("Thresh").build();

        Team team2 = new Team(Arrays.asList(top2, jungle2, mid2, adc2, support2));

        modelStub.addTeam(team1);
        modelStub.addTeam(team2);

        UngroupCommand ungroupCommand = new UngroupCommand(INDEX_FIRST_TEAM);
        CommandResult result = ungroupCommand.execute(modelStub);

        assertEquals(1, modelStub.teams.size());
        assertTrue(result.getFeedbackToUser().contains("Removed team"));
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        ModelStubWithTeams modelStub = new ModelStubWithTeams();

        UngroupCommand ungroupCommand = new UngroupCommand(Index.fromOneBased(5));

        assertThrows(CommandException.class, () -> ungroupCommand.execute(modelStub),
                Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_removeAllWithTeams_success() throws Exception {
        ModelStubWithTeams modelStub = new ModelStubWithTeams();

        // Create 2 teams
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

        Team team1 = new Team(Arrays.asList(top, jungle, mid, adc, support));

        Player top2 = new PlayerBuilder().withName("Top2").withRole("top")
                .withRank("Silver").withChampion("Darius").build();
        Player jungle2 = new PlayerBuilder().withName("Jungle2").withRole("jungle")
                .withRank("Silver").withChampion("Jarvan IV").build();
        Player mid2 = new PlayerBuilder().withName("Mid2").withRole("mid")
                .withRank("Silver").withChampion("Zed").build();
        Player adc2 = new PlayerBuilder().withName("Adc2").withRole("adc")
                .withRank("Silver").withChampion("Ashe").build();
        Player support2 = new PlayerBuilder().withName("Support2").withRole("support")
                .withRank("Silver").withChampion("Thresh").build();

        Team team2 = new Team(Arrays.asList(top2, jungle2, mid2, adc2, support2));

        modelStub.addTeam(team1);
        modelStub.addTeam(team2);

        UngroupCommand ungroupCommand = new UngroupCommand();
        CommandResult result = ungroupCommand.execute(modelStub);

        assertEquals(0, modelStub.teams.size());
        assertTrue(result.getFeedbackToUser().contains("Successfully removed 2 team(s)"));
    }

    @Test
    public void execute_removeAllWithNoTeams_throwsCommandException() {
        ModelStubWithTeams modelStub = new ModelStubWithTeams();

        UngroupCommand ungroupCommand = new UngroupCommand();

        assertThrows(CommandException.class, () -> ungroupCommand.execute(modelStub),
                UngroupCommand.MESSAGE_NO_TEAMS);
    }

    @Test
    public void equals() {
        UngroupCommand ungroupFirstCommand = new UngroupCommand(INDEX_FIRST_TEAM);
        UngroupCommand ungroupSecondCommand = new UngroupCommand(INDEX_SECOND_TEAM);
        UngroupCommand ungroupAllCommand = new UngroupCommand();
        UngroupCommand ungroupAllCommand2 = new UngroupCommand();

        // same object -> returns true
        assertTrue(ungroupFirstCommand.equals(ungroupFirstCommand));

        // same values -> returns true
        UngroupCommand ungroupFirstCommandCopy = new UngroupCommand(INDEX_FIRST_TEAM);
        assertTrue(ungroupFirstCommand.equals(ungroupFirstCommandCopy));

        // different types -> returns false
        assertFalse(ungroupFirstCommand.equals(1));

        // null -> returns false
        assertFalse(ungroupFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(ungroupFirstCommand.equals(ungroupSecondCommand));

        // both remove all -> returns true
        assertTrue(ungroupAllCommand.equals(ungroupAllCommand2));

        // one remove all, one remove specific -> returns false
        assertFalse(ungroupAllCommand.equals(ungroupFirstCommand));
    }

    @Test
    public void hashCode_test() {
        UngroupCommand ungroupFirstCommand = new UngroupCommand(INDEX_FIRST_TEAM);
        UngroupCommand ungroupSecondCommand = new UngroupCommand(INDEX_SECOND_TEAM);
        UngroupCommand ungroupAllCommand = new UngroupCommand();
        UngroupCommand ungroupAllCommand2 = new UngroupCommand();

        // same values -> same hash code
        UngroupCommand ungroupFirstCommandCopy = new UngroupCommand(INDEX_FIRST_TEAM);
        assertEquals(ungroupFirstCommand.hashCode(), ungroupFirstCommandCopy.hashCode());

        // both remove all -> same hash code
        assertEquals(ungroupAllCommand.hashCode(), ungroupAllCommand2.hashCode());

        // different index -> different hash code (highly likely but not guaranteed)
        assertFalse(ungroupFirstCommand.hashCode() == ungroupSecondCommand.hashCode());

        // remove all vs remove specific -> different hash code (highly likely but not guaranteed)
        assertFalse(ungroupAllCommand.hashCode() == ungroupFirstCommand.hashCode());

        // hash code is consistent across multiple calls
        int firstHash = ungroupFirstCommand.hashCode();
        assertEquals(firstHash, ungroupFirstCommand.hashCode());
        assertEquals(firstHash, ungroupFirstCommand.hashCode());
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UngroupCommand ungroupCommand = new UngroupCommand(targetIndex);
        String expected = UngroupCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex
                + ", removeAll=" + false + "}";
        assertEquals(expected, ungroupCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
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
            return Optional.empty(); // default stub returns empty
        }

    }

    /**
     * A Model stub that contains teams.
     */
    private class ModelStubWithTeams extends ModelStub {
        final ArrayList<Team> teams = new ArrayList<>();

        @Override
        public void addTeam(Team team) {
            teams.add(team);
        }

        @Override
        public ObservableList<Team> getFilteredTeamList() {
            return FXCollections.observableArrayList(teams);
        }

        @Override
        public void deleteTeam(Team target) {
            teams.remove(target);
        }
    }
}
