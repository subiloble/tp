package seedu.mentorstack.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.mentorstack.commons.exceptions.IllegalValueException;
import seedu.mentorstack.model.Mentorstack;
import seedu.mentorstack.model.ReadOnlyMentorstack;
import seedu.mentorstack.model.person.Person;

/**
 * An Immutable Mentorstack that is serializable to JSON format.
 */
@JsonRootName(value = "mentorstack")
class JsonSerializableMentorstack {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableMentorstack} with the given persons.
     */
    @JsonCreator
    public JsonSerializableMentorstack(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyMentorstack} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableMentorstack}.
     */
    public JsonSerializableMentorstack(ReadOnlyMentorstack source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code Mentorstack} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Mentorstack toModelType() throws IllegalValueException {
        Mentorstack mentorstack = new Mentorstack();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (mentorstack.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            mentorstack.addPerson(person);
        }
        return mentorstack;
    }

}
