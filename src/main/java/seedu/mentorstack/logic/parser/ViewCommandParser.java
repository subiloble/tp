package seedu.mentorstack.logic.parser;

import static seedu.mentorstack.logic.parser.CliSyntax.PREFIX_FILTER_TYPE;
import static seedu.mentorstack.logic.parser.CliSyntax.PREFIX_FILTER_VALUE;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.mentorstack.logic.commands.ViewCommand;
import seedu.mentorstack.logic.parser.exceptions.ParseException;
import seedu.mentorstack.model.person.Person;
import seedu.mentorstack.model.person.predicates.FilterPredicate;

/**
 * Parses input arguments and creates a new ViewStudentsCommand object.
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    @Override
    public ViewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FILTER_TYPE, PREFIX_FILTER_VALUE);

        List<String> filterTypes = argMultimap.getAllValues(PREFIX_FILTER_TYPE);
        List<String> filterValues = argMultimap.getAllValues(PREFIX_FILTER_VALUE);

        if (filterTypes.isEmpty() || filterValues.isEmpty() || filterTypes.size() != filterValues.size()) {
            return new ViewCommand(person -> true); // No valid filters, return all students
        }

        // Create multiple predicates
        List<Predicate<Person>> predicates = new ArrayList<>();
        for (int i = 0; i < filterTypes.size(); i++) {
            Predicate<Person> predicate = FilterPredicate.createPredicate(filterTypes.get(i)
                    .toLowerCase(), filterValues.get(i));
            if (predicate == null) {
                throw new ParseException(ViewCommand.MESSAGE_INVALID_FILTER);
            }
            predicates.add(predicate);
        }

        // Combine predicates (Logical AND)
        Predicate<Person> finalPredicate = predicates.stream().reduce(p -> true, Predicate::and);

        return new ViewCommand(finalPredicate);
    }
}
