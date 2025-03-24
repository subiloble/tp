package seedu.mentorstack.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mentorstack.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.mentorstack.logic.parser.exceptions.ParseException;

class GenderTest {

    @Test
    public void constructor_validGender_success() throws ParseException {
        // Valid cases
        assertEquals(new Gender("M").toString(), "M");
        assertEquals(new Gender("F").toString(), "F");
        assertEquals(new Gender("m").toString(), "M");
        assertEquals(new Gender("f").toString(), "F");
    }

    @Test
    public void constructor_invalidGender_throwsParseException() {
        // Invalid cases
        assertThrows(ParseException.class, () -> new Gender("X"));
        assertThrows(ParseException.class, () -> new Gender("Male"));
        assertThrows(ParseException.class, () -> new Gender("Female"));
        assertThrows(ParseException.class, () -> new Gender("MF"));
        assertThrows(ParseException.class, () -> new Gender(""));
        assertThrows(ParseException.class, () -> new Gender(" "));
    }

    @Test
    public void isValidGender() {
        // Valid genders
        assertTrue(Gender.isValidGender("M"));
        assertTrue(Gender.isValidGender("F"));
        assertTrue(Gender.isValidGender("m"));
        assertTrue(Gender.isValidGender("f"));

        // Invalid genders
        assertFalse(Gender.isValidGender("X"));
        assertFalse(Gender.isValidGender("Male"));
        assertFalse(Gender.isValidGender("Female"));
        assertFalse(Gender.isValidGender("MF"));
        assertFalse(Gender.isValidGender(""));
        assertFalse(Gender.isValidGender(" "));
    }

    @Test
    public void equals() throws ParseException {
        Gender genderM1 = new Gender("M");
        Gender genderM2 = new Gender("m");
        Gender genderF = new Gender("F");

        // Same object -> returns true
        assertTrue(genderM1.equals(genderM1));

        // Same value -> returns true
        assertTrue(genderM1.equals(genderM2));

        // Different values -> returns false
        assertFalse(genderM1.equals(genderF));

        // Different object type -> returns false
        assertFalse(genderM1.equals("M"));
    }

    @Test
    public void toStringMethod() throws ParseException {
        assertEquals(new Gender("M").toString(), "M");
        assertEquals(new Gender("f").toString(), "F");
    }
}
