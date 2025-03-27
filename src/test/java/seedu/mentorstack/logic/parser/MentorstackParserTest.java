package seedu.mentorstack.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mentorstack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mentorstack.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.mentorstack.testutil.Assert.assertThrows;
import static seedu.mentorstack.testutil.TypicalIndexSets.INDEX_SET_FIRST_PERSON;
import static seedu.mentorstack.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.mentorstack.logic.commands.AddCommand;
import seedu.mentorstack.logic.commands.ArchiveCommand;
import seedu.mentorstack.logic.commands.ClearCommand;
import seedu.mentorstack.logic.commands.DeleteCommand;
import seedu.mentorstack.logic.commands.EditCommand;
import seedu.mentorstack.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.mentorstack.logic.commands.ExitCommand;
import seedu.mentorstack.logic.commands.FindCommand;
import seedu.mentorstack.logic.commands.FinishCommand;
import seedu.mentorstack.logic.commands.HelpCommand;
import seedu.mentorstack.logic.commands.ListCommand;
import seedu.mentorstack.logic.commands.MarkCommand;
import seedu.mentorstack.logic.commands.ShowArchiveCommand;
import seedu.mentorstack.logic.commands.StatsCommand;
import seedu.mentorstack.logic.commands.UnarchiveCommand;
import seedu.mentorstack.logic.commands.UndoCommand;
import seedu.mentorstack.logic.commands.UnfinishCommand;
import seedu.mentorstack.logic.commands.UnmarkCommand;
import seedu.mentorstack.logic.commands.ViewCommand;
import seedu.mentorstack.logic.parser.exceptions.ParseException;
import seedu.mentorstack.model.person.NameContainsKeywordsPredicate;
import seedu.mentorstack.model.person.Person;
import seedu.mentorstack.model.person.Subject;
import seedu.mentorstack.testutil.EditPersonDescriptorBuilder;
import seedu.mentorstack.testutil.PersonBuilder;
import seedu.mentorstack.testutil.PersonUtil;

public class MentorstackParserTest {

    private final MentorstackParser parser = new MentorstackParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " 1 ");
        assertEquals(new DeleteCommand(INDEX_SET_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_view() throws Exception {
        assertTrue(parser.parseCommand(ViewCommand.COMMAND_WORD) instanceof ViewCommand);
        assertTrue(parser.parseCommand(ViewCommand.COMMAND_WORD + " 3") instanceof ViewCommand);
    }

    @Test
    public void parseCommand_undo() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD + " 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_stats() throws Exception {
        assertTrue(parser.parseCommand(StatsCommand.COMMAND_WORD) instanceof StatsCommand);
        assertTrue(parser.parseCommand(StatsCommand.COMMAND_WORD + " s/CS2103") instanceof StatsCommand);
    }

    @Test
    public void parseCommand_finish() throws Exception {
        Set<Subject> subjects = new HashSet<>();
        subjects.add(new Subject("CS2103"));
        FinishCommand command = (FinishCommand) parser.parseCommand(
                FinishCommand.COMMAND_WORD + " 1 " + "s/CS2103");
        assertEquals(new FinishCommand(INDEX_FIRST_PERSON, subjects), command);
    }

    @Test
    public void parseCommand_unfinish() throws Exception {
        Set<Subject> subjects = new HashSet<>();
        subjects.add(new Subject("CS2103"));
        UnfinishCommand command = (UnfinishCommand) parser.parseCommand(
                UnfinishCommand.COMMAND_WORD + " 1 " + "s/CS2103");
        assertEquals(new UnfinishCommand(INDEX_FIRST_PERSON, subjects), command);
    }

    @Test
    public void parseCommand_mark() throws Exception {
        MarkCommand command = (MarkCommand) parser.parseCommand(
                MarkCommand.COMMAND_WORD + " 1 ");
        assertEquals(new MarkCommand(INDEX_SET_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_unmark() throws Exception {
        UnmarkCommand command = (UnmarkCommand) parser.parseCommand(
                UnmarkCommand.COMMAND_WORD + " 1 ");
        assertEquals(new UnmarkCommand(INDEX_SET_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_archive() throws Exception {
        ArchiveCommand command = (ArchiveCommand) parser.parseCommand(
                ArchiveCommand.COMMAND_WORD + " 1 ");
        assertEquals(new ArchiveCommand(INDEX_SET_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_unarchive() throws Exception {
        UnarchiveCommand command = (UnarchiveCommand) parser.parseCommand(
                UnarchiveCommand.COMMAND_WORD + " 1 ");
        assertEquals(new UnarchiveCommand(INDEX_SET_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_showArchive() throws Exception {
        assertTrue(parser.parseCommand(ShowArchiveCommand.COMMAND_WORD) instanceof ShowArchiveCommand);
        assertTrue(parser.parseCommand(ShowArchiveCommand.COMMAND_WORD + " 3") instanceof ShowArchiveCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
