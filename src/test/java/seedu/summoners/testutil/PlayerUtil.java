package seedu.summoners.testutil;

import static seedu.summoners.logic.parser.CliSyntax.PREFIX_CHAMPION;
import static seedu.summoners.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.summoners.logic.parser.CliSyntax.PREFIX_RANK;
import static seedu.summoners.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.summoners.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.summoners.logic.commands.AddCommand;
import seedu.summoners.logic.commands.EditCommand.EditPlayerDescriptor;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.tag.Tag;

/**
 * A utility class for Player.
 */
public class PlayerUtil {

    /**
     * Returns an add command string for adding the {@code player}.
     */
    public static String getAddCommand(Player player) {
        return AddCommand.COMMAND_WORD + " " + getPlayerDetails(player);
    }

    /**
     * Returns the part of command string for the given {@code player}'s details.
     */
    public static String getPlayerDetails(Player player) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME).append(player.getName().fullName).append(" ");
        sb.append(PREFIX_RANK).append(player.getRank().toString()).append(" ");
        sb.append(PREFIX_ROLE).append(player.getRole().toString()).append(" ");
        sb.append(PREFIX_CHAMPION).append(player.getChampion().toString()).append(" ");
        player.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPlayerDescriptor}'s details.
     */
    public static String getEditPlayerDescriptorDetails(EditPlayerDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getRole().ifPresent(role -> sb.append(PREFIX_ROLE).append(role.value).append(" "));
        descriptor.getRank().ifPresent(rank -> sb.append(PREFIX_RANK).append(rank.value).append(" "));
        descriptor.getChampion().ifPresent(champion -> sb.append(PREFIX_CHAMPION).append(champion.value)
                .append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
