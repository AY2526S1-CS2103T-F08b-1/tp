package seedu.summoners.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.summoners.logic.commands.FilterCommand.FilterPlayerDescriptor;
import seedu.summoners.model.player.Champion;
import seedu.summoners.model.player.Rank;
import seedu.summoners.model.player.Role;

/**
 * A utility class to help with building FilterPlayerDescriptor objects.
 */
public class FilterPlayerDescriptorBuilder {

    private FilterPlayerDescriptor descriptor;

    public FilterPlayerDescriptorBuilder() {
        descriptor = new FilterPlayerDescriptor();
    }

    public FilterPlayerDescriptorBuilder(FilterPlayerDescriptor descriptor) {
        this.descriptor = new FilterPlayerDescriptor(descriptor);
    }

    /**
     * Sets the {@code roles} of the {@code FilterPlayerDescriptor} that we are building.
     */
    public FilterPlayerDescriptorBuilder withRoles(String... roles) {
        Set<Role> roleSet = Stream.of(roles).map(Role::new).collect(Collectors.toSet());
        descriptor.setRoles(roleSet);
        return this;
    }

    /**
     * Sets the {@code ranks} of the {@code FilterPlayerDescriptor} that we are building.
     */
    public FilterPlayerDescriptorBuilder withRanks(String... ranks) {
        Set<Rank> rankSet = Stream.of(ranks).map(Rank::new).collect(Collectors.toSet());
        descriptor.setRanks(rankSet);
        return this;
    }

    /**
     * Sets the {@code champions} of the {@code FilterPlayerDescriptor} that we are building.
     */
    public FilterPlayerDescriptorBuilder withChampions(String... champions) {
        Set<Champion> championSet = Stream.of(champions).map(Champion::new).collect(Collectors.toSet());
        descriptor.setChampions(championSet);
        return this;
    }

    /**
     * Sets the score threshold for this descriptor.
     * @param scoreThreshold the minimum score threshold to filter by; may be {@code null}
     *                       if no score filter is applied
     * @return this builder instance for method chaining
     */
    public FilterPlayerDescriptorBuilder withScoreThreshold(Float scoreThreshold) {
        descriptor.setScoreThreshold(scoreThreshold);
        return this;
    }

    public FilterPlayerDescriptor build() {
        return descriptor;
    }
}
