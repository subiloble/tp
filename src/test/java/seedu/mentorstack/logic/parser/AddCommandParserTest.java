package seedu.mentorstack.logic.parser;

import static seedu.mentorstack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mentorstack.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.mentorstack.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.mentorstack.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.mentorstack.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.mentorstack.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.mentorstack.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.mentorstack.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.mentorstack.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.mentorstack.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.mentorstack.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.mentorstack.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.mentorstack.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.mentorstack.logic.commands.CommandTestUtil.SUBJECT_DESC_FRIEND;
import static seedu.mentorstack.logic.commands.CommandTestUtil.SUBJECT_DESC_HUSBAND;
import static seedu.mentorstack.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.mentorstack.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.mentorstack.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.mentorstack.logic.commands.CommandTestUtil.VALID_SUB_CS2100;
import static seedu.mentorstack.logic.commands.CommandTestUtil.VALID_SUB_CS2102;
import static seedu.mentorstack.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.mentorstack.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.mentorstack.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.mentorstack.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.mentorstack.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.mentorstack.testutil.TypicalPersons.AMY;
import static seedu.mentorstack.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.mentorstack.logic.Messages;
import seedu.mentorstack.logic.commands.AddCommand;
import seedu.mentorstack.model.person.Email;
import seedu.mentorstack.model.person.Name;
import seedu.mentorstack.model.person.Person;
import seedu.mentorstack.model.person.Phone;
import seedu.mentorstack.model.person.Subject;
import seedu.mentorstack.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withSubjects(VALID_SUB_CS2100).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + SUBJECT_DESC_FRIEND, new AddCommand(expectedPerson));


        // multiple subjects - all accepted
        Person expectedPersonMultipleSubjects = new PersonBuilder(BOB).withSubjects(VALID_SUB_CS2100, VALID_SUB_CS2102)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + SUBJECT_DESC_HUSBAND + SUBJECT_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleSubjects));
    }

    @Test
    public void parse_repeatedNonSubjectValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + SUBJECT_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero subjects
        Person expectedPerson = new PersonBuilder(AMY).build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + SUBJECT_DESC_FRIEND,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + SUBJECT_DESC_HUSBAND + SUBJECT_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + SUBJECT_DESC_HUSBAND + SUBJECT_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + SUBJECT_DESC_HUSBAND + SUBJECT_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid subject
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_SUBJECT_DESC + VALID_SUB_CS2100, Subject.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + SUBJECT_DESC_FRIEND,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + SUBJECT_DESC_HUSBAND + SUBJECT_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
