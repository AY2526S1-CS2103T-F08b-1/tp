package seedu.summoners.logic.commands;

import static seedu.summoners.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.summoners.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.summoners.testutil.TypicalPlayers.getTypicalSummonersBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.summoners.logic.Messages;
import seedu.summoners.model.Model;
import seedu.summoners.model.ModelManager;
import seedu.summoners.model.UserPrefs;
import seedu.summoners.model.player.Player;
import seedu.summoners.testutil.PlayerBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalSummonersBook(), new UserPrefs());
    }

    @Test
    public void execute_newPlayer_success() {
        Player validPlayer = new PlayerBuilder().build();

        Model expectedModel = new ModelManager(model.getSummonersBook(), new UserPrefs());
        expectedModel.addPlayer(validPlayer);

        assertCommandSuccess(new AddCommand(validPlayer), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPlayer)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePlayer_throwsCommandException() {
        Player playerInList = model.getSummonersBook().getPlayerList().get(0);
        assertCommandFailure(new AddCommand(playerInList), model,
                AddCommand.MESSAGE_DUPLICATE_PLAYER);
    }

}
