package seedu.mentorstack.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.mentorstack.testutil.TypicalPersons.getTypicalMentorstack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.mentorstack.logic.commands.exceptions.CommandException;
import seedu.mentorstack.model.Mentorstack;
import seedu.mentorstack.model.Model;
import seedu.mentorstack.model.ModelManager;
import seedu.mentorstack.model.UserPrefs;

/**
 * Contains tests for {@code UndoCommand}.
 */
public class UndoCommandTest {

    private Model model;
    private UndoCommand undoCommand;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMentorstack(), new UserPrefs());
        undoCommand = new UndoCommand();
    }

    @Test
    public void execute_undo_successful() throws CommandException {
        model.setMentorstack(new Mentorstack());
        CommandResult result = undoCommand.execute(model);

        // Verify successful undo
        assertEquals(UndoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(getTypicalMentorstack(), model.getMentorstack());
    }

    @Test
    public void execute_noPreviousState_throwsCommandException() {
        // Attempt to undo without any prior state change
        assertThrows(CommandException.class, () -> undoCommand.execute(model), UndoCommand.MESSAGE_FAILURE);
    }
}
