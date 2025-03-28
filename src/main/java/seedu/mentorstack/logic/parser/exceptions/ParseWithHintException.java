package seedu.mentorstack.logic.parser.exceptions;

/**
 * Represents a parse error encountered by a parser, contains a hint
 */

public class ParseWithHintException extends ParseException{

    private String hint;
    
    public ParseWithHintException(String message, String hint) {
        super(message);
        this.hint = hint;
    }

    public ParseWithHintException(String message, Throwable cause, String hint) {
        super(message, cause);
        this.hint = hint;
    }

    public String getHint() {
        return this.hint;
    }
}
