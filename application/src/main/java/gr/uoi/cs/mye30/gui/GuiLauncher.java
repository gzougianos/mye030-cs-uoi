package gr.uoi.cs.mye30.gui;

import javax.swing.UIManager;

import gr.uoi.cs.mye30.EntityManager;
import gr.uoi.cs.mye30.EntityManagerImpl;
import gr.uoi.cs.mye30.gui.controller.AskDatabaseInformationController;
import gr.uoi.cs.mye30.gui.controller.MainViewController;
import gr.uoi.cs.mye30.gui.controller.SelectIndicatorsViewController;
import gr.uoi.cs.mye30.gui.view.AskDatabaseInformationView;
import gr.uoi.cs.mye30.gui.view.MainView;

public class GuiLauncher {

	public GuiLauncher() {
		installLookAndFeel();
		EntityManager entityHolder = new EntityManagerImpl();

		AskDatabaseInformationView askDatabaseInformationView = new AskDatabaseInformationView();
		MainView mainView = new MainView();

		new AskDatabaseInformationController(askDatabaseInformationView, entityHolder, mainView);
		new MainViewController(mainView, entityHolder);
		new SelectIndicatorsViewController(mainView.getSelectIndicatorsView(), entityHolder);

		askDatabaseInformationView.setVisible(true);
		mainView.getRefreshButton().doClick();
	}

	public static void installLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
