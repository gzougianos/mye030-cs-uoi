package gr.uoi.cs.mye30.gui;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CustomDocumentListener implements DocumentListener {
	private Runnable r;

	public CustomDocumentListener(Runnable r) {
		this.r = r;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		r.run();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		r.run();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		r.run();
	}
}
