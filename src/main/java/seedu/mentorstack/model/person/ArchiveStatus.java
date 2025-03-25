package seedu.mentorstack.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's archive status in Mentorstack.
 * Guarantees: immutable;
 */
public class ArchiveStatus {


    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should only contain numbers, and it should be at least 3 digits long";
    public static final String VALIDATION_REGEX = "\\d{3,}";
    public final Boolean isArchived;

    /**
     * Constructs a {@code ArchiveStatus}.
     *
     * @param isArchived A boolean value.
     */
    public ArchiveStatus(Boolean isArchived) {
        requireNonNull(isArchived);
        this.isArchived = isArchived;
    }

    @Override
    public String toString() {
        return isArchived.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Phone)) {
            return false;
        }

        ArchiveStatus otherArchiveStatus = (ArchiveStatus) other;
        return isArchived.equals(otherArchiveStatus.isArchived);
    }

    @Override
    public int hashCode() {
        return isArchived.hashCode();
    }

    public String getStatus() {
        return this.isArchived.toString();
    }
    
}