package seedu.mentorstack.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.mentorstack.commons.exceptions.DataLoadingException;
<<<<<<< HEAD
import seedu.mentorstack.model.ReadOnlyAddressBook;

/**
 * Represents a storage for {@link seedu.mentorstack.model.AddressBook}.
=======
import seedu.mentorstack.model.ReadOnlyMentorstack;

/**
 * Represents a storage for {@link seedu.mentorstack.model.Mentorstack}.
>>>>>>> master
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyMentorstack}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyMentorstack> readAddressBook() throws DataLoadingException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyMentorstack> readAddressBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyMentorstack} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyMentorstack addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyMentorstack)
     */
    void saveAddressBook(ReadOnlyMentorstack addressBook, Path filePath) throws IOException;

}
