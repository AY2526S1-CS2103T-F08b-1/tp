package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.JAMES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * A utility class containing a list of {@code Team} objects to be used in tests.
 */
public class TypicalTeams {
    public static final Team TEAM_A = new TeamBuilder().withPersons(
            ALICE, BENSON, CARL, DANIEL, ELLE).build();

    public static final Team TEAM_B = new TeamBuilder().withPersons(
            FIONA, GEORGE, HOON, IDA, JAMES).build();

    private static final List<Person> TEAM_A_ROSTER = new ArrayList<>(Arrays.asList(
            ALICE, BENSON, CARL, DANIEL, ELLE));

    private static final List<Person> TEAM_B_ROSTER = new ArrayList<>(Arrays.asList(
            FIONA, GEORGE, HOON, IDA, JAMES));


    private TypicalTeams() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons and a typical team.
     * This is useful for integration tests.
     */
    public static AddressBook getTypicalAddressBookWithTeams() {
        AddressBook ab = new AddressBook();

        // Add the persons to the address book first
        for (Person person : TEAM_A_ROSTER) {
            ab.addPerson(person);
        }
        for (Person person : TEAM_B_ROSTER) {
            ab.addPerson(person);
        }
        // Then add the teams
        ab.addTeam(TEAM_A);
        ab.addTeam(TEAM_B);
        return ab;
    }

    /**
     * Returns a list of typical teams.
     */
    public static List<Team> getTypicalTeams() {
        return new ArrayList<>(Arrays.asList(TEAM_A, TEAM_B));
    }
}
