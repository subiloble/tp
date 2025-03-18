package seedu.mentorstack.logic.parser;

import static seedu.mentorstack.logic.parser.CliSyntax.PREFIX_FILTER_TYPE;
import static seedu.mentorstack.logic.parser.CliSyntax.PREFIX_FILTER_VALUE;
import static seedu.mentorstack.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.mentorstack.logic.commands.ViewCommand;
import seedu.mentorstack.logic.parser.exceptions.ParseException;
import seedu.mentorstack.model.person.Person;
import seedu.mentorstack.testutil.PersonBuilder;

/**
 * Contains unit tests for {@code ViewCommandParser}.
 */
public class ViewCommandParserTest {

    private final ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_validFilterType_returnsCorrectPredicate() throws ParseException {
        String userInput = " f/n v/Alice";
        ViewCommand command = parser.parse(userInput);

        Predicate<Person> predicate = command.predicate;

        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();

        assert predicate.test(alice) : "Alice should match the filter";
        assert !predicate.test(bob) : "Bob should NOT match the filter";
    }

    @Test
    public void parse_invalidFilter_throwsParseException() {
        String userInput = " " + PREFIX_FILTER_TYPE + " invalid " + PREFIX_FILTER_VALUE + " XYZ";
        assertParseFailure(parser, userInput, "Invalid filter type or value.");
    }
}
