package seedu.summoners.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.summoners.model.player.Player;

/**
 * A UI component that displays information of a {@code Player}.
 */
public class PlayerCard extends UiPart<Region> {

    private static final String FXML = "PlayerListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/summonersbook-level4/issues/336">The issue on SummonersBook level 4</a>
     */

    public final Player player;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private FlowPane details;
    @FXML
    private FlowPane tags;


    /**
     * Creates a {@code PlayerCode} with the given {@code Player} and index to display.
     */
    public PlayerCard(Player player, int displayedIndex) {
        super(FXML);
        this.player = player;
        id.setText(displayedIndex + ". ");
        name.setText(player.getName().fullName);

        player.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        StyledLabel rankLabel = new StyledLabel(player.getRank().value, "rank", "details_label");
        StyledLabel roleLabel = new StyledLabel(player.getRole().value, "role", "details_label");
        StyledLabel championLabel = new StyledLabel(player.getChampion().value, "champion", "details_label");
        details.getChildren().addAll(rankLabel.getRoot(), roleLabel.getRoot(), championLabel.getRoot());
    }
}
