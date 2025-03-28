package seedu.mentorstack.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.mentorstack.commons.core.LogsCenter;
import seedu.mentorstack.commons.exceptions.DataLoadingException;
import seedu.mentorstack.commons.exceptions.IllegalValueException;
import seedu.mentorstack.commons.util.FileUtil;
import seedu.mentorstack.commons.util.JsonUtil;
import seedu.mentorstack.model.ReadOnlyMentorstack;

/**
 * A class to access Mentorstack data stored as a json file on the hard disk.
 */
public class JsonMentorstackStorage implements MentorstackStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonMentorstackStorage.class);

    private Path filePath;

    public JsonMentorstackStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getMentorstackFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyMentorstack> readMentorstack() throws DataLoadingException {
        return readMentorstack(filePath);
    }

    /**
     * Similar to {@link #readMentorstack()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyMentorstack> readMentorstack(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableMentorstack> jsonMentorstack = JsonUtil.readJsonFile(
                filePath, JsonSerializableMentorstack.class);
        if (!jsonMentorstack.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonMentorstack.get().toModelType());

        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveMentorstack(ReadOnlyMentorstack mentorstack) throws IOException {
        saveMentorstack(mentorstack, filePath);
    }

    /**
     * Similar to {@link #saveMentorstack(ReadOnlyMentorstack)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveMentorstack(ReadOnlyMentorstack mentorstack, Path filePath) throws IOException {
        requireNonNull(mentorstack);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableMentorstack(mentorstack), filePath);
    }

}
