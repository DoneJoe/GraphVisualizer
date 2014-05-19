package ch.bfh.ti.gravis;

import java.awt.EventQueue;
import java.io.PrintWriter;
import java.io.StringWriter;

import ch.bfh.ti.gravis.core.CoreFactory;
import ch.bfh.ti.gravis.gui.GuiFactory;
import ch.bfh.ti.gravis.gui.dialog.MessageDialogAdapter;

/**
 * This is the main class of the GraphVisualizer application.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class GravisStartup {
	private final static String STARTUP_ERROR = "Fehler beim Starten der Applikation!";
	private final static String TITLE = "Graph Visualizer";

	/**
	 * The application entry point with the main method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					GuiFactory.createGUI(CoreFactory.createCore());
				} catch (Throwable e) {
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					new MessageDialogAdapter().showErrorMessageDialog(
							STARTUP_ERROR, sw.toString(), TITLE);

					// abnormal termination of application
					System.exit(1);
				}
			}
		});
	}

}
