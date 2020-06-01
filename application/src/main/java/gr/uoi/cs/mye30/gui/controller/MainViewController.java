package gr.uoi.cs.mye30.gui.controller;

import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gr.uoi.cs.mye30.EntityManager;
import gr.uoi.cs.mye30.entity.Country;
import gr.uoi.cs.mye30.entity.Indicator;
import gr.uoi.cs.mye30.entity.YearValue;
import gr.uoi.cs.mye30.gui.FirstTimeShownListener;
import gr.uoi.cs.mye30.gui.GuiUtils;
import gr.uoi.cs.mye30.gui.chart.ChartPanelFactory;
import gr.uoi.cs.mye30.gui.view.MainView;

public class MainViewController {
	private MainView mainView;
	private EntityManager entityManager;

	public MainViewController(MainView mainView, EntityManager entityManager) {
		this.mainView = mainView;
		this.entityManager = entityManager;

		mainView.addComponentListener(new FirstTimeShownListener(() -> initCountryMenu()));
		initMenus();
		mainView.getRefreshButton().addActionListener(e -> refreshChart());
	}

	private void initMenus() {
		mainView.getIndicatorsMenuItem().addActionListener(e -> showSelectIndicatorsDialog());
	}

	private void showSelectIndicatorsDialog() {
		JOptionPane.showMessageDialog(mainView, mainView.getSelectIndicatorsView(), "Select Indicators",
				JOptionPane.PLAIN_MESSAGE);
	}

	private void initCountryMenu() {
		mainView.getCountriesMenu().showCountries(entityManager.getCountries());
	}

	private void refreshChart() {
		Collection<Indicator> selectedIndicators = mainView.getSelectIndicatorsView().getSelectedIndicators();
		if (selectedIndicators.isEmpty()) {
			showMessageToMainPanel("No indicators selected.");
			return;
		}

		Collection<Country> selectedCountries = mainView.getCountriesMenu().getSelectedCountries();
		if (selectedCountries.isEmpty()) {
			showMessageToMainPanel("No countries selected.");
			return;
		}

		int highSelectedYear = mainView.getHighSelectedYear();
		int lowSelectedYear = mainView.getLowSelectedYear();
		try {
			List<YearValue> yearValues = entityManager.getYearValues(selectedCountries, selectedIndicators,
					lowSelectedYear, highSelectedYear);
			if (yearValues.isEmpty()) {
				showMessageToMainPanel("No values found.");
				return;
			}
			mainView.setMainPanel(ChartPanelFactory.create(mainView.getSelectedChartType(), yearValues, lowSelectedYear,
					highSelectedYear));
		} catch (SQLException e) {
			e.printStackTrace();
			GuiUtils.showError(mainView, e);
		}
	}

	private void showMessageToMainPanel(String text) {
		JPanel panel = new JPanel(new FlowLayout());
		panel.add(new JLabel(text));
		mainView.setMainPanel(panel);

	}
}
