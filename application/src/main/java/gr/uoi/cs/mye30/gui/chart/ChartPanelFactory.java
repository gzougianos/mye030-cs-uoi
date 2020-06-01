package gr.uoi.cs.mye30.gui.chart;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;

import gr.uoi.cs.mye30.entity.YearValue;
import gr.uoi.cs.mye30.gui.ChartType;

public class ChartPanelFactory {
	private static final NumberFormat yearValueFormatter = new DecimalFormat("00");

	public static ChartPanel create(ChartType type, List<YearValue> yearValues) {
		switch (type) {
		case BAR:
			return createBarChart(yearValues);
		default:
			return createBarChart(yearValues);
		}
	}

	private static ChartPanel createBarChart(List<YearValue> yearValues) {
		long numberOfIndicators = yearValues.stream().map(YearValue::getIndicator).distinct().count();
		DefaultStatisticalCategoryDataset dataset = new DefaultStatisticalCategoryDataset();
		double max = yearValues.stream().mapToDouble(YearValue::getValue).max().getAsDouble();
		if (max == 0) {
		}
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
		JFreeChart barChart = ChartFactory.createBarChart(title, "Year", divderTitle, dataset, PlotOrientation.VERTICAL,
				true, true, false);

		fixDomainAxisIfThereIsNoValue(max, barChart);
		return new ChartPanel(barChart);
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
