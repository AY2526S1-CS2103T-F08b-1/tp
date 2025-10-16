package seedu.address.logic.commands;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.person.Person;


public class RemarkCommand extends Command {
    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Person: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from Person: %1$s";

    private final Index index;
    private final Remark remark;

    public RemarkCommand(Index index, String remark) {
        this(index, new Remark(remark));
    }

    public RemarkCommand(Index index, Remark remark) {
        this.index = index;
        this.remark = remark;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> list = model.getFilteredPersonList();

        if (index.getZeroBased() >= list.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = list.get(index.getZeroBased());
        Person edited = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                remark,
                personToEdit.getTags());

        model.setPerson(personToEdit, edited);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        String msg = remark.value.isEmpty() ? MESSAGE_DELETE_REMARK_SUCCESS : MESSAGE_ADD_REMARK_SUCCESS;
        return new CommandResult(String.format(msg, Messages.format(edited)));
    }
}

