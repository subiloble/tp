package seedu.mentorstack.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows statistics of the mentor list.\n"
            + "Usage: " + COMMAND_WORD + " s/[SUBJECT]\n"
            + "Example: " + COMMAND_WORD + " s/CS1010S";

    public static final String MESSAGE_SUCCESS =
            "Total Persons: %d\nGender\nMale: %d\nFemale: %d";

    private static final Logger logger = Logger.getLogger(StatsCommand.class.getName());

    private final Subject subject;

    /**
     * Initialise the statistics command with no parameter input
     */
    public StatsCommand() {
        this.subject = null;
        logger.log(Level.INFO, "StatsCommand created without a subject filter.");
    }

    /**
     * Initialise the statistics command
     * filters the students statistics based on the input subject
     * @param subject
     */
    public StatsCommand(Subject subject) {
        this.subject = subject;
        logger.log(Level.INFO, "StatsCommand created with subject filter: {0}", subject);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.log(Level.INFO, "Executing StatsCommand...");

        List<Person> personList = model.getFilteredPersonList();
        logger.log(Level.INFO, "Total persons before filtering: {0}", personList.size());

        Predicate<Person> subjectFilter;

        if (subject == null) {
            subjectFilter = p -> true; // No filtering
            logger.log(Level.INFO, "No subject filter applied.");
        } else {
            subjectFilter = p -> p.getSubjects().stream()
                    .anyMatch(sub -> sub.equals(subject));
            logger.log(Level.INFO, "Applying subject filter for: {0}", subject);
        }

        personList = personList.stream()
                .filter(subjectFilter)
                .collect(Collectors.toList());

        int totalPersons = personList.size();
        Gender maleGender = new Gender("M");
        int maleCount = (int) personList.stream().filter(p -> maleGender.equals(p.getGender())).count();
        int femaleCount = totalPersons - maleCount;

        logger.log(Level.INFO, "Filtered total persons: {0}", totalPersons);
        logger.log(Level.INFO, "Male count: {0}, Female count: {1}", new Object[]{maleCount, femaleCount});

        String resultMessage = subject == null
                ? String.format(MESSAGE_SUCCESS, totalPersons, maleCount, femaleCount)
                : String.format("Statistics for %s:\n" + MESSAGE_SUCCESS, subject, totalPersons,
                maleCount, femaleCount);

        logger.log(Level.INFO, "StatsCommand executed successfully.");
        return new CommandResult(resultMessage);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof StatsCommand)) {
            return false;
        }

        StatsCommand otherStatsCommand = (StatsCommand) other;
        return subject.equals(otherStatsCommand.subject);
    }
}
