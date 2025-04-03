package seedu.mentorstack.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.mentorstack.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.mentorstack.model.Model;
import seedu.mentorstack.model.person.Gender;
import seedu.mentorstack.model.person.Person;
import seedu.mentorstack.model.person.Subject;

/**
 * Displays statistics about the persons in Mentorstack.
 * If a subject is specified, it filters stats by that subject.
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows statistics of the Mentorstack.\n"
            + "Usage: " + COMMAND_WORD + " s/[SUBJECT]\n"
            + "Example: " + COMMAND_WORD + " s/CS1010S";

    public static final String MESSAGE_SUCCESS =
            "Total Persons: %d\nGender\nMale: %d\nFemale: %d";

    private final Subject subject;

    public StatsCommand() {
        this.subject = null;
    }

    public StatsCommand(Subject subject) {
        this.subject = subject;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS); // navigate to active list
        List<Person> personList = model.getFilteredPersonList();

        Predicate<Person> subjectFilter;
        Predicate<Person> personFilter;

        if (subject == null) {
            subjectFilter = p -> true; // No filtering
        } else {
            subjectFilter = p -> p.getSubjects().stream()
                    .anyMatch(sub -> sub.equals(subject)); // Compare with object
        }
        personList = personList.stream()
                .filter(subjectFilter)
                .collect(Collectors.toList());
        personFilter = personList::contains;

        model.updateFilteredPersonList(personFilter);

        int totalPersons = personList.size();
        Gender maleGender = new Gender("M");
        int maleCount = (int) personList.stream().filter(p -> maleGender.equals(p.getGender())).count();
        int femaleCount = totalPersons - maleCount;

        String resultMessage = subject == null
                ? String.format(MESSAGE_SUCCESS, totalPersons, maleCount, femaleCount)
                : String.format("Statistics for %s:\n" + MESSAGE_SUCCESS, subject, totalPersons,
                maleCount, femaleCount);

        return new CommandResult(resultMessage);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StatsCommand)) {
            return false;
        }

        StatsCommand otherStatsCommand = (StatsCommand) other;
        return subject.equals(otherStatsCommand.subject);
    }
}
