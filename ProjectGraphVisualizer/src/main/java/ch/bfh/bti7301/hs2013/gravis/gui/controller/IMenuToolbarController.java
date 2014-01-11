package ch.bfh.bti7301.hs2013.gravis.gui.controller;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.WindowListener;

import ch.bfh.bti7301.hs2013.gravis.gui.dialog.ConfirmDialogAdapter;
import ch.bfh.bti7301.hs2013.gravis.gui.dialog.FileChooserAdapter;
import ch.bfh.bti7301.hs2013.gravis.gui.dialog.GraphPropertyDialogFactory;
import ch.bfh.bti7301.hs2013.gravis.gui.dialog.MessageDialogAdapter;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IMenuToolbarController extends ActionListener, WindowListener, ItemListener,
		GravisObservable {

	/**
	 * This enum constants are usefull to distinguish between different kinds of
	 * ui events.
	 * 
	 * @author Patrick Kofmel (kofmp1@bfh.ch)
	 * 
	 */
	public static enum EventSource {
		NEW_DIR_GRAPH, NEW_UNDIR_GRAPH, OPEN_GRAPH, SAVE_GRAPH, GRAPH_PROPERTY, EXIT,
		NEW_CALC, MODE, ALGORITHM
	}

	/**
	 * @param fileChooserAdapter
	 */
	public abstract void setFileChooserAdapter(
			FileChooserAdapter fileChooserAdapter);

	/**
	 * @param confirmDialogAdapter
	 */
	public abstract void setConfirmDialogAdapter(
			ConfirmDialogAdapter confirmDialogAdapter);

	/**
	 * @param messageDialogAdapter
	 */
	public abstract void setMessageDialogAdapter(
			MessageDialogAdapter messageDialogAdapter);

	/**
	 * @param graphPropertyDialogFactory
	 */
	public abstract void setGraphPropertyDialogFactory(
			GraphPropertyDialogFactory graphPropertyDialogFactory);

}
