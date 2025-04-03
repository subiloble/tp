package seedu.mentorstack.logic.parser;

import static seedu.mentorstack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mentorstack.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.mentorstack.logic.commands.StatsCommand;
import seedu.mentorstack.logic.parser.exceptions.ParseException;
import seedu.mentorstack.logic.parser.exceptions.ParseWithHintException;
import seedu.mentorstack.model.person.Subject;

/**
 * Parses the given {@code String} of arguments in the context of the StatsCommand
 * and returns a StatsCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class StatsCommandParser extends CommandParser implements Parser<StatsCommand> {

    private static final Logger logger = Logger.getLogger(StatsCommandParser.class.getName());

    @Override
    public StatsCommand parse(String args) throws ParseException, ParseWithHintException {
        logger.log(Level.INFO, "Parsing StatsCommand with arguments: {0}", args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            logger.log(Level.INFO, "No arguments provided, returning StatsCommand without filtering.");
            return new StatsCommand();
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT);

        if (!arePrefixesPresent(argMultimap, PREFIX_SUBJECT)
                || !argMultimap.getPreamble().isEmpty()) {
            logger.log(Level.WARNING, "Invalid command format for StatsCommand.");
            throw new ParseWithHintException(String.format(
                MESSAGE_INVALID_COMMAND_FORMAT,
                StatsCommand.MESSAGE_USAGE),
                "s/[SUBJECT]"
            );
        }

        Subject subject;
        try {
            subject = ParserUtil.parseSubjects(argMultimap.getValue(PREFIX_SUBJECT).get());
            logger.log(Level.INFO, "Successfully parsed subject: {0}", subject);
        } catch (ParseException pe) {
            logger.log(Level.WARNING, "Failed to parse subject in StatsCommand.", pe);
            throw new ParseWithHintException(String.format(
                MESSAGE_INVALID_COMMAND_FORMAT,
                StatsCommand.MESSAGE_USAGE),
                pe,
                "[SUBJECT]"
            );
        }

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
