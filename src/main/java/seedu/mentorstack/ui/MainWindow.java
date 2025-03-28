package seedu.mentorstack.ui;

import java.util.Map;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import seedu.mentorstack.commons.core.GuiSettings;
import seedu.mentorstack.commons.core.LogsCenter;
import seedu.mentorstack.logic.Logic;
import seedu.mentorstack.logic.LogicManager;
import seedu.mentorstack.logic.commands.CommandResult;
import seedu.mentorstack.logic.commands.exceptions.CommandException;
import seedu.mentorstack.logic.parser.exceptions.ParseException;
import seedu.mentorstack.logic.parser.exceptions.ParseWithHintException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private Menu themeBar;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem statMenuItem;

    @FXML
    private CheckMenuItem hideCliMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private BarChart<?, ?> barChart;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getMentorstackFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show(); //
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide(); //
        primaryStage.hide();
    }

    @FXML
    private void handleCliVisibility() {

        if (((CheckMenuItem) fxmlLoader.getNamespace()
            .get("hideCliMenuItem"))
            .isSelected()) {
                commandBoxPlaceholder.setVisible(false);
                resultDisplayPlaceholder.setVisible(false);
                commandBoxPlaceholder.setManaged(false);
                resultDisplayPlaceholder.setManaged(false);
                ((CheckMenuItem) fxmlLoader
                    .getNamespace()
                    .get("hideCliMenuItem"))
                    .setSelected(true);
        } else {
            commandBoxPlaceholder.setVisible(true);
            resultDisplayPlaceholder.setVisible(true);
            commandBoxPlaceholder.setManaged(true);
            resultDisplayPlaceholder.setManaged(true);
            ((CheckMenuItem) fxmlLoader.getNamespace()
                .get("hideCliMenuItem"))
                .setSelected(false);
        }
    }

    private ObservableList<String> getCleanStyleSheetObject() {
        Scene scene = primaryStage.getScene();

        ObservableList<String> stylesheets = scene.getStylesheets();

        // remove all stylesheets
        for (int i = 0; i < stylesheets.toArray().length; i++) {
            stylesheets.remove(i);
        }

        // add back required stylesheets
        stylesheets.add(getClass().getResource("/view/BaseTheme.css").toExternalForm());
        stylesheets.add(getClass().getResource("/view/Extensions.css").toExternalForm());

        return stylesheets;
    }


    private void uncheckAllBoxes() {
        Map<String, Object> namespace = fxmlLoader.getNamespace();
        int k = 0;
        while (namespace.get("helpMenuItem" + String.valueOf(k)) != null) {
            ((CheckMenuItem) namespace
                .get("helpMenuItem" + String.valueOf(k)))
                .setSelected(false);
            k += 1;
        }
    }

    @FXML
    private void handleThemeSwap0() {
        getCleanStyleSheetObject().add(getClass().getResource("/view/palette1.css").toExternalForm());
        uncheckAllBoxes();
        ((CheckMenuItem) fxmlLoader.getNamespace()
            .get("helpMenuItem" + String.valueOf(0)))
            .setSelected(true);
    }

    @FXML
    private void handleThemeSwap1() {
        getCleanStyleSheetObject().add(getClass().getResource("/view/palette2.css").toExternalForm());
        uncheckAllBoxes();
        ((CheckMenuItem) fxmlLoader.getNamespace()
            .get("helpMenuItem" + String.valueOf(1)))
            .setSelected(true);
    }

    @FXML
    private void handleThemeSwap2() {
        getCleanStyleSheetObject().add(getClass().getResource("/view/palette3.css").toExternalForm());
        uncheckAllBoxes();
        ((CheckMenuItem) fxmlLoader.getNamespace()
            .get("helpMenuItem" + String.valueOf(2)))
            .setSelected(true);
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Triggers from menuItem click. Generates statistics window.
     */
    public void handleStatWindow() {

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Subjects");
        yAxis.setLabel("Students");
        yAxis.setTickUnit(1);

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Student Distribution");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("");

        Map<String, Integer> studentsBySubjects = ((LogicManager) logic).getStudentsBySubject();

        series = ((LogicManager) logic).populateSeries(series, studentsBySubjects);
        barChart.getData().add(series);

        Stage popupStage = new Stage();
        popupStage.setTitle("Statistics");

        Scene scene = new Scene(barChart, 600, 400);
        popupStage.setScene(scene);
        popupStage.show();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.mentorstack.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException,
                                                                    ParseWithHintException,
                                                                    ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException ce) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(ce.getMessage());
            throw ce;
        }
    }
}
