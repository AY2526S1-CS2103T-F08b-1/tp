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
    private Label top;
    @FXML
    private Label topName;
    @FXML
    private Label jungle;
    @FXML
    private Label jungleName;
    @FXML
    private Label mid;
    @FXML
    private Label midName;
    @FXML
    private Label adc;
    @FXML
    private Label adcName;
    @FXML
    private Label support;
    @FXML
    private Label supportName;
    @FXML
    private Label topRank;
    @FXML
    private Label jungleRank;
    @FXML
    private Label midRank;
    @FXML
    private Label adcRank;
    @FXML
    private Label supportRank;


    /**
     * Creates a {@code TeamListCard} with the given {@code Team} and index to display.
     */
    public TeamCard(Team team, int displayedIndex) {
        super(FXML);
        this.team = team;
        id.setText(displayedIndex + ". ");
        teamName.setText("Team " + displayedIndex);

        for (Person person : team.getPersons()) {
            String role = person.getRole().value;
            String name = person.getName().fullName;
            String rank = person.getRank().value;

            switch (person.getRole().toString().toUpperCase()) {
            case "TOP":
                configureRoleLabels(top, topName, topRank, role, name, rank);
                break;
            case "JUNGLE":
                configureRoleLabels(jungle, jungleName, jungleRank, role, name, rank);
                break;
            case "MID":
                configureRoleLabels(mid, midName, midRank, role, name, rank);
                break;
            case "ADC":
                configureRoleLabels(adc, adcName, adcRank, role, name, rank);
                break;
            case "SUPPORT":
                configureRoleLabels(support, supportName, supportRank, role, name, rank);
                break;
            default:
                break;
            }
        }
    }

    /**
     * Helper method to set the text and style for role, name, and rank labels.
     */
    private void configureRoleLabels(Label roleLabel, Label nameLabel, Label rankLabel,
                                      String role, String name, String rank) {
        roleLabel.setText(role);
        nameLabel.setText(name);
        rankLabel.setText(rank);
        roleLabel.getStyleClass().add("role_" + role.toLowerCase());
        rankLabel.getStyleClass().add(rank.toLowerCase() + "_rank");
    }
}
