package seedu.mentorstack.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.mentorstack.model.Mentorstack;
import seedu.mentorstack.model.ReadOnlyMentorstack;
import seedu.mentorstack.model.person.Address;
import seedu.mentorstack.model.person.Email;
import seedu.mentorstack.model.person.Name;
import seedu.mentorstack.model.person.Person;
import seedu.mentorstack.model.person.Phone;
import seedu.mentorstack.model.person.Subjects;
import seedu.mentorstack.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Mentorstack} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                getSubjectSet("CS2102")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                getSubjectSet("CS2103", "CS2101")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                getSubjectSet("MA1100T", "CS2100", "CS2106")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                getSubjectSet("CS2105")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                getSubjectSet("GE2215")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                getSubjectSet("LAJ1101"))
        };
    }

    public static ReadOnlyMentorstack getSampleAddressBook() {
        Mentorstack sampleAb = new Mentorstack();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public static Set<Subjects> getSubjectSet(String... strings) {
        return Arrays.stream(strings)
                .map(Subjects::new)
                .collect(Collectors.toSet());
    }
}
