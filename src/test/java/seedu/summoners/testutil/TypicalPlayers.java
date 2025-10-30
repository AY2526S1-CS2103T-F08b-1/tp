package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CHAMPION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CHAMPION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RANK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RANK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withRole("mid").withRank("gold").withChampion("Ahri")
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withRole("top").withRank("silver").withChampion("Garen")
            .withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withRole("jungle").withRank("platinum").withChampion("Lee Sin").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withRole("adc").withRank("gold").withChampion("Caitlyn")
            .withTags("friends").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withRole("support").withRank("diamond").withChampion("Lulu").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withRole("mid").withRank("master").withChampion("Zed").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withRole("top").withRank("iron").withChampion("Darius").build();
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .withRole("support").withRank("silver").withChampion("Leona").build();

    // Manually added
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .withRole("jungle").withRank("gold").withChampion("Warwick").build();
    public static final Person JAMES = new PersonBuilder().withName("James Franco")
            .withRole("adc").withRank("platinum").withChampion("Jhin").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withRole(VALID_ROLE_AMY).withRank(VALID_RANK_AMY).withChampion(VALID_CHAMPION_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withRole(VALID_ROLE_BOB).withRank(VALID_RANK_BOB).withChampion(VALID_CHAMPION_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    /**
     * Returns a list of typical persons.
     */
    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, HOON));
    }
}
