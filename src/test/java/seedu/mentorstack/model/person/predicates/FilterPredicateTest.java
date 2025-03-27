package seedu.mentorstack.model.person.predicates;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Set;
import java.util.function.Predicate;

import seedu.mentorstack.model.person.Person;
import seedu.mentorstack.model.person.Name;
import seedu.mentorstack.model.person.Gender;
import seedu.mentorstack.model.person.Phone;
import seedu.mentorstack.model.person.Email;
import seedu.mentorstack.model.person.Subject;
import seedu.mentorstack.model.person.ArchiveStatus;

class FilterPredicateTest {

    private Person createPerson(String name, String gender, String phone, String email,
                                Set<Subject> subjects, Set<Subject> finishedSubjects) {
        return new Person(new Name(name), new Gender(gender), new Phone(phone),
                new Email(email), subjects, finishedSubjects, new ArchiveStatus("false"), false);
    }

    @Test
    void createPredicate_nameFilter_returnsCorrectPredicate() {
        Person person = createPerson("Alice Tan", "F", "12345678",
                "alice@example.com", Set.of(new Subject("Math"),
                        new Subject("Physics")), Set.of());

        Predicate<Person> predicate = FilterPredicate.createPredicate("n", "Alice");
        assertNotNull(predicate);
        assertTrue(predicate.test(person));

        Person otherPerson = createPerson("Bob", "M", "87654321",
                "bob@example.com", Set.of(new Subject("CS")), Set.of());
        assertFalse(predicate.test(otherPerson));
    }

    @Test
    void createPredicate_phoneFilter_returnsCorrectPredicate() {
        Person person = createPerson("John Doe", "M", "98765432",
                "john@example.com", Set.of(new Subject("Science")), Set.of());

        Predicate<Person> predicate = FilterPredicate.createPredicate("p", "98765432");
        assertNotNull(predicate);
        assertTrue(predicate.test(person));
    }

    @Test
    void createPredicate_emailFilter_returnsCorrectPredicate() {
        Person person = createPerson("Emily", "F", "55556666",
                "emily@example.com", Set.of(new Subject("Biology")), Set.of());

        Predicate<Person> predicate = FilterPredicate.createPredicate("e",
                "emily@example.com");
        assertNotNull(predicate);
        assertTrue(predicate.test(person));
    }

    @Test
    void createPredicate_subjectFilter_returnsCorrectPredicate() {
        Person person = createPerson("Anna", "F", "99998888",
                "anna@example.com", Set.of(new Subject("Mathematics"),
                        new Subject("Physics")), Set.of());

        Predicate<Person> predicate = FilterPredicate.createPredicate("s", "math");
        assertNotNull(predicate);
        assertTrue(predicate.test(person));
    }

    @Test
    void createPredicate_genderFilter_returnsCorrectPredicate() {
        Person person = createPerson("Ethan", "M", "22223333",
                "ethan@example.com", Set.of(new Subject("English")), Set.of());

        Predicate<Person> predicate = FilterPredicate.createPredicate("g", "M");
        assertNotNull(predicate);
        assertTrue(predicate.test(person));
    }

    @Test
    void createPredicate_invalidFilterType_returnsNull() {
        Predicate<Person> predicate = FilterPredicate.createPredicate("invalid",
                "test");
        assertNull(predicate);
    }
}
