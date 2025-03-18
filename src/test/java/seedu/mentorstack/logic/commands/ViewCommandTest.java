package seedu.mentorstack.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mentorstack.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mentorstack.testutil.TypicalPersons.getTypicalMentorstack;

import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.mentorstack.model.Model;
import seedu.mentorstack.model.ModelManager;
import seedu.mentorstack.model.UserPrefs;
import seedu.mentorstack.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for {@code ViewCommand}.
 */
public class ViewCommandTest {
    private Model model = new ModelManager(getTypicalMentorstack(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalMentorstack(), new UserPrefs());

    @Test
    public void equals() {
        Predicate<Person> firstPredicate = person -> person.getName().fullName.equalsIgnoreCase("Alice");
        Predicate<Person> secondPredicate = person -> person.getName().fullName.equalsIgnoreCase("Bob");

        ViewCommand viewFirstCommand = new ViewCommand(firstPredicate);
        ViewCommand viewSecondCommand = new ViewCommand(secondPredicate);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewCommand viewFirstCommandCopy = new ViewCommand(firstPredicate);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different predicates -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

    @Test
    public void execute_noMatchingResults_noPersonFound() {
        Predicate<Person> predicate = person -> person.getName().fullName.equalsIgnoreCase("NonExistent");
        ViewCommand command = new ViewCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, ViewCommand.MESSAGE_NO_MATCH, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleMatchingResults_multiplePersonsFound() {
        Predicate<Person> predicate = person -> person.getName().fullName.contains("Alice") ||
                person.getName().fullName.contains("Benson");
        ViewCommand command = new ViewCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        String expectedMessage = String.format(ViewCommand.MESSAGE_SUCCESS, "[Alice Pauline, Benson Meier]");
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void toStringMethod() {
        Predicate<Person> predicate = person -> person.getName().fullName.contains("Alice");
        ViewCommand command = new ViewCommand(predicate);
        String expected = ViewCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, command.toString());
    }
}
