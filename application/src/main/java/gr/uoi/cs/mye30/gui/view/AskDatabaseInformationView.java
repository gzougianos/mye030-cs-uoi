package gr.uoi.cs.mye30.gui.view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class AskDatabaseInformationView extends JFrame {
	private static final int LABELS_WIDTH = 100;
	private JTextField addressTextField;
	private JTextField portTextField;
	private JTextField databaseNameField;
	private JTextField usernameTextField;
	private JTextField passwordTextField;
	private JButton connectButton;
	private JButton exitButton;

	public AskDatabaseInformationView() {
		super("mye030");
		setLayout(new GridLayout(0, 1));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel contentPane = (JPanel) getContentPane();
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		addressTextField = new JTextField("localhost", 25);
		portTextField = new JTextField("3306", 25);
		databaseNameField = new JTextField("mydb", 25);
		usernameTextField = new JTextField("root", 25);
		passwordTextField = new JTextField("root", 25);

		connectButton = new JButton("Connect");
		exitButton = new JButton("Exit");

		add(createRow("Address:", addressTextField));
		add(createRow("Port:", portTextField));
		add(createRow("Database:", databaseNameField));
		add(createRow("Username:", usernameTextField));
		add(createRow("Password:", passwordTextField));
		add(createButtonsRow());

		pack();
		setLocationRelativeTo(null);
		setMinimumSize(getPreferredSize());
	}

	private Component createButtonsRow() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.add(connectButton);
		panel.add(exitButton);
		return panel;
	}

	private JPanel createRow(String textLabel, Component component) {
		String widthParagraph = "<body style='width:" + LABELS_WIDTH + "px'>";
		JPanel panel = new JPanel(new FlowLayout());
		panel.add(new JLabel("<html>" + widthParagraph + textLabel));
		panel.add(component);
		return panel;
	}

	public JTextField getAddressTextField() {
		return addressTextField;
	}

	public JTextField getPortTextField() {
		return portTextField;
	}

	public JTextField getDatabaseNameField() {
		return databaseNameField;
	}

	public JTextField getUsernameTextField() {
		return usernameTextField;
	}

	public JTextField getPasswordTextField() {
		return passwordTextField;
	}

	public JButton getConnectButton() {
		return connectButton;
	}

	public JButton getExitButton() {
		return exitButton;
	}

}
