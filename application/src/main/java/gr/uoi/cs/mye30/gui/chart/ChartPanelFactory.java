package gr.uoi.cs.mye30.gui.chart;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import gr.uoi.cs.mye30.entity.YearValue;
import gr.uoi.cs.mye30.gui.ChartType;

public class ChartPanelFactory {
	private static final Color CHART_BACKGROUND = new Color(240, 240, 240);
	private static final NumberFormat yearValueFormatter = new DecimalFormat("00");

	public static ChartPanel create(ChartType type, List<YearValue> yearValues, int fromYear, int toYear) {
		switch (type) {
		case BAR:
			return createBarChart(yearValues);
		case SCATTER:
			return createScatterChart(yearValues);
		default:
			return createTimelineChart(yearValues);
		}
	}

	private static ChartPanel createTimelineChart(List<YearValue> yearValues) {
		ChartPanel scatterPanel = createScatterChart(yearValues);

		JFreeChart chart = scatterPanel.getChart();
		chart.getXYPlot().setRenderer(new XYLineAndShapeRenderer());
		return scatterPanel;
	}

	private static ChartPanel createScatterChart(List<YearValue> yearValues) {
		yearValues = new ArrayList<>(yearValues);
		yearValues.removeIf(YearValue::isFake);
		XYSeriesCollection dataset = new XYSeriesCollection();
		if (yearValues.isEmpty()) {
			JFreeChart chart = ChartFactory.createScatterPlot(null, "Year", null, dataset, PlotOrientation.VERTICAL,
					true, true, false);

			chart.getXYPlot().getDomainAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			return new ChartPanel(chart);
		}

		long numberOfIndicators = yearValues.stream().map(YearValue::getIndicator).distinct().count();
		double max = yearValues.stream().mapToDouble(YearValue::getValue).max().getAsDouble();
		double divider = max > 1_000_000 ? 1_000_000 : max > 1_000 ? 1_000 : 1;
		String divderTitle = divider == 1_000_000 ? "Millions" : divider == 1_000 ? "Thousands" : null;

		Map<String, XYSeries> seriesMap = new HashMap<>();
		if (numberOfIndicators == 1) {
			yearValues.stream().map(YearValue::getCountry)
					.forEach(c -> seriesMap.put(c.getName(), new XYSeries(c.getName())));
		} else {
			yearValues.forEach(yv -> {
				String key = yv.getCountry().getName() + "-" + yv.getIndicator().getName();
				seriesMap.put(key, new XYSeries(key));
			});
		}
		yearValues.forEach(v -> {
			double value = v.getValue() / divider;

			String rowName;
			if (numberOfIndicators == 1)
				rowName = v.getCountry().getName();
			else
				rowName = v.getCountry().getName() + "-" + v.getIndicator().getName();
			seriesMap.get(rowName).add(v.getYear(), value);
		});

		seriesMap.values().forEach(dataset::addSeries);

		String title = null;
		if (numberOfIndicators == 1)
			title = yearValues.get(0).getIndicator().getName();

		JFreeChart chart = ChartFactory.createScatterPlot(title, "Year", divderTitle, dataset, PlotOrientation.VERTICAL,
				true, true, false);

		chart.getXYPlot().getDomainAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		chart.getPlot().setBackgroundPaint(CHART_BACKGROUND);
		return new ChartPanel(chart);
	}

	private static ChartPanel createBarChart(List<YearValue> yearValues) {
		long numberOfIndicators = yearValues.stream().map(YearValue::getIndicator).distinct().count();
		DefaultStatisticalCategoryDataset dataset = new DefaultStatisticalCategoryDataset();
		double max = yearValues.stream().mapToDouble(YearValue::getValue).max().getAsDouble();
		double divider = max > 1_000_000 ? 1_000_000 : max > 1_000 ? 1_000 : 1;
		String divderTitle = divider == 1_000_000 ? "Millions" : divider == 1_000 ? "Thousands" : null;

		yearValues.forEach(v -> {
			double value = v.getValue() / divider;

			String rowName;
			if (numberOfIndicators == 1)
				rowName = v.getCountry().getName();
			else
				rowName = v.getCountry().getName() + "-" + v.getIndicator().getName();
			dataset.add(value, null, rowName, yearValueFormatter.format(v.getYear() % 100));
		});

		String title = null;
		if (numberOfIndicators == 1)
			title = yearValues.get(0).getIndicator().getName();
		JFreeChart chart = ChartFactory.createBarChart(title, "Year", divderTitle, dataset, PlotOrientation.VERTICAL,
				true, true, false);

		fixDomainAxisIfThereIsNoValue(max, chart);
		chart.getPlot().setBackgroundPaint(CHART_BACKGROUND);
		return new ChartPanel(chart);
	}

	private static void fixDomainAxisIfThereIsNoValue(double max, JFreeChart barChart) {
		if (max == 0) {
			barChart.getCategoryPlot().setDomainAxis(new CategoryAxis());
			NumberAxis axis = new NumberAxis();
			axis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			barChart.getCategoryPlot().setRangeAxis(axis);
		}
	}
}
