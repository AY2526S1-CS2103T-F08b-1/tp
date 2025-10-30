package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.person.Stats;

/**
 * Controller for a window that displays detailed information about a person.
 *
 * This class follows a robust hybrid UI design pattern:
 * 1.  Declarative FXML for Static Content: The window's static structure, layout,
 *     labels, and chart components are all defined in PersonDetailWindow.fxml.
 *     This adheres to the principle of Separation of Concerns, making the view's
 *     structure easy to understand and modify without touching Java code.
 *
 * 2.  Dynamic Data Binding: The Java controller populates the static FXML components
 *     with data from the Person object. Charts are populated with data series through
 *     private methods that handle data transformation and axis configuration.
 *
 * The controller's primary role is to act as a coordinator: it binds data to both
 * static labels and chart components defined in FXML. This represents a pragmatic
 * and maintainable best practice. Because for a specific, fixed view like this,
 * attempting to make it open for extension would be over-engineering and a
 * violation of YAGNI and KISS.
 */
public class PersonDetailWindow extends UiPart<Stage> {

    private static final String FXML = "PersonDetailWindow.fxml";
    private static final int MAX_DISPLAYED_MATCHES = 10;
    private final Logger logger = LogsCenter.getLogger(getClass());

    private Person person;

    // @FXML fields for the static components defined in the FXML file.
    @FXML private Label nameLabel;
    @FXML private Label roleLabel;
    @FXML private Label rankLabel;
    @FXML private Label championLabel;
    @FXML private Label winsLabel;
    @FXML private Label lossesLabel;
    @FXML private Label tagsLabel;
    @FXML private Label performanceLabel;
    @FXML private GridPane detailsPane;

    // @FXML fields for the charts defined in FXML.
    @FXML private LineChart<Number, Number> performanceChart;
    @FXML private LineChart<Number, Number> csChart;
    @FXML private LineChart<Number, Number> kdaChart;
    @FXML private LineChart<Number, Number> goldDiffChart;

    /**
     * Creates a PersonDetailWindow.
     */
    public PersonDetailWindow() {
        this(new Stage());
    }

    /**
     * Creates a PersonDetailWindow with the given Stage.
     *
     * @param root The Stage to be used as the root of this window.
     */
    public PersonDetailWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Sets the person to be displayed and populates all UI elements.
     * This is the single entry point for updating the window's content.
     *
     * @param person The person whose details will be displayed. Must not be null.
     */
    public void setPerson(Person person) {
        assert person != null : "Person object cannot be null.";
        this.person = person;
        displayPersonDetails();
        displayCharts();
    }

    /**
     * Populates the static labels (defined in FXML) with the person's information.
     * This method's responsibility is purely data binding.
     */
    private void displayPersonDetails() {
        nameLabel.setText(person.getName().toString());
        roleLabel.setText(person.getRole().toString());
        rankLabel.setText(person.getRank().toString());
        championLabel.setText(person.getChampion().toString());
        tagsLabel.setText(person.getTags().toString());
        winsLabel.setText(String.valueOf(person.getWins()));
        lossesLabel.setText(String.valueOf(person.getLosses()));
        performanceLabel.setText(String.format("%.1f / 10.0", person.getStats().getValue()));
    }

    /**
     * Populates the charts with data from the person's stats.
     * Charts are statically defined in FXML; only their data is populated here.
     */
    private void displayCharts() {
        Stats stats = person.getStats();

        // Populate each chart with data
        populatePerformanceChart(stats.getScores());
        populateCsPerMinuteChart(stats.getCsPerMinute());
        populateKdaChart(stats.getKdaScores());
        populateGoldDifferenceChart(stats.getGoldDiffAt15());
    }

    /**
     * Populates the Performance Score chart with data.
     *
     * @param scores The list of performance scores to display.
     */
    private void populatePerformanceChart(List<? extends Number> scores) {
        String title = String.format("Performance Score Over Time (Latest %d)", MAX_DISPLAYED_MATCHES);
        String yAxisLabel = "Performance Score";
        XYChart.Series<Number, Number> series = createChartSeries(scores);
        populateChart(performanceChart, title, yAxisLabel, series);
    }

    /**
     * Populates the CS per Minute chart with data.
     *
     * @param csPerMinute The list of CS per minute values to display.
     */
    private void populateCsPerMinuteChart(List<? extends Number> csPerMinute) {
        String title = String.format("CS per Minute Over Time (Latest %d)", MAX_DISPLAYED_MATCHES);
        String yAxisLabel = "CS per Minute";
        XYChart.Series<Number, Number> series = createChartSeries(csPerMinute);
        populateChart(csChart, title, yAxisLabel, series);
    }

    /**
     * Populates the KDA chart with data.
     *
     * @param kdaScores The list of KDA scores to display.
     */
    private void populateKdaChart(List<? extends Number> kdaScores) {
        String title = String.format("KDA Over Time (Latest %d)", MAX_DISPLAYED_MATCHES);
        String yAxisLabel = "KDA";
        XYChart.Series<Number, Number> series = createChartSeries(kdaScores);
        populateChart(kdaChart, title, yAxisLabel, series);
    }

    /**
     * Populates the Gold Difference chart with data.
     *
     * @param goldDiffAt15 The list of gold difference at 15 minutes values to display.
     */
    private void populateGoldDifferenceChart(List<? extends Number> goldDiffAt15) {
        String title = String.format("Gold Diff @15 Over Time (Latest %d)", MAX_DISPLAYED_MATCHES);
        String yAxisLabel = "Gold Diff @15";
        XYChart.Series<Number, Number> series = createChartSeries(goldDiffAt15);
        populateChart(goldDiffChart, title, yAxisLabel, series);
    }

    /**
     * Populates a chart with the given title, axis label, and data series.
     *
     * @param chart The chart to populate.
     * @param title The title to display on the chart.
     * @param yAxisLabel The label for the Y-axis.
     * @param series The data series to plot.
     */
    private void populateChart(LineChart<Number, Number> chart, String title, String yAxisLabel,
            XYChart.Series<Number, Number> series) {
        chart.setLegendVisible(false);
        setLabels(chart, title, yAxisLabel);
        plotData(chart, series);
        configureAxes(chart);
    }

    /**
     * Creates a data series from the last N matches.
     * Package-private to allow testing.
     *
     * @param data The list of data points to create a series from. Must not be null.
     * @return A series containing the last N data points.
     */
    static XYChart.Series<Number, Number> createChartSeries(List<? extends Number> data) {
        requireNonNull(data);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        int startIndex = Math.max(0, data.size() - MAX_DISPLAYED_MATCHES);
        List<? extends Number> relevantData = data.subList(startIndex, data.size());

        for (int i = 0; i < relevantData.size(); i++) {
            int matchNumber = startIndex + i + 1;
            series.getData().add(new XYChart.Data<>(matchNumber, relevantData.get(i)));
        }
        return series;
    }

    /**
     * Plots the given data series onto the chart, replacing any existing data.
     *
     * @param chart The chart to plot data on.
     * @param series The data series to plot.
     */
    private void plotData(LineChart<Number, Number> chart, XYChart.Series<Number, Number> series) {
        chart.getData().setAll(List.of(series));
    }

    /**
     * Sets the title and axis labels for the chart.
     *
     * @param chart The chart to set labels for.
     * @param title The title to set.
     * @param yAxisLabel The Y-axis label to set.
     */
    private void setLabels(LineChart<Number, Number> chart, String title, String yAxisLabel) {
        chart.setTitle(title);
        chart.getYAxis().setLabel(yAxisLabel);
    }

    /**
     * Configures the X-Axis bounds and ticks based on the data in the plotted series.
     * This method must be called after plotData() has been called.
     *
     * @param chart The chart whose axes to configure.
     */
    private void configureAxes(LineChart<Number, Number> chart) {
        if (chart.getData().isEmpty() || chart.getData().get(0).getData().isEmpty()) {
            return;
        }

        XYChart.Series<Number, Number> series = chart.getData().get(0);
        int startTick = series.getData().get(0).getXValue().intValue();
        int endTick = series.getData().get(series.getData().size() - 1).getXValue().intValue();

        NumberAxis xAxis = (NumberAxis) chart.getXAxis();
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(startTick);
        xAxis.setUpperBound(endTick);
        xAxis.setTickUnit(1.0);
    }

    /**
     * Shows the person detail window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing person detail window.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the person detail window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the person detail window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the person detail window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
