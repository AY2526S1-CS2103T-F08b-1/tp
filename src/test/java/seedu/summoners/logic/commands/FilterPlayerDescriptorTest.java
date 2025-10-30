package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.FILTER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.FILTER_AMY_AND_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CHAMPION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RANK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand.FilterPersonDescriptor;
import seedu.address.testutil.FilterPersonDescriptorBuilder;

public class FilterPersonDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        FilterPersonDescriptor descriptorWithSameValues = new FilterPersonDescriptor(FILTER_AMY);
        assertTrue(FILTER_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(FILTER_AMY.equals(FILTER_AMY));

        // null -> returns false
        assertFalse(FILTER_AMY.equals(null));

        // different types -> returns false
        assertFalse(FILTER_AMY.equals(5));

        // different values -> returns false
        assertFalse(FILTER_AMY.equals(FILTER_AMY_AND_BOB));

        // different role -> returns false
        FilterPersonDescriptor filteredAmy = new FilterPersonDescriptorBuilder(FILTER_AMY)
                .withRoles(VALID_ROLE_BOB).build();
        assertFalse(FILTER_AMY.equals(filteredAmy));

        // different rank -> returns false
        filteredAmy = new FilterPersonDescriptorBuilder(FILTER_AMY).withRanks(VALID_RANK_BOB).build();
        assertFalse(DESC_AMY.equals(filteredAmy));

        // different champion -> returns false
        filteredAmy = new FilterPersonDescriptorBuilder(FILTER_AMY).withChampions(VALID_CHAMPION_BOB).build();
        assertFalse(DESC_AMY.equals(filteredAmy));
    }

    @Test
    public void toStringMethod() {
        FilterPersonDescriptor filterPersonDescriptor = new FilterPersonDescriptor();
        String expected = FilterPersonDescriptor.class.getCanonicalName() + "{roles="
                + Arrays.toString(filterPersonDescriptor.getRoles()) + ", ranks="
                + Arrays.toString(filterPersonDescriptor.getRanks()) + ", champions="
                + Arrays.toString(filterPersonDescriptor.getChampions()) + ", scoreThreshold="
                + filterPersonDescriptor.getScoreThreshold() + "}";
        assertEquals(expected, filterPersonDescriptor.toString());
    }
}
