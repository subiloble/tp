package seedu.mentorstack.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.mentorstack.commons.util.AppUtil.checkArgument;

/**
 * Represents a Subject in the mentorstack.
 * Guarantees: immutable; name is valid as declared in {@link #isValidSubjectName(String)}
 */
public class Subjects {
    public static final String MESSAGE_CONSTRAINTS = "Subject names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String subjectName;

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
