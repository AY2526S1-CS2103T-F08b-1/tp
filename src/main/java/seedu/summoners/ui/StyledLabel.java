package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A UI component for a label with dynamic styling and an optional icon.
 */
public class StyledLabel extends UiPart<Region> {

    private static final String FXML = "StyledLabel.fxml";

    // Icon constants
    private static final String ICON_RANK = "\u269C";
    private static final String ICON_ROLE = "\u2694";
    private static final String ICON_CHAMPION = "\u2727";

    @FXML
    private Label styledLabel;

    /**
     * Creates a styled label that dynamically applies CSS classes and an icon.
     *
     * @param text The text to display in the label.
     * @param type The type of the label ("rank", "role", or "champion").
     * @param baseStyleClass The base CSS class to apply to the label.
     */
    public StyledLabel(String text, String type, String baseStyleClass) {
        super(FXML);
        styledLabel.setText(text);
        styledLabel.getStyleClass().add(baseStyleClass);

        String dynamicStyleClass = "";
        String iconCode = "";

        switch (type) {
        case "rank":
            dynamicStyleClass = text.toLowerCase() + "_rank";
            iconCode = ICON_RANK;
            break;
        case "role":
            dynamicStyleClass = "role_" + text.toLowerCase();
            iconCode = ICON_ROLE;
            break;
        case "champion":
            dynamicStyleClass = "champion_label";
            iconCode = ICON_CHAMPION;
            break;
        default:
            // No dynamic style or icon for other types
            break;
        }

        if (!dynamicStyleClass.isEmpty()) {
            styledLabel.getStyleClass().add(dynamicStyleClass);
        }

        // Set the icon if one is defined for the type
        if (!iconCode.isEmpty()) {
            setIcon(iconCode);
        }
    }


    /**
     * Sets an icon for the label.
     */
    private void setIcon(String iconCode) {
        Label icon = new Label(iconCode);
        icon.getStyleClass().add("icon_label");
        styledLabel.setGraphic(icon);
    }
}
