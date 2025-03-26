package seedu.mentorstack.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.mentorstack.model.Model.PREDICATE_SHOW_ALL_ARCHIVED_PERSONS;

import seedu.mentorstack.model.Model;

/**
 * Lists all persons in Mentorstack to the user.
 */
public class ShowArchiveCommand extends Command {

    public static final String COMMAND_WORD = "showarchive";

    public static final String MESSAGE_SUCCESS = "Showed all archived persons";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_ARCHIVED_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
