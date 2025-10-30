package seedu.summoners.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.summoners.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.summoners.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_CHAMPION_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_RANK_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.summoners.logic.commands.EditCommand.EditPlayerDescriptor;
import seedu.summoners.testutil.EditPlayerDescriptorBuilder;

public class EditPlayerDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPlayerDescriptor descriptorWithSameValues = new EditPlayerDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditPlayerDescriptor editedAmy = new EditPlayerDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different role -> returns false
        editedAmy = new EditPlayerDescriptorBuilder(DESC_AMY).withRole(VALID_ROLE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different rank -> returns false
        editedAmy = new EditPlayerDescriptorBuilder(DESC_AMY).withRank(VALID_RANK_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different champion -> returns false
        editedAmy = new EditPlayerDescriptorBuilder(DESC_AMY).withChampion(VALID_CHAMPION_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditPlayerDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditPlayerDescriptor editPlayerDescriptor = new EditPlayerDescriptor();
        String expected = EditPlayerDescriptor.class.getCanonicalName() + "{name="
                + editPlayerDescriptor.getName().orElse(null) + ", role="
                + editPlayerDescriptor.getRole().orElse(null) + ", rank="
                + editPlayerDescriptor.getRank().orElse(null) + ", champion="
                + editPlayerDescriptor.getChampion().orElse(null) + ", tags="
                + editPlayerDescriptor.getTags().orElse(null) + "}";
        assertEquals(expected, editPlayerDescriptor.toString());
    }
}
