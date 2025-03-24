package seedu.mentorstack.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.mentorstack.commons.core.index.Index;
import seedu.mentorstack.commons.util.ToStringBuilder;
import seedu.mentorstack.logic.Messages;
import seedu.mentorstack.logic.commands.exceptions.CommandException;
import seedu.mentorstack.model.Model;
import seedu.mentorstack.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from Mentorstack.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the persons identified by the index numbers used in the displayed person list.\n"
            + "Parameters: INDEX1 INDEX2 ... (each must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 2 3";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Persons: %1$s";

    private final Set<Index> targetIndices;

    public DeleteCommand(Set<Index> targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // Ensure all indices are valid before deletion
        for (Index index : targetIndices) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        ArrayList<Person> personsToDelete = new ArrayList<>();
        // Perform deletion
        StringBuilder deletedPersons = new StringBuilder();
        for (Index index : targetIndices) {
            Person personToDelete = lastShownList.get(index.getZeroBased());
            personsToDelete.add(personToDelete);
        }

        for (Person personToDelete : personsToDelete) {
            model.deletePerson(personToDelete);
            deletedPersons.append(Messages.format(personToDelete)).append("\n");
        }
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPersons.toString().trim()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndices.equals(otherDeleteCommand.targetIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndices)
                .toString();
    }
}
