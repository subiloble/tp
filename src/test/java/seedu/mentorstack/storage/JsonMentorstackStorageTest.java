package seedu.mentorstack.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.mentorstack.testutil.Assert.assertThrows;
import static seedu.mentorstack.testutil.TypicalPersons.ALICE;
import static seedu.mentorstack.testutil.TypicalPersons.HOON;
import static seedu.mentorstack.testutil.TypicalPersons.IDA;
import static seedu.mentorstack.testutil.TypicalPersons.getTypicalMentorstack;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.mentorstack.commons.exceptions.DataLoadingException;
import seedu.mentorstack.model.Mentorstack;
import seedu.mentorstack.model.ReadOnlyMentorstack;

public class JsonMentorstackStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonMentorstackStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readMentorstack_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readMentorstack(null));
    }

    private java.util.Optional<ReadOnlyMentorstack> readMentorstack(String filePath) throws Exception {
        return new JsonMentorstackStorage(Paths.get(filePath)).readMentorstack(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readMentorstack("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readMentorstack("notJsonFormatMentorstack.json"));
    }

    @Test
    public void readMentorstack_invalidPersonMentorstack_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readMentorstack("invalidPersonMentorstack.json"));
    }

    @Test
    public void readMentorstack_invalidAndValidPersonMentorstack_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readMentorstack("invalidAndValidPersonMentorstack.json"));
    }

    @Test
    public void readAndSaveMentorstack_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempMentorstack.json");
        Mentorstack original = getTypicalMentorstack();
        JsonMentorstackStorage jsonMentorstackStorage = new JsonMentorstackStorage(filePath);

        // Save in new file and read back
        jsonMentorstackStorage.saveMentorstack(original, filePath);
        ReadOnlyMentorstack readBack = jsonMentorstackStorage.readMentorstack(filePath).get();
        assertEquals(original, new Mentorstack(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonMentorstackStorage.saveMentorstack(original, filePath);
        readBack = jsonMentorstackStorage.readMentorstack(filePath).get();
        assertEquals(original, new Mentorstack(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonMentorstackStorage.saveMentorstack(original); // file path not specified
        readBack = jsonMentorstackStorage.readMentorstack().get(); // file path not specified
        assertEquals(original, new Mentorstack(readBack));

    }

    @Test
    public void saveMentorstack_nullMentorstack_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveMentorstack(null, "SomeFile.json"));
    }

    /**
     * Saves {@code mentorstack} at the specified {@code filePath}.
     */
    private void saveMentorstack(ReadOnlyMentorstack mentorstack, String filePath) {
        try {
            new JsonMentorstackStorage(Paths.get(filePath))
                    .saveMentorstack(mentorstack, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveMentorstack_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveMentorstack(new Mentorstack(), null));
    }
}
