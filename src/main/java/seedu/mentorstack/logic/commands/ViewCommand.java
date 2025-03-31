package seedu.mentorstack.logic.commands;

import static seedu.mentorstack.logic.parser.CliSyntax.PREFIX_FILTER_TYPE;
import static seedu.mentorstack.logic.parser.CliSyntax.PREFIX_FILTER_VALUE;

import java.util.List;
import java.util.function.Predicate;

import seedu.mentorstack.commons.util.ToStringBuilder;
import seedu.mentorstack.model.Model;
import seedu.mentorstack.model.person.Person;

/**
 * Finds and lists all persons in Mentorstack according to the filter.
 * Keyword matching is case insensitive.
 */
public class ViewCommand extends Command {
    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays a list of students matching the filter. "
            + "Optional filters can be applied.\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_FILTER_TYPE + " s " + PREFIX_FILTER_VALUE + " A";

    public static final String MESSAGE_SUCCESS = "Filtered Students:\n%s";
    public static final String MESSAGE_NO_MATCH = "No students match the given criteria.";
    public static final String MESSAGE_INVALID_FILTER = "Invalid filter type or value.";

    public final Predicate<Person> predicate;

    public ViewCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        model.updateFilteredPersonList(predicate);
        List<String> filteredStudents = model.getFilteredPersonList()
                .stream()
                .filter(predicate)
                .map(person -> person.getName().fullName) // Extract only the name
                .toList();

        if (filteredStudents.isEmpty()) {
            return new CommandResult(MESSAGE_NO_MATCH);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, filteredStudents));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand)) {
            return false;
        }

        ViewCommand otherViewCommand = (ViewCommand) other;
        return predicate.equals(otherViewCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
