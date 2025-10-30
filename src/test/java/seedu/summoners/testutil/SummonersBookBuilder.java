package seedu.summoners.testutil;

import seedu.summoners.model.SummonersBook;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.Team;

/**
 * A utility class to help with building Summonersbook objects.
 * Example usage: <br>
 *     {@code SummonersBook ab = new SummonersBookBuilder().withPlayer("John", "Doe").build();}
 */
public class SummonersBookBuilder {

    private SummonersBook summonersBook;

    public SummonersBookBuilder() {
        summonersBook = new SummonersBook();
    }

    public SummonersBookBuilder(SummonersBook summonersBook) {
        this.summonersBook = summonersBook;
    }

    /**
     * Adds a new {@code Player} to the {@code SummonersBook} that we are building.
     */
    public SummonersBookBuilder withPlayer(Player player) {
        summonersBook.addPlayer(player);
        return this;
    }

    /**
     * Adds a new {@code Team} to the {@code SummonersBook} that we are building.
     */
    public SummonersBookBuilder withTeam(Team team) {
        summonersBook.addTeam(team);
        return this;
    }

    public SummonersBook build() {
        return summonersBook;
    }
}
