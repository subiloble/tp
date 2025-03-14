package seedu.mentorstack.logic.commands;

import static seedu.mentorstack.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mentorstack.testutil.TypicalPersons.getTypicalMentorstack;

import org.junit.jupiter.api.Test;

import seedu.mentorstack.model.Mentorstack;
import seedu.mentorstack.model.Model;
import seedu.mentorstack.model.ModelManager;
import seedu.mentorstack.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyMentorstack_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyMentorstack_success() {
        Model model = new ModelManager(getTypicalMentorstack(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalMentorstack(), new UserPrefs());
        expectedModel.setMentorstack(new Mentorstack());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
