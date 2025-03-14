package seedu.mentorstack.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.mentorstack.model.Mentorstack;
import seedu.mentorstack.model.Model;

/**
 * Clears the Mentorstack.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Mentorstack has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setMentorstack(new Mentorstack());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
