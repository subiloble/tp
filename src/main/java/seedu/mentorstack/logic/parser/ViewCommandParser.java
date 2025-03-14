package seedu.mentorstack.logic.parser;

import static seedu.mentorstack.logic.parser.CliSyntax.PREFIX_FILTER_TYPE;
import static seedu.mentorstack.logic.parser.CliSyntax.PREFIX_FILTER_VALUE;

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

        if (argMultimap.getValue(PREFIX_FILTER_TYPE).isEmpty() || argMultimap.getValue(PREFIX_FILTER_VALUE).isEmpty()) {
            return new ViewCommand(person -> true); // No filter applied, return all students
        }

        String filterType = argMultimap.getValue(PREFIX_FILTER_TYPE).get().toLowerCase();
        String filterValue = argMultimap.getValue(PREFIX_FILTER_VALUE).get();

        Predicate<Person> predicate = FilterPredicate.createPredicate(filterType, filterValue);
        if (predicate == null) {
            throw new ParseException(ViewCommand.MESSAGE_INVALID_FILTER);
        }

        return new ViewCommand(predicate);
    }
}
