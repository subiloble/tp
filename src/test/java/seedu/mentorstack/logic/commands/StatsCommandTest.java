package seedu.mentorstack.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.mentorstack.testutil.TypicalPersons.ALICE;
import static seedu.mentorstack.testutil.TypicalPersons.CARL;
import static seedu.mentorstack.testutil.TypicalPersons.getTypicalMentorstack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.mentorstack.logic.commands.exceptions.CommandException;
import seedu.mentorstack.model.Mentorstack;
import seedu.mentorstack.model.Model;
import seedu.mentorstack.model.ModelManager;
import seedu.mentorstack.model.person.Subject;

public class StatsCommandTest {
    private Model model;
    private StatsCommand statsCommand;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();

        // Add Alice and Carl to the model
        model.addPerson(ALICE);
        model.addPerson(CARL);
    }

    @Test
    public void execute_stats_successful() throws CommandException {
        model.setMentorstack(new Mentorstack());
        CommandResult result = statsCommand.execute(model);

        // Verify successful undo
        assertEquals(StatsCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(getTypicalMentorstack(), model.getMentorstack());
    }



    @Test
    public void execute_noSubject_showsCorrectStats() {
        // Create the command with no subject filter
        statsCommand = new StatsCommand();

        // Execute the command
        CommandResult result = statsCommand.execute(model);

        // Validate the result
        String expectedMessage = String.format(StatsCommand.MESSAGE_SUCCESS, 2, 1, 1); // 2 persons, 1 male, 1 female
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_withSubject_showsCorrectStats() {
        // Create the command with a subject filter (CS1010S)
        StatsCommand statsCommand = new StatsCommand(new Subject("CS1010S"));

        // Execute the command
        CommandResult result = statsCommand.execute(model);

        // Validate the result
        String expectedMessage = String.format("Statistics for CS1010S:\n" + StatsCommand.MESSAGE_SUCCESS, 1, 0, 1);
        // 1 person in CS1010S, 0 male, 1 female (Alice)
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }
}
