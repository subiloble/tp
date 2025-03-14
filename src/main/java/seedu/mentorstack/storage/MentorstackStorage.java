package seedu.mentorstack.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.mentorstack.commons.exceptions.DataLoadingException;
import seedu.mentorstack.model.ReadOnlyMentorstack;

/**
 * Represents a storage for {@link seedu.mentorstack.model.Mentorstack}.
 */
public interface MentorstackStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getMentorstackFilePath();

    /**
     * Returns Mentorstack data as a {@link ReadOnlyMentorstack}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyMentorstack> readMentorstack() throws DataLoadingException;

    /**
     * @see #getMentorstackFilePath()
     */
    Optional<ReadOnlyMentorstack> readMentorstack(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyMentorstack} to the storage.
     * @param mentorstack cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveMentorstack(ReadOnlyMentorstack mentorstack) throws IOException;

    /**
     * @see #saveMentorstack(ReadOnlyMentorstack)
     */
    void saveMentorstack(ReadOnlyMentorstack mentorstack, Path filePath) throws IOException;

}
