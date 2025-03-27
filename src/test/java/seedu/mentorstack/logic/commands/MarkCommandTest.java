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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import seedu.mentorstack.commons.core.index.Index;
import seedu.mentorstack.logic.Messages;
import seedu.mentorstack.model.Model;
import seedu.mentorstack.model.ModelManager;
import seedu.mentorstack.model.UserPrefs;
import seedu.mentorstack.model.person.Person;
import seedu.mentorstack.testutil.PersonBuilder;

public class MarkCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMentorstack(), new UserPrefs());
    }

    @Test
    public void execute_validIndex_success() {
        Set<Index> indices = new HashSet<>();
        indices.add(INDEX_FIRST_PERSON);
        MarkCommand markCommand = new MarkCommand(indices);

        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person markedPerson = new PersonBuilder(personToMark).build().marked();

        String expectedMessage = String.format(MarkCommand.MESSAGE_MARK_PERSON_SUCCESS, Messages.format(personToMark));

        Model expectedModel = new ModelManager(model.getMentorstack(), new UserPrefs());
        expectedModel.markPerson(personToMark, markedPerson);

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Set<Index> indices = new HashSet<>();
        indices.add(Index.fromOneBased(model.getFilteredPersonList().size() + 1));
        MarkCommand markCommand = new MarkCommand(indices);

        assertCommandFailure(markCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_markAlreadyMarkedPerson_success() {
        Set<Index> indices = new HashSet<>();
        indices.add(INDEX_FIRST_PERSON);
        MarkCommand markCommand = new MarkCommand(indices);

        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.markPerson(personToMark, new PersonBuilder(personToMark).build().marked());

        Person alreadyMarkedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String expectedMessage = String.format(MarkCommand.MESSAGE_MARK_PERSON_SUCCESS, Messages.format(alreadyMarkedPerson));

        Model expectedModel = new ModelManager(model.getMentorstack(), new UserPrefs());
        expectedModel.markPerson(alreadyMarkedPerson, alreadyMarkedPerson);

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Set<Index> firstIndices = new HashSet<>();
        firstIndices.add(INDEX_FIRST_PERSON);
        Set<Index> secondIndices = new HashSet<>();
        secondIndices.add(INDEX_SECOND_PERSON);

        MarkCommand markFirstCommand = new MarkCommand(firstIndices);
        MarkCommand markSecondCommand = new MarkCommand(secondIndices);

        // same values -> returns true
        Set<Index> indices = new HashSet<>();
        indices.add(INDEX_FIRST_PERSON);
        MarkCommand commandWithSameValues = new MarkCommand(indices);
        assertTrue(markFirstCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(markFirstCommand.equals(markFirstCommand));

        // null -> returns false
        assertFalse(markFirstCommand.equals(null));

        // different types -> returns false
        assertFalse(markFirstCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(markFirstCommand.equals(markSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        MarkCommand markCommand = new MarkCommand(Set.of(targetIndex));
        String expected = MarkCommand.class.getCanonicalName() + "{targetIndex=" + Set.of(targetIndex) + "}";
        assertEquals(expected, markCommand.toString());
    }
}
