package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * Jackson-friendly version of {@link Team}.
 */
public class JsonAdaptedTeam {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Team's %s field is missing!";

    private final String teamId;
    private final List<String> personIds = new ArrayList<>();
    private final int wins;
    private final int losses;

    /**
     * Constructs a {@code JsonAdaptedTeam} with the given team details.
     */
    @JsonCreator
    public JsonAdaptedTeam(@JsonProperty("teamId") String teamId,
                           @JsonProperty("personIds") List<String> personIds,
                           @JsonProperty("wins") int wins,
                           @JsonProperty("losses") int losses) {
        this.teamId = teamId;
        if (personIds != null) {
            this.personIds.addAll(personIds);
        }
        this.wins = wins;
        this.losses = losses;
    }

    /**
     * Converts a given {@code Team} into this class for Jackson use.
     */
    public JsonAdaptedTeam(Team source) {
        this.teamId = source.getId();
        this.personIds.addAll(source.getPersons().stream()
                .map(Person::getId)
                .collect(Collectors.toList()));
        this.wins = source.getWins();
        this.losses = source.getLosses();
    }

    /**
     * Converts this Jackson-friendly adapted team object into the model's {@code Team} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted team.
     */
    public Team toModelType(List<Person> allPersons) throws IllegalValueException {
        if (teamId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "teamId"));
        }

        final List<Person> teamPersons = new ArrayList<>();
        for (String personId : personIds) {
            Person person = allPersons.stream()
                    .filter(p -> p.getId().equals(personId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalValueException("Invalid Person ID in Team: " + personId));
            teamPersons.add(person);
        }
        return new Team(teamId, teamPersons, wins, losses);
    }
}
