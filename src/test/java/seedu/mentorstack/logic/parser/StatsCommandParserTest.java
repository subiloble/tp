package seedu.mentorstack.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.mentorstack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mentorstack.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.mentorstack.logic.commands.CommandTestUtil.SUBJECT_DESC_CS2100;
import static seedu.mentorstack.logic.parser.CliSyntax.PREFIX_SUBJECT;

import org.junit.jupiter.api.Test;

import seedu.mentorstack.logic.commands.StatsCommand;
import seedu.mentorstack.logic.parser.exceptions.ParseException;
import seedu.mentorstack.model.person.Subject;
import seedu.mentorstack.testutil.Assert;

class StatsCommandParserTest {

    private StatsCommandParser parser = new StatsCommandParser();

    @Test
    public void parse_emptyArgs_returnsStatsCommandWithoutSubject() throws ParseException {
        // Test for empty input
        StatsCommand statsCommand = parser.parse("");
        assertNotNull(statsCommand);
    }

    @Test
    public void parse_validSubject_returnsStatsCommandWithSubject() throws ParseException {
        // Test for valid subject input with the prefix
        Subject subject = new Subject("CS2100");
        StatsCommand statsCommand = parser.parse(SUBJECT_DESC_CS2100);
        assertEquals(new StatsCommand(subject), statsCommand); // Should match the subject
    }

    @Test
    public void parse_invalidSubject_throwsParseException() {
        // Test for invalid subject input
        assertThrows(ParseException.class, () -> {
            parser.parse(" " + PREFIX_SUBJECT + "INVALID_SUBJECT ");
        });
    }

    @Test
    public void parse_mixedCaseSubject_returnsStatsCommandWithSubject() throws ParseException {
        // Test for valid mixed-case subject input with the prefix
        Subject subject = new Subject("Cs1010s"); // Assuming Subject is case-insensitive
        StatsCommand statsCommand = parser.parse(" " + PREFIX_SUBJECT + "Cs1010s ");
        assertEquals(new StatsCommand(subject), statsCommand); // Should match the subject
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        Assert.assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                StatsCommand.MESSAGE_USAGE), ()
                -> parser.parse(INVALID_SUBJECT_DESC));
    }
}
