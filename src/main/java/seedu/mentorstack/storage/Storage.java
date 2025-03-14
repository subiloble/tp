package seedu.mentorstack.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.mentorstack.commons.exceptions.DataLoadingException;
import seedu.mentorstack.model.ReadOnlyMentorstack;
import seedu.mentorstack.model.ReadOnlyUserPrefs;
import seedu.mentorstack.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends MentorstackStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getMentorstackFilePath();

    @Override
    Optional<ReadOnlyMentorstack> readMentorstack() throws DataLoadingException;

    @Override
    void saveMentorstack(ReadOnlyMentorstack mentorstack) throws IOException;

}
