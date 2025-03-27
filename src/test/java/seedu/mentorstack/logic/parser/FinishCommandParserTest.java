package seedu.mentorstack.logic.parser;

import static seedu.mentorstack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mentorstack.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.mentorstack.logic.commands.CommandTestUtil.SUBJECT_DESC_CS2100;
import static seedu.mentorstack.logic.commands.CommandTestUtil.SUBJECT_DESC_CS2102;
import static seedu.mentorstack.logic.commands.CommandTestUtil.VALID_SUB_CS2100;
import static seedu.mentorstack.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.mentorstack.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.mentorstack.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.mentorstack.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Set;
import org.junit.jupiter.api.Test;

import seedu.mentorstack.commons.core.index.Index;
import seedu.mentorstack.logic.commands.FinishCommand;
import seedu.mentorstack.model.person.Subject;

public class FinishCommandParserTest {

    private static final String SUBJECT_EMPTY = " " + PREFIX_SUBJECT;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FinishCommand.MESSAGE_USAGE);

    private FinishCommandParser parser = new FinishCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_SUB_CS2100, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", FinishCommand.MESSAGE_NO_SUBJECTS);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + SUBJECT_DESC_CS2100, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + SUBJECT_DESC_CS2100, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_SUBJECT_DESC, Subject.MESSAGE_CONSTRAINTS); // invalid subject

        // while parsing {@code PREFIX_SUBJECT} alone will reset the subjects of the {@code Person} being finished,
        // parsing it together with a valid subject results in error
        assertParseFailure(parser, "1" + SUBJECT_DESC_CS2102 + SUBJECT_DESC_CS2100 + SUBJECT_EMPTY,
                Subject.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + SUBJECT_DESC_CS2102 + SUBJECT_EMPTY + SUBJECT_DESC_CS2100,
                Subject.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + SUBJECT_EMPTY + SUBJECT_DESC_CS2102 + SUBJECT_DESC_CS2100,
                Subject.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validInput_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String input = "1" + SUBJECT_DESC_CS2100;
        FinishCommand expectedCommand = new FinishCommand(targetIndex, Set.of(new Subject("CS2100")));

        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_multipleValidSubjects_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String input = "1" + SUBJECT_DESC_CS2100 + SUBJECT_DESC_CS2102;
        Set<Subject> subjects = Set.of(new Subject("CS2100"), new Subject("CS2102"));
        FinishCommand expectedCommand = new FinishCommand(targetIndex, subjects);

        assertParseSuccess(parser, input, expectedCommand);
    }
}
