package seedu.mentorstack.logic.parser;

import static seedu.mentorstack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Set;

import seedu.mentorstack.commons.core.index.Index;
import seedu.mentorstack.logic.commands.UnarchiveCommand;
import seedu.mentorstack.logic.parser.exceptions.ParseException;
import seedu.mentorstack.logic.parser.exceptions.ParseWithHintException;

/**
 * Parses input arguments and creates a new UnarchiveCommand object
 */
public class UnarchiveCommandParser extends CommandParser implements Parser<UnarchiveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnarchiveCommand
     * and returns a UnarchiveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnarchiveCommand parse(String args) throws ParseException, ParseWithHintException {
        try {
            Set<Index> index = ParserUtil.parseIndexes(args);
            return new UnarchiveCommand(index);
        } catch (ParseException pe) {
            throw new ParseWithHintException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnarchiveCommand.MESSAGE_USAGE), pe, "INDEX [INDEX] [INDEX]");
        }
    }
}
