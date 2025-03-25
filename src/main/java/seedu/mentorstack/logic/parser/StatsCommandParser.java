package seedu.mentorstack.logic.parser;

import static seedu.mentorstack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mentorstack.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.mentorstack.logic.commands.StatsCommand;
import seedu.mentorstack.logic.parser.exceptions.ParseException;
import seedu.mentorstack.model.person.Subject;

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

        Subject subject;
        try {
            subject = ParserUtil.parseSubjects(argMultimap.getValue(PREFIX_SUBJECT).get());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SUBJECT);

        return new StatsCommand(subject);
    }
}
