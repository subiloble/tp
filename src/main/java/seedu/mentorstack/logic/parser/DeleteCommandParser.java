package seedu.mentorstack.logic.parser;

import static seedu.mentorstack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.mentorstack.model.person.Email;
import seedu.mentorstack.logic.commands.DeleteCommand;
import seedu.mentorstack.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        String email = args.trim();
        if (email.isEmpty()) {
            throw new ParseException("Email cannot be empty. Usage: delete-student <email>");
        }
        return new DeleteCommand(new Email(email));
    }

}
