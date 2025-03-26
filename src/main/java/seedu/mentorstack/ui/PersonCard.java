package seedu.mentorstack.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import seedu.mentorstack.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final String MALE_IMAGE_PATH = "/images/male_profile.png";
    private static final String FEMALE_IMAGE_PATH = "/images/female_profile.png";
    private static final String DEFAULT_IMAGE_PATH = "/images/default_profile.png";
    private static final String MARKED_STYLE_CLASS = "marked";
    private static final String UNMARKED_STYLE_CLASS = "unmarked";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private ImageView profilePicture;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private FlowPane subjects;
    @FXML
    private Circle markerCircle;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);

        // Load the profile picture based on gender
        profilePicture.setImage(new Image(getGenderImagePath(person)));

        person.getSubjects().stream()
                .sorted(Comparator.comparing(subject -> subject.subjectName))
                .forEach(subject -> subjects.getChildren().add(new Label(subject.subjectName)));

        updateMarkedStyle();
    }

    private void updateMarkedStyle() {
        markerCircle.setVisible(person.getIsMarked());
    }

    private String getGenderImagePath(Person person) {
        if (person.getGender().value.equals("M")) {
            return getClass().getResource(MALE_IMAGE_PATH).toExternalForm();
        } else if (person.getGender().value.equals("F")) {
            return getClass().getResource(FEMALE_IMAGE_PATH).toExternalForm();
        } else {
            return getClass().getResource(DEFAULT_IMAGE_PATH).toExternalForm();
        }
    }
}
