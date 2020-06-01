package gr.uoi.cs.mye30.gui;

import java.awt.Component;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GuiUtils {

	public static void showError(Component parentComponent, Throwable t) {
		String text = stacktraceAsString(t);
		JTextArea textArea = new JTextArea(text);
		textArea.setFont(new JTextField().getFont());
		textArea.setFocusable(false);
		textArea.setEditable(false);
		textArea.setColumns(100);
		textArea.setRows(20);

		JOptionPane.showMessageDialog(parentComponent, new JScrollPane(textArea), "Error", JOptionPane.ERROR_MESSAGE);
	}

	private static String stacktraceAsString(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString();
	}
}
