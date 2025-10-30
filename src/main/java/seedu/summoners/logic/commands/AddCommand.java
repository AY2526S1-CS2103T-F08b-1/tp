package seedu.summoners.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.summoners.logic.parser.CliSyntax.PREFIX_CHAMPION;
import static seedu.summoners.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.summoners.logic.parser.CliSyntax.PREFIX_RANK;
import static seedu.summoners.logic.parser.CliSyntax.PREFIX_ROLE;

import seedu.summoners.commons.util.ToStringBuilder;
import seedu.summoners.logic.Messages;
import seedu.summoners.logic.commands.exceptions.CommandException;
import seedu.summoners.model.Model;
import seedu.summoners.model.player.Player;

/**
 * Adds a player to SummonersBook.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a player to SummonersBook.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_RANK + "RANK "
            + PREFIX_ROLE + "ROLE "
            + PREFIX_CHAMPION + "CHAMPION\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Valerie "
            + PREFIX_RANK + "Gold "
            + PREFIX_ROLE + "Mid "
            + PREFIX_CHAMPION + "Ahri";

    public static final String MESSAGE_SUCCESS = "New player added: %1$s";
    public static final String MESSAGE_DUPLICATE_PLAYER = "This player already exists in SummonersBook";

    private final Player toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Player}
     */
    public AddCommand(Player player) {
        requireNonNull(player);
        toAdd = player;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPlayer(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PLAYER);
        }

        model.addPlayer(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
