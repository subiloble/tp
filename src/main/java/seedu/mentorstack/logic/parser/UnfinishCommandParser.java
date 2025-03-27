package seedu.mentorstack.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.mentorstack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mentorstack.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import seedu.mentorstack.commons.core.index.Index;
import seedu.mentorstack.logic.commands.UnfinishCommand;
import seedu.mentorstack.logic.parser.exceptions.ParseException;
import seedu.mentorstack.model.person.Subject;

/**
 * Parses input arguments and creates a new UnfinishCommand object
 */
public class UnfinishCommandParser implements Parser<UnfinishCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnfinishCommand
     * and returns an UnfinishCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnfinishCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnfinishCommand.MESSAGE_USAGE), pe);
        }

        Set<Subject> subjectsToUnfinish = parseSubjectsForUnfinish(argMultimap.getAllValues(PREFIX_SUBJECT))
                .orElseThrow(() -> new ParseException(UnfinishCommand.MESSAGE_NO_SUBJECTS));

        return new UnfinishCommand(index, subjectsToUnfinish);
    }

    /**
     * Parses {@code Collection<String> subjects} into a {@code Set<Subjects>} if {@code subjects} is non-empty.
     * If {@code subjects} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Subjects>} containing zero subjects.
     */
    private Optional<Set<Subject>> parseSubjectsForUnfinish(Collection<String> subjects) throws ParseException {
        assert subjects != null;

        if (subjects.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(ParserUtil.parseSubjects(subjects));
    }

}
