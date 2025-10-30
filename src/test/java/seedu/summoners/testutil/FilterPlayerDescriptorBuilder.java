package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.FilterCommand.FilterPersonDescriptor;
import seedu.address.model.person.Champion;
import seedu.address.model.person.Rank;
import seedu.address.model.person.Role;

/**
 * A utility class to help with building FilterPersonDescriptor objects.
 */
public class FilterPersonDescriptorBuilder {

    private FilterPersonDescriptor descriptor;

    public FilterPersonDescriptorBuilder() {
        descriptor = new FilterPersonDescriptor();
    }

    public FilterPersonDescriptorBuilder(FilterPersonDescriptor descriptor) {
        this.descriptor = new FilterPersonDescriptor(descriptor);
    }

    /**
     * Sets the {@code roles} of the {@code FilterPersonDescriptor} that we are building.
     */
    public FilterPersonDescriptorBuilder withRoles(String... roles) {
        Set<Role> roleSet = Stream.of(roles).map(Role::new).collect(Collectors.toSet());
        descriptor.setRoles(roleSet);
        return this;
    }

    /**
     * Sets the {@code ranks} of the {@code FilterPersonDescriptor} that we are building.
     */
    public FilterPersonDescriptorBuilder withRanks(String... ranks) {
        Set<Rank> rankSet = Stream.of(ranks).map(Rank::new).collect(Collectors.toSet());
        descriptor.setRanks(rankSet);
        return this;
    }

    /**
     * Sets the {@code champions} of the {@code FilterPersonDescriptor} that we are building.
     */
    public FilterPersonDescriptorBuilder withChampions(String... champions) {
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
    public FilterPersonDescriptorBuilder withScoreThreshold(Float scoreThreshold) {
        descriptor.setScoreThreshold(scoreThreshold);
        return this;
    }

    public FilterPersonDescriptor build() {
        return descriptor;
    }
}
