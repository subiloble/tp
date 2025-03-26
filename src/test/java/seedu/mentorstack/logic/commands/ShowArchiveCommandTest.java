package seedu.mentorstack.logic.commands;

import static seedu.mentorstack.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mentorstack.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.mentorstack.model.Model.PREDICATE_SHOW_ALL_ARCHIVED_PERSONS;
import static seedu.mentorstack.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.mentorstack.testutil.TypicalPersons.getTypicalMentorstack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.mentorstack.model.Model;
import seedu.mentorstack.model.ModelManager;
import seedu.mentorstack.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ShowArchiveCommand.
 */
public class ShowArchiveCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMentorstack(), new UserPrefs());
        expectedModel = new ModelManager(model.getMentorstack(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_ARCHIVED_PERSONS);
        assertCommandSuccess(new ShowArchiveCommand(), model, ShowArchiveCommand.MESSAGE_SUCCESS, expectedModel);
    }

}