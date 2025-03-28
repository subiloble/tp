package seedu.mentorstack.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import seedu.mentorstack.commons.core.GuiSettings;
import seedu.mentorstack.commons.core.LogsCenter;
import seedu.mentorstack.logic.commands.Command;
import seedu.mentorstack.logic.commands.CommandResult;
import seedu.mentorstack.logic.commands.exceptions.CommandException;
import seedu.mentorstack.logic.parser.MentorstackParser;
import seedu.mentorstack.logic.parser.exceptions.ParseException;
import seedu.mentorstack.model.Model;
import seedu.mentorstack.model.ReadOnlyMentorstack;
import seedu.mentorstack.model.person.Person;
import seedu.mentorstack.model.person.Subject;
import seedu.mentorstack.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final MentorstackParser mentorstackParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        mentorstackParser = new MentorstackParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = mentorstackParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveMentorstack(model.getMentorstack());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyMentorstack getMentorstack() {
        return model.getMentorstack();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    public Map<String, Integer> getStudentsBySubject() {

        Map<String, Integer> studentsBySubjects = new HashMap<>();
        List<Person> personList = this.getFilteredPersonList();

        for (Person person : personList) {
            Set<Subject> subjects = person.getSubjects();

            for (Subject subject : subjects) {
                String subjectName = subject.toString();
                studentsBySubjects.put(subjectName, studentsBySubjects.getOrDefault(subjectName, 0) + 1);
            }
        }

        return studentsBySubjects;
    }

    public XYChart.Series<String, Number> populateSeries(XYChart.Series<String, Number> series, Map<String, Integer> studentsBySubjects) {

        for (Map.Entry<String, Integer> entry : studentsBySubjects.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        return series;
    }

    @Override
    public Path getMentorstackFilePath() {
        return model.getMentorstackFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
