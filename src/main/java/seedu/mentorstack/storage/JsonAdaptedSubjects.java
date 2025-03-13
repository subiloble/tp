package seedu.mentorstack.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.mentorstack.commons.exceptions.IllegalValueException;
<<<<<<<< HEAD:src/main/java/seedu/mentorstack/storage/JsonAdaptedTag.java
========
import seedu.mentorstack.model.person.Subjects;
>>>>>>>> master:src/main/java/seedu/mentorstack/storage/JsonAdaptedSubjects.java
import seedu.mentorstack.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedSubjects {

    private final String subjectName;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedSubjects(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedSubjects(Subjects source) {
        subjectName = source.subjectName;
    }

    @JsonValue
    public String getTagName() {
        return subjectName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Subjects toModelType() throws IllegalValueException {
        if (!Tag.isValidTagName(subjectName)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Subjects(subjectName);
    }

}
