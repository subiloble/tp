package seedu.mentorstack.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mentorstack.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.mentorstack.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mentorstack.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.mentorstack.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.mentorstack.testutil.TypicalPersons.getTypicalMentorstack;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.mentorstack.commons.core.index.Index;
import seedu.mentorstack.logic.Messages;
import seedu.mentorstack.model.Model;
import seedu.mentorstack.model.ModelManager;
import seedu.mentorstack.model.UserPrefs;
import seedu.mentorstack.model.person.Person;
import seedu.mentorstack.testutil.PersonBuilder;

public class UnmarkCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMentorstack(), new UserPrefs());
        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person markedPerson = new PersonBuilder(personToMark).build().marked();
        model.markPerson(personToMark, markedPerson);
    }

    @Test
    public void execute_validIndex_success() {
        Set<Index> indices = new HashSet<>();
        indices.add(INDEX_FIRST_PERSON);
        UnmarkCommand unmarkCommand = new UnmarkCommand(indices);

        Person personToUnmark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person unmarkedPerson = new PersonBuilder(personToUnmark).build().unmarked();

        String expectedMessage = String.format(UnmarkCommand.MESSAGE_UNMARK_PERSON_SUCCESS,
                Messages.format(personToUnmark));

        Model expectedModel = new ModelManager(model.getMentorstack(), new UserPrefs());
        expectedModel.unmarkPerson(personToUnmark, unmarkedPerson);

        assertCommandSuccess(unmarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Set<Index> indices = new HashSet<>();
        indices.add(Index.fromOneBased(model.getFilteredPersonList().size() + 1));
        UnmarkCommand unmarkCommand = new UnmarkCommand(indices);

        assertCommandFailure(unmarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_unmarkAlreadyUnmarkedPerson_success() {
        Set<Index> indices = new HashSet<>();
        indices.add(INDEX_FIRST_PERSON);
        UnmarkCommand unmarkCommand = new UnmarkCommand(indices);

        Person personToUnmark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.unmarkPerson(personToUnmark, new PersonBuilder(personToUnmark).build().unmarked());

        Person alreadyUnmarkedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String expectedMessage = String.format(UnmarkCommand.MESSAGE_UNMARK_PERSON_SUCCESS,
                Messages.format(alreadyUnmarkedPerson));

        Model expectedModel = new ModelManager(model.getMentorstack(), new UserPrefs());
        expectedModel.unmarkPerson(alreadyUnmarkedPerson, alreadyUnmarkedPerson);

        assertCommandSuccess(unmarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Set<Index> firstIndices = new HashSet<>();
        firstIndices.add(INDEX_FIRST_PERSON);
        Set<Index> secondIndices = new HashSet<>();
        secondIndices.add(INDEX_SECOND_PERSON);

        UnmarkCommand unmarkFirstCommand = new UnmarkCommand(firstIndices);
        UnmarkCommand unmarkSecondCommand = new UnmarkCommand(secondIndices);

        // same values -> returns true
        Set<Index> indices = new HashSet<>();
        indices.add(INDEX_FIRST_PERSON);
        UnmarkCommand commandWithSameValues = new UnmarkCommand(indices);
        assertTrue(unmarkFirstCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommand));

        // null -> returns false
        assertFalse(unmarkFirstCommand.equals(null));

        // different types -> returns false
        assertFalse(unmarkFirstCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(unmarkFirstCommand.equals(unmarkSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnmarkCommand unmarkCommand = new UnmarkCommand(Set.of(targetIndex));
        String expected = UnmarkCommand.class.getCanonicalName() + "{targetIndex=" + Set.of(targetIndex) + "}";
        assertEquals(expected, unmarkCommand.toString());
    }
}
