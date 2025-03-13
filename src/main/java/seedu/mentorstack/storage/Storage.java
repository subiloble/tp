package seedu.mentorstack.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.mentorstack.commons.exceptions.DataLoadingException;
<<<<<<< HEAD
import seedu.mentorstack.model.ReadOnlyAddressBook;
=======
import seedu.mentorstack.model.ReadOnlyMentorstack;
>>>>>>> master
import seedu.mentorstack.model.ReadOnlyUserPrefs;
import seedu.mentorstack.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyMentorstack> readAddressBook() throws DataLoadingException;

    @Override
    void saveAddressBook(ReadOnlyMentorstack addressBook) throws IOException;

}
