package seedu.mentorstack.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.mentorstack.commons.core.index.Index;
import seedu.mentorstack.logic.Messages;
import seedu.mentorstack.model.Model;
import seedu.mentorstack.model.ModelManager;
import seedu.mentorstack.model.UserPrefs;
import seedu.mentorstack.model.person.Person;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.mentorstack.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mentorstack.testutil.TypicalIndexSets.INDEX_SET_FIRST_PERSON;
import static seedu.mentorstack.testutil.TypicalIndexSets.INDEX_SET_SECOND_PERSON;
import static seedu.mentorstack.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.mentorstack.testutil.TypicalPersons.getTypicalMentorstack;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ArchiveCommand.
 */
class ArchiveCommandTest {

    private Model model = new ModelManager(getTypicalMentorstack(), new UserPrefs());

    @Test
    public void execute_success() {
        Person personToArchive = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_SET_FIRST_PERSON);

        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_PERSON_SUCCESS,
                Messages.format(personToArchive));

        ModelManager expectedModel = new ModelManager(model.getMentorstack(), new UserPrefs());
        expectedModel.archivePerson(personToArchive, personToArchive.archived());

        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void testEquals() {
        ArchiveCommand archiveFirstCommand = new ArchiveCommand(INDEX_SET_FIRST_PERSON);
        ArchiveCommand archiveSecondCommand = new ArchiveCommand(INDEX_SET_SECOND_PERSON);

        // same object -> returns true
        assertTrue(archiveFirstCommand.equals(archiveFirstCommand));

        // same values -> returns true
        ArchiveCommand archiveFirstCommandCopy = new ArchiveCommand(INDEX_SET_FIRST_PERSON);
        assertTrue(archiveFirstCommand.equals(archiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(archiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(archiveFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(archiveFirstCommand.equals(archiveSecondCommand));
    }

    @Test
    public void testToString() {
        Index targetIndex = Index.fromOneBased(1);
        ArchiveCommand archiveCommand = new ArchiveCommand(Set.of(targetIndex));
        String expected = ArchiveCommand.class.getCanonicalName() + "{targetIndex=" + Set.of(targetIndex) + "}";
        assertEquals(expected, archiveCommand.toString());
    }
}