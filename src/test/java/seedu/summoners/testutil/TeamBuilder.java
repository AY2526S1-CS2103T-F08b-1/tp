package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * A utility class to help with building Team objects.
 */
public class TeamBuilder {

    public static final String DUMMY_ID = "";

    public static final int DEFAULT_WINS = 0;
    public static final int DEFAULT_LOSSES = 0;

    private String id;
    private List<Person> persons;
    private int wins;
    private int losses;

    /**
     * Creates a {@code TeamBuilder} with the default details.
     */
    public TeamBuilder() {
        id = UUID.randomUUID().toString();
        persons = new ArrayList<>();
        wins = DEFAULT_WINS;
        losses = DEFAULT_LOSSES;
    }

    /**
     * Initializes the TeamBuilder with the data of {@code teamToCopy}.
     */
    public TeamBuilder(Team teamToCopy) {
        id = teamToCopy.getId();
        persons = new ArrayList<>(teamToCopy.getPersons());
        wins = teamToCopy.getWins();
        losses = teamToCopy.getLosses();
    }

    /**
     * Sets the {@code persons} of the {@code Team} that we are building.
     */
    public TeamBuilder withPersons(Person... persons) {
        this.persons = Arrays.asList(persons);
        return this;
    }

    /**
     * Replaces the target {@code Person} in the team with the {@code editedPerson}.
     * If the target person does not exist in the team, the team remains unchanged.
     */
    public TeamBuilder replacePerson(Person target, Person editedPerson) {
        List<Person> updatedPersons = new ArrayList<>(persons);
        int index = updatedPersons.indexOf(target);
        if (index != -1) {
            updatedPersons.set(index, editedPerson);
        }
        this.persons = updatedPersons;
        return this;
    }

    public Team build() {
        return new Team(id, persons, wins, losses);
    }
}
