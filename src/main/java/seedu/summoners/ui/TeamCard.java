package seedu.summoners.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.Team;

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
    private HBox topPlayerDetails;
    @FXML
    private HBox junglePlayerDetails;
    @FXML
    private HBox midPlayerDetails;
    @FXML
    private HBox adcPlayerDetails;
    @FXML
    private HBox supportPlayerDetails;


    /**
     * Creates a {@code TeamListCard} with the given {@code Team} and index to display.
     */
    public TeamCard(Team team, int displayedIndex) {
        super(FXML);
        this.team = team;
        id.setText(displayedIndex + ". ");
        teamName.setText("Team " + displayedIndex);

        for (Player player : team.getPlayers()) {
            switch (player.getRole().toString().toUpperCase()) {
            case "TOP":
                populatePlayerDetails(topPlayerDetails, player);
                break;
            case "JUNGLE":
                populatePlayerDetails(junglePlayerDetails, player);
                break;
            case "MID":
                populatePlayerDetails(midPlayerDetails, player);
                break;
            case "ADC":
                populatePlayerDetails(adcPlayerDetails, player);
                break;
            case "SUPPORT":
                populatePlayerDetails(supportPlayerDetails, player);
                break;
            default:
                break;
            }
        }
    }

    /**
     * A clean helper method to create and add all UI components for a single player's details.
     *
     * @param playerDetails The HBox container for the player's info.
     * @param player The player to display in the row.
     */
    private void populatePlayerDetails(HBox playerDetails, Player player) {
        Label bulletPoint = new Label("•");
        bulletPoint.getStyleClass().add("bullet_point");

        Label nameLabel = new Label(player.getName().fullName);
        nameLabel.getStyleClass().add("cell_small_label");
        nameLabel.setMinWidth(120);

        StyledLabel roleLabel = new StyledLabel(player.getRole().value, "role", "details_label");
        StyledLabel rankLabel = new StyledLabel(player.getRank().value, "rank", "details_label");
        StyledLabel championLabel = new StyledLabel(player.getChampion().value, "champion", "details_label");

        playerDetails.getChildren().clear();
        playerDetails.getChildren().addAll(bulletPoint, roleLabel.getRoot(), rankLabel.getRoot(),
                championLabel.getRoot(), nameLabel);
    }
}
