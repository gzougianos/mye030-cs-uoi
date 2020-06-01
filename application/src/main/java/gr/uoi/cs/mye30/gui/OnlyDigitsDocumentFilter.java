package gr.uoi.cs.mye30.gui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class OnlyDigitsDocumentFilter extends DocumentFilter {
	public OnlyDigitsDocumentFilter() {
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
			throws BadLocationException {
		super.replace(fb, offset, length, filter(text), attrs);
	}

	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
			throws BadLocationException {
		super.insertString(fb, offset, filter(string), attr);
	}

	private String filter(String text) {
		StringBuilder sb = new StringBuilder();
		text.chars().filter(Character::isDigit).forEach(sb::append);
		return sb.toString();
	}
}
