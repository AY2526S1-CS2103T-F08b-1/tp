package seedu.summoners.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.summoners.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.summoners.logic.commands.CommandTestUtil.FILTER_AMY;
import static seedu.summoners.logic.commands.CommandTestUtil.FILTER_AMY_AND_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_CHAMPION_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_RANK_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_ROLE_BOB;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.summoners.logic.commands.FilterCommand.FilterPlayerDescriptor;
import seedu.summoners.testutil.FilterPlayerDescriptorBuilder;

public class FilterPlayerDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        FilterPlayerDescriptor descriptorWithSameValues = new FilterPlayerDescriptor(FILTER_AMY);
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
        FilterPlayerDescriptor filteredAmy = new FilterPlayerDescriptorBuilder(FILTER_AMY)
                .withRoles(VALID_ROLE_BOB).build();
        assertFalse(FILTER_AMY.equals(filteredAmy));

        // different rank -> returns false
        filteredAmy = new FilterPlayerDescriptorBuilder(FILTER_AMY).withRanks(VALID_RANK_BOB).build();
        assertFalse(DESC_AMY.equals(filteredAmy));

        // different champion -> returns false
        filteredAmy = new FilterPlayerDescriptorBuilder(FILTER_AMY).withChampions(VALID_CHAMPION_BOB).build();
        assertFalse(DESC_AMY.equals(filteredAmy));
    }

    @Test
    public void toStringMethod() {
        FilterPlayerDescriptor filterPlayerDescriptor = new FilterPlayerDescriptor();
        String expected = FilterPlayerDescriptor.class.getCanonicalName() + "{roles="
                + Arrays.toString(filterPlayerDescriptor.getRoles()) + ", ranks="
                + Arrays.toString(filterPlayerDescriptor.getRanks()) + ", champions="
                + Arrays.toString(filterPlayerDescriptor.getChampions()) + ", scoreThreshold="
                + filterPlayerDescriptor.getScoreThreshold() + "}";
        assertEquals(expected, filterPlayerDescriptor.toString());
    }
}
