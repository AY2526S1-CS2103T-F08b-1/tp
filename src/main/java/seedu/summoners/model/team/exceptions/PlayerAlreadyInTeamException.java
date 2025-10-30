package seedu.address.model.team.exceptions;

import seedu.address.model.person.Person;

/**
 * Signals that the operation will result in a person being assigned to multiple teams.
 * A person can only be in one team at a time.
 */
public class PersonAlreadyInTeamException extends RuntimeException {

    /**
     * Constructs a PersonAlreadyInTeamException with a default message.
     */
    public PersonAlreadyInTeamException() {
        super("Operation would result in a person being assigned to multiple teams");
    }

    /**
     * Constructs a PersonAlreadyInTeamException with details about which person is already in a team.
     *
     * @param person The person who is already in a team.
     */
    public PersonAlreadyInTeamException(Person person) {
        super(String.format("Person %s is already in a team and cannot be added to another team",
                person.getName()));
    }
}
