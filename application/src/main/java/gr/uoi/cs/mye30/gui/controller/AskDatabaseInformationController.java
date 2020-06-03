package gr.uoi.cs.mye30.gui.controller;

import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import gr.uoi.cs.mye30.Database;
import gr.uoi.cs.mye30.EntityManager;
import gr.uoi.cs.mye30.gui.ComponentSupport;
import gr.uoi.cs.mye30.gui.GuiUtils;
import gr.uoi.cs.mye30.gui.OnlyDigitsDocumentFilter;
import gr.uoi.cs.mye30.gui.view.AskDatabaseInformationView;
import gr.uoi.cs.mye30.gui.view.MainView;

public class AskDatabaseInformationController {
	private static final String CONNECTION_URL_BASE = "jdbc:mysql://%s:%s/%s?user=%s&password=%s";
	private AskDatabaseInformationView view;
	private EntityManager entityHolder;
	private MainView mainView;

	public AskDatabaseInformationController(AskDatabaseInformationView view, EntityManager entityHolder,
			MainView mainView) {
		this.view = view;
		this.entityHolder = entityHolder;
		this.mainView = mainView;
		makePortTextFieldAcceptOnlyNumbers();
		initConnectButton();
		addConnectListenerToAllTextFields();
		initExitButton();
	}

	private void initExitButton() {
		view.getExitButton().addActionListener(e -> {
			int ans = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
			if (ans == JOptionPane.OK_OPTION) {
				System.exit(0);
			}
		});
	}

	private void initConnectButton() {
		view.getConnectButton().addActionListener(e -> {
			String url = createUrlBasedOnViewFields();
			try {
				Database.connectTo(url);
				entityHolder.loadEntities();
				view.dispose();
				mainView.setVisible(true);
			} catch (SQLException e1) {
				GuiUtils.showError(view, e1);
			}
		});
	}

	private void addConnectListenerToAllTextFields() {
		ComponentSupport.getChildren(JTextField.class, view)
				.forEach(tf -> tf.addActionListener(e -> view.getConnectButton().doClick()));
	}

	private String createUrlBasedOnViewFields() {
		//@formatter:off
		return String.format(CONNECTION_URL_BASE, view.getAddressTextField().getText().trim(),
				view.getPortTextField().getText().trim(),
				view.getDatabaseNameField().getText().trim(),
				view.getUsernameTextField().getText().trim(),
				view.getPasswordTextField().getText().trim());
		//@formatter:on
	}

	private void makePortTextFieldAcceptOnlyNumbers() {
		AbstractDocument document = (AbstractDocument) view.getPortTextField().getDocument();
		document.setDocumentFilter(new OnlyDigitsDocumentFilter());
	}
}
