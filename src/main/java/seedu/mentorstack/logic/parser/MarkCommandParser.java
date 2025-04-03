package seedu.mentorstack.logic.parser;

import static seedu.mentorstack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Set;

import seedu.mentorstack.commons.core.index.Index;
import seedu.mentorstack.logic.commands.MarkCommand;
import seedu.mentorstack.logic.parser.exceptions.ParseException;
import seedu.mentorstack.logic.parser.exceptions.ParseWithHintException;

/**
 * Parses input arguments and creates a new MarkCommand object
 */
public class MarkCommandParser extends CommandParser implements Parser<MarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkCommand
     * and returns a MarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkCommand parse(String args) throws ParseException, ParseWithHintException {
        try {
            Set<Index> index = ParserUtil.parseIndexes(args);
            return new MarkCommand(index);
        } catch (ParseException pe) {
            throw new ParseWithHintException(
                    String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT,
                        MarkCommand.MESSAGE_USAGE),
                        pe,
                        "INDEX [INDEX] [INDEX]"
                    );
        }
    }

}
