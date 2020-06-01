package gr.uoi.cs.mye30.gui.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import gr.uoi.cs.mye30.EntityManager;
import gr.uoi.cs.mye30.entity.Indicator;
import gr.uoi.cs.mye30.gui.CustomDocumentListener;
import gr.uoi.cs.mye30.gui.FirstTimeShownListener;
import gr.uoi.cs.mye30.gui.view.SelectIndicatorsView;

public class SelectIndicatorsViewController {
	private SelectIndicatorsView view;
	private EntityManager entityHolder;

	public SelectIndicatorsViewController(SelectIndicatorsView view, EntityManager entityHolder) {
		this.view = view;
		this.entityHolder = entityHolder;
		view.addAncestorListener(new FirstTimeShownListener(() -> showAvailableIndicators()));
		initSearchField();
		initDoubleClickListeners();
	}

	private void initDoubleClickListeners() {
		view.getAllIndicatorsList().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getClickCount() == 2) {
					Indicator selectedValue = view.getAllIndicatorsList().getSelectedValue();
					getModel(view.getSelectedIndicatorsList()).addElement(selectedValue);
				}
			}
		});
		view.getSelectedIndicatorsList().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getClickCount() == 2) {
					Indicator selectedValue = view.getSelectedIndicatorsList().getSelectedValue();
					getModel(view.getSelectedIndicatorsList()).removeElement(selectedValue);
				}
			}
		});
	}

	private void initSearchField() {
		view.getSearchField().getDocument().addDocumentListener(new CustomDocumentListener(() -> searchIndicators()));
	}

	private void searchIndicators() {
		String searchText = view.getSearchField().getText().trim().toLowerCase();
		DefaultListModel<Indicator> model = getModel(view.getAllIndicatorsList());
		model.removeAllElements();
		entityHolder.getIndicators().stream().filter(indicator -> indicator.getCode().toLowerCase().contains(searchText)
				|| indicator.getName().toLowerCase().contains(searchText)).forEach(model::addElement);
	}

	private void showAvailableIndicators() {
		DefaultListModel<Indicator> model = getModel(view.getAllIndicatorsList());
		entityHolder.getIndicators().forEach(model::addElement);
	}

	private DefaultListModel<Indicator> getModel(JList<Indicator> jlist) {
		return (DefaultListModel<Indicator>) jlist.getModel();
	}
}
