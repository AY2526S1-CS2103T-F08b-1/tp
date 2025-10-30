package seedu.summoners.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.summoners.logic.commands.EditCommand.EditPlayerDescriptor;
import seedu.summoners.model.player.Champion;
import seedu.summoners.model.player.Name;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.player.Rank;
import seedu.summoners.model.player.Role;
import seedu.summoners.model.tag.Tag;

/**
 * A utility class to help with building EditPlayerDescriptor objects.
 */
public class EditPlayerDescriptorBuilder {

    private EditPlayerDescriptor descriptor;

    public EditPlayerDescriptorBuilder() {
        descriptor = new EditPlayerDescriptor();
    }

    public EditPlayerDescriptorBuilder(EditPlayerDescriptor descriptor) {
        this.descriptor = new EditPlayerDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPlayerDescriptor} with fields containing {@code player}'s details
     */
    public EditPlayerDescriptorBuilder(Player player) {
        descriptor = new EditPlayerDescriptor();
        descriptor.setName(player.getName());
        descriptor.setRole(player.getRole());
        descriptor.setRank(player.getRank());
        descriptor.setChampion(player.getChampion());
        descriptor.setTags(player.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPlayerDescriptor} that we are building.
     */
    public EditPlayerDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code EditPlayerDescriptor} that we are building.
     */
    public EditPlayerDescriptorBuilder withRole(String role) {
        descriptor.setRole(new Role(role));
        return this;
    }

    /**
     * Sets the {@code Rank} of the {@code EditPlayerDescriptor} that we are building.
     */
    public EditPlayerDescriptorBuilder withRank(String rank) {
        descriptor.setRank(new Rank(rank));
        return this;
    }

    /**
     * Sets the {@code Summoners} of the {@code EditPlayerDescriptor} that we are building.
     */
    public EditPlayerDescriptorBuilder withChampion(String champion) {
        descriptor.setChampion(new Champion(champion));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPlayerDescriptor}
     * that we are building.
     */
    public EditPlayerDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPlayerDescriptor build() {
        return descriptor;
    }
}
