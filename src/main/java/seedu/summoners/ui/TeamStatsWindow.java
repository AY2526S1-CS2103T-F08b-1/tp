package seedu.address.ui;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * A standalone JavaFX window that displays detailed statistics for a selected {@code Team}.
 * <p>
 * The window shows:
 * <ul>
 *   <li>A pie chart of the team's wins and losses</li>
 *   <li>A roster section listing each member by role</li>
 * </ul>
 * It is opened when the {@code viewteam} command is executed and updates dynamically
 * when the same team is viewed again.
 */
public class TeamStatsWindow extends Stage {

    private final Label teamTitle = new Label();
    private final PieChart wlChart = new PieChart();
    private final GridPane rosterGrid = new GridPane();

    /**
     * Constructs an empty {@code TeamStatsWindow} with layout and styling initialized.
     * <p>
     * The window is modeless (non-blocking), meaning the user can continue using the main
     * application window while this one is open. It sets up all UI components, spacing,
     * and default scene size.
     */
    public TeamStatsWindow() {
        setTitle("Team Details");
        initModality(Modality.NONE); // not blocking main window

        teamTitle.getStyleClass().add("heading");
        wlChart.setLegendVisible(true);
        wlChart.setLabelsVisible(true);

        rosterGrid.setHgap(12);
        rosterGrid.setVgap(6);
        rosterGrid.setPadding(new Insets(6));

        VBox content = new VBox(12,
                teamTitle,
                wlChart,
                new Separator(),
                new Label("Roster"),
                rosterGrid
        );
        content.setAlignment(Pos.TOP_LEFT);
        content.setPadding(new Insets(12));

        BorderPane root = new BorderPane(content);
        Scene scene = new Scene(root, 460, 520);
        setScene(scene);
    }

    /**
     * Populates the window with the given team data and header label.
     *
     * @param team         team whose stats are displayed
     * @param displayLabel friendly label to show in the header (e.g., "Team 2")
     */
    public void setTeam(Team team, String displayLabel) {
        teamTitle.setText(displayLabel);

        int wins = team.getWins();
        int losses = team.getLosses();
        wlChart.setData(FXCollections.observableArrayList(
                new PieChart.Data("Wins (" + wins + ")", Math.max(0, wins)),
                new PieChart.Data("Losses (" + losses + ")", Math.max(0, losses))
        ));

        // Build role -> player name map (Top, Jungle, Mid, Adc, Support)
        Map<String, String> roleToName = new LinkedHashMap<>();
        roleToName.put("Top", "");
        roleToName.put("Jungle", "");
        roleToName.put("Mid", "");
        roleToName.put("Adc", "");
        roleToName.put("Support", "");

        for (Person p : team.getPersons()) {
            String role = p.getRole().toString(); // ensure your Role#toString returns Top/Jungle/Mid/Adc/Support
            roleToName.put(role, p.getName().toString());
        }

        rosterGrid.getChildren().clear();
        int r = 0;
        for (Map.Entry<String, String> e : roleToName.entrySet()) {
            rosterGrid.add(new Label(e.getKey() + ":"), 0, r);
            rosterGrid.add(new Label(e.getValue()), 1, r);
            r++;
        }
    }

    /**
     * Displays the team statistics window for the specified team.
     * <p>
     * This method updates the content via {@link #setTeam(Team, String)} and ensures
     * the window is visible and brought to the front.
     *
     * @param team         the team whose stats are displayed
     * @param displayLabel the friendly label for the header (e.g., "Team 1")
     */
    public void showTeam(Team team, String displayLabel) {
        setTeam(team, displayLabel);
        show();
        toFront();
    }

    /**
     * Brings this window to the foreground and requests keyboard focus.
     * <p>
     * Used when the window is already open to ensure it remains visible
     * after executing another command.
     */
    public void focus() {
        requestFocus();
        toFront();
    }
}

