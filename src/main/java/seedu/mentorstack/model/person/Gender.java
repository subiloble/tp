package seedu.mentorstack.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.mentorstack.commons.util.AppUtil.checkArgument;

/**
 * Represents a person's gender.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */
public class Gender {
    public static final String MESSAGE_CONSTRAINTS = "Gender should be 'F' or 'M' only.";

    public final String value;

    /**
     * Constructs a {@code Gender}.
     *
     * @param gender A valid gender string.
     */
    public Gender(String gender) {
        requireNonNull(gender);
        String upperGender = gender.substring(0).trim().toUpperCase();
        checkArgument(isValidGender(gender), MESSAGE_CONSTRAINTS);
        this.value = upperGender;
    }

    /**
     * Returns true if a given string is a valid gender.
     */
    public static boolean isValidGender(String test) {
        return test.equalsIgnoreCase("F") || test.equalsIgnoreCase("M");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof Gender && value.equals(((Gender) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
