package seedu.address.model.team.exceptions;

import seedu.address.model.person.Person;

/**
 * Signals that the operation would result in duplicate roles in a team.
 * Each team must have exactly one person for each of the five roles: Top, Jungle, Mid, ADC, Support.
 */
public class DuplicateRoleException extends RuntimeException {
    private final Person person1;
    private final Person person2;

    /**
     * Constructs a DuplicateRoleException with the two persons who have conflicting roles.
     *
     * @param person1 The first person with the duplicate role.
     * @param person2 The second person with the duplicate role.
     */
    public DuplicateRoleException(Person person1, Person person2) {
        super("Operation would result in duplicate roles in the team. "
                + person1.getName() + " and " + person2.getName() + " both have the role: "
                + person1.getRole());
        this.person1 = person1;
        this.person2 = person2;
    }

    public Person getPerson1() {
        return person1;
    }

    public Person getPerson2() {
        return person2;
    }
}
