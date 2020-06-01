package gr.uoi.cs.mye30.gui;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JComponent;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class FirstTimeShownListener implements ComponentListener, AncestorListener {
	private Runnable r;

	public FirstTimeShownListener(Runnable r) {
		this.r = r;
	}

	@Override
	public void componentResized(ComponentEvent e) {
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
		r.run();
		e.getComponent().removeComponentListener(this);
		if (e.getComponent() instanceof JComponent)
			((JComponent) e.getComponent()).removeAncestorListener(this);
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

	@Override
	public void ancestorAdded(AncestorEvent event) {
		r.run();
		event.getComponent().removeComponentListener(this);
		event.getComponent().removeAncestorListener(this);
	}

	@Override
	public void ancestorRemoved(AncestorEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ancestorMoved(AncestorEvent event) {
		// TODO Auto-generated method stub

	}

}
