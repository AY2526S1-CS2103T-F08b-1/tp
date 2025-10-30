package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * A UI component that displays information of a {@code Team}.
 */
public class TeamCard extends UiPart<Region> {

    private static final String FXML = "TeamListCard.fxml";

    public final Team team;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label teamName;
    @FXML
    private HBox topPersonDetails;
    @FXML
    private HBox junglePersonDetails;
    @FXML
    private HBox midPersonDetails;
    @FXML
    private HBox adcPersonDetails;
    @FXML
    private HBox supportPersonDetails;


    /**
     * Creates a {@code TeamListCard} with the given {@code Team} and index to display.
     */
    public TeamCard(Team team, int displayedIndex) {
        super(FXML);
        this.team = team;
        id.setText(displayedIndex + ". ");
        teamName.setText("Team " + displayedIndex);

        for (Person person : team.getPersons()) {
            switch (person.getRole().toString().toUpperCase()) {
            case "TOP":
                populatePersonDetails(topPersonDetails, person);
                break;
            case "JUNGLE":
                populatePersonDetails(junglePersonDetails, person);
                break;
            case "MID":
                populatePersonDetails(midPersonDetails, person);
                break;
            case "ADC":
                populatePersonDetails(adcPersonDetails, person);
                break;
            case "SUPPORT":
                populatePersonDetails(supportPersonDetails, person);
                break;
            default:
                break;
            }
        }
    }

    /**
     * A clean helper method to create and add all UI components for a single person's details.
     *
     * @param personDetails The HBox container for the person's info.
     * @param person The person to display in the row.
     */
    private void populatePersonDetails(HBox personDetails, Person person) {
        Label bulletPoint = new Label("•");
        bulletPoint.getStyleClass().add("bullet_point");

        Label nameLabel = new Label(person.getName().fullName);
        nameLabel.getStyleClass().add("cell_small_label");
        nameLabel.setMinWidth(120);

        StyledLabel roleLabel = new StyledLabel(person.getRole().value, "role", "details_label");
        StyledLabel rankLabel = new StyledLabel(person.getRank().value, "rank", "details_label");
        StyledLabel championLabel = new StyledLabel(person.getChampion().value, "champion", "details_label");

        personDetails.getChildren().clear();
        personDetails.getChildren().addAll(bulletPoint, roleLabel.getRoot(), rankLabel.getRoot(),
                championLabel.getRoot(), nameLabel);
    }
}
