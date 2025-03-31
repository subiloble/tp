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
 * Marks a subject of an existing person as finished in Mentorstack.
 */
public class FinishCommand extends Command {

    public static final String COMMAND_WORD = "finish";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a subject of an existing person "
            + "by the index number used in the displayed person list as finished.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_SUBJECT + "SUBJECT]... "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SUBJECT + "CS2103";

    public static final String MESSAGE_FINISH_SUBJECT_SUCCESS = "Marked subjects as finished.";
    public static final String MESSAGE_NO_SUBJECTS = "At least one subject must be provided.";
    public static final String MESSAGE_SUBJECT_DOES_NOT_EXIST = "Student is not enrolled in subjects provided.";

    private final Index index;
    private final Set<Subject> subjectsToFinish;

    /**
     * @param index of the person in the filtered person list to edit
     * @param subjectsToFinish subjects to mark as finished
     */
    public FinishCommand(Index index, Set<Subject> subjectsToFinish) {
        requireNonNull(index);
        requireNonNull(subjectsToFinish);

        this.index = index;
        this.subjectsToFinish = subjectsToFinish;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToFinish = lastShownList.get(index.getZeroBased());
        Set<Subject> subjects = personToFinish.getSubjects();
        Set<Subject> finishedSubjects = personToFinish.getFinishedSubjects();

        for (Subject subject : subjectsToFinish) {
            if (!subjects.contains(subject) && !finishedSubjects.contains(subject)) {
                throw new CommandException(MESSAGE_SUBJECT_DOES_NOT_EXIST);
            }
        }

        Person finishedPerson = createFinishedPerson(personToFinish, subjectsToFinish);

        model.rememberMentorstack(); // save the state for undo
        model.setPerson(personToFinish, finishedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_FINISH_SUBJECT_SUCCESS, Messages.format(finishedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToFinish}
     * finished with {@code subjectsToFinish}.
     */
    private static Person createFinishedPerson(Person personToFinish, Set<Subject> subjectsToFinish) {
        assert personToFinish != null;
        assert subjectsToFinish != null;

        Set<Subject> subjects = personToFinish.getSubjects();
        Set<Subject> finishedSubjects = personToFinish.getFinishedSubjects();

        Set<Subject> newSubjects = new HashSet<>();
        Set<Subject> newFinishedSubjects = new HashSet<>(finishedSubjects);

        for (Subject subject : subjects) {
            if (subjectsToFinish.contains(subject)) {
                newFinishedSubjects.add(subject);
            } else {
                newSubjects.add(subject);
            }
        }

        return new Person(personToFinish.getName(), personToFinish.getGender(), personToFinish.getPhone(),
                personToFinish.getEmail(), newSubjects, newFinishedSubjects,
                personToFinish.getIsArchived(), personToFinish.getIsMarked());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FinishCommand)) {
            return false;
        }

        FinishCommand otherFinishCommand = (FinishCommand) other;
        return index.equals(otherFinishCommand.index)
                && subjectsToFinish.equals(otherFinishCommand.subjectsToFinish);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("subjectsToFinish", subjectsToFinish)
                .toString();
    }
}
