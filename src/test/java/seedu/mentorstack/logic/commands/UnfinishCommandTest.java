package seedu.mentorstack.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mentorstack.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.mentorstack.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.mentorstack.testutil.TypicalPersons.getTypicalMentorstack;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.mentorstack.commons.core.index.Index;
import seedu.mentorstack.logic.commands.exceptions.CommandException;
import seedu.mentorstack.model.Model;
import seedu.mentorstack.model.ModelManager;
import seedu.mentorstack.model.UserPrefs;
import seedu.mentorstack.model.person.Person;
import seedu.mentorstack.model.person.Subject;

class UnfinishCommandTest {

    private Model model;

    @BeforeEach
    void setUp() {
        model = new ModelManager(getTypicalMentorstack(), new UserPrefs());
    }

    @Test
    void execute_validSubjects_success() throws Exception {
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Subject> subjectsToUnfinish = new HashSet<>(person.getFinishedSubjects());
        UnfinishCommand unfinishCommand = new UnfinishCommand(INDEX_FIRST_PERSON, subjectsToUnfinish);

        CommandResult result = unfinishCommand.execute(model);

        assertEquals(UnfinishCommand.MESSAGE_UNFINISH_SUBJECT_SUCCESS, result.getFeedbackToUser());
    }

    @Test
    void execute_subjectNotEnrolled_throwsCommandException() {
        Set<Subject> nonExistentSubjects = new HashSet<>();
        nonExistentSubjects.add(new Subject("NONEXISTENTSUBJECT"));
        UnfinishCommand unfinishCommand = new UnfinishCommand(INDEX_FIRST_PERSON, nonExistentSubjects);

        assertThrows(CommandException.class, () -> unfinishCommand.execute(model));
    }

    @Test
    void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Set<Subject> subjects = new HashSet<>();
        subjects.add(new Subject("CS2103"));
        UnfinishCommand unfinishCommand = new UnfinishCommand(outOfBoundsIndex, subjects);

        assertThrows(CommandException.class, () -> unfinishCommand.execute(model));
    }

    @Test
    public void equals() {
        Set<Subject> subjectsA = new HashSet<>();
        subjectsA.add(new Subject("CS2103"));
        UnfinishCommand commandA = new UnfinishCommand(INDEX_FIRST_PERSON, subjectsA);

        Set<Subject> subjectsB = new HashSet<>();
        subjectsB.add(new Subject("CS2101"));
        UnfinishCommand commandB = new UnfinishCommand(INDEX_SECOND_PERSON, subjectsB);

        // same values -> returns true
        UnfinishCommand commandACopy = new UnfinishCommand(INDEX_FIRST_PERSON, subjectsA);
        assertTrue(commandA.equals(commandACopy));

        // same object -> returns true
        assertTrue(commandA.equals(commandA));

        // null -> returns false
        assertFalse(commandA.equals(null));

        // different types -> returns false
        assertFalse(commandA.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(commandA.equals(commandB));

        // different subjects -> returns false
        assertFalse(commandA.equals(new UnfinishCommand(INDEX_FIRST_PERSON, subjectsB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        Set<Subject> subjects = new HashSet<>();
        subjects.add(new Subject("CS2103"));
        UnfinishCommand unfinishCommand = new UnfinishCommand(index, subjects);
        String expected = UnfinishCommand.class.getCanonicalName()
                + "{index=" + index + ", subjectsToUnfinish=" + subjects + "}";
        assertEquals(expected, unfinishCommand.toString());
    }
}
