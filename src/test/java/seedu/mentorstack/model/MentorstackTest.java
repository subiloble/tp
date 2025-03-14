package seedu.mentorstack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mentorstack.logic.commands.CommandTestUtil.VALID_SUB_HUSBAND;
import static seedu.mentorstack.testutil.Assert.assertThrows;
import static seedu.mentorstack.testutil.TypicalPersons.ALICE;
import static seedu.mentorstack.testutil.TypicalPersons.getTypicalMentorstack;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.mentorstack.model.person.Person;
import seedu.mentorstack.model.person.exceptions.DuplicatePersonException;
import seedu.mentorstack.testutil.PersonBuilder;

public class MentorstackTest {

    private final Mentorstack mentorstack = new Mentorstack();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), mentorstack.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mentorstack.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyMentorstack_replacesData() {
        Mentorstack newData = getTypicalMentorstack();
        mentorstack.resetData(newData);
        assertEquals(newData, mentorstack);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withSubjects(VALID_SUB_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        MentorstackStub newData = new MentorstackStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> mentorstack.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mentorstack.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInMentorstack_returnsFalse() {
        assertFalse(mentorstack.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInMentorstack_returnsTrue() {
        mentorstack.addPerson(ALICE);
        assertTrue(mentorstack.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInMentorstack_returnsTrue() {
        mentorstack.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withSubjects(VALID_SUB_HUSBAND)
                .build();
        assertTrue(mentorstack.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> mentorstack.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = Mentorstack.class.getCanonicalName() + "{persons=" + mentorstack.getPersonList() + "}";
        assertEquals(expected, mentorstack.toString());
    }

    /**
     * A stub ReadOnlyMentorstack whose persons list can violate interface constraints.
     */
    private static class MentorstackStub implements ReadOnlyMentorstack {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        MentorstackStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}
