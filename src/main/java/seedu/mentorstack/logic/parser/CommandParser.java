package seedu.mentorstack.logic.parser;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import seedu.mentorstack.logic.commands.Command;
import seedu.mentorstack.logic.parser.exceptions.ParseException;
import seedu.mentorstack.logic.parser.exceptions.ParseWithHintException;

/**
 * Abstract class responsible for parsing commands. This class defines a method for parsing arguments
 * and checking for missing arguments based on an ideal set of arguments.
 * Subclasses must implement the {@link #parse(String)} method to provide the logic for parsing the
 * command arguments.
 */
public abstract class CommandParser {

    /**
     * Parses the given arguments into a {@link Command} object.
     *
     * @param args The arguments to parse.
     * @return A {@link Command} object representing the parsed command.
     * @throws ParseException If there is an error during parsing.
     * @throws ParseWithHintException If there is an error during parsing with a hint provided.
     */
    public abstract Command parse(String args) throws ParseException, ParseWithHintException;

    /**
     * Checks for missing arguments by comparing the provided arguments with an ideal set of arguments.
     * The method generates a string containing the missing arguments from the ideal set,
     * appending their associated values.
     *
     * @param argMultimap A map of argument prefixes to argument values.
     * @param ideal A map of ideal argument prefixes to their corresponding values.
     * @return A string containing the missing arguments in the format "prefix value".
     */
    public String getMissingArgs(ArgumentMultimap argMultimap, Map<String, String> ideal) {
        Set<String> hset = new HashSet<String>();
        for (Map.Entry<Prefix, List<String>> entry : argMultimap.getArgMap().entrySet()) {
            hset.add(entry.getKey().toString());
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : ideal.entrySet()) {
            if (!hset.contains(entry.getKey())) {
                sb.append(" " + entry.getKey() + entry.getValue());
            }
        }
        return sb.toString().trim();
    }
}
