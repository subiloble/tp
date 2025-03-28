package seedu.mentorstack.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
// import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.mentorstack.logic.commands.CommandResult;
import seedu.mentorstack.logic.commands.exceptions.CommandException;
import seedu.mentorstack.logic.parser.exceptions.ParseException;

import java.util.HashMap;
import java.util.Map;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    
    // @FXML
    // private HBox hboxx;

    @FXML
    private TextField commandTextField;

    @FXML
    private TextField promptTextField;

    // @FXML
    // private void initialize() {
    //     // Log to ensure everything is initialized properly
    //     System.out.println("CommandBox Initialized:");
    //     System.out.println("HBox: " + hboxx);
    //     System.out.println("Command TextField: " + commandTextField);
    //     System.out.println("Prompt TextField: " + promptTextField);
    // }

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        promptTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        try {
            commandExecutor.execute(commandText);
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            // commandTextField.setText(commandTextField.getText() + "BLABLABLABLABLA");
            setStyleToIndicateCommandFailure();
            System.out.println(e.getMessage());
            // commandTextField.setText("");
            // commandTextField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background,-30%); }");
            // commandTextField.setPromptText("Follow the instructions");
            
        }
    }


    public static String missing(String ui) {
        String[] splitted = ui.split(" ");

        Map<String, String> ideal = new HashMap<>();
        ideal.put("n", "John Doe");
        ideal.put("e", "johnd@example.com");
        ideal.put("p", "98765432");
        ideal.put("s", "maths computer science");
        ideal.put("g", "F");

        int indOrg = 0;
        int indMap = 0;
        String[] resultArray = new String[splitted.length * 2];

        while (indOrg < splitted.length) {
            if (splitted[indOrg].equals("add")) {
                indOrg++;
                continue;
            }

            if (splitted[indOrg].contains("/")) {
                String[] slashed = splitted[indOrg].split("/");
                resultArray[indMap++] = slashed[0];
                resultArray[indMap++] = String.join(" ", java.util.Arrays.copyOfRange(slashed, 1, slashed.length));
            } else {
                resultArray[indMap - 1] += " " + splitted[indOrg];
            }

            indOrg++;
        }

        Map<String, String> res = new HashMap<>();
        for (int i = 0; i < indMap; i += 2) {
            res.put(resultArray[i], resultArray[i + 1]);
        }

        StringBuilder toreturn = new StringBuilder();
        for (Map.Entry<String, String> entry : ideal.entrySet()) {
            if (!res.containsKey(entry.getKey())) {
                toreturn.append(entry.getKey()).append("/").append(entry.getValue()).append(" ");
            }
        }

        return " " + toreturn.toString().trim();
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
        // commandTextField.setVisible(true);
        promptTextField.setVisible(false);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();
        promptTextField.setVisible(true);
        promptTextField.setPromptText(commandTextField.getText() + missing(commandTextField.getText()));

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);

        StackPane.setAlignment(promptTextField, javafx.geometry.Pos.CENTER); 
        
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.mentorstack.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
