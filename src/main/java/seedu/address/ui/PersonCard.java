package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    private static final String ICON_RANK = "\u269C";
    private static final String ICON_ROLE = "\u2694";
    private static final String ICON_CHAMPION = "\u2727";

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
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label rank;
    @FXML
    private Label role;
    @FXML
    private Label champion;
    @FXML
    private Label teamStatus;
    @FXML
    private FlowPane tags;


    /**
     * Creates a {@code PersonCode} with the given {@code Person}, index, and {@code Logic}.
     */
    public PersonCard(Person person, int displayedIndex, Logic logic) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);

        setupDetailLabel(rank, ICON_RANK, person.getRank().value, "rank");
        setupDetailLabel(role, ICON_ROLE, person.getRole().value, "role");
        setupDetailLabel(champion, ICON_CHAMPION, person.getChampion().value, "champion");
        setupTeamStatus(logic);

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * A helper method to configure a detail label with an icon, text, and dynamic style.
     * @param label The main label to configure.
     * @param iconCode The Unicode string for the icon.
     * @param text The text to display.
     * @param type The type of detail ("rank", "role", or "champion") for styling.
     */
    private void setupDetailLabel(Label label, String iconCode, String text, String type) {
        // Create the icon as its own Label
        Label icon = new Label(iconCode);
        icon.getStyleClass().add("icon_label");

        // Set the icon as the graphic for the main label
        label.setGraphic(icon);

        // Set the text for the main label
        label.setText(text);

        // Apply dynamic styling based on the type
        String dynamicStyleClass = "";
        switch (type) {
        case "rank":
            dynamicStyleClass = text.toLowerCase() + "_rank";
            break;
        case "role":
            dynamicStyleClass = "role_" + text.toLowerCase();
            break;
        case "champion":
            dynamicStyleClass = "champion_label";
            break;
        default:
            break;
        }
        if (!dynamicStyleClass.isEmpty()) {
            label.getStyleClass().add(dynamicStyleClass);
        }
    }

    /**
     * Configures the team status label to display whether the person is assigned to a team.
     * @param logic The Logic component used to query team assignment status.
     */
    private void setupTeamStatus(Logic logic) {
        boolean isInTeam = logic.isPersonInAnyTeam(person);
        if (isInTeam) {
            teamStatus.setText("✓ In Team");
            teamStatus.getStyleClass().add("team_status_assigned");
        } else {
            teamStatus.setText("○ Available");
            teamStatus.getStyleClass().add("team_status_available");
        }
    }
}
