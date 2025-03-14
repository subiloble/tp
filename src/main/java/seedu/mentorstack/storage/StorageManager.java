package seedu.mentorstack.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.mentorstack.commons.core.LogsCenter;
import seedu.mentorstack.commons.exceptions.DataLoadingException;
import seedu.mentorstack.model.ReadOnlyMentorstack;
import seedu.mentorstack.model.ReadOnlyUserPrefs;
import seedu.mentorstack.model.UserPrefs;

/**
 * Manages storage of Mentorstack data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private MentorstackStorage mentorstackStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code MentorstackStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(MentorstackStorage mentorstackStorage, UserPrefsStorage userPrefsStorage) {
        this.mentorstackStorage = mentorstackStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ Mentorstack methods ==============================

    @Override
    public Path getMentorstackFilePath() {
        return mentorstackStorage.getMentorstackFilePath();
    }

    @Override
    public Optional<ReadOnlyMentorstack> readMentorstack() throws DataLoadingException {
        return readMentorstack(mentorstackStorage.getMentorstackFilePath());
    }

    @Override
    public Optional<ReadOnlyMentorstack> readMentorstack(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return mentorstackStorage.readMentorstack(filePath);
    }

    @Override
    public void saveMentorstack(ReadOnlyMentorstack mentorstack) throws IOException {
        saveMentorstack(mentorstack, mentorstackStorage.getMentorstackFilePath());
    }

    @Override
    public void saveMentorstack(ReadOnlyMentorstack mentorstack, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        mentorstackStorage.saveMentorstack(mentorstack, filePath);
    }

}
