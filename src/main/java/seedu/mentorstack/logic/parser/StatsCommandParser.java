package seedu.mentorstack.logic.parser;

import static seedu.mentorstack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mentorstack.logic.parser.CliSyntax.*;

import seedu.mentorstack.logic.commands.AddCommand;
import seedu.mentorstack.logic.commands.StatsCommand;
import seedu.mentorstack.logic.parser.exceptions.ParseException;
import seedu.mentorstack.model.person.Subject;

import java.util.stream.Stream;

/**
 * Parses the given {@code String} of arguments in the context of the StatsCommand
 * and returns a StatsCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class StatsCommandParser implements Parser<StatsCommand> {

    @Override
    public StatsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new StatsCommand();
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT);

        if (!arePrefixesPresent(argMultimap, PREFIX_SUBJECT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE));
        }
        Subject subject;
        subject = ParserUtil.parseSubjects(argMultimap.getValue(PREFIX_SUBJECT).get());

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SUBJECT);

        return new StatsCommand(subject);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
