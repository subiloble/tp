package seedu.mentorstack.testutil;

import static seedu.mentorstack.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.mentorstack.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.mentorstack.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.HashSet;
import java.util.Set;

import seedu.mentorstack.commons.core.index.Index;

/**
 * A utility class containing sets of {@code Index} objects to be used in tests.
 */
public class TypicalIndexSets {
    public static final Set<Index> INDEX_SET_FIRST_PERSON = Set.of(INDEX_FIRST_PERSON);
    public static final Set<Index> INDEX_SET_SECOND_PERSON = Set.of(INDEX_SECOND_PERSON);
    public static final Set<Index> INDEX_SET_THIRD_PERSON = Set.of(INDEX_THIRD_PERSON);
    public static final Set<Index> INDEX_SET_ALL = new HashSet<Index>(Set.of(
            INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON
    ));
}
