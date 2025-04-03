package seedu.mentorstack.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.mentorstack.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.mentorstack.commons.core.index.Index;
import seedu.mentorstack.commons.util.ToStringBuilder;
import seedu.mentorstack.logic.Messages;
import seedu.mentorstack.logic.commands.exceptions.CommandException;
import seedu.mentorstack.model.Model;
import seedu.mentorstack.model.person.Person;

/**
 * Marks a person identified using it's displayed index from Mentorstack.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the persons identified by the index numbers used in the displayed person list.\n"
            + "Parameters: INDEX1 INDEX2 ... (each must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 2 3";

    public static final String MESSAGE_MARK_PERSON_SUCCESS = "Marked Persons: %1$s";

    private final Set<Index> targetIndices;

    public MarkCommand(Set<Index> targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        for (Index index : targetIndices) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        for (Index index : targetIndices) {
            Person target = lastShownList.get(index.getZeroBased());
            createMarkedPerson(target); //throws error if person is archived
        }

        model.rememberMentorstack(); // save the state for undo
        StringBuilder markedPersons = new StringBuilder();

        for (Index index : targetIndices) {
            Person target = lastShownList.get(index.getZeroBased());
            Person marked = createMarkedPerson(target);
            model.markPerson(target, marked);
            markedPersons.append(Messages.format(target)).append("\n");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_MARK_PERSON_SUCCESS, markedPersons.toString().trim()));
    }

    private static Person createMarkedPerson(Person target) throws CommandException {
        assert target != null;

        if (target.getIsArchived().testStatus()) {
            throw new CommandException(Messages.MESSAGE_IS_ARCHIVED);
        }

        return new Person(target.getName(), target.getGender(), target.getPhone(), target.getEmail(),
                target.getSubjects(), target.getFinishedSubjects(), target.getIsArchived(), true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkCommand)) {
            return false;
        }

        MarkCommand otherMarkCommand = (MarkCommand) other;
        return targetIndices.equals(otherMarkCommand.targetIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndices)
                .toString();
    }
}
