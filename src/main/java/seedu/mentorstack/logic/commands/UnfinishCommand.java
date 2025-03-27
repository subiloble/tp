package seedu.mentorstack.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.mentorstack.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.mentorstack.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.mentorstack.commons.core.index.Index;
import seedu.mentorstack.commons.util.ToStringBuilder;
import seedu.mentorstack.logic.Messages;
import seedu.mentorstack.logic.commands.exceptions.CommandException;
import seedu.mentorstack.model.Model;
import seedu.mentorstack.model.person.Person;
import seedu.mentorstack.model.person.Subject;

/**
 * Marks a subject of an existing person as unfinished in Mentorstack.
 */
public class UnfinishCommand extends Command {

    public static final String COMMAND_WORD = "unfinish";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a subject of an existing person "
            + "by the index number used in the displayed person list as unfinished.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_SUBJECT + "SUBJECT]... "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SUBJECT + "CS2103";

    public static final String MESSAGE_UNFINISH_SUBJECT_SUCCESS = "Marked subjects as unfinished.";
    public static final String MESSAGE_NO_SUBJECTS = "At least one subject must be provided.";
    public static final String MESSAGE_SUBJECT_DOES_NOT_EXIST = "Student is not enrolled in subjects provided.";

    private final Index index;
    private final Set<Subject> subjectsToUnfinish;

    /**
     * @param index of the person in the filtered person list to edit
     * @param subjectsToUnfinish subjects to mark as unfinished
     */
    public UnfinishCommand(Index index, Set<Subject> subjectsToUnfinish) {
        requireNonNull(index);
        requireNonNull(subjectsToUnfinish);

        this.index = index;
        this.subjectsToUnfinish = subjectsToUnfinish;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUnfinish = lastShownList.get(index.getZeroBased());
        Set<Subject> subjects = personToUnfinish.getSubjects();
        Set<Subject> finishedSubjects = personToUnfinish.getFinishedSubjects();

        for (Subject subject : subjectsToUnfinish) {
            if (!subjects.contains(subject) && !finishedSubjects.contains(subject)) {
                throw new CommandException(MESSAGE_SUBJECT_DOES_NOT_EXIST);
            }
        }

        Person unfinishedPerson = createUnfinishedPerson(personToUnfinish, subjectsToUnfinish);

        model.setPerson(personToUnfinish, unfinishedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_UNFINISH_SUBJECT_SUCCESS, Messages.format(unfinishedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToUnfinish}
     * unfinished with {@code subjectsToUnfinish}.
     */
    private static Person createUnfinishedPerson(Person personToUnfinish, Set<Subject> subjectsToUnfinish) {
        assert personToUnfinish != null;
        assert subjectsToUnfinish != null;

        Set<Subject> subjects = personToUnfinish.getSubjects();
        Set<Subject> finishedSubjects = personToUnfinish.getFinishedSubjects();

        Set<Subject> newSubjects = new HashSet<>(subjects);
        Set<Subject> newFinishedSubjects = new HashSet<>();

        for (Subject subject : finishedSubjects) {
            if (subjectsToUnfinish.contains(subject)) {
                newSubjects.add(subject);
            } else {
                newFinishedSubjects.add(subject);
            }
        }

        return new Person(personToUnfinish.getName(), personToUnfinish.getGender(), personToUnfinish.getPhone(),
                personToUnfinish.getEmail(), newSubjects, newFinishedSubjects, personToUnfinish.getIsArchived());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnfinishCommand)) {
            return false;
        }

        UnfinishCommand otherUnfinishCommand = (UnfinishCommand) other;
        return index.equals(otherUnfinishCommand.index)
                && subjectsToUnfinish.equals(otherUnfinishCommand.subjectsToUnfinish);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("subjectsToUnfinish", subjectsToUnfinish)
                .toString();
    }
}
