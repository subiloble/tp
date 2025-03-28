package seedu.mentorstack.logic.parser.exceptions;

/**
 * Represents a parse error encountered by a parser, contains a hint
 */

public class ParseWithHintException extends ParseException {

    private String hint;

    /**
     * Constructs a new exception with a message and a hint.
     *
     * @param message The error message.
     * @param hint The hint to resolve the error.
     */
    public ParseWithHintException(String message, String hint) {
        super(message);
        this.hint = hint;
    }

    /**
     * Constructs a new exception with a message, cause, and a hint.
     *
     * @param message The error message.
     * @param cause The cause of the exception.
     * @param hint The hint to resolve the error.
     */
    public ParseWithHintException(String message, Throwable cause, String hint) {
        super(message, cause);
        this.hint = hint;
    }

    /**
     * Returns the hint associated with this exception.
     *
     * @return The hint.
     */
    public String getHint() {
        return this.hint;
    }
}
