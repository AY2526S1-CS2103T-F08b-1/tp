package seedu.summoners.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.summoners.logic.parser.CliSyntax.PREFIX_CHAMPION;
import static seedu.summoners.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.summoners.logic.parser.CliSyntax.PREFIX_RANK;
import static seedu.summoners.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.summoners.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.summoners.model.Model.PREDICATE_SHOW_ALL_PLAYERS;
import static seedu.summoners.model.Model.PREDICATE_SHOW_ALL_TEAMS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.summoners.commons.core.index.Index;
import seedu.summoners.commons.util.CollectionUtil;
import seedu.summoners.commons.util.ToStringBuilder;
import seedu.summoners.logic.Messages;
import seedu.summoners.logic.commands.exceptions.CommandException;
import seedu.summoners.model.Model;
import seedu.summoners.model.player.Champion;
import seedu.summoners.model.player.Name;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.player.Rank;
import seedu.summoners.model.player.Role;
import seedu.summoners.model.tag.Tag;
import seedu.summoners.model.team.Team;
import seedu.summoners.model.team.exceptions.DuplicateChampionException;
import seedu.summoners.model.team.exceptions.DuplicateRoleException;

/**
 * Edits the details of an existing player in the summoners book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the player identified "
            + "by the index number used in the displayed player list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_RANK + "RANK] "
            + "[" + PREFIX_ROLE + "ROLE] "
            + "[" + PREFIX_CHAMPION + "CHAMPION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_RANK + "Diamond "
            + PREFIX_CHAMPION + "Yasuo";

    public static final String MESSAGE_EDIT_PLAYER_SUCCESS = "Edited Player: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PLAYER = "This player already exists in the summoners book.";

    private final Index index;
    private final EditPlayerDescriptor editPlayerDescriptor;

    /**
     * @param index of the player in the filtered player list to edit
     * @param editPlayerDescriptor details to edit the player with
     */
    public EditCommand(Index index, EditPlayerDescriptor editPlayerDescriptor) {
        requireNonNull(index);
        requireNonNull(editPlayerDescriptor);

        this.index = index;
        this.editPlayerDescriptor = new EditPlayerDescriptor(editPlayerDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Player> lastShownPlayerList = model.getFilteredPlayerList();
        List<Team> lastShownTeamList = model.getFilteredTeamList();

        if (index.getZeroBased() >= lastShownPlayerList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PLAYER_DISPLAYED_INDEX);
        }

        Player playerToEdit = lastShownPlayerList.get(index.getZeroBased());
        Player editedPlayer = createEditedPlayer(playerToEdit, editPlayerDescriptor);

        if (!playerToEdit.isSamePlayer(editedPlayer) && model.hasPlayer(editedPlayer)) {
            throw new CommandException(MESSAGE_DUPLICATE_PLAYER);
        }

        Optional<Team> teamToEditOptional = lastShownTeamList.stream()
                .filter(team -> team.hasPlayer(playerToEdit))
                .findFirst();

        if (teamToEditOptional.isPresent()) {
            Team teamToEdit = teamToEditOptional.get();

            // Tries to create an updated team that includes the edited player.
            // This step validates that no duplicate roles or champions exist.
            // If validation fails, an exception is thrown and no model updates occur.
            try {
                Team editedTeam = createEditedTeam(teamToEdit, playerToEdit, editedPlayer);

                // Apply the updates only after successful validation.
                model.setPlayer(playerToEdit, editedPlayer);
                model.setTeam(teamToEdit, editedTeam);
            } catch (DuplicateRoleException | DuplicateChampionException e) {
                throw new CommandException(e.getMessage());
            }
        } else {
            model.setPlayer(playerToEdit, editedPlayer);
        }

        model.updateFilteredPlayerList(PREDICATE_SHOW_ALL_PLAYERS);
        model.updateFilteredTeamList(PREDICATE_SHOW_ALL_TEAMS);
        return new CommandResult(String.format(MESSAGE_EDIT_PLAYER_SUCCESS, Messages.format(editedPlayer)));
    }

    /**
     * Creates and returns a {@code Player} with the details of {@code playerToEdit}
     * edited with {@code editPlayerDescriptor}.
     */
    private static Player createEditedPlayer(Player playerToEdit, EditPlayerDescriptor editPlayerDescriptor) {
        assert playerToEdit != null;

        String id = playerToEdit.getId();
        Name updatedName = editPlayerDescriptor.getName().orElse(playerToEdit.getName());
        Rank updatedRank = editPlayerDescriptor.getRank().orElse(playerToEdit.getRank());
        Role updatedRole = editPlayerDescriptor.getRole().orElse(playerToEdit.getRole());
        Champion updatedChampion = editPlayerDescriptor.getChampion().orElse(playerToEdit.getChampion());
        Set<Tag> updatedTags = editPlayerDescriptor.getTags().orElse(playerToEdit.getTags());
        int wins = playerToEdit.getWins();
        int losses = playerToEdit.getLosses();

        // Preserve id from the original player
        return new Player(id, updatedName, updatedRole, updatedRank, updatedChampion, updatedTags, wins, losses);
    }

    private static Team createEditedTeam(Team teamToEdit, Player playerToEdit, Player editedPlayer) {
        assert teamToEdit.hasPlayer(playerToEdit);

        String id = teamToEdit.getId();
        List<Player> updatedPlayerList = new ArrayList<>(teamToEdit.getPlayers());
        int playerIndex = updatedPlayerList.indexOf(playerToEdit);
        updatedPlayerList.set(playerIndex, editedPlayer);
        int wins = teamToEdit.getWins();
        int losses = teamToEdit.getLosses();

        return new Team(id, updatedPlayerList, wins, losses);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editPlayerDescriptor.equals(otherEditCommand.editPlayerDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPlayerDescriptor", editPlayerDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the player with. Each non-empty field value will replace the
     * corresponding field value of the player.
     */
    public static class EditPlayerDescriptor {
        private Name name;
        private Role role;
        private Rank rank;
        private Champion champion;
        private Set<Tag> tags;

        public EditPlayerDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPlayerDescriptor(EditPlayerDescriptor toCopy) {
            setName(toCopy.name);
            setRole(toCopy.role);
            setRank(toCopy.rank);
            setChampion(toCopy.champion);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, role, rank, champion, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public Optional<Role> getRole() {
            return Optional.ofNullable(role);
        }

        public void setRank(Rank rank) {
            this.rank = rank;
        }

        public Optional<Rank> getRank() {
            return Optional.ofNullable(rank);
        }

        public void setChampion(Champion champion) {
            this.champion = champion;
        }

        public Optional<Champion> getChampion() {
            return Optional.ofNullable(champion);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPlayerDescriptor)) {
                return false;
            }

            EditPlayerDescriptor otherEditPlayerDescriptor = (EditPlayerDescriptor) other;
            return Objects.equals(name, otherEditPlayerDescriptor.name)
                    && Objects.equals(role, otherEditPlayerDescriptor.role)
                    && Objects.equals(rank, otherEditPlayerDescriptor.rank)
                    && Objects.equals(champion, otherEditPlayerDescriptor.champion)
                    && Objects.equals(tags, otherEditPlayerDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("role", role)
                    .add("rank", rank)
                    .add("champion", champion)
                    .add("tags", tags)
                    .toString();
        }
    }
}
