package seedu.summoners.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.summoners.testutil.Assert.assertThrows;
import static seedu.summoners.testutil.TypicalPlayers.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.summoners.commons.core.GuiSettings;
import seedu.summoners.logic.Messages;
import seedu.summoners.logic.commands.exceptions.CommandException;
import seedu.summoners.model.SummonersBook;
import seedu.summoners.model.Model;
import seedu.summoners.model.ReadOnlySummonersBook;
import seedu.summoners.model.ReadOnlyUserPrefs;
import seedu.summoners.model.player.Name;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.Team;
import seedu.summoners.testutil.PlayerBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPlayer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_playerAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPlayerAdded modelStub = new ModelStubAcceptingPlayerAdded();
        Player validPlayer = new PlayerBuilder().build();

        CommandResult commandResult = new AddCommand(validPlayer).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPlayer)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPlayer), modelStub.playersAdded);
    }

    @Test
    public void execute_duplicatePlayer_throwsCommandException() {
        Player validPlayer = new PlayerBuilder().build();
        AddCommand addCommand = new AddCommand(validPlayer);
        ModelStub modelStub = new ModelStubWithPlayer(validPlayer);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PLAYER, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Player alice = new PlayerBuilder().withName("Alice").build();
        Player bob = new PlayerBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different player -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
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
        public boolean hasTeam(seedu.summoners.model.team.Team team) {
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
        public void deleteTeam(seedu.summoners.model.team.Team target) {
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

        @Override
        public ObservableList<Player> getUnassignedPlayerList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single player.
     */
    private class ModelStubWithPlayer extends ModelStub {
        private final Player player;

        ModelStubWithPlayer(Player player) {
            requireNonNull(player);
            this.player = player;
        }

        @Override
        public boolean hasPlayer(Player player) {
            requireNonNull(player);
            return this.player.isSamePlayer(player);
        }
    }

    /**
     * A Model stub that always accept the player being added.
     */
    private class ModelStubAcceptingPlayerAdded extends ModelStub {
        final ArrayList<Player> playersAdded = new ArrayList<>();

        @Override
        public boolean hasPlayer(Player player) {
            requireNonNull(player);
            return playersAdded.stream().anyMatch(player::isSamePlayer);
        }

        @Override
        public void addPlayer(Player player) {
            requireNonNull(player);
            playersAdded.add(player);
        }

        @Override
        public ReadOnlySummonersBook getSummonersBook() {
            return new SummonersBook();
        }
    }

}
