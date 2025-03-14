package seedu.mentorstack.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.mentorstack.testutil.TypicalPersons.getTypicalMentorstack;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.mentorstack.commons.core.GuiSettings;
import seedu.mentorstack.model.Mentorstack;
import seedu.mentorstack.model.ReadOnlyMentorstack;
import seedu.mentorstack.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonMentorstackStorage mentorstackStorage = new JsonMentorstackStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(mentorstackStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void mentorstackReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonMentorstackStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonMentorstackStorageTest} class.
         */
        Mentorstack original = getTypicalMentorstack();
        storageManager.saveMentorstack(original);
        ReadOnlyMentorstack retrieved = storageManager.readMentorstack().get();
        assertEquals(original, new Mentorstack(retrieved));
    }

    @Test
    public void getMentorstackFilePath() {
        assertNotNull(storageManager.getMentorstackFilePath());
    }

}
