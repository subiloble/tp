package seedu.mentorstack.logic.parser;

import static seedu.mentorstack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Set;

import seedu.mentorstack.commons.core.index.Index;
import seedu.mentorstack.logic.commands.ArchiveCommand;
import seedu.mentorstack.logic.parser.exceptions.ParseException;
import seedu.mentorstack.logic.parser.exceptions.ParseWithHintException;

/**
 * Parses input arguments and creates a new ArchiveCommand object
 */
public class ArchiveCommandParser extends CommandParser implements Parser<ArchiveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ArchiveCommand
     * and returns a ArchiveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ArchiveCommand parse(String args) throws ParseException, ParseWithHintException {
        try {
            Set<Index> index = ParserUtil.parseIndexes(args);
            return new ArchiveCommand(index);
        } catch (ParseException pe) {
            throw new ParseWithHintException(
                    String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT,
                        ArchiveCommand.MESSAGE_USAGE),
                        pe,
                        "INDEX [INDEX] [INDEX]"
                    );
        }
    }

}
