package gr.uoi.cs.mye30.gui.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import gr.uoi.cs.mye30.gui.chart.ChartType;

@SuppressWarnings("serial")
public class MainView extends JFrame implements ChangeListener {
	private JPanel chartContainerPanel;
	private CountriesMenu countriesMenu;
	private JMenuItem indicatorsMenuItem;
	private SelectIndicatorsView selectIndicatorsView;
	private JCheckBoxMenuItem timelineChartMenuItem;
	private JCheckBoxMenuItem scatterChartMenuItem;
	private JCheckBoxMenuItem barChartMenuItem;
	private JSpinner yearLowSpinner;
	private JSpinner yearHighSpinner;
	private JButton refreshButton;

	public MainView() {
		super("mye030");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		chartContainerPanel = new JPanel(new BorderLayout());
		add(chartContainerPanel, BorderLayout.CENTER);

		add(createTopPanel(), BorderLayout.PAGE_START);

		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Chart");

		countriesMenu = new CountriesMenu();
		indicatorsMenuItem = new JMenuItem("Indicators");

		timelineChartMenuItem = new JCheckBoxMenuItem("Timeline");
		timelineChartMenuItem.doClick();
		scatterChartMenuItem = new JCheckBoxMenuItem("Scatter");
		barChartMenuItem = new JCheckBoxMenuItem("Bar");

		menu.add(countriesMenu);
		menu.add(indicatorsMenuItem);
		menu.addSeparator();

		menu.add(timelineChartMenuItem);
		menu.add(scatterChartMenuItem);
		menu.add(barChartMenuItem);
		createButtonGroup();

		menuBar.add(menu);
		setJMenuBar(menuBar);

		selectIndicatorsView = new SelectIndicatorsView();

		setMinimumSize(new Dimension(700, 700));
		pack();
		setLocationRelativeTo(null);

	}

	private Component createTopPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(new JLabel("From: "));
		panel.add(Box.createRigidArea(new Dimension(10, 10)));

		yearLowSpinner = new JSpinner(new SpinnerNumberModel(1960, 1960, LocalDate.now().getYear(), 1));
		yearLowSpinner.addChangeListener(this);
		panel.add(yearLowSpinner);

		panel.add(Box.createRigidArea(new Dimension(10, 10)));

		panel.add(new JLabel("To: "));
		panel.add(Box.createRigidArea(new Dimension(10, 10)));

		yearHighSpinner = new JSpinner(new SpinnerNumberModel(1960, 1960, LocalDate.now().getYear() - 1, 1));
		yearHighSpinner.addChangeListener(this);
		panel.add(yearHighSpinner);

		panel.add(Box.createHorizontalGlue());

		refreshButton = new JButton("Refresh Chart");
		panel.add(refreshButton);

		return panel;
	}

	public JButton getRefreshButton() {
		return refreshButton;
	}

	public int getLowSelectedYear() {
		return (int) yearLowSpinner.getValue();
	}

	public int getHighSelectedYear() {
		return (int) yearHighSpinner.getValue();
	}

	private void createButtonGroup() {
		ButtonGroup group = new ButtonGroup();
		group.add(timelineChartMenuItem);
		group.add(scatterChartMenuItem);
		group.add(barChartMenuItem);
	}

	public ChartType getSelectedChartType() {
		if (timelineChartMenuItem.isSelected())
			return ChartType.TIMELINE;
		if (scatterChartMenuItem.isSelected())
			return ChartType.SCATTER;
		if (barChartMenuItem.isSelected())
			return ChartType.BAR;
		throw new RuntimeException();
	}

	public SelectIndicatorsView getSelectIndicatorsView() {
		return selectIndicatorsView;
	}

	public CountriesMenu getCountriesMenu() {
		return countriesMenu;
	}

	public JMenuItem getIndicatorsMenuItem() {
		return indicatorsMenuItem;
	}

	public void setMainPanel(JPanel chartPanel) {
		chartContainerPanel.removeAll();
		chartContainerPanel.add(chartPanel);
		chartContainerPanel.revalidate();
		chartContainerPanel.repaint();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		SpinnerNumberModel highModel = (SpinnerNumberModel) yearHighSpinner.getModel();
		highModel.setMinimum(getLowSelectedYear());
		highModel.setValue(Math.max(getHighSelectedYear(), (int) highModel.getMinimum()));
	}

}
