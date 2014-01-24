package ch.bfh.bti7301.hs2013.gravis;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

import ch.bfh.bti7301.hs2013.gravis.core.CoreFactory;
import ch.bfh.bti7301.hs2013.gravis.gui.GuiFactory;

/**
 * This is the main class of the GraphVisualizer application.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class GravisStartup {

	private final static String STARTUP_ERROR = "Fehler beim Starten der Applikation:%s%s";
	private final static String TITLE = "Graph Visualizer";

	/**
	 * Program starter
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					GuiFactory.createGUI(CoreFactory.createCore());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, String.format(
							STARTUP_ERROR, System.lineSeparator(),
							e.getMessage()), TITLE, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

}
