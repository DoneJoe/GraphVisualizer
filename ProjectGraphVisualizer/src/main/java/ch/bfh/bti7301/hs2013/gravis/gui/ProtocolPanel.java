package ch.bfh.bti7301.hs2013.gravis.gui;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;

import ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph;
import ch.bfh.bti7301.hs2013.gravis.gui.controller.IMenuToolbarController;

import javax.swing.JTextArea;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class ProtocolPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 4198819175596919745L;

	private JTextArea textArea;

	/**
	 * Create the panel.
	 */
	public ProtocolPanel() {
		// TODO enable scrollbar
		
		this.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		this.add(scrollPane, BorderLayout.CENTER);

		this.textArea = new JTextArea();
		this.textArea.setEditable(false);
		scrollPane.setViewportView(this.textArea);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof IMenuToolbarController && arg instanceof IGravisGraph) {
			IGravisGraph graph = (IGravisGraph) arg;

			this.textArea.append(graph.getName() + System.lineSeparator()
					+ graph.getDescription() + System.lineSeparator()
					+ System.lineSeparator());
		}
	}

}
