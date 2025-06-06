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
 * Archives a person identified using it's displayed index from Mentorstack.
 */
public class ArchiveCommand extends Command {

    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Archives the persons identified by the index numbers used in the displayed person list.\n"
            + "Parameters: INDEX1 INDEX2 ... (each must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 2 3";

    public static final String MESSAGE_ARCHIVE_PERSON_SUCCESS = "Archived Persons: %1$s\n"
            + "Here is the list of all unarchived persons:";

    public static final String MESSAGE_DUPLICATE_ARCHIVE = "This Person is already archived: ";

    private final Set<Index> targetIndices;

    public ArchiveCommand(Set<Index> targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // Ensure all indices are valid before archive
        for (Index index : targetIndices) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        for (Index index : targetIndices) {
            if (lastShownList.get(index.getZeroBased()).getIsArchived().testStatus()) {
                throw new CommandException(MESSAGE_DUPLICATE_ARCHIVE
                        + Messages.format(lastShownList.get(index.getZeroBased())));
            }
        }

        ArrayList<Person> personsToArchive = new ArrayList<Person>();
        for (Index index : targetIndices) {
            Person personToArchive = lastShownList.get(index.getZeroBased());
            personsToArchive.add(personToArchive);
        }

        // Perform archiving
        model.rememberMentorstack(); // save the state for undo
        StringBuilder archivedPersons = new StringBuilder();
        for (Person personToArchive : personsToArchive) {
            model.archivePerson(personToArchive, personToArchive.archived());
            archivedPersons.append(Messages.format(personToArchive)).append("\n");
        }
        return new CommandResult(String.format(MESSAGE_ARCHIVE_PERSON_SUCCESS, archivedPersons.toString().trim()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ArchiveCommand)) {
            return false;
        }

        ArchiveCommand otherArchiveCommand = (ArchiveCommand) other;
        return targetIndices.equals(otherArchiveCommand.targetIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndices)
                .toString();
    }
}
