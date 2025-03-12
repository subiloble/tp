package seedu.mentorstack.logic.parser;

import static seedu.mentorstack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mentorstack.logic.parser.CliSyntax.*;

import java.util.Set;
import java.util.stream.Stream;

import seedu.mentorstack.logic.commands.AddCommand;
import seedu.mentorstack.logic.parser.exceptions.ParseException;
import seedu.mentorstack.model.person.*;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_SUBJECT);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_SUBJECT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Set<Subjects> subjectList = ParserUtil.parseSubjects(argMultimap.getAllValues(PREFIX_SUBJECT));

        Person person = new Person(name, phone, email, subjectList);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
