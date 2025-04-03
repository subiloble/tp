package seedu.mentorstack.logic.parser;

import static seedu.mentorstack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mentorstack.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.mentorstack.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.mentorstack.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.mentorstack.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.mentorstack.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.mentorstack.logic.commands.AddCommand;
import seedu.mentorstack.logic.parser.exceptions.ParseException;
import seedu.mentorstack.logic.parser.exceptions.ParseWithHintException;
import seedu.mentorstack.model.person.Email;
import seedu.mentorstack.model.person.Gender;
import seedu.mentorstack.model.person.Name;
import seedu.mentorstack.model.person.Person;
import seedu.mentorstack.model.person.Phone;
import seedu.mentorstack.model.person.Subject;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser extends CommandParser implements Parser<AddCommand> {

    private static final Logger logger = Logger.getLogger(AddCommandParser.class.getName());

    @Override
    public AddCommand parse(String args) throws ParseException, ParseWithHintException {
        logger.log(Level.INFO, "Parsing AddCommand with arguments: {0}", args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GENDER, PREFIX_EMAIL,
                        PREFIX_PHONE, PREFIX_SUBJECT);

        Map<String, String> ideal = new HashMap<>();
        ideal.put("n/", "John Doe");
        ideal.put("e/", "johnd@example.com");
        ideal.put("p/", "98765432");
        ideal.put("s/", "maths computer science");
        ideal.put("g/", "F");

        String missing = super.getMissingArgs(argMultimap, ideal);

        if (missing.length() > 0) {
            throw new ParseWithHintException(
                String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT,
                    AddCommand.MESSAGE_USAGE
                ),
                missing
            );
        } else if (!arePrefixesPresent(
            argMultimap,
            PREFIX_NAME,
            PREFIX_GENDER,
            PREFIX_EMAIL,
            PREFIX_PHONE,
            PREFIX_SUBJECT) || !argMultimap.getPreamble().isEmpty()) {
            logger.log(Level.WARNING, "Missing required prefixes or unexpected preamble.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_GENDER, PREFIX_PHONE, PREFIX_EMAIL);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Gender gender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Set<Subject> subjectList = ParserUtil.parseSubjects(argMultimap.getAllValues(PREFIX_SUBJECT));

        Person person = new Person(name, gender, phone, email, subjectList);

        logger.log(Level.INFO, "Successfully parsed AddCommand: {0}", person);

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
