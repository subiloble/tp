package seedu.mentorstack.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mentorstack.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.mentorstack.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mentorstack.testutil.TypicalIndexSets.INDEX_SET_FIRST_PERSON;
import static seedu.mentorstack.testutil.TypicalIndexSets.INDEX_SET_SECOND_PERSON;
import static seedu.mentorstack.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.mentorstack.testutil.TypicalPersons.getTypicalMentorstack;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.mentorstack.commons.core.index.Index;
import seedu.mentorstack.logic.Messages;
import seedu.mentorstack.logic.commands.exceptions.CommandException;
import seedu.mentorstack.model.Model;
import seedu.mentorstack.model.ModelManager;
import seedu.mentorstack.model.UserPrefs;
import seedu.mentorstack.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UnarchiveCommand.
 */
class UnarchiveCommandTest {

    private Model model = new ModelManager(getTypicalMentorstack(), new UserPrefs());

    @Test
    public void execute_success() throws CommandException {
        Person personToArchive = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_SET_FIRST_PERSON);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_SET_FIRST_PERSON);
        ShowArchiveCommand showArchiveCommand = new ShowArchiveCommand();
        archiveCommand.execute(model);
        showArchiveCommand.execute(model);
        Person personToUnarchive = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());


        String expectedMessage = String.format(UnarchiveCommand.MESSAGE_UNARCHIVE_PERSON_SUCCESS,
                Messages.format(personToUnarchive));

        ModelManager expectedModel = new ModelManager(model.getMentorstack(), new UserPrefs());
        expectedModel.unarchivePerson(personToUnarchive, personToUnarchive.unarchived());

        assertCommandSuccess(unarchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_fail() {
        Person personToUnarchive = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_SET_FIRST_PERSON);

        String expectedMessage = String.format(UnarchiveCommand.MESSAGE_UNARCHIVE_PERSON_SUCCESS,
                Messages.format(personToUnarchive));

        ModelManager expectedModel = new ModelManager(model.getMentorstack(), new UserPrefs());
        expectedModel.unarchivePerson(personToUnarchive, personToUnarchive.unarchived());

        assertCommandFailure(unarchiveCommand, model,
                UnarchiveCommand.MESSAGE_DUPLICATE_UNARCHIVE + Messages.format(personToUnarchive));
    }

    @Test
    public void testEquals() {
        UnarchiveCommand unarchiveFirstCommand = new UnarchiveCommand(INDEX_SET_FIRST_PERSON);
        UnarchiveCommand unarchiveSecondCommand = new UnarchiveCommand(INDEX_SET_SECOND_PERSON);

        // same object -> returns true
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommand));

        // same values -> returns true
        UnarchiveCommand archiveFirstCommandCopy = new UnarchiveCommand(INDEX_SET_FIRST_PERSON);
        assertTrue(unarchiveFirstCommand.equals(archiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(unarchiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unarchiveFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unarchiveFirstCommand.equals(unarchiveSecondCommand));
    }

    @Test
    public void testToString() {
        Index targetIndex = Index.fromOneBased(1);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(Set.of(targetIndex));
        String expected = UnarchiveCommand.class.getCanonicalName() + "{targetIndex=" + Set.of(targetIndex) + "}";
        assertEquals(expected, unarchiveCommand.toString());
    }
}
