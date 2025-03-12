package seedu.mentorstack.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.mentorstack.commons.util.AppUtil.checkArgument;

public class Subjects {

    public final String subjectName;

    /**
     * Represents a Subject in the mentorstack.
     * Guarantees: immutable; name is valid as declared in {@link #isValidSubjectName(String)}
     */

    public static final String MESSAGE_CONSTRAINTS = "Subjects must be in the format: XXYYYY, XXYYYYZ, or XXXYYYY "
            + "(two or three uppercase letters, followed by four digits, optionally followed by one uppercase letter) "
            + "e.g. CS2103, CS2103T, LAF1101";
    public static final String VALIDATION_REGEX = "^[A-Z]{2,3}\\d{4}[A-Z]?$";

    /**
     * Constructs a {@code Subjects}.
     *
     * @param subjectName A valid subject name.
     */
    public Subjects(String subjectName) {
        requireNonNull(subjectName);
        checkArgument(isValidSubjectName(subjectName), MESSAGE_CONSTRAINTS);
        this.subjectName = subjectName;
    }

    /**
     * Returns true if a given string is a valid subject name.
     */
    public static boolean isValidSubjectName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Subjects)) {
            return false;
        }

        Subjects otherSubject = (Subjects) other;
        return subjectName.equals(otherSubject.subjectName);
    }

    @Override
    public int hashCode() {
            return subjectName.hashCode();
        }

    /**
    * Format state as text for viewing.
    */
    public String toString() {
            return '[' + subjectName + ']';
        }

}
