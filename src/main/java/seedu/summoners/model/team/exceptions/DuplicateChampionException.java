package seedu.address.model.team.exceptions;

import seedu.address.model.person.Person;

/**
 * Signals that the operation would result in duplicate champions in a team.
 * Each team cannot have multiple persons with the same champion.
 */
public class DuplicateChampionException extends RuntimeException {
    private final Person person1;
    private final Person person2;

    /**
     * Constructs a DuplicateChampionException with the two persons who have conflicting champions.
     *
     * @param person1 The first person with the duplicate champion.
     * @param person2 The second person with the duplicate champion.
     */
    public DuplicateChampionException(Person person1, Person person2) {
        super("Operation would result in duplicate champions in the team. "
                + person1.getName() + " and " + person2.getName() + " both play: "
                + person1.getChampion());
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
