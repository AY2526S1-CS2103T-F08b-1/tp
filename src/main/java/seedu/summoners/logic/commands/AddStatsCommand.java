package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CPM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GD15;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KDA;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TEAMS;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Champion;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Rank;
import seedu.address.model.person.Role;
import seedu.address.model.person.Stats;
import seedu.address.model.tag.Tag;
import seedu.address.model.team.Team;

/**
 * Adds a new set of performance statistics (CPM, GD@15, KDA) to a specified person,
 * and updates any team containing that person to reference the edited person.
 * <p>
 * The command targets a person by index in the currently displayed person list.
 * If the person is part of a team shown in the current team list, that team is updated
 * to point to the edited person instance, preserving team identity and order.
 * </p>
 *
 * <p><b>Usage:</b></p>
 * <pre>
 *   {@code addStats INDEX }{ @code PREFIX_CPM }{@code <cpm> }
 *                       { @code PREFIX_GD15 }{@code <gd15> }
 *                       { @code PREFIX_KDA }{@code <kda> }
 *   e.g. addStats 1 c/10.2 g/2400 k/2.6
 * </pre>
 */
public class AddStatsCommand extends Command {
    /** Primary command word for adding statistics. */
    public static final String COMMAND_WORD = "addStats";

    /** Usage string describing parameters and an example. */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add new performance stats of the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_CPM + "CPM "
            + PREFIX_GD15 + "GD15 "
            + PREFIX_KDA + "KDA\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_CPM + "10.2 "
            + PREFIX_GD15 + "2400 "
            + PREFIX_KDA + "2.6";

    /** Success message format used in the result. */
    public static final String MESSAGE_RECORD_SUCCESS = "Stats recorded: %1$s";
    public static final String MESSAGE_EMPTY_FIELDS = "All fields are required and cannot be blank";


    private final Index index;
    private final String cpm;
    private final String gd15;
    private final String kda;

    /**
     * Constructs an {@code AddStatsCommand}.
     *
     * @param index Index of the target person in the current filtered person list. Must be non-null.
     * @param cpm   CS per minute as a string (validated by {@link Stats#isValidStats(String, String, String)}).
     * @param gd15  Gold difference at 15 minutes as a string.
     * @param kda   KDA as a string.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public AddStatsCommand(Index index, String cpm, String gd15, String kda) {
        requireNonNull(index);
        requireAllNonNull(cpm, gd15, kda);

        this.index = index;
        this.cpm = cpm;
        this.gd15 = gd15;
        this.kda = kda;
    }

    /**
     * Executes the command: appends a new statistics entry to the selected person, and if that
     * person appears in a displayed team, updates that team to reference the edited person.
     * <ul>
     *   <li>Validates the index against the current filtered person list.</li>
     *   <li>Builds an edited {@link Person} with updated {@link Stats}.</li>
     *   <li>Updates the model's person and, if applicable, the owning team.</li>
     *   <li>Refreshes both person and team filtered lists.</li>
     * </ul>
     *
     * @param model The model in which the update is performed. Must be non-null.
     * @return Command result with a success message describing the edited person.
     * @throws CommandException if the index is invalid or the model rejects the update.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Team> lastShownTeamList = model.getFilteredTeamList();

        if (index.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownPersonList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, cpm, gd15, kda);

        Optional<Team> teamToEditOptional = lastShownTeamList.stream()
                .filter(team -> team.hasPerson(personToEdit))
                .findFirst();

        if (teamToEditOptional.isPresent()) {
            Team teamToEdit = teamToEditOptional.get();
            Team editedTeam = createEditedTeam(teamToEdit, personToEdit, editedPerson);

            // Apply the updates only after successful validation.
            model.setPerson(personToEdit, editedPerson);
            model.setTeam(teamToEdit, editedTeam);
        } else {
            model.setPerson(personToEdit, editedPerson);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredTeamList(PREDICATE_SHOW_ALL_TEAMS);
        return new CommandResult(String.format(MESSAGE_RECORD_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates a new {@link Person} based on {@code personToEdit} with updated {@link Stats}
     * produced by appending the given CPM, GD@15, and KDA values.
     * <p>
     * Identity (ID) and other attributes are preserved; wins and losses are also carried over.
     * </p>
     *
     * @param personToEdit The original person to update. Must be non-null.
     * @param cpm          CS per minute as a string.
     * @param gd15         Gold difference at 15 minutes as a string.
     * @param kda          KDA as a string.
     * @return A new {@code Person} instance with the updated stats.
     * @throws IllegalArgumentException if the stats values are invalid.
     */
    private static Person createEditedPerson(Person personToEdit, String cpm, String gd15, String kda) {
        assert personToEdit != null;

        String id = personToEdit.getId();
        Name updatedName = personToEdit.getName();
        Rank updatedRank = personToEdit.getRank();
        Role updatedRole = personToEdit.getRole();
        Champion updatedChampion = personToEdit.getChampion();
        Set<Tag> updatedTags = personToEdit.getTags();
        int wins = personToEdit.getWins();
        int losses = personToEdit.getLosses();

        Stats updatedStats = personToEdit.getStats().addLatestStats(cpm, gd15, kda);

        // Preserve id from the original person
        return new Person(id, updatedName, updatedRole, updatedRank, updatedChampion, updatedTags,
                wins, losses, updatedStats);
    }

    /**
     * Produces a new {@link Team} identical to {@code teamToEdit} except that
     * {@code personToEdit} is replaced by {@code editedPerson} in the team roster.
     * Preserves team identity (ID) and its win–loss record.
     *
     * @param teamToEdit    The team currently containing {@code personToEdit}. Must contain that person.
     * @param personToEdit  The original person entry to replace.
     * @param editedPerson  The new person entry to insert.
     * @return A new {@code Team} reflecting the person update.
     * @throws AssertionError if the team does not contain {@code personToEdit}.
     */
    private static Team createEditedTeam(Team teamToEdit, Person personToEdit, Person editedPerson) {
        assert teamToEdit.hasPerson(personToEdit);

        String id = teamToEdit.getId();
        List<Person> updatedPersonList = new ArrayList<>(teamToEdit.getPersons());
        int personIndex = updatedPersonList.indexOf(personToEdit);
        updatedPersonList.set(personIndex, editedPerson);
        int wins = teamToEdit.getWins();
        int losses = teamToEdit.getLosses();

        return new Team(id, updatedPersonList, wins, losses);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddStatsCommand)) {
            return false;
        }

        AddStatsCommand otherAddStatsCommand = (AddStatsCommand) other;
        return index.equals(otherAddStatsCommand.index)
                && cpm.equals(otherAddStatsCommand.cpm)
                && gd15.equals(otherAddStatsCommand.gd15)
                && kda.equals(otherAddStatsCommand.kda);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("cpm", cpm)
                .add("gd15", gd15)
                .add("kda", kda)
                .toString();
    }
}
