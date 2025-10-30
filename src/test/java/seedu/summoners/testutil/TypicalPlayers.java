package seedu.summoners.testutil;

import static seedu.summoners.logic.commands.CommandTestUtil.VALID_CHAMPION_AMY;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_CHAMPION_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_RANK_AMY;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_RANK_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.summoners.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.summoners.model.SummonersBook;
import seedu.summoners.model.player.Player;

/**
 * A utility class containing a list of {@code Player} objects to be used in tests.
 */
public class TypicalPlayers {

    public static final Player ALICE = new PlayerBuilder().withName("Alice Pauline")
            .withRole("mid").withRank("gold").withChampion("Ahri")
            .withTags("friends").build();
    public static final Player BENSON = new PlayerBuilder().withName("Benson Meier")
            .withRole("top").withRank("silver").withChampion("Garen")
            .withTags("owesMoney", "friends").build();
    public static final Player CARL = new PlayerBuilder().withName("Carl Kurz")
            .withRole("jungle").withRank("platinum").withChampion("Lee Sin").build();
    public static final Player DANIEL = new PlayerBuilder().withName("Daniel Meier")
            .withRole("adc").withRank("gold").withChampion("Caitlyn")
            .withTags("friends").build();
    public static final Player ELLE = new PlayerBuilder().withName("Elle Meyer")
            .withRole("support").withRank("diamond").withChampion("Lulu").build();
    public static final Player FIONA = new PlayerBuilder().withName("Fiona Kunz")
            .withRole("mid").withRank("master").withChampion("Zed").build();
    public static final Player GEORGE = new PlayerBuilder().withName("George Best")
            .withRole("top").withRank("iron").withChampion("Darius").build();
    public static final Player HOON = new PlayerBuilder().withName("Hoon Meier")
            .withRole("support").withRank("silver").withChampion("Leona").build();

    // Manually added
    public static final Player IDA = new PlayerBuilder().withName("Ida Mueller")
            .withRole("jungle").withRank("gold").withChampion("Warwick").build();
    public static final Player JAMES = new PlayerBuilder().withName("James Franco")
            .withRole("adc").withRank("platinum").withChampion("Jhin").build();

    // Manually added - Player's details found in {@code CommandTestUtil}
    public static final Player AMY = new PlayerBuilder().withName(VALID_NAME_AMY)
            .withRole(VALID_ROLE_AMY).withRank(VALID_RANK_AMY).withChampion(VALID_CHAMPION_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Player BOB = new PlayerBuilder().withName(VALID_NAME_BOB)
            .withRole(VALID_ROLE_BOB).withRank(VALID_RANK_BOB).withChampion(VALID_CHAMPION_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPlayers() {} // prevents instantiation

    /**
     * Returns an {@code SummonersBook} with all the typical players.
     */
    public static SummonersBook getTypicalSummonersBook() {
        SummonersBook ab = new SummonersBook();
        for (Player player : getTypicalPlayers()) {
            ab.addPlayer(player);
        }
        return ab;
    }

    /**
     * Returns a list of typical players.
     */
    public static List<Player> getTypicalPlayers() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, HOON));
    }
}
