package gr.uoi.cs.mye30.gui.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import gr.uoi.cs.mye30.entity.Indicator;

@SuppressWarnings("serial")
public class SelectIndicatorsView extends JPanel {
	private JList<Indicator> allIndicatorsList;
	private JList<Indicator> selectedIndicatorsList;
	private JTextField searchField;

	public SelectIndicatorsView() {
		super(new GridLayout(0, 2, 10, 10));

		allIndicatorsList = new JList<>(new DefaultListModel<>());
		allIndicatorsList.setCellRenderer(new IndicatorRenderer());
		allIndicatorsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		allIndicatorsList.setVisibleRowCount(35);

		selectedIndicatorsList = new JList<>(new DefaultListModel<>());
		selectedIndicatorsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectedIndicatorsList.setCellRenderer(new IndicatorRenderer());

		searchField = new JTextField(45);

		JTextField dummyField = new JTextField();
		dummyField.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		dummyField.setFocusable(false);
		dummyField.setEditable(false);

		JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
		leftPanel.setBorder(new TitledBorder("Available Indicators"));
		leftPanel.add(searchField, BorderLayout.PAGE_START);
		leftPanel.add(new JScrollPane(allIndicatorsList), BorderLayout.CENTER);

		JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
		rightPanel.setBorder(new TitledBorder("Selected Indicators"));
		rightPanel.add(new JScrollPane(selectedIndicatorsList), BorderLayout.CENTER);

		add(leftPanel);
		add(rightPanel);
	}

	public JList<Indicator> getAllIndicatorsList() {
		return allIndicatorsList;
	}

	public JList<Indicator> getSelectedIndicatorsList() {
		return selectedIndicatorsList;
	}

	public Collection<Indicator> getSelectedIndicators() {
		//@formatter:off
		return IntStream.range(0, selectedIndicatorsList.getModel().getSize())
				.mapToObj(selectedIndicatorsList.getModel()::getElementAt)
				.collect(Collectors.toList());
		//@formatter:on
	}

	public JTextField getSearchField() {
		return searchField;
	}

	private class IndicatorRenderer extends DefaultListCellRenderer {
		public IndicatorRenderer() {
			super();
		}

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof Indicator) {
				Indicator indicator = (Indicator) value;
				label.setText(String.format("[%s] %s", indicator.getCode(), indicator.getName()));
			}

			return label;
		}
	};
}
