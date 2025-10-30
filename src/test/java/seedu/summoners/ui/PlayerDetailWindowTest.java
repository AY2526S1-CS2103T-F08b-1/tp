package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.scene.chart.XYChart;

class PersonDetailWindowTest {

    @Test
    void createChartSeries_nullData_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> PersonDetailWindow.createChartSeries(null));
    }

    @Test
    void createChartSeries_emptyList_returnsEmptySeries() {
        List<Integer> emptyData = new ArrayList<>();
        XYChart.Series<Number, Number> series = PersonDetailWindow.createChartSeries(emptyData);
        assertTrue(series.getData().isEmpty());
    }

    @Test
    void createChartSeries_fewerThanMaxMatches_usesAllData() {
        List<Integer> data = List.of(10, 20, 30); // 3 matches
        XYChart.Series<Number, Number> series = PersonDetailWindow.createChartSeries(data);
        assertEquals(3, series.getData().size());
        // Check first point: Match #1 has value 10
        assertEquals(1, series.getData().get(0).getXValue());
        assertEquals(10, series.getData().get(0).getYValue());
    }

    @Test
    void createChartSeries_moreThanMaxMatches_usesLastTen() {
        // MAX_DISPLAYED_MATCHES is 10
        List<Integer> data = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 100, 200); // 11 matches
        XYChart.Series<Number, Number> series = PersonDetailWindow.createChartSeries(data);

        // Should only have 10 data points
        assertEquals(10, series.getData().size());

        // Check first point: This should be match #2 with value 2
        assertEquals(2, series.getData().get(0).getXValue());
        assertEquals(2, series.getData().get(0).getYValue());

        // Check last point: This should be match #11 with value 200
        assertEquals(11, series.getData().get(9).getXValue());
        assertEquals(200, series.getData().get(9).getYValue());
    }
}
