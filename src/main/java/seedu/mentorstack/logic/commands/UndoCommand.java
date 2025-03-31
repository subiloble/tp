package seedu.mentorstack.logic.commands;

import seedu.mentorstack.logic.commands.exceptions.CommandException;
import seedu.mentorstack.model.Model;

import static seedu.mentorstack.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Undo the last operation that changed the data.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo successful!";
    public static final String MESSAGE_FAILURE = "Nothing to undo.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.canUndo()) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        assert model.canUndo() : "not undoable";
        model.undo();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
