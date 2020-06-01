package gr.uoi.cs.mye30;

import javax.swing.SwingUtilities;

import gr.uoi.cs.mye30.gui.GuiLauncher;

public class Main {

	public static void main(String[] args) {
		System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tH:%1$tM:%1$tS %4$-1s %5$s%6$s%n");
		SwingUtilities.invokeLater(GuiLauncher::new);
	}

}
