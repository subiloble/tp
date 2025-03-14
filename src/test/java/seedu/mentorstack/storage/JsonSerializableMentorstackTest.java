package seedu.mentorstack.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.mentorstack.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.mentorstack.commons.exceptions.IllegalValueException;
import seedu.mentorstack.commons.util.JsonUtil;
import seedu.mentorstack.model.Mentorstack;
import seedu.mentorstack.testutil.TypicalPersons;

public class JsonSerializableMentorstackTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableMentorstackTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsMentorstack.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonMentorstack.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonMentorstack.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableMentorstack dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableMentorstack.class).get();
        Mentorstack mentorstackFromFile = dataFromFile.toModelType();
        Mentorstack typicalPersonsMentorstack = TypicalPersons.getTypicalMentorstack();
        assertEquals(mentorstackFromFile, typicalPersonsMentorstack);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableMentorstack dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableMentorstack.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableMentorstack dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableMentorstack.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableMentorstack.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
