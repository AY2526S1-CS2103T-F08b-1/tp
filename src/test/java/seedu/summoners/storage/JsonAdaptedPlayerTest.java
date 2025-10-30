package seedu.summoners.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.summoners.storage.JsonAdaptedPlayer.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.summoners.testutil.Assert.assertThrows;
import static seedu.summoners.testutil.TypicalPlayers.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.summoners.commons.exceptions.IllegalValueException;
import seedu.summoners.model.player.Champion;
import seedu.summoners.model.player.Name;
import seedu.summoners.model.player.Rank;
import seedu.summoners.model.player.Role;

public class JsonAdaptedPlayerTest {
    private static final String DUMMY_ID = "";
    private static final int DUMMY_WINS = 0;
    private static final int DUMMY_LOSSES = 0;

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_ROLE = "Sky";
    private static final String INVALID_RANK = "Wood";
    private static final String INVALID_CHAMPION = "Aniga";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_ROLE = BENSON.getRole().toString();
    private static final String VALID_RANK = BENSON.getRank().toString();
    private static final String VALID_CHAMPION = BENSON.getChampion().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final JsonAdaptedStats VALID_STATS = new JsonAdaptedStats(BENSON.getStats());

    @Test
    public void toModelType_validPlayerDetails_returnsPlayer() throws Exception {
        JsonAdaptedPlayer player = new JsonAdaptedPlayer(BENSON);
        assertEquals(BENSON, player.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPlayer player =
                new JsonAdaptedPlayer(DUMMY_ID, INVALID_NAME, VALID_ROLE, VALID_RANK, VALID_CHAMPION, VALID_TAGS,
                        DUMMY_WINS, DUMMY_LOSSES, VALID_STATS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, player::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPlayer player = new JsonAdaptedPlayer(DUMMY_ID, null, VALID_ROLE, VALID_RANK, VALID_CHAMPION,
                VALID_TAGS, DUMMY_WINS, DUMMY_LOSSES, VALID_STATS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, player::toModelType);
    }

    @Test
    public void toModelType_nullRole_throwsIllegalValueException() {
        JsonAdaptedPlayer player = new JsonAdaptedPlayer(DUMMY_ID, VALID_NAME, null, VALID_RANK, VALID_CHAMPION,
                VALID_TAGS, DUMMY_WINS, DUMMY_LOSSES, VALID_STATS);
        String expectedMessage = String.format(JsonAdaptedPlayer.MISSING_FIELD_MESSAGE_FORMAT,
                Role.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, player::toModelType);
    }

    @Test
    public void toModelType_invalidRole_throwsIllegalValueException() {
        JsonAdaptedPlayer player = new JsonAdaptedPlayer(DUMMY_ID, VALID_NAME, INVALID_ROLE, VALID_RANK, VALID_CHAMPION,
                VALID_TAGS, DUMMY_WINS, DUMMY_LOSSES, VALID_STATS);
        assertThrows(IllegalValueException.class, Role.MESSAGE_CONSTRAINTS, player::toModelType);
    }

    @Test
    public void toModelType_nullRank_throwsIllegalValueException() {
        JsonAdaptedPlayer player = new JsonAdaptedPlayer(DUMMY_ID, VALID_NAME, VALID_ROLE, null, VALID_CHAMPION,
                VALID_TAGS, DUMMY_WINS, DUMMY_LOSSES, VALID_STATS);
        String expectedMessage = String.format(JsonAdaptedPlayer.MISSING_FIELD_MESSAGE_FORMAT,
                Rank.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, player::toModelType);
    }

    @Test
    public void toModelType_invalidRank_throwsIllegalValueException() {
        JsonAdaptedPlayer player = new JsonAdaptedPlayer(DUMMY_ID, VALID_NAME, VALID_ROLE, INVALID_RANK, VALID_CHAMPION,
                VALID_TAGS, DUMMY_WINS, DUMMY_LOSSES, VALID_STATS);
        assertThrows(IllegalValueException.class, Rank.MESSAGE_CONSTRAINTS, player::toModelType);
    }

    @Test
    public void toModelType_nullChampion_throwsIllegalValueException() {
        JsonAdaptedPlayer player = new JsonAdaptedPlayer(DUMMY_ID, VALID_NAME, VALID_ROLE, VALID_RANK, null,
                VALID_TAGS, DUMMY_WINS, DUMMY_LOSSES, VALID_STATS);
        String expectedMessage = String.format(JsonAdaptedPlayer.MISSING_FIELD_MESSAGE_FORMAT,
                Champion.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, player::toModelType);
    }

    @Test
    public void toModelType_invalidChampion_throwsIllegalValueException() {
        JsonAdaptedPlayer player = new JsonAdaptedPlayer(DUMMY_ID, VALID_NAME, VALID_ROLE, VALID_RANK, INVALID_CHAMPION,
                VALID_TAGS, DUMMY_WINS, DUMMY_LOSSES, VALID_STATS);
        assertThrows(IllegalValueException.class, Champion.MESSAGE_CONSTRAINTS, player::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPlayer player =
                new JsonAdaptedPlayer(DUMMY_ID, VALID_NAME, VALID_ROLE, VALID_RANK, VALID_CHAMPION, invalidTags,
                        DUMMY_WINS, DUMMY_LOSSES, VALID_STATS);
        assertThrows(IllegalValueException.class, player::toModelType);
    }

}
