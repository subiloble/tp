package seedu.mentorstack.testutil;

import seedu.mentorstack.model.Mentorstack;
import seedu.mentorstack.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private Mentorstack addressBook;

    public AddressBookBuilder() {
        addressBook = new Mentorstack();
    }

    public AddressBookBuilder(Mentorstack addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        addressBook.addPerson(person);
        return this;
    }

    public Mentorstack build() {
        return addressBook;
    }
}
