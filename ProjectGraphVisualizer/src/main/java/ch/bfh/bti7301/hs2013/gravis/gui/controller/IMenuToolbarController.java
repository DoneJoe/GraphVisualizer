package ch.bfh.bti7301.hs2013.gravis.gui.controller;

import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import ch.bfh.bti7301.hs2013.gravis.gui.dialog.ExitDialogAdapter;
import ch.bfh.bti7301.hs2013.gravis.gui.dialog.FileChooserAdapter;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IMenuToolbarController extends ActionListener, WindowListener,
		GravisObservable {

	/**
	 * This enum constants are usefull to distinguish between different kinds of
	 * ui events.
	 * 
	 * @author Patrick Kofmel (kofmp1@bfh.ch)
	 * 
	 */
	public enum EventSource {
		NEW_DIR_GRAPH, NEW_UNDIR_GRAPH, OPEN_GRAPH, SAVE_GRAPH, GRAPH_PROPERTIES, EXIT
	}

	/**
	 * @param fileChooserAdapter
	 */
	public abstract void setFileChooserAdapter(
			FileChooserAdapter fileChooserAdapter);

	/**
	 * @param exitDialogAdapter
	 */
	public abstract void setExitDialogAdapter(
			ExitDialogAdapter exitDialogAdapter);

}
